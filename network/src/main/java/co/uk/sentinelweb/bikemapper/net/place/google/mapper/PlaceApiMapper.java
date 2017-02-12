package co.uk.sentinelweb.bikemapper.net.place.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;

/**
 * Created by robert on 01/11/2016.
 */
public class PlaceApiMapper implements IPlaceApiMapper {
    @Override
    public Place map(final Result place) {
        final float rating = place.rating != null ? place.rating.floatValue() : -1;
        final Place p = new Place(place.formattedAddress, place.name, new Location(place.geometry.location.lat, place.geometry.location.lng), place.icon, rating);
        return p;
    }
}
