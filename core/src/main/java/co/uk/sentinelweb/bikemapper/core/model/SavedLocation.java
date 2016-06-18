package co.uk.sentinelweb.bikemapper.core.model;

/**
 * Created by robert on 12/06/16.
 */
public class SavedLocation {
    private long id;

    private String name;

    Location location;

    public SavedLocation(final long id, final String name, final double latitude, final double longitude) {
        this.id = id;
        this.location = new Location(latitude, longitude);
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
