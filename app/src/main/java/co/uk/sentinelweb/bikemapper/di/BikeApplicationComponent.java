package co.uk.sentinelweb.bikemapper.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.BikeApplication;
import co.uk.sentinelweb.bikemapper.data.DataModule;
import co.uk.sentinelweb.bikemapper.data.ILocationDataSource;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import co.uk.sentinelweb.bikemapper.locationedit.LocationEditPresenter;
import co.uk.sentinelweb.bikemapper.locations.LocationListPresenter;
import co.uk.sentinelweb.bikemapper.net.directions.google.interactor.IDirectionsInteractor;
import co.uk.sentinelweb.bikemapper.network.NetworkModule;
import co.uk.sentinelweb.bikemapper.sensors.LocationHandler;
import co.uk.sentinelweb.bikemapper.template.LocationTmplPresenter;
import co.uk.sentinelweb.bikemapper.util.BikeApplicationPreferences;
import co.uk.sentinelweb.bikemapper.util.context.IWrappedContext;
import co.uk.sentinelweb.bikemapper.util.rx.ISchedulers;
import dagger.Component;

/**
 * Created by robert on 14/06/16.
 */
@Singleton
@Component(modules = {BikeApplicationModule.class, DataModule.class, NetworkModule.class})
public interface BikeApplicationComponent {

    ILocationsRepository provideLocationsRepository();

    BikeApplication provideApplication();

    BikeApplicationPreferences providePreferences();

    LocationHandler provideLocationHandler();

    ISchedulers provideSchedulers();

    @Named(DataModule.NAMED_DATASOURCE_MOCK)
    ILocationDataSource provideMockLocationsDataSource();

    @Named(DataModule.NAMED_DATASOURCE_PREFS)
    ILocationDataSource providePrefLocationsDataSource();

    IDirectionsInteractor provideDirectionsInteractor();

    IWrappedContext provideWrappedContext();

    // presenters
    public void inject(LocationListPresenter fragment);

    public void inject(LocationEditPresenter fragment);

    public void inject(LocationTmplPresenter fragment);// unused

    class Injector {
        public static BikeApplicationComponent getComponent(final Context c) {
            return ((BikeApplication) c.getApplicationContext()).getComponent();
        }

        public BikeApplicationComponent createComponent(final BikeApplication c) {
            final BikeApplicationComponent build = DaggerBikeApplicationComponent.builder()
                    .dataModule(new DataModule(c))
                    .bikeApplicationModule(new BikeApplicationModule(c))
                    .networkModule(new NetworkModule()).build();
            return build;
        }
    }

}
