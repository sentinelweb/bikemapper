package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.Location;

/**
 * map location for directions API request
 * maps a {@link co.uk.sentinelweb.bikemapper.domain.model.Location} to a string lat,long
 * Created by robert on 12/02/2017.
 */

public class LocationRequestMapper {

    public String map(final Location location) {
        return String.format("%f,%f", location.getLatitude(), location.getLongitude());
    }
}
