package co.uk.sentinelweb.bikemapper.locationmap;

import co.uk.sentinelweb.bikemapper.MVPContract;

/**
 * Created by robert on 11/02/2017.
 */
public interface LocationMapContract {
    interface Presenter extends MVPContract.BasePresenter {
        void setData(Long id);
    }

    interface View extends MVPContract.BaseView<Presenter> {
        //void setLoadingIndicator(boolean loading);

        void showError(String message);

        void setModel(LocationMapModel location);

//        void setCurrentLocation(Location location);
//
//        void showRoutes(List<Route> routes);
    }
}
