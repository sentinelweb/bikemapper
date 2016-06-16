package co.uk.sentinelweb.bikemapper.core.model;

/**
 * Created by robert on 12/06/16.
 */
public class Location {
    private long id;

    private String name;

    private double latitude;
    private double longitude;

    public Location(final long id, final String name, final double latitude, final double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
