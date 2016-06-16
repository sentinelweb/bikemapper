package co.uk.sentinelweb.bikemapper;

import android.content.Context;

import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.data.DataModule;
import co.uk.sentinelweb.bikemapper.data.LocationsRepository;
import co.uk.sentinelweb.bikemapper.locationedit.LocationEditPresenter;
import co.uk.sentinelweb.bikemapper.locations.LocationListPresenter;
import dagger.Component;

/**
 * Created by robert on 14/06/16.
 */
@Singleton
@Component(modules = {BikeApplicationModule.class, DataModule.class})
public interface BikeApplicationComponent {
    LocationsRepository provideLocationsRepository();

    @Singleton
    public void plus(DataModule module);

    // presenters
    public void inject(LocationListPresenter fragemnt);

    public void inject(LocationEditPresenter fragemnt);

    class Injector {
        public static BikeApplicationComponent getComponent(final Context c) {
            return ((BikeApplication) c.getApplicationContext()).getComponent();
        }

        public BikeApplicationComponent createComponent(final BikeApplication c) {
            final BikeApplicationComponent build = DaggerBikeApplicationComponent.builder().
                    dataModule(new DataModule(c)).build();
            return build;
        }
    }

}
