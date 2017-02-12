package co.uk.sentinelweb.bikemapper.util.converter;

import android.support.annotation.ColorInt;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;

import javax.inject.Inject;

import co.uk.sentinelweb.bikemapper.domain.model.Leg;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.domain.model.Step;

/**
 * Created by robert on 12/02/2017.
 */

public class RouteConverter {
    private final LocationConverter _locationConverter;

    @Inject
    public RouteConverter(final LocationConverter locationConverter) {
        _locationConverter = locationConverter;
    }

    public PolylineOptions getRoutePolyline(final Route route, @ColorInt final int color) {
        final PolylineOptions plo = new PolylineOptions();

        // plot route
        Step lastStep = null;
        for (final Leg leg : route.legs()) {
            for (final Step step : leg.steps()) {
                plo.add(_locationConverter.toLatLng(step.start()));
                lastStep = step;
            }
        }
        plo.add(_locationConverter.toLatLng(lastStep.end()));
        plo.color(color);
        return plo;
    }

    public LatLngBounds getRouteBounds(final Route route) {
        final LatLngBounds latLngBounds = new LatLngBounds(
                _locationConverter.toLatLng(route.southWest()),
                _locationConverter.toLatLng(route.northEast()));
        return latLngBounds;
    }

    public String getRouteOverview(final Route route) {
        final Step lastStep = getLastStep(getLastLeg(route));
        final Leg firstLeg = getFirstLeg(route);
        final String message = String.format(Locale.ENGLISH,
                "%s -> %s [%d %s, %d %s] by %s",
                route.startAddress().getText(),
                route.endAddress().getText(),
                firstLeg.distance(), firstLeg.distanceUnit(),
                firstLeg.time(), firstLeg.timeUnit(),
                lastStep.travelMode());
        return message;
    }

    private Leg getFirstLeg(final Route route) {
        if (route != null && route.legs() != null && route.legs().size() > 0) {
            return route.legs().get(0);
        }
        return null;
    }

    private Leg getLastLeg(final Route route) {
        if (route != null && route.legs() != null && route.legs().size() > 0) {
            return route.legs().get(route.legs().size() - 1);
        }
        return null;
    }

    private Step getLastStep(final Leg leg) {
        if (leg != null && leg.steps().size() > 0) {
            return leg.steps().get(leg.steps().size() - 1);
        }
        return null;
    }
}
