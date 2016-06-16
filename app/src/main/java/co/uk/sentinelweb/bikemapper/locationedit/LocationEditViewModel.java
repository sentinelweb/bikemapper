package co.uk.sentinelweb.bikemapper.locationedit;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;

import co.uk.sentinelweb.bikemapper.BR;
import co.uk.sentinelweb.bikemapper.core.model.Location;

/**
 * Created by robert on 15/06/16.
 */
public class LocationEditViewModel extends BaseObservable {

    public static final Location EMPTY_LOCATION = new Location(-1, "Empty", 0, 0);

    private final Location location;
    private LatLng _latLng;

    public LocationEditViewModel(final Location location) {
        this.location = location;
    }

    @Bindable
    public String getName() {
        return location.getName();
    }

    public void setName(final String name) {
        location.setName(name);
        notifyPropertyChanged(BR.name);
    }

    public void onNameChanged(final CharSequence s, final int start, final int before, final int count) {
        setName(s.toString());
    }

    public LatLng getLatLng() {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
