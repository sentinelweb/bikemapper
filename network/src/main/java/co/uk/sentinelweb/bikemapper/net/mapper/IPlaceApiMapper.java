package co.uk.sentinelweb.bikemapper.net.mapper;

import co.uk.sentinelweb.bikemapper.core.model.Place;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;

/**
 * Created by robert on 01/11/2016.
 */

public interface IPlaceApiMapper {
    Place map(Result place);
}
