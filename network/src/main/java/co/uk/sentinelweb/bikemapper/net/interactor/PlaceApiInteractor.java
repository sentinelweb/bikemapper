package co.uk.sentinelweb.bikemapper.net.interactor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import co.uk.sentinelweb.bikemapper.core.model.Place;
import co.uk.sentinelweb.bikemapper.net.mapper.IPlaceApiMapper;
import co.uk.sentinelweb.bikemapper.net.response.google.places.GoogleMapsPlacesTextResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.places.Result;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import co.uk.sentinelweb.bikemapper.net.retrofit.client.GoogleMapsClient;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by robert on 31/10/2016.
 */
public class PlaceApiInteractor implements IPlaceApiInteractor {
    private final IPlaceApiMapper _placeApiMapper;

    public PlaceApiInteractor(final IPlaceApiMapper placeApiMapper) {
        _placeApiMapper = placeApiMapper;
    }

    @Override
    public List<Place> getPlaces(final String searchKey, final IApiKeyProvider apiKey) {
        final GoogleMapsClient client = new GoogleMapsClient(apiKey);
        final Call<GoogleMapsPlacesTextResponse> directionsResponse = client.getService().getTextPlaces(searchKey, apiKey.getApiKey());
        try {
            final Response<GoogleMapsPlacesTextResponse> execute = directionsResponse.execute();
            final GoogleMapsPlacesTextResponse body = execute.body();
            final List<Place> places = new LinkedList<>();
            for (final Result place : body.results) {
                places.add(_placeApiMapper.map(place));
            }
            return places;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
