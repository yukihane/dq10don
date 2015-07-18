package yukihane.dq10don.login.model;

import android.webkit.JavascriptInterface;

/**
 * webview 上のログイン画面でユーザーが入力したidを取得するためのクラス.
 */
public class UserIdGetter {

    private final EventListener listener;

    public UserIdGetter(EventListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        this.listener = listener;
    }

    @JavascriptInterface
    public void get(String userId) {
        listener.onComplete(userId);
    }

    public interface EventListener {
        void onComplete(String userId);
    }
}
