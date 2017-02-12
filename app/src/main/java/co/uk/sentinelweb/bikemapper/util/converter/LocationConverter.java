package co.uk.sentinelweb.bikemapper.util.converter;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.domain.model.Location;

/**
 * Created by robert on 16/06/16.
 */
public class LocationConverter {

    @Inject
    public LocationConverter() {
    }

    public Location fromAndroidLocation(final android.location.Location location) {
        return new Location(location.getLatitude(), location.getLongitude());
    }

    public Location fromLatLng(final LatLng latlng) {
        return new Location(latlng.latitude, latlng.longitude);
    }

    public LatLng toLatLng(final Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public android.location.Location toAndroidLocation(final Location location) {
        final android.location.Location alocation = new android.location.Location("Domain");
        alocation.setLatitude(location.getLatitude());
        alocation.setLongitude(location.getLongitude());
        return alocation;
    }

    public String toString(final Location location) {
        if (location != null) {
            return String.format("Location: [%f, %f]", location.getLatitude(), location.getLongitude());
        } else {
            return "Location [none]";
        }
    }
}
