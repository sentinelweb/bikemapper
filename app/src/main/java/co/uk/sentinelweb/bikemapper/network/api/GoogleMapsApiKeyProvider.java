package co.uk.sentinelweb.bikemapper.network.api;

import co.uk.sentinelweb.bikemapper.BuildConfig;
import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;

/**
 * Created by robert on 01/11/2016.
 */

public class GoogleMapsApiKeyProvider implements IApiKeyProvider {
    @Override
    public String getApiKey() {
        return BuildConfig.GOOGLE_MAPS_KEY;
    }
}
