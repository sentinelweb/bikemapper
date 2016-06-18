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
public class LocationsRepository {

    private final Context _context;

    private final LinkedHashMap<Long, SavedLocation> _cache = new LinkedHashMap<>();

    @Inject
    LocationsRepository(final Context context) {
        _context = context;
        refresh();
    }

    public void refresh() {
        _cache.clear();
        Observable.from(MockLocations.getMockLocations())
                .doOnNext(location -> _cache.put(location.getId(), location))
                .subscribe();
    }

    public Observable<SavedLocation> getItems() {
        return Observable.from(_cache.values());
    }

    public void saveLocation(final SavedLocation location) {
        // TODO save location
        _cache.put(location.getId(), location);
    }

    public Observable<SavedLocation> getLocation(final Long id) {
        final SavedLocation location = _cache.get(id);
        if (location != null) {
            return Observable.just(location);
        }
        return Observable.error(new IllegalArgumentException("Location doest exist"));
    }
}
