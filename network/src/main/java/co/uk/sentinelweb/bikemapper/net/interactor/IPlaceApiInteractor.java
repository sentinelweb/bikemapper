package co.uk.sentinelweb.bikemapper.net.interactor;

import java.util.List;

import co.uk.sentinelweb.bikemapper.core.model.Place;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import rx.Observable;

/**
 * Created by robert on 31/10/2016.
 */

public interface IPlaceApiInteractor {
    Observable<List<Place>> getPlaces(String searchKey, IApiKeyProvider apiKey);
}
