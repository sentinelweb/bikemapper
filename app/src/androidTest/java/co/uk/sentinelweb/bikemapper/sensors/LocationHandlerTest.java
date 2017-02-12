package co.uk.sentinelweb.bikemapper.sensors;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * use this command to give app permission
 * adb shell pm grant co.uk.sentinelweb.bikemapper android.permission.ACCESS_FINE_LOCATION
 * see http://stackoverflow.com/questions/32787234/how-to-manage-runtime-permissions-android-marshmallow-espresso-tests#32798532
 * Created by robert on 11/02/2017.
 */
public class LocationHandlerTest {

    public static final int INITIAL_STATUS = -1;
    LocationHandler _sut;
    int locationListenerStatus = INITIAL_STATUS;
    private final LocationListener _testLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            locationListenerStatus = LocationProvider.AVAILABLE;
            System.out.println(String.format("onLocationChanged.locationListenerStatus: %d", locationListenerStatus));
        }

        @Override
        public void onStatusChanged(final String provider, final int status, final Bundle extras) {
            locationListenerStatus = status;
            System.out.println(String.format("onStatusChanged.locationListenerStatus: %d", locationListenerStatus));
        }

        @Override
        public void onProviderEnabled(final String provider) {

        }

        @Override
        public void onProviderDisabled(final String provider) {

        }
    };


    @Before
    public void setUp() throws Exception {
        _sut = LocationHandler.from(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        if (_sut != null) {
            _sut.stop(_testLocationListener);
        }
    }

    @Test
    public void start() throws Exception {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> _sut.start(_testLocationListener));

        wait4Assert(LocationProvider.AVAILABLE, 30000);
    }

//    @Test
//    public void stop() throws Exception {
//        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> _sut.start(_testLocationListener));
//        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> _sut.stop(_testLocationListener));
//
//
//        wait4Assert(LocationProvider., 1000);
//    }

    private void wait4Assert(final int status, final int timeout) {
        final long startTime = SystemClock.uptimeMillis();
        while (locationListenerStatus == INITIAL_STATUS &&
                (SystemClock.uptimeMillis() - startTime) < timeout) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("locationListenerStatus: %d, time: %d", locationListenerStatus, (SystemClock.uptimeMillis() - startTime)));
        }
        Assert.assertEquals(status, locationListenerStatus);
    }


}