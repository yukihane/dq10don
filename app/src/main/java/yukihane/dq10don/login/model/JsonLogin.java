package yukihane.dq10don.login.model;

import android.webkit.JavascriptInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ログイン処理を行った結果はHTML形式で帰ってきますが, そのHTMLをJSONに変換するクラスです.
 */
public class JsonLogin {

    private static final Pattern PATTERN = Pattern.compile("\\{.+\\}");

    private final Logger logger = LoggerFactory.getLogger(JsonLogin.class);
    private final EventListener listener;

    public JsonLogin(EventListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        this.listener = listener;
    }

    /**
     * 変換処理を実行します.
     *
     * @param html 受け取った結果. 呼び出し側の都合上, jsonが書かれたhtml形式です.
     */
    @JavascriptInterface
    public void processHTML(String html) {
        String result = null;
        try {
            Matcher matcher = PATTERN.matcher(html);
            if (matcher.find()) {
                result = matcher.group();
            }
        } finally {
            listener.onComplete(result);
        }
    }

    public interface EventListener {
        void onComplete(String result);
    }
}
