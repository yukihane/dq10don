package yukihane.dq10don.communication.utils;


import android.annotation.TargetApi;
import android.os.Build;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import yukihane.dq10don.communication.dto.LoginCharacterDto;
import yukihane.dq10don.communication.dto.LoginDto;

/**
 * Created by yuki on 15/07/07.
 */
public class HttpUtil {

    public static final String HEADER_SESSION = "X-Smile-3DS-SESSIONID";
    public static final String ENDPOINT = "https://happy.dqx.jp/capi";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private final LoginDto loginInfo;
    private String sessionId;

    public HttpUtil(boolean readSessionFromFile) {
        if (readSessionFromFile) {
            loginInfo = readSessionInfo();
            sessionId = loginInfo.getSessionId();
        } else {
            loginInfo = null;
        }
    }

    public HttpUtil() {
        this(true);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static LoginDto readSessionInfo() {
        try {
            URI file = HttpUtil.class.getClassLoader().getResource("real_session.json").toURI();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(file), LoginDto.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LoginDto getLoginInfo() {
        return loginInfo;
    }

    public String get(String url) {
        try {
            Request req = new Request.Builder().url(url).header(HEADER_SESSION, sessionId).build();
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            response = client.newCall(req).execute();
            String body = response.body().string();
            LOGGER.info("body: {}", body);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String characterselect(int characterListNo) {
        if (loginInfo == null) {
            throw new RuntimeException("ファイルからセッションを作成していない場合このメソッドは使用できません");
        }
        LoginCharacterDto c = loginInfo.getCharacterList().get(characterListNo);
        long webPcNo = c.getWebPcNo();
        return characterselect(webPcNo);
    }

    public String characterselect(long webPcNo) {
        return get(ENDPOINT + "/login/characterselect/" + webPcNo + "/");
    }

    public String getTobatsuList() {
        return get(ENDPOINT + "/tobatsu/tobatsulist/");
    }
}
