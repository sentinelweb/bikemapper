package co.uk.sentinelweb.bikemapper.sensors;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.util.converter.LocationConverter;
import rx.Observable;
import rx.subscriptions.Subscriptions;

public class LocationHandler {

    public static LocationHandler from(final Context c) {
        final LocationManager locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        return new LocationHandler(locationManager);
    }

    Long minTime = 10000L;
    Float minDist = 0f;//    200.0f

    private final LocationManager _locationManager;

    public Observable<Location> getObservable() {
        return Observable.create(subscriber -> {
                    final LocationListener locationListener = new LocationListener() {
                        boolean connected = false;
                        LocationConverter _locationDecorator = new LocationConverter();

                        @Override
                        public void onLocationChanged(final android.location.Location location) {
                            final Location locationDomain = _locationDecorator.fromAndroidLocation(location);
                            subscriber.onNext(locationDomain);
                            Log.d(LocationHandler.class.getSimpleName(), "get Location" + _locationDecorator.toString(locationDomain));
                        }

                        @Override
                        public void onStatusChanged(final String provider, final int status, final Bundle extras) {
                            connected = status == LocationProvider.AVAILABLE;
                            Log.d(LocationHandler.class.getSimpleName(), "onStatusChanged:" + status);
                        }

                        @Override
                        public void onProviderEnabled(final String provider) {

                        }

                        @Override
                        public void onProviderDisabled(final String provider) {

                        }
                    };

                    start(locationListener);

                    subscriber.add(Subscriptions.create(() -> stop(locationListener)));
                }
        );
    }

    public LocationHandler(final LocationManager lm) {
        this._locationManager = lm;
    }


    public void start(final LocationListener locationListener) {
        _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist, locationListener);
        Log.d(getClass().getSimpleName(), "start LocationListener");
    }

    public void stop(final LocationListener locationListener) {
        _locationManager.removeUpdates(locationListener);
        Log.d(getClass().getSimpleName(), "stop LocationListener");
    }
}
