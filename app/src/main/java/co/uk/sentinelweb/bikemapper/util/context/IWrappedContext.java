package co.uk.sentinelweb.bikemapper.util.context;

/**
 * Created by robert on 12/02/2017.
 */

public interface IWrappedContext {
    int getColor(int resId);

    String getString(int resId);

    String getString(int resId, Object... args);

    float getDimension(int resId);

}
