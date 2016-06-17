package co.uk.sentinelweb.bikemapper.decorator;

import com.google.android.gms.maps.model.LatLng;

import co.uk.sentinelweb.bikemapper.core.model.Location;

/**
 * Created by robert on 16/06/16.
 */
public class LocationLatLngDecorator {
    private Location location;

    public LocationLatLngDecorator(final Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public LatLng getLatLng() {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


}
