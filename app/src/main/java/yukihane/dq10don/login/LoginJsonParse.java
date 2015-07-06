package yukihane.dq10don.login;

import android.webkit.JavascriptInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginJsonParse {

    private static final String TAG = "LoginJsonParse";
    private static final Pattern PATTERN = Pattern.compile("\\{.+\\}");

    private final Logger logger = LoggerFactory.getLogger(LoginJsonParse.class);
    private LoginAccountDto result;

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

    public LoginAccountDto getResult() {
        return result;
    }
}
