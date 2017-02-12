package co.uk.sentinelweb.bikemapper.util.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Wrapped rx schedulers for
 * Created by robert on 11/02/2017.
 */
public class Schedulers implements ISchedulers {
    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler io() {
        return rx.schedulers.Schedulers.io();
    }

    @Override
    public Scheduler computaton() {
        return rx.schedulers.Schedulers.computation();
    }

    @Override
    public Scheduler immediate() {
        return rx.schedulers.Schedulers.immediate();
    }
}
