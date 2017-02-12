package co.uk.sentinelweb.bikemapper.net.directions.google.interactor;

import java.util.List;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.domain.model.TravelMode;
import rx.Observable;

/**
 * Created by robert on 12/02/2017.
 */

public interface IDirectionsInteractor {
    Observable<List<Route>> getDirections(Location start, Location end, TravelMode travelMode);
}
