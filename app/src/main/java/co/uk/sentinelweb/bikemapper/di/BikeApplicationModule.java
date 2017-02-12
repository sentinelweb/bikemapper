package co.uk.sentinelweb.bikemapper.di;

import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.sensors.LocationHandler;
import co.uk.sentinelweb.bikemapper.util.BikeApplicationPreferences;
import co.uk.sentinelweb.bikemapper.util.context.IWrappedContext;
import co.uk.sentinelweb.bikemapper.util.context.WrappedContext;
import co.uk.sentinelweb.bikemapper.util.rx.ISchedulers;
import co.uk.sentinelweb.bikemapper.util.rx.Schedulers;
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
    public BikeApplicationPreferences providePreferences() {
        return new BikeApplicationPreferences(mApp);
    }

    @Provides
    @Singleton
    public LocationHandler provideLocationHandler() {
        return LocationHandler.from(mApp);
    }

    @Provides
    @Singleton
    public ISchedulers provideSchedulers() {
        return new Schedulers();
    }

    @Provides
    @Singleton
    public IWrappedContext provideWrappedContext() {
        return new WrappedContext(mApp);
    }

}
