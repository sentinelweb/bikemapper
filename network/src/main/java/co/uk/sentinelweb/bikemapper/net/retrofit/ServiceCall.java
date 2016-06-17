package co.uk.sentinelweb.bikemapper.net.retrofit;


import java.io.IOException;

import co.uk.sentinelweb.bikemapper.net.Const;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Executes a service call.
 * <p>
 * Extra error handling can be added here.
 * Created by robert on 28/11/15.
 */
public class ServiceCall<T> {

    public T call(final Call<T> call) {
        T result = null;
        try {
            final Response<T> response = call.execute();
            result = response.body();
        } catch (final IOException e) {
            System.err.println(Const.LOG + ":" + "Service call failed.");
            e.printStackTrace(System.err);
        }
        return result;
    }

}
