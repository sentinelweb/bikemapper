package co.uk.sentinelweb.bikemapper.domain.model;

/**
 * TODO move to autovalue
 * Created by robert on 12/06/16.
 */
public class Location {
    private double latitude;
    private double longitude;

    public Location(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }


}
