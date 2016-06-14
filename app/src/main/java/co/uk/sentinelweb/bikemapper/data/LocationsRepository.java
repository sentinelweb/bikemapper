package co.uk.sentinelweb.bikemapper.data;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.core.model.Location;
import rx.Observable;

/**
 * Created by robert on 14/06/16.
 */
public class LocationsRepository {

    private final Context _context;

    @Inject
    LocationsRepository(final Context context) {
        _context = context;
    }

    public Observable<Location> getItems() {
        return Observable.from(mockLocations());
    }

    private List<Location> mockLocations() {
        final List<Location> locations = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            final Location l = new Location();
            l.setId(i);
            l.setName("Locaation " + i);
            l.setLatitude(i * 10);
            l.setLongitude(i * 10);
            locations.add(l);
        }
        return locations;
    }

}
