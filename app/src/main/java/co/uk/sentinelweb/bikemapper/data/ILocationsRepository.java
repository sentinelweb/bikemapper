package co.uk.sentinelweb.bikemapper.data;

import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import rx.Observable;

/**
 * Created by robert on 30/10/2016.
 */
public interface ILocationsRepository {
    void refresh();

    Observable<SavedLocation> getItems();

    void saveLocation(SavedLocation location);

    Observable<SavedLocation> getLocation(Long id);
}
