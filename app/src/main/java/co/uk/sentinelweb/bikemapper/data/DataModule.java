package co.uk.sentinelweb.bikemapper.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by robert on 14/06/16.
 */
@Module
public class DataModule {

    Context _context;

    public DataModule(final Context c) {
        _context = c;
    }

    @Provides
    @Singleton
    public LocationsRepository provideLocationsRepository() {
        return new LocationsRepository(_context);
    }
}
