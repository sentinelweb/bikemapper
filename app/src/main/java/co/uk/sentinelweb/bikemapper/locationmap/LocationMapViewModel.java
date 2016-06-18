package co.uk.sentinelweb.bikemapper.locationmap;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;

import co.uk.sentinelweb.bikemapper.BR;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;
import co.uk.sentinelweb.bikemapper.decorator.LocationLatLngDecorator;

/**
 * Created by robert on 15/06/16.
 */
public class LocationMapViewModel extends BaseObservable {

    private final SavedLocation _location;
    private final LocationLatLngDecorator _locationLatLngDecorator;

    public LocationMapViewModel(final SavedLocation location) {
        this._location = location;
        _locationLatLngDecorator = new LocationLatLngDecorator(location.getLocation());
    }

    @Bindable
    public String getName() {
        return _location.getName();
    }

    public void setName(final String name) {
        _location.setName(name);
        notifyPropertyChanged(BR.name);
    }

    public LatLng getLatLng() {
        return _locationLatLngDecorator.getLatLng();
    }
}
