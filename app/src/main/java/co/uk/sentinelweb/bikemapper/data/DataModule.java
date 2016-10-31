package co.uk.sentinelweb.bikemapper.data;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import co.uk.sentinelweb.bikemapper.util.BikeApplicationPreferences;
import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 14/06/16.
 */
@Module
public class DataModule {

    public static final String NAMED_DATASOURCE_MOCK = "mock";
    public static final String NAMED_DATASOURCE_PREFS = "prefs";
    Context _context;

    public DataModule(final Context c) {
        _context = c;
    }

    @Provides
    @Singleton
    public ILocationsRepository provideLocationsRepository(
            @Named(NAMED_DATASOURCE_MOCK) final ILocationDataSource locationDataSource
    ) {
        return new LocationsRepository(_context, locationDataSource);
    }

    @Provides
    @Singleton
    @Named(NAMED_DATASOURCE_MOCK)
    public ILocationDataSource provideMockLocationsDataSource() {
        return new MockLocationDataSource();
    }

    @Provides
    @Singleton
    @Named(NAMED_DATASOURCE_PREFS)
    public ILocationDataSource providePrefLocationsDataSource(final BikeApplicationPreferences prefs) {
        return new PreferenceLocationDataSource(prefs);
    }


}
