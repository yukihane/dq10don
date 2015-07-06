package yukihane.dq10don.login;

import android.webkit.JavascriptInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ログイン処理を行った結果はJSON形式で帰ってきますが, そのJSONをJavaオブジェクトに変換するクラスです.
 */
public class LoginJsonParse {

    private static final Pattern PATTERN = Pattern.compile("\\{.+\\}");

    private final Logger logger = LoggerFactory.getLogger(LoginJsonParse.class);
    private LoginAccountDto result;

    /**
     * 変換処理を実行します.
     *
     * @param html 受け取った結果. 呼び出し側の都合上, jsonが書かれたhtml形式です.
     */
    @JavascriptInterface
    public void processHTML(String html) {
        try {
            Matcher matcher = PATTERN.matcher(html);
            if (matcher.find()) {
                String jsonText = matcher.group();
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(jsonText, LoginAccountDto.class);
            }
        } catch (IOException e) {
            logger.error("parse error", e);
        }
    }

    /**
     * 直前に実行した変換処理結果を取得します.
     *
     * @return 変換結果. 処理を行っていない場合や変換処理でエラーが発生した場合にはnull.
     */
    public LoginAccountDto getResult() {
        return result;
    }
}
