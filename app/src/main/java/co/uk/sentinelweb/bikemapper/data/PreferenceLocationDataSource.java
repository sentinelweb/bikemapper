package co.uk.sentinelweb.bikemapper.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.util.BikeApplicationPreferences;

/**
 * Created by robert on 30/10/2016.
 */

public class PreferenceLocationDataSource implements ILocationDataSource {
    public static final String LOCATION_KEY = "location.";

    private final BikeApplicationPreferences _prefs;
    private final Gson _serialiser;

    public PreferenceLocationDataSource(final BikeApplicationPreferences prefs) {
        this._prefs = prefs;
        this._serialiser = new GsonBuilder().create();
    }

    @Override
    public SavedLocation getById(final Long id) {
        final String prefValue = _prefs.getString(getKeyForId(id));
        return deserialise(prefValue);
    }

    @Override
    public List<SavedLocation> getAllLocations() {
        final List<SavedLocation> locations = new ArrayList<>();
        final Map<String, String> stringsWithPrefix = _prefs.getStringsWithPrefix(LOCATION_KEY);
        for (final String key : stringsWithPrefix.keySet()) {
            locations.add(deserialise(stringsWithPrefix.get(key)));
        }
        return locations;
    }

    @Override
    public void saveLocation(final SavedLocation savedLocation) {
        if (savedLocation.getId() != -1l && _prefs.hasKey(getKeyForId(savedLocation.getId()))) {
            _prefs.putString(getKeyForId(savedLocation.getId()), serialise(savedLocation));
        } else {
            long newKey = 0;
            while (_prefs.hasKey(getKeyForId(savedLocation.getId()))) {
                newKey++;
            }
            savedLocation.setId(newKey);
            _prefs.putString(getKeyForId(newKey), serialise(savedLocation));
        }
    }

    private String getKeyForId(final Long id) {
        return LOCATION_KEY + id;
    }

    private SavedLocation deserialise(final String prefValue) {
        return _serialiser.fromJson(prefValue, SavedLocation.class);
    }

    private String serialise(final SavedLocation location) {
        return _serialiser.toJson(location);
    }
}
