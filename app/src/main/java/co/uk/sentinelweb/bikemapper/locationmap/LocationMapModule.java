package co.uk.sentinelweb.bikemapper.locationmap;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 11/02/2017.
 */
@Module(includes = LocationMapModule.Bindings.class)
public class LocationMapModule {

    LocationMapContract.View view;

    public LocationMapModule(final LocationMapContract.View view) {
        this.view = view;
    }

    @Provides
    public LocationMapContract.View provideView() {
        //view.setPresenter(presenter);
        return view;
    }

    @Module
    public interface Bindings {
        @Binds
        LocationMapContract.Presenter provide(LocationMapPresenter presenter);
    }
}
