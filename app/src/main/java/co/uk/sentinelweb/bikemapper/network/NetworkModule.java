package co.uk.sentinelweb.bikemapper.network;

import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.net.directions.google.interactor.DirectionsInteractor;
import co.uk.sentinelweb.bikemapper.net.directions.google.interactor.IDirectionsInteractor;
import co.uk.sentinelweb.bikemapper.net.place.google.interactor.IPlaceApiInteractor;
import co.uk.sentinelweb.bikemapper.net.place.google.interactor.PlaceApiInteractor;
import co.uk.sentinelweb.bikemapper.net.place.google.mapper.PlaceApiMapper;
import co.uk.sentinelweb.bikemapper.net.retrofit.client.GoogleMapsClient;
import co.uk.sentinelweb.bikemapper.network.api.GoogleMapsApiKeyProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 01/11/2016.
 */
@Module
public class NetworkModule {
    @Provides
    public IPlaceApiInteractor providePlaceInteractor(final GoogleMapsClient client) {
        return new PlaceApiInteractor(client, new PlaceApiMapper());
    }

    @Provides
    public IDirectionsInteractor provideDirectionsInteractor(final GoogleMapsClient client) {
        return DirectionsInteractor.build(client);
    }

    @Provides
    @Singleton
    public GoogleMapsClient provideGoogleMapsClient() {
        return new GoogleMapsClient(new GoogleMapsApiKeyProvider());
    }

}
