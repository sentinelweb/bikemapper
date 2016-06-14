package net.robmunro.gpstest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import net.robmunro.gpstest.util.DistanceCalc;
import net.robmunro.gpstest.util.GeoUtils;
import net.robmunro.gpstest.util.MyTimer;
import net.robmunro.gpstest.util.OnCompleteListener;
import net.robmunro.gpstest.util.SensorListeners;

import java.util.Timer;

public class RadarService extends Service {
    public static final String ACTION_START_LISTENING = "net.robmunro.gpstest.ACTION_START_LISTENING";
    public static final String ACTION_STOP_LISTENING = "net.robmunro.gpstest.ACTION_STOP_LISTENING";
    public static final String ACTION_START_BEEPING = "net.robmunro.gpstest.ACTION_START_BEEPING";
    public static final String ACTION_STOP_BEEPING = "net.robmunro.gpstest.ACTION_STOP_BEEPING";

    public static final int NONE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TURN = 3;

    public static final int REFERENCE_TIME = 5 * 1000;//10sec
    public static final int BEEP_TIME = 100;//10sec

    Timer updateTimer = null;
    Timer beepTimer = null;

    int timeTillNextBeep = -1;
    int beepType = NONE;
    boolean beepActive = false;
    // refernce time sets the default scaling from the start of the journey.


    Vibrator v = null;

    SensorListeners sensorListeners;

    OnCompleteListener<SensorListeners> clientLocationListener = new OnCompleteListener<SensorListeners>() {
        @Override
        public void onComplete(final SensorListeners o) {
            if (LocationTestActivity.clientLocationListener != null) {
                LocationTestActivity.clientLocationListener.onComplete(sensorListeners);
            }
        }
    };
    OnCompleteListener<Integer> beepListener = new OnCompleteListener<Integer>() {
        @Override
        public void onComplete(final Integer o) {
            if (LocationTestActivity.beepListener != null) {
                LocationTestActivity.beepListener.onComplete(o);
            }
        }
    };

    @Override
    public IBinder onBind(final Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();


    }

    @Override
    public void onStart(final Intent intent, final int startId) {
        super.onStart(intent, startId);
        final String action = intent.getAction();
        if (ACTION_START_LISTENING.equals(action)) {
            if (sensorListeners == null) {
                sensorListeners = new SensorListeners(this, LocationTestActivity.compassData);
                sensorListeners.setClientLocationListener(clientLocationListener);
                sensorListeners.startLocListening();
            }
        } else if (ACTION_STOP_LISTENING.equals(action)) {
            if (sensorListeners != null) {
                sensorListeners.stopLocListeneing();
                sensorListeners = null;
            }
        } else if (ACTION_START_BEEPING.equals(action)) {
            startBeeping();
        } else if (ACTION_STOP_BEEPING.equals(action)) {
            stopBeeping();
        }
    }

    private void startBeeping() {
        beepActive = true;
        beepListener.onComplete(LEFT);
        updateTimer = MyTimer.activateTimer(this, "updateBeep", 1000, false, false);
        calculateBeepTime();
        if (beepType > NONE) {
            beepTimer = MyTimer.activateTimer(this, "beep", timeTillNextBeep, false, true);
        }
    }

    private void stopBeeping() {
        beepActive = false;
        beepListener.onComplete(NONE);
        if (updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
        if (beepTimer != null) {
            beepTimer.cancel();
            beepTimer = null;
        }
    }

    private void calculateBeepTime() {
        timeTillNextBeep = -1;
        beepType = NONE;
        if (LocationTestActivity.directions != null) {
            final LatLng currentLocation = LocationTestActivity.compassData.getCurrentLocation();
            final LatLng journeyEnd = LocationTestActivity.directions.getJourneyEnd();
            final LatLng journeyStart = LocationTestActivity.directions.getJourneyStart();
            if (currentLocation != null && journeyEnd != null && journeyStart != null) {
                final float distCurrEnd = (float) DistanceCalc.calculateDistance(currentLocation, journeyEnd, DistanceCalc.METRES);
                final float distStartEnd = (float) DistanceCalc.calculateDistance(journeyStart, journeyEnd, DistanceCalc.METRES);
                double bearingCurrEnd = GeoUtils.bearing(currentLocation, journeyEnd);
                bearingCurrEnd += LocationTestActivity.compassData.north;// subtract the bearing
                bearingCurrEnd %= 360;
                //have to compensate for the rotational position of the phone.
                final int beepTime = (int) (REFERENCE_TIME * (distCurrEnd / distStartEnd));
                this.timeTillNextBeep = beepTime;
                int bearingLeftOrRight = NONE;
                if (bearingCurrEnd < 180) {
                    bearingLeftOrRight = RIGHT;
                } else {
                    bearingLeftOrRight = LEFT;
                }
                beepType = bearingLeftOrRight;
                beepListener.onComplete(bearingLeftOrRight);
                Log.d(LocationTestActivity.LOG_TAG, "c->e:" + distCurrEnd + "m s->e:" + distStartEnd + "m bearing:" + bearingCurrEnd);
            } else {
                Log.d(LocationTestActivity.LOG_TAG, "no data");
            }
        } else {
            Log.d(LocationTestActivity.LOG_TAG, "no data");
        }
    }

    public void updateBeep() {
        calculateBeepTime();
    }

    public void beep() {

        doBeep(beepType);
        if (beepActive) {
            beepTimer = MyTimer.activateTimer(this, "beep", timeTillNextBeep, false, true);
        }
    }

    public void doBeep(final int type) {
        vibrateShort();
        if (type == RIGHT) {// double beep
            MyTimer.activateTimer(this, "vibrateShort", BEEP_TIME * 2, false, true);
        }
    }

    public void vibrateShort() {
        v.vibrate(BEEP_TIME);
    }

    public void vibrateLong() {
        v.vibrate(BEEP_TIME * 2);
    }
}
