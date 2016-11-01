package co.uk.sentinelweb.bikemapper.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import co.uk.sentinelweb.bikemapper.BikeApplication;

/**
 * Created by robert on 30/10/2016.
 */
public class BikeApplicationPreferences {

    private final SharedPreferences _sharedPrefs;

    public BikeApplicationPreferences(final BikeApplication app) {
        this._sharedPrefs = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public String getString(final String key) {
        return _sharedPrefs.getString(key, null);
    }

    public boolean hasKey(final String key) {
        return _sharedPrefs.contains(key);
    }

    public Map<String, String> getStringsWithPrefix(final String prefix) {
        final Map<String, String> keys = new HashMap<>();
        for (final String key : _sharedPrefs.getAll().keySet()) {
            if (key.startsWith(prefix)) {
                keys.put(key, _sharedPrefs.getString(key, null));
            }
        }
        return keys;
    }

    public void putString(final String key, final String value) {
        _sharedPrefs.edit().putString(key, value).apply();
    }
}
