package co.uk.sentinelweb.bikemapper.network;

import co.uk.sentinelweb.bikemapper.net.interactor.IPlaceApiInteractor;
import co.uk.sentinelweb.bikemapper.net.interactor.PlaceApiInteractor;
import co.uk.sentinelweb.bikemapper.net.mapper.PlaceApiMapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 01/11/2016.
 */
@Module
public class NetworkModule {
    @Provides
    public IPlaceApiInteractor providePlaceInteractor() {
        return new PlaceApiInteractor(new PlaceApiMapper());
    }
}
