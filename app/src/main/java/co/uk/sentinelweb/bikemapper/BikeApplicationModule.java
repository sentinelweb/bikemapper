package co.uk.sentinelweb.bikemapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 14/06/16.
 */
@Module
public class BikeApplicationModule {
    protected BikeApplication mApp;

    public BikeApplicationModule(final BikeApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public BikeApplication provideApplication() {
        return mApp;
    }
}
