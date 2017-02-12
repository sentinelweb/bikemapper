package co.uk.sentinelweb.bikemapper.util.context;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Wraps the context to make dependents testable
 * Created by robert on 12/02/2017.
 */
public class WrappedContext implements IWrappedContext {

    final Context c;

    public WrappedContext(final Context c) {
        this.c = c;
    }

    @Override
    public int getColor(final int resId) {
        return ContextCompat.getColor(c, resId);
    }

    @Override
    public String getString(final int resId) {
        return c.getString(resId);
    }

    @Override
    public String getString(final int resId, final Object... args) {
        return c.getString(resId, args);
    }

    @Override
    public float getDimension(final int resId) {
        return c.getResources().getDimension(resId);
    }
}
