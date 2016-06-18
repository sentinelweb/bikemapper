package co.uk.sentinelweb.bikemapper.net.response.google;

import org.junit.Test;

import co.uk.sentinelweb.bikemapper.net.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Created by robert on 18/06/16.
 */
public class GoogleMapsDirectionsResponseTest {
    @Test
    public void shouldTestGmapDirections() {
        final String string = TestUtils.getString(GoogleMapsDirectionsResponse.class, "gmap_response.json");
        final GoogleMapsDirectionsResponse body = TestUtils.getGson().fromJson(string, GoogleMapsDirectionsResponse.class);
        GoogleMapsDirectionsResponseTest.checkResponse(body);
    }

    public static void checkResponse(final GoogleMapsDirectionsResponse body) {
        assertThat("response was null", body, is(notNullValue()));
        assertThat("status", body.status, is("OK"));
        assertThat("routes shouldnt be null", body.routes, is(notNullValue()));
        assertThat("routes should be size 1", body.routes.size(), is(1));

        // route
        final GoogleMapsDirectionsResponse.GRoute firstRoute = body.routes.get(0);
        assertThat("route bounds should not be null", firstRoute.bounds, is(notNullValue()));
        assertThat("route northeast should not be null", firstRoute.bounds.northeast, is(notNullValue()));
        assertThat("route southwest should not be null", firstRoute.bounds.southwest, is(notNullValue()));
        assertThat("route southwest lat should be 51.5202921", firstRoute.bounds.southwest.lat, is(closeTo(51.5202921, 0.0000001)));
        assertThat("route overview_polyline should not be null", firstRoute.overview_polyline, is(notNullValue()));
        assertThat("route copyrights should not be null", firstRoute.copyrights, is(notNullValue()));
        assertThat("route warnings should not be null", firstRoute.warnings, is(notNullValue()));
        assertThat("route warnings should be text", firstRoute.warnings.get(0), startsWith("Bicycling directions are in beta. Use caution"));
        assertThat("route warnings should be size 1", firstRoute.warnings.size(), is(1));
        assertThat("route legs should not be null", firstRoute.legs, is(notNullValue()));
        assertThat("route legs should be size 1", firstRoute.legs.size(), is(1));

        //leg
        final GoogleMapsDirectionsResponse.GLeg firstLeg = firstRoute.legs.get(0);
        assertThat("firstLeg steps should not be null", firstLeg.steps, is(notNullValue()));
        assertThat("firstLeg distance should not be null", firstLeg.distance, is(notNullValue()));
        assertThat("firstLeg duration should not be null", firstLeg.duration, is(notNullValue()));
        assertThat("firstLeg end_address should not be null", firstLeg.end_address, is(notNullValue()));
        assertThat("firstLeg start_address should not be null", firstLeg.start_address, is(notNullValue()));
        assertThat("firstLeg end_location should not be null", firstLeg.end_location, is(notNullValue()));
        assertThat("firstLeg start_location should not be null", firstLeg.start_location, is(notNullValue()));
        assertThat("firstLeg start_location should not be null", firstLeg.start_location.lat, is(notNullValue()));
        assertThat("firstLeg steps should be size 1", firstLeg.steps.size(), is(7));

        //steps
        int ctr = 1;
        for (final GoogleMapsDirectionsResponse.GStep step : firstLeg.steps) {
            assertThat("step " + ctr + " should not be null", step, is(notNullValue()));
            assertThat("step " + ctr + " distance should not be null", step.distance, is(notNullValue()));
            assertThat("step " + ctr + " duration should not be null", step.duration, is(notNullValue()));
            assertThat("step " + ctr + " start_location should not be null", step.start_location, is(notNullValue()));
            assertThat("step " + ctr + " end_location should not be null", step.end_location, is(notNullValue()));
            assertThat("step " + ctr + " html_instructions should not be null", step.html_instructions, is(notNullValue()));
            assertThat("step " + ctr + " polyline should not be null", step.polyline, is(notNullValue()));
            assertThat("step " + ctr + " polyline.points should not be null", step.polyline.points, is(notNullValue()));
            assertThat("step " + ctr + " travel_mode should not be null", step.travel_mode, is(notNullValue()));
            assertThat("step " + ctr + " travel_mode should be BICYCLE", step.travel_mode, is("BICYCLING"));
            ctr++;
        }
    }
}