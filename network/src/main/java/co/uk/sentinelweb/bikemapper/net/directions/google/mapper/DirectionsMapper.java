package co.uk.sentinelweb.bikemapper.net.directions.google.mapper;

import java.util.ArrayList;
import java.util.List;

import co.uk.sentinelweb.bikemapper.domain.model.Address;
import co.uk.sentinelweb.bikemapper.domain.model.DistanceUnit;
import co.uk.sentinelweb.bikemapper.domain.model.Leg;
import co.uk.sentinelweb.bikemapper.domain.model.Route;
import co.uk.sentinelweb.bikemapper.domain.model.Step;
import co.uk.sentinelweb.bikemapper.domain.model.TextType;
import co.uk.sentinelweb.bikemapper.domain.model.TimeUnit;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse.GLeg;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse.GRoute;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse.GStep;

/**
 * Created by robert on 12/02/2017.
 */

public class DirectionsMapper {

    private final LocationMapper _locationMapper;
    private final DistanceUnitMapper _distanceUnitMapper;
    private final TimeUnitMapper _timeUnitMapper;
    private final TravelModeMapper _travelModeMapper;

    public DirectionsMapper(final LocationMapper locationMapper,
                            final DistanceUnitMapper distanceUnitMapper,
                            final TimeUnitMapper timeUnitMapper,
                            final TravelModeMapper travelModeMapper) {
        this._locationMapper = locationMapper;
        _distanceUnitMapper = distanceUnitMapper;
        _timeUnitMapper = timeUnitMapper;
        _travelModeMapper = travelModeMapper;
    }

    public List<Route> map(final GoogleMapsDirectionsResponse response) {
        if (response == null || response.routes == null || response.routes.size() == 0) {
            return null;
        }
        final List<Route> routes = new ArrayList<>();
        for (final GRoute route : response.routes) {
            final List<Leg> legs = new ArrayList<>();
            for (final GLeg leg : route.legs) {
                final List<Step> steps = new ArrayList<>();
                for (final GStep step : leg.steps) {
                    steps.add(
                            Step.create(
                                    _locationMapper.map(step.start_location),
                                    _locationMapper.map(step.end_location),
                                    step.distance.value,
                                    DistanceUnit.M,//_distanceUnitMapper.map(step.distance.text),
                                    step.duration.value / 60,
                                    TimeUnit.MINUTE,// _timeUnitMapper.map(step.duration.text),
                                    step.html_instructions,
                                    TextType.HTML,
                                    _travelModeMapper.map(step.travel_mode),
                                    null,
                                    null
                            ));
                }
                legs.add(Leg.create(
                        _locationMapper.map(leg.start_location),
                        _locationMapper.map(leg.end_location),
                        Address.create(leg.start_address),
                        Address.create(leg.end_address),
                        leg.distance.value,
                        DistanceUnit.M,//_distanceUnitMapper.map(leg.distance.text),
                        leg.duration.value / 60,
                        TimeUnit.MINUTE,//_timeUnitMapper.map(leg.duration.text),
                        steps
                ));
            }
            if (legs.size() > 0) {
                final Leg startLeg = legs.get(0);
                final Leg endLeg = legs.get(legs.size() - 1);
                routes.add(Route.create(startLeg.start(),
                        startLeg.startAddress(),
                        endLeg.end(),
                        endLeg.endAddress(),
                        _locationMapper.map(route.bounds.northeast),
                        _locationMapper.map(route.bounds.southwest),
                        legs
                ));
            } else {
                routes.add(Route.create(null, null, null, null, null, null, legs));
            }
        }
        return routes;

    }
}
