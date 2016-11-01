package co.uk.sentinelweb.bikemapper.data;

import java.util.List;

import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;

/**
 * Created by robert on 30/10/2016.
 */

public interface ILocationDataSource {
    SavedLocation getById(Long id);

    List<SavedLocation> getAllLocations();

    void saveLocation(SavedLocation savedLocation);
}
