package co.uk.sentinelweb.bikemapper;

/**
 * Created by robert on 29/10/2016.
 */

public interface MVPContract {
    interface BasePresenter {

        void subscribe();

        void unsubscribe();

    }

    interface BaseView<T extends BasePresenter> {

        void setPresenter(T presenter);

    }
}
