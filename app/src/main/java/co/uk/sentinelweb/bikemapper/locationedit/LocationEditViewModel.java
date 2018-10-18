package co.uk.sentinelweb.bikemapper.locationedit;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;

import co.uk.sentinelweb.bikemapper.BR;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.util.converter.LocationConverter;

/**
 * Created by robert on 15/06/16.
 */
public class LocationEditViewModel extends BaseObservable {

    private final SavedLocation _savedLocation;
    private final LocationConverter _locationConverter;

    public LocationEditViewModel(final SavedLocation savedLocation, final LocationConverter locationConverter) {
        this._savedLocation = savedLocation;
        _locationConverter = locationConverter;
    }

    @Bindable
    public String getName() {
        return _savedLocation.getName();
    }

    public void setName(final String name) {
        _savedLocation.setName(name);
        notifyPropertyChanged(BR.name);
    }

    public void onNameChanged(final CharSequence s, final int start, final int before, final int count) {
        setName(s.toString());
    }

    public LatLng getLatLng() {
        return _locationConverter.toLatLng(_savedLocation.getLocation());
    }

    public SavedLocation getSavedLocation() {
        return _savedLocation;
    }
}
