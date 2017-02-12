package co.uk.sentinelweb.bikemapper.domain.model;

/**
 * TODO move to autovalue
 * Created by robert on 31/10/2016.
 */

public class Place {
    private final String _name;
    private final Location _location;
    private final String _address;
    private final String _iconUrl;
    private final float _rating;

    public Place(final String address, final String name, final Location location, final String iconUrl, final float rating) {
        _address = address;
        _name = name;
        _location = location;
        _iconUrl = iconUrl;
        _rating = rating;
    }

    public String getAddress() {
        return _address;
    }

    public String getIconUrl() {
        return _iconUrl;
    }

    public Location getLocation() {
        return _location;
    }

    public String getName() {
        return _name;
    }

    public float getRating() {
        return _rating;
    }
}
