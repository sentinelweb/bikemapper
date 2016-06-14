package net.robmunro.gpstest.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import net.robmunro.gpstest.model.CompassData;


public class SensorListeners {
    private static final float LOC_UPD_DIST_MIN = 10.0f;
    private static final long LOC_UPD_TIME_MIN = 20000L;
    static String TAG = "SensorListeners";
    private final Context _cxt;
    CompassData compassData = null;
    MylocationListener mylocationlistener = null;
    OnCompleteListener<SensorListeners> clientLocationListener;
    private final LocationManager lm;
    private final SensorManager mSensorManager;
    public LatLng currLoc;

    public SensorListeners(final Context cxt, final CompassData c) {
        _cxt = cxt;
        compassData = c;
        lm = (LocationManager) cxt.getSystemService(Context.LOCATION_SERVICE);
        mSensorManager = (SensorManager) cxt.getSystemService(Context.SENSOR_SERVICE);

    }

    public class MylocationListener implements LocationListener {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d(TAG, "onLocationChanged start...:" + location);
            if (location != null) {
                //Log.d(TAG, "onLocationChanged loc...");
                Log.d(TAG, Location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + "o " + Location.convert(location.getLatitude(), Location.FORMAT_MINUTES) + "'");
                Log.d(TAG, Location.convert(location.getLongitude(), Location.FORMAT_DEGREES) + "o " + Location.convert(location.getLongitude(), Location.FORMAT_MINUTES) + "'");
                currLoc = new LatLng((int) (location.getLatitude()), (int) (location.getLongitude()));
                //setCurrentLocation(currLoc);
                if (compassData != null) {
                    compassData.setCurrentLocation(currLoc);
                    compassData.setBearingDirection(location.getBearing());
                }
                if (clientLocationListener != null) {
                    clientLocationListener.onComplete(SensorListeners.this);
                }
                //stopLocListeneing()
                Log.d(TAG, "onLocationChanged finished...");
            }
        }

        @Override
        public void onProviderDisabled(final String provider) {
        }

        @Override
        public void onProviderEnabled(final String provider) {
        }

        @Override
        public void onStatusChanged(final String provider, final int status, final Bundle extras) {
        }
    }


    private final SensorEventListener orientListener = new SensorEventListener() {
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        }

        @Override
        public void onSensorChanged(final SensorEvent event) {
            //Log.d(LOCATION_TEST_TAG, "Sensor event");
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                if (compassData != null) {
                    compassData.setOrientationValues(event.values);
                    //System.arraycopy(event.values, 0, compass.getOritentationValues(), 0, event.values.length);
                }
            }
        }
    };
    private final SensorEventListener magneticListener = new SensorEventListener() {
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        }

        @Override
        public void onSensorChanged(final SensorEvent event) {
            //Log.d(LOCATION_TEST_TAG, "MagSensor event");
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                if (compassData != null) {
                    compassData.setMagneticFieldValues(event.values);
                    //System.arraycopy(event.values, 0, compass.getMagneticFieldValues(), 0, event.values.length);
                }
            }
        }
    };
    private final SensorEventListener accListener = new SensorEventListener() {
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        }

        @Override
        public void onSensorChanged(final SensorEvent event) {
            //Log.d(LOCATION_TEST_TAG, "Sensor event");
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (compassData != null) {
                    compassData.setAccelFieldValues(event.values);
                    //System.arraycopy(event.values, 0, compass.getOritentationValues(), 0, event.values.length);
                }
            }
        }
    };

    public void startLocListening() {
        mylocationlistener = new MylocationListener();
        if (ActivityCompat.checkSelfPermission(_cxt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(_cxt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(_cxt, "No perms for location", Toast.LENGTH_SHORT).show();
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOC_UPD_TIME_MIN, LOC_UPD_DIST_MIN, mylocationlistener);// 10 metres or 20 secs
        mSensorManager.registerListener(orientListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(magneticListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(accListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }


    public void stopLocListeneing() {
        if (ActivityCompat.checkSelfPermission(_cxt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(_cxt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(_cxt, "No perms for location", Toast.LENGTH_SHORT).show();
            return;
        }
        lm.removeUpdates(mylocationlistener);
        mSensorManager.unregisterListener(orientListener);
        mSensorManager.unregisterListener(magneticListener);
        mSensorManager.unregisterListener(accListener);
    }


    public OnCompleteListener<SensorListeners> getClientLocationListener() {
        return clientLocationListener;
    }


    public void setClientLocationListener(
            final OnCompleteListener<SensorListeners> clientLocationListener) {
        this.clientLocationListener = clientLocationListener;
    }


}
