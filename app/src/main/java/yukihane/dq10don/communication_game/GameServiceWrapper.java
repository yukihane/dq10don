package yukihane.dq10don.communication_game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.login.Data;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.exception.HappyServiceException;

import static yukihane.dq10don.communication_game.Constants.GAMESERVICE_ENDPOINT;
import static yukihane.dq10don.communication_game.Constants.HEADER_CASINOTOKEN;
import static yukihane.dq10don.communication_game.Constants.HEADER_FARMTOKEN;
import static yukihane.dq10don.communication_game.Constants.RESULT_SUCCESS;

/**
 * Created by yuki on 2015/08/18.
 */
public class GameServiceWrapper implements GameService, RequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceWrapper.class);

    private final String sessionId;
    private final RetrofitGameService service;

    private String token;

    GameServiceWrapper(String sessionId) {
        this.sessionId = sessionId;

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(GAMESERVICE_ENDPOINT)
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(this);

        RestAdapter adapter = builder.build();

        service = adapter.create(RetrofitGameService.class);
    }

    @Override
    public GameLoginDto login() throws HappyServiceException {
        try {
            LOGGER.debug("GameService login");
            GameLoginDto res = service.login("{force:true}");
            if (!RESULT_SUCCESS.equals(res.getResultCode())) {
                LOGGER.error("GameService login error resultCode: {}",
                        res.getResultCode());
            }
            Data data = res.getData();
            if (data != null) {
                // data が null になることは無いと思われるが、念の為
                token = data.getFarmToken();
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("login error", e);
        }
    }

    @Override
    public GameInfoDto getInfo() throws HappyServiceException {
        try {
            GameInfoDto res = service.getInfo("{}");
            if (!RESULT_SUCCESS.equals(res.getResultCode())) {
                LOGGER.error("GameService getInfo error resultCode: {}",
                        res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("getInfo error", e);
        }
    }

    @Override
    public ServerTimeDto getServerTime() throws HappyServiceException {
        try {
            LOGGER.debug("GameService getServerTime");
            ServerTimeDto res = service.getServerTime("{}");
            if (!RESULT_SUCCESS.equals(res.getResultCode())) {
                LOGGER.error("GameService getServerTime error resultCode: {}",
                        res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("getServerTime error", e);
        }
    }

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestFacade}.
     *
     * @param request
     */
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(yukihane.dq10don.communication.Constants.HEADER_SESSION, sessionId);
        if (token != null && !token.isEmpty()) {
            request.addHeader(HEADER_FARMTOKEN, token);
            request.addHeader(HEADER_CASINOTOKEN, token);
        }
    }
}
