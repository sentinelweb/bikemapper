package co.uk.sentinelweb.bikemapper.locationmap;

import android.content.Context;

import co.uk.sentinelweb.bikemapper.di.ActivityScope;
import co.uk.sentinelweb.bikemapper.di.BikeApplicationComponent;
import dagger.Component;

/**
 * Created by robert on 11/02/2017.
 */
@ActivityScope
@Component(dependencies = BikeApplicationComponent.class, modules = {LocationMapModule.class})
public interface LocationMapComponent {

    void inject(LocationMapActivity activity);

    class Injector {
//        public static BikeApplicationComponent getComponent(final Context c) {
//            return ((BikeApplication) c.getApplicationContext()).getComponent();
//        }

        public LocationMapComponent createComponent(final Context c, final LocationMapFragment view) {
            //final DaggerLocationMapComponent build = DaggerLocationMapComponent.builder();

            final LocationMapComponent build = DaggerLocationMapComponent.builder()
                    .bikeApplicationComponent(BikeApplicationComponent.Injector.getComponent(c))
                    .locationMapModule(new LocationMapModule(view))
                    .build();


//            final BikeApplicationComponent build = DaggerBikeApplicationComponent.builder()
//                    .dataModule(new DataModule(c))
//                    .bikeApplicationModule(new BikeApplicationModule(c))
//                    .networkModule(new NetworkModule()).build();
//            return build;
            return build;
        }
    }
}
