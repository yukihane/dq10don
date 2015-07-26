package yukihane.dq10don.debug.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.Utils;

/**
 * Created by yuki on 15/07/26.
 */
public class DebugPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugPresenter.class);

    private View view;

    public DebugPresenter(View view) {
        this.view = view;
    }

    public void onCreate() {
        view.setEventListener();
        view.showDownloadDirectory();
    }

    public void exportLog(File fromDir, File toDir) {

        Observable<Void> observable = Observable.create(new Observable.OnSubscribe<Void>() {

            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onStart();

                File[] logfiles = fromDir.listFiles((File dir, String filename) -> {
                    if (filename.endsWith("log") || filename.endsWith("log.txt")) {
                        return true;
                    }
                    return false;
                });
                LOGGER.info("export log file(s): from:{} to:{}", logfiles, toDir);

                for (File f : logfiles) {
                    try {
                        Utils.copy(f, toDir);
                    } catch (IOException e) {
                        LOGGER.error("file copy failed", e);
                    }
                }

                LOGGER.info("completed export log file(s)");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());


        view.bind(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {
                view.showMessage(Message.COMPLETED_EXPORTFILE);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Void aVoid) {
            }
        });
    }

    public enum Message {
        COMPLETED_EXPORTFILE;
    }

    public interface View {
        void showDownloadDirectory();

        void setEventListener();

        void bind(Observable<?> observable);

        void showMessage(Message message);
    }
}
