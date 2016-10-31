package co.uk.sentinelweb.bikemapper.data;

import android.content.Context;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import rx.Observable;

/**
 * Memory Repository for Locations.
 * Created by robert on 14/06/16.
 */
public class LocationsRepository implements ILocationsRepository {

    private final Context _context;
    private final ILocationDataSource _locationDataSource;

    private final LinkedHashMap<Long, SavedLocation> _cache = new LinkedHashMap<>();

    @Inject
    public LocationsRepository(final Context context, final ILocationDataSource locationDataSource) {
        _context = context;
        _locationDataSource = locationDataSource;
        refresh();
    }

    @Override
    public void refresh() {
        _cache.clear();
        Observable.from(_locationDataSource.getAllLocations())
                .doOnNext(location -> _cache.put(location.getId(), location))
                .subscribe();
    }

    @Override
    public Observable<SavedLocation> getItems() {
        return Observable.from(_cache.values());
    }

    @Override
    public void saveLocation(final SavedLocation location) {
        _cache.put(location.getId(), location);
        _locationDataSource.saveLocation(location);
    }

    @Override
    public Observable<SavedLocation> getLocation(final Long id) {
        final SavedLocation location = _cache.get(id);
        if (location != null) {
            return Observable.just(location);
        }
        return Observable.error(new IllegalArgumentException("Location doest exist"));
    }
}
