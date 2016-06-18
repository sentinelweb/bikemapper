package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import org.junit.Test;

import java.io.IOException;

import co.uk.sentinelweb.bikemapper.net.response.mapquest.MapQuestDirectionsResponse;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import retrofit2.Call;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 17/06/16.
 */
public class MapquestDirectionsClientTest {
    private static class TestKeyProvider implements IApiKeyProvider {

        @Override
        public String getApiKey() {
            return "FMRPx9Oy4ICRoMulfDFvtUvfJiSIbb4K";
        }
    }

    @Test
    public void shouldTestMapquestDirections() {
        final MapQuestDirectionsClient client = new MapQuestDirectionsClient(new TestKeyProvider());
        final Call<MapQuestDirectionsResponse> directionsResponse = client.service.getDirections("51.525493,-0.0822173", "51.5202109,-0.1070064", client._keyProvider.getApiKey(), "bicycle");
        try {
            final Response<MapQuestDirectionsResponse> execute = directionsResponse.execute();
            final MapQuestDirectionsResponse body = execute.body();
            assertThat("response was null", body, is(notNullValue()));
            assertThat("response wasn't OK null", body.info.status, is(0));
        } catch (final IOException e) {
            e.printStackTrace();
            assertTrue("Exception running test", false);
        }
    }


}
