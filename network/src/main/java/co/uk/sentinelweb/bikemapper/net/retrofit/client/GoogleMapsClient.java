package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import co.uk.sentinelweb.bikemapper.net.services.GoogleMapsService;

/**
 * Created by robert on 17/06/16.
 */
public class GoogleMapsClient extends BaseClient {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    GoogleMapsService service;

    public GoogleMapsClient(final IApiKeyProvider keyProvider) {
        super(keyProvider);
        service = (GoogleMapsService) createClient(BASE_URL, GoogleMapsService.class);
    }


}
