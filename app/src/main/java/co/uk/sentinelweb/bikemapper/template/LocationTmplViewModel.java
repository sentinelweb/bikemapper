package co.uk.sentinelweb.bikemapper.template;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import co.uk.sentinelweb.bikemapper.BR;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;

/**
 * Created by robert on 15/06/16.
 */
public class LocationTmplViewModel extends BaseObservable {

    private final SavedLocation location;

    public LocationTmplViewModel(final SavedLocation location) {
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

//    public LatLng getLatLng() {
//        return new LocationConverter(location.getLocation()).toLatLng();
//    }
}
