package yukihane.dq10don.communication_game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import yukihane.dq10don.communication.utils.HttpUtil;
import yukihane.dq10don.communication.utils.SessionProperties;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;

/**
 * Created by yuki on 2015/08/15.
 */
public class GameLoginTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoginTest.class);

    @Test
    public void testGameLogin() {
        SessionProperties sessionProp = new SessionProperties();

        String sessionId = sessionProp.getSessionId();
        HttpUtil httpUtil = new HttpUtil(false);
        httpUtil.setSessionId(sessionId);
        httpUtil.characterselect(sessionProp.getWebPcNo());
        String res = httpUtil.get("https://happy.dqx.jp/capi/farm/login");
        LOGGER.info("login: {}", res);

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "data={force:true}&");
            Request req = new Request.Builder()
                    .url("https://game.happy.dqx.jp/api/member/login")
                    .header(HttpUtil.HEADER_SESSION, sessionId)
                    .post(requestBody)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            response = client.newCall(req).execute();
            String body = response.body().string();
            LOGGER.info("gamelogin: {}", body);

            ObjectMapper mapper = new ObjectMapper();
            GameLoginDto gameLoginGto = mapper.readValue(body, GameLoginDto.class);
            String farmToken = gameLoginGto.getData().getFarmToken();

            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "data={}&");
            req = new Request.Builder()
                    .url("https://game.happy.dqx.jp/api/farm/getinfo")
                    .header(HttpUtil.HEADER_SESSION, sessionId)
                    .header(HttpUtil.HEADER_FARMTOKEN, farmToken)
                    .header(HttpUtil.HEADER_CASINOTOKEN, farmToken)
                    .post(requestBody)
                    .build();

            response = client.newCall(req).execute();
            body = response.body().string();
            LOGGER.info("getinfo: {}", body);

            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "data={}&");
            req = new Request.Builder()
                    .url("https://game.happy.dqx.jp/api/common/getservertime")
                    .header(HttpUtil.HEADER_SESSION, sessionId)
                    .header(HttpUtil.HEADER_FARMTOKEN, farmToken)
                    .header(HttpUtil.HEADER_CASINOTOKEN, farmToken)
                    .post(requestBody)
                    .build();
            response = client.newCall(req).execute();
            body = response.body().string();
            LOGGER.info("getservertime: {}", body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
