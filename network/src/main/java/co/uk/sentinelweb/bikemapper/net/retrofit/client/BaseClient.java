package co.uk.sentinelweb.bikemapper.net.retrofit.client;

import co.uk.sentinelweb.bikemapper.net.retrofit.IApiKeyProvider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by robert on 17/06/16.
 */
public class BaseClient<T> {

    IApiKeyProvider _keyProvider;

    public BaseClient(final IApiKeyProvider keyProvider) {
        _keyProvider = keyProvider;
    }

    public Object createClient(final String baseUrl, final Class<?> serviceClass) {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }
}
