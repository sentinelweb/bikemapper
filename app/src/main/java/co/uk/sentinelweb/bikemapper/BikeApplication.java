package co.uk.sentinelweb.bikemapper;

import android.app.Application;

/**
 * Created by robert on 14/06/16.
 */
public class BikeApplication extends Application {

    BikeApplicationComponent component;

    public BikeApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = new BikeApplicationComponent.Injector().createComponent(this);
    }
}
