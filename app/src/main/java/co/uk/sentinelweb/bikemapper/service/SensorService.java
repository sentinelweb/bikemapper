package co.uk.sentinelweb.bikemapper.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import co.uk.sentinelweb.bikemapper.sensors.LocationHandlerKt;

public class SensorService extends Service {
    public static final int LISTENING_NOTIFICATION_ID = 1;
    public static final String START_LOCATION_LISTENING = "start.location";
    public static final String STOP_LOCATION_LISTENING = "stop.location";
    public final LocationHandlerKt _locationHandler;

    public SensorService() {
        _locationHandler = new LocationHandlerKt(this);
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (intent != null) {
            if (START_LOCATION_LISTENING.equals(intent.getAction())) {
                // _locationHandler.startDefault();
            } else if (STOP_LOCATION_LISTENING.equals(intent.getAction())) {
                // _locationHandler.stop();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //_locationHandler.stop();
    }

    private void showNotification() {
        final Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Listening ...")
                .build();
        startForeground(LISTENING_NOTIFICATION_ID, notification);
    }
}
