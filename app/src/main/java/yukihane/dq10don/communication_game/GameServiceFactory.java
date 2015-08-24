package yukihane.dq10don.communication_game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceWrapper;

/**
 * Created by yuki on 15/08/20.
 */
public class GameServiceFactory {

    private GameServiceFactory() {
    }

    public static GameService getService(final String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("Need session ID.");
        }

        return new GameServiceWrapper(sessionId);
    }

}
