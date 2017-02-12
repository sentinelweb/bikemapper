package co.uk.sentinelweb.bikemapper.net.place.google.interactor;

import java.util.List;

import co.uk.sentinelweb.bikemapper.domain.model.Place;
import rx.Observable;

/**
 * Created by robert on 31/10/2016.
 */

public interface IPlaceApiInteractor {
    Observable<List<Place>> getPlaces(String searchKey);
}
