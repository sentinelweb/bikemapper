package co.uk.sentinelweb.bikemapper.locationmap;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import co.uk.sentinelweb.bikemapper.BR;

/**
 * Created by robert on 15/06/16.
 */
public class LocationMapModel extends BaseObservable {

//    private final SavedLocation _savedLocation;
//    private final LocationConverter _locationConverter;

    private String _name;
    private LatLng _currentLocation;
    private LatLng _targetLocation;
    private PolylineOptions _route;
    private LatLngBounds _routeBounds;
    private String _routeDescription;
    private boolean loadingVisible;

    public LocationMapModel() {

    }

    @Bindable
    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        this._name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public LatLng getCurrentLocation() {
        return _currentLocation;
    }

    public void setCurrentLocation(final LatLng currentLocation) {
        this._currentLocation = currentLocation;
        notifyPropertyChanged(BR.currentLocation);
    }

    @Bindable
    public PolylineOptions getRoute() {
        return _route;
    }

    public void setRoute(final PolylineOptions route) {
        _route = route;
        notifyPropertyChanged(BR.route);
    }

    @Bindable
    public String getRouteDescription() {
        return _routeDescription;
    }

    public void setRouteDescription(final String routeDescription) {
        _routeDescription = routeDescription;
        notifyPropertyChanged(BR.routeDescription);
    }

    @Bindable
    public LatLng getTargetLocation() {
        return _targetLocation;
    }

    public void setTargetLocation(final LatLng targetLocation) {
        _targetLocation = targetLocation;
        notifyPropertyChanged(BR.targetLocation);
    }

    @Bindable
    public boolean isLoadingVisible() {
        return loadingVisible;
    }

    public void setLoadingVisible(final boolean loadingVisible) {
        this.loadingVisible = loadingVisible;
        notifyPropertyChanged(BR.loadingVisible);
    }

    @Bindable
    public LatLngBounds getRouteBounds() {
        return _routeBounds;
    }

    public void setRouteBounds(final LatLngBounds routeBounds) {
        _routeBounds = routeBounds;
        notifyPropertyChanged(BR.routeBounds);
    }
}
