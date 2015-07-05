package yukihane.dq10don.utils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginJsonParse {

    private static final Pattern PATTERN = Pattern.compile("\\{.+\\}");

    private LoginAccountDto result;

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String json) {
        try {
            Matcher matcher = PATTERN.matcher(json);
            if (matcher.find()) {
                String res = matcher.group();
                ObjectMapper mapper = new ObjectMapper();
                LoginAccountDto dto = mapper.readValue(json, LoginAccountDto.class);
            }
        } catch (IOException e) {
            Log.e("exception", "JSON parse failed.", e);
        }
    }

    public LoginAccountDto getResult() {
        return result;
    }
}
