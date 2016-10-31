package co.uk.sentinelweb.bikemapper.locationedit;

import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.core.model.SavedLocation;

/**
 * Created by robert on 14/06/16.
 */
public interface LocationEditView extends MVPContract.BaseView<LocationEditPresenter> {

    void setLoadingIndicator(boolean loading);

    void showLoadingError(String message);

    void setLocation(SavedLocation location);
}
