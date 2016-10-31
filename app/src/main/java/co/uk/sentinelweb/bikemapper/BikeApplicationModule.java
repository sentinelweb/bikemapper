package co.uk.sentinelweb.bikemapper;

import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.util.BikeApplicationPreferences;
import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 14/06/16.
 */
@Module
public class BikeApplicationModule {
    private final BikeApplication mApp;

    public BikeApplicationModule(final BikeApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public BikeApplication provideApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    public BikeApplicationPreferences providePreferences(final BikeApplication app) {
        return new BikeApplicationPreferences(app);
    }


}
