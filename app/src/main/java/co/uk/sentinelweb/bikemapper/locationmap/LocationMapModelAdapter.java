package co.uk.sentinelweb.bikemapper.locationmap;

import android.support.annotation.ColorInt;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.domain.model.Location;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.util.converter.LocationConverter;
import co.uk.sentinelweb.bikemapper.util.converter.RouteConverter;

/**
 * Created by robert on 12/02/2017.
 */
public class LocationMapModelAdapter {
    final LocationConverter _locationConverter;
    final RouteConverter _routeConverter;

    LocationMapModel _model;

    @Inject
    public LocationMapModelAdapter(
            final LocationConverter locationConverter,
            final RouteConverter routeConverter) {
        _locationConverter = locationConverter;
        _routeConverter = routeConverter;
    }

    public void setModel(final LocationMapModel model) {
        _model = model;
    }

    public void setCurrent(final Location location) {
        _model.setCurrentLocation(_locationConverter.toLatLng(location));
    }

    public void setTarget(final Location location) {
        _model.setTargetLocation(_locationConverter.toLatLng(location));
    }

    public void setRoute(final Route route, @ColorInt final int color) {
        _model.setRoute(_routeConverter.getRoutePolyline(route, color));
        _model.setRouteBounds(_routeConverter.getRouteBounds(route));
        _model.setRouteDescription(_routeConverter.getRouteOverview(route));
    }
}
