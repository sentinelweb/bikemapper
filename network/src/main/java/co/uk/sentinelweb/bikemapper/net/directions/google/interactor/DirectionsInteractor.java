package co.uk.sentinelweb.bikemapper.net.directions.google.interactor;

import java.io.IOException;
import java.util.List;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.domain.model.TravelMode;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.DirectionsMapper;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.DistanceUnitMapper;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.LocationMapper;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.LocationRequestMapper;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.TimeUnitMapper;
import co.uk.sentinelweb.bikemapper.net.directions.google.mapper.TravelModeMapper;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.retrofit.client.GoogleMapsClient;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by robert on 12/02/2017.
 */

public class DirectionsInteractor implements IDirectionsInteractor {

    public static DirectionsInteractor build(final GoogleMapsClient client) {
        return new DirectionsInteractor(
                client,
                new DirectionsMapper(new LocationMapper(), new DistanceUnitMapper(), new TimeUnitMapper(), new TravelModeMapper()),
                new LocationRequestMapper()
        );
    }

    private final DirectionsMapper _directionsMapper;
    private final LocationRequestMapper _locationRequestMapper;
    private final GoogleMapsClient _client;

    public DirectionsInteractor(final GoogleMapsClient client,
                                final DirectionsMapper directionsMapper,
                                final LocationRequestMapper locationRequestMapper) {
        _directionsMapper = directionsMapper;
        _client = client;
        _locationRequestMapper = locationRequestMapper;
    }

    @Override
    public Observable<List<Route>> getDirections(final Location start, final Location end, final TravelMode travelMode) {
        return Observable.defer(new Func0<Observable<List<Route>>>() {
            @Override
            public Observable<List<Route>> call() {
                final Call<GoogleMapsDirectionsResponse> directionsResponse = _client.getService().getDirections(
                        _locationRequestMapper.map(start), _locationRequestMapper.map(end), _client.getKeyProvider().getApiKey(), travelMode.name().toLowerCase());
                try {
                    final Response<GoogleMapsDirectionsResponse> execute = directionsResponse.execute();
                    return Observable.just(execute.body())
                            .map(new Func1<GoogleMapsDirectionsResponse, List<Route>>() {
                                @Override
                                public List<Route> call(final GoogleMapsDirectionsResponse response) {
                                    return _directionsMapper.map(response);
                                }
                            });
                } catch (final IOException e) {
                    return Observable.error(e);
                } catch (final Exception e) {
                    return Observable.error(e);
                }
            }
        });

    }
}
