package yukihane.dq10don.base.presenter;

import rx.Observable;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/29.
 */
public interface BasePresenter {
    void onCreate();

    void onUpdateClick();

    void onViewCreated();

    void onDestroy();

    interface View<T> {

        void setHeader(String sqexid, String smileUniqNo);

        void tobatsuListUpdate(T list);

        void bind(Observable<?> observable);

        void showMessage(HappyServiceException ex);

        void showMessage(String message);

    }
}
