package co.uk.sentinelweb.bikemapper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;

/**
 * Created by robert on 15/06/16.
 */
public class MockLocationDataSource implements ILocationDataSource {

    private static Map<Long, SavedLocation> _locations;
    private final Comparator<SavedLocation> _savedLocationComparator = (location1, location2) -> (int) (location1.getId() - location2.getId());

    public MockLocationDataSource() {
        getMockLocations();
    }

    private static Map<Long, SavedLocation> getMockLocations() {
        _locations = new HashMap<>();
        _locations.put(0l, new SavedLocation(0, "Hackney city farm", 51.5309206, -0.0718702));
        _locations.put(1l, new SavedLocation(1, "Home", 51.525493, -0.0822173));
        _locations.put(2l, new SavedLocation(2, "Farringdon station", 51.5202109, -0.1070064));
        _locations.put(3l, new SavedLocation(3, "London bridge station", 51.5045761, -0.0882424));
        _locations.put(4l, new SavedLocation(4, "Monument station", 51.5096179, -0.0860752));
        _locations.put(5l, new SavedLocation(5, "Ledenhall Market", 51.511374, -0.0848521));
        _locations.put(6l, new SavedLocation(6, "Fournier st", 51.519309, -0.071881));
        _locations.put(7l, new SavedLocation(7, "Boundary Gardens", 51.526011, -0.075089));
        return _locations;
    }


    @Override
    public SavedLocation getById(final Long id) {
        return _locations.get(id.intValue());
    }

    @Override
    public List<SavedLocation> getAllLocations() {
        final ArrayList<SavedLocation> locations = new ArrayList<>(_locations.values());
        Collections.sort(locations, _savedLocationComparator);
        return locations;
    }

    @Override
    public void saveLocation(final SavedLocation savedLocation) {
        if (savedLocation.getId() != -1l && _locations.containsKey(savedLocation.getId())) {
            _locations.put(savedLocation.getId(), savedLocation);
        } else {
            long newKey = 0;
            while (_locations.containsKey(newKey)) {
                newKey++;
            }
            savedLocation.setId(newKey);
            _locations.put(newKey, savedLocation);
        }
    }
}
