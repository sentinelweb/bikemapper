package co.uk.sentinelweb.bikemapper.locationmap;

import co.uk.sentinelweb.bikemapper.BaseView;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;

/**
 * Created by robert on 14/06/16.
 */
public interface LocationMapView extends BaseView<LocationMapPresenter> {

    void setLoadingIndicator(boolean loading);

    void showLoadingError(String message);

    void setLocation(SavedLocation location);
}
