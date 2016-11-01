package co.uk.sentinelweb.bikemapper.net.interactor;

import java.io.IOException;
import java.util.List;

import co.uk.sentinelweb.bikemapper.core.model.Place;
import co.uk.sentinelweb.bikemapper.net.mapper.IPlaceApiMapper;
import co.uk.sentinelweb.bikemapper.net.response.google.places.GoogleMapsPlacesTextResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import co.uk.sentinelweb.bikemapper.net.retrofit.client.GoogleMapsClient;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by robert on 31/10/2016.
 */
public class PlaceApiInteractor implements IPlaceApiInteractor {
    private final IPlaceApiMapper _placeApiMapper;

    public PlaceApiInteractor(final IPlaceApiMapper placeApiMapper) {
        _placeApiMapper = placeApiMapper;
    }

    @Override
    public Observable<List<Place>> getPlaces(final String searchKey, final IApiKeyProvider apiKey) {
        return Observable.defer(new Func0<Observable<List<Place>>>() {
            @Override
            public Observable<List<Place>> call() {
                final GoogleMapsClient client = new GoogleMapsClient(apiKey);
                final Call<GoogleMapsPlacesTextResponse> directionsResponse = client.getService().getTextPlaces(searchKey, apiKey.getApiKey());
                try {
                    final Response<GoogleMapsPlacesTextResponse> execute = directionsResponse.execute();
                    return Observable.from(execute.body().results)
                            .flatMap(new Func1<Result, Observable<Place>>() {
                                @Override
                                public Observable<Place> call(final Result result) {
                                    return Observable.just(_placeApiMapper.map(result));
                                }
                            })
                            .toList();
                } catch (final IOException e) {
                    return Observable.error(e);
                }
            }
        });

    }

}
