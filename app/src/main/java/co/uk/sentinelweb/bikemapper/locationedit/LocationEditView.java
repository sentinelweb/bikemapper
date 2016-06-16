package co.uk.sentinelweb.bikemapper.locationedit;

import co.uk.sentinelweb.bikemapper.BaseView;
import co.uk.sentinelweb.bikemapper.core.model.Location;

/**
 * Created by robert on 14/06/16.
 */
public interface LocationEditView extends BaseView<LocationEditPresenter> {

    void setLoadingIndicator(boolean loading);

    void showLoadingError(String message);

    void setLocation(Location location);
}
