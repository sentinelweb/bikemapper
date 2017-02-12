package co.uk.sentinelweb.bikemapper.net.place.google.interactor;

import java.io.IOException;
import java.util.List;

import co.uk.sentinelweb.bikemapper.domain.model.Place;
import co.uk.sentinelweb.bikemapper.net.place.google.mapper.IPlaceApiMapper;
import co.uk.sentinelweb.bikemapper.net.place.google.mapper.PlaceApiMapper;
import co.uk.sentinelweb.bikemapper.net.response.google.places.GoogleMapsPlacesTextResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;
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
    public static PlaceApiInteractor build(final GoogleMapsClient client) {
        return new PlaceApiInteractor(client, new PlaceApiMapper());
    }

    private final IPlaceApiMapper _placeApiMapper;
    private final GoogleMapsClient _client;

    public PlaceApiInteractor(final GoogleMapsClient client, final IPlaceApiMapper placeApiMapper) {
        _placeApiMapper = placeApiMapper;
        _client = client;
    }

    @Override
    public Observable<List<Place>> getPlaces(final String searchKey) {
        return Observable.defer(new Func0<Observable<List<Place>>>() {
            @Override
            public Observable<List<Place>> call() {
                final Call<GoogleMapsPlacesTextResponse> directionsResponse = _client.getService().getTextPlaces(searchKey, _client.getKeyProvider().getApiKey());
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
                } catch (final Exception e) {
                    return Observable.error(e);
                }
            }
        });

    }

}
