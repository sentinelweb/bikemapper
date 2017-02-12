package co.uk.sentinelweb.bikemapper.template;

import co.uk.sentinelweb.bikemapper.MVPContract;
import co.uk.sentinelweb.bikemapper.domain.model.SavedLocation;

/**
 * Created by robert on 14/06/16.
 */
public interface LocationTmplView extends MVPContract.BaseView<LocationTmplPresenter> {

    void setLoadingIndicator(boolean loading);

    void showLoadingError(String message);

    void setLocation(SavedLocation location);
}
