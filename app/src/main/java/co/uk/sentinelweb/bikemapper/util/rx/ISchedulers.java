package co.uk.sentinelweb.bikemapper.util.rx;

import rx.Scheduler;

/**
 * Created by robert on 11/02/2017.
 */
public interface ISchedulers {
    public Scheduler main();

    public Scheduler io();

    public Scheduler computaton();

    public Scheduler immediate();
}
