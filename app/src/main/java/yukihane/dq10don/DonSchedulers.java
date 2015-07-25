package yukihane.dq10don;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * 本アプリケーション独自のスケジューラ群.
 * {@link Schedulers} を参考に作成したシングルトンです.
 */
public final class DonSchedulers {
    private static final DonSchedulers INSTANCE = new DonSchedulers();
    private final Scheduler happyServerScheduler;

    private DonSchedulers() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        happyServerScheduler = Schedulers.from(service);
    }

    /**
     * HappyServiceを利用するためのスケジューラ.
     * 1スレッドで直列に実行したいので用意しています.
     */
    public static Scheduler happyServer() {
        return INSTANCE.happyServerScheduler;
    }
}
