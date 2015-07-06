package yukihane.dq10don.login;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.JavascriptInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ログイン処理を行った結果はJSON形式で帰ってきますが, そのJSONをJavaオブジェクトに変換するクラスです.
 */
public class LoginJsonParse {

    private static final Pattern PATTERN = Pattern.compile("\\{.+\\}");

    private final Logger logger = LoggerFactory.getLogger(LoginJsonParse.class);
    private final EventListener listener;

    public LoginJsonParse(EventListener listener) {
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
        LoginAccountDto result = null;
        try {
            Matcher matcher = PATTERN.matcher(html);
            if (matcher.find()) {
                String jsonText = matcher.group();
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(jsonText, LoginAccountDto.class);
            }
        } catch (IOException e) {
            logger.error("parse error", e);
        } finally {
            listener.onComplete(result);
        }
    }

    public interface EventListener {
        void onComplete(LoginAccountDto dto);
    }
}
