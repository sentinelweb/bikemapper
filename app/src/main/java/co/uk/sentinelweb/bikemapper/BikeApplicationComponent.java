package co.uk.sentinelweb.bikemapper;

import android.content.Context;

import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.data.DataModule;
import co.uk.sentinelweb.bikemapper.data.ILocationsRepository;
import co.uk.sentinelweb.bikemapper.locationedit.LocationEditPresenter;
import co.uk.sentinelweb.bikemapper.locationmap.LocationMapPresenter;
import co.uk.sentinelweb.bikemapper.locations.LocationListPresenter;
import co.uk.sentinelweb.bikemapper.template.LocationTmplPresenter;
import dagger.Component;

/**
 * Created by robert on 14/06/16.
 */
@Singleton
@Component(modules = {BikeApplicationModule.class, DataModule.class})
public interface BikeApplicationComponent {
    ILocationsRepository provideLocationsRepository();

    //SharedPreferences providePreferences(final BikeApplication app);

    @Singleton
    public void plus(DataModule module);

    // presenters
    public void inject(LocationListPresenter fragment);

    public void inject(LocationEditPresenter fragment);

    public void inject(LocationMapPresenter fragment);

    public void inject(LocationTmplPresenter fragment);// unused

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
