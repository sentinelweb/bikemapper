package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import org.junit.Test;

import java.io.IOException;

import co.uk.sentinelweb.bikemapper.net.response.google.GoogleMapsDirectionsResponseTest;
import co.uk.sentinelweb.bikemapper.net.response.google.directions.GoogleMapsDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.places.GoogleMapsPlacesTextResponse;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 17/06/16.
 */
public class GoogleMapsClientTest {
    private static class TestKeyProvider implements IApiKeyProvider {

        @Override
        public String getApiKey() {
            return System.getProperty("googlemaps.api.key", "NO_KEY");
        }
    }

    @Test
    public void shouldTestGmapDirectionsResponse() {
        final GoogleMapsClient client = new GoogleMapsClient(new TestKeyProvider());
        final Call<GoogleMapsDirectionsResponse> directionsResponse = client.getService().getDirections("51.525493,-0.0822173", "51.5202109,-0.1070064", client._keyProvider.getApiKey(), "bicycling");
        try {
            final Response<GoogleMapsDirectionsResponse> execute = directionsResponse.execute();
            final GoogleMapsDirectionsResponse body = execute.body();
            GoogleMapsDirectionsResponseTest.checkResponse(body);
        } catch (final IOException e) {
            e.printStackTrace();
            assertTrue("Exception running test", false);
        }
    }

    @Test
    public void shouldTestGmapPlacessResponse() {
        final GoogleMapsClient client = new GoogleMapsClient(new TestKeyProvider());
        final Call<GoogleMapsPlacesTextResponse> directionsResponse = client.getService().getTextPlaces("pitfield", client._keyProvider.getApiKey());
        try {
            final Response<GoogleMapsPlacesTextResponse> execute = directionsResponse.execute();
            final GoogleMapsPlacesTextResponse body = execute.body();
            // TODO validate output
            //GoogleMapsDirectionsResponseTest.checkResponse(body);
        } catch (final IOException e) {
            e.printStackTrace();
            assertTrue("Exception running test", false);
        }
    }


}
