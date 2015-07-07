package yukihane.dq10don.communication.utils;


import android.annotation.TargetApi;
import android.os.Build;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by yuki on 15/07/07.
 */
public class HttpUtil {

    private static final String ENDPOINT = "https://happy.dqx.jp/capi";

    public static final String HEADER_SESSION = "X-Smile-3DS-SESSIONID";

    private String sessionId;

    public HttpUtil(boolean readSessionIdFromFile) {
        sessionId = readSessionId();
    }

    public HttpUtil() {
        this(true);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String readSessionId() {

        try {
            URI file = HttpUtil.class.getClassLoader().getResource("sessionId.txt").toURI();

            String sess = null;
            try (FileReader reader = new FileReader(new File(file))) {
                BufferedReader br = new BufferedReader(reader);
                sess = br.readLine();
                System.out.println("[" + sess + "]");
                return sess;
            } finally {
                if (sess == null || sess.isEmpty()) {
                    throw new RuntimeException("/app/src/test/resources/sessionId.txt というファイルを作成し, 有効なsession IDを記入してください");
                }
            }
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

    public String get(String url) {
        try {
            Request req = new Request.Builder().url(url).header(HEADER_SESSION, sessionId).build();
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            response = client.newCall(req).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTobatsu() {
        return get(ENDPOINT + "/tobatsu/tobatsulist/");
    }
}
