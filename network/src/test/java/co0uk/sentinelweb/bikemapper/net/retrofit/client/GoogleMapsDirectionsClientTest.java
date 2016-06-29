package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import org.junit.Test;

import java.io.IOException;

import co.uk.sentinelweb.bikemapper.net.response.google.GoogleMapsDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.response.google.GoogleMapsDirectionsResponseTest;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 17/06/16.
 */
public class GoogleMapsDirectionsClientTest {
    private static class TestKeyProvider implements IApiKeyProvider {

        @Override
        public String getApiKey() {
            //return "AIzaSyDQDz5a82BWWsF673kjB6KjLMcdl1h5mNc";
            return "AIzaSyCBN95GAgbRr650S2eL_B8KqlaQP7FlXpM";// using browser key for testing
        }
    }

    @Test
    public void shouldTestGmapResponse() {
        final GoogleMapsDirectionsClient client = new GoogleMapsDirectionsClient(new TestKeyProvider());
        final Call<GoogleMapsDirectionsResponse> directionsResponse = client.service.getDirections("51.525493,-0.0822173", "51.5202109,-0.1070064", client._keyProvider.getApiKey(), "bicycling");
        try {
            final Response<GoogleMapsDirectionsResponse> execute = directionsResponse.execute();
            final GoogleMapsDirectionsResponse body = execute.body();
            GoogleMapsDirectionsResponseTest.checkResponse(body);
        } catch (final IOException e) {
            e.printStackTrace();
            assertTrue("Exception running test", false);
        }
    }


}
