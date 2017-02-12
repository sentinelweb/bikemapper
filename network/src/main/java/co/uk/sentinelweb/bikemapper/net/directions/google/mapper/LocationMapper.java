package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse;

/**
 * for Location response mapping (google) in directions API
 * maps a {@link co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse.GLocation}
 * to a {@link Location}
 * Created by robert on 12/02/2017.
 */

public class LocationMapper {
    public Location map(final GoogleMapsDirectionsResponse.GLocation location) {
        return new Location(location.lat, location.lng);
    }
}
