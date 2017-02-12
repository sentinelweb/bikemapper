package co.uk.sentinelweb.bikemapper.net.place.google.mapper;

import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;

/**
 * Created by robert on 01/11/2016.
 */

public interface IPlaceApiMapper {
    Place map(Result place);
}
