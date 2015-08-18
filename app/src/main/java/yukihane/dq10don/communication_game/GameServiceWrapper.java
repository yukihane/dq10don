package yukihane.dq10don.communication_game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RetrofitError;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.farm.mowgrass.MowGrassDto;
import yukihane.dq10don.communication_game.dto.farm.openalltresurebox.OpenAllTresureBoxDto;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 2015/08/18.
 */
public class GameServiceWrapper implements GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceWrapper.class);
    private static final String RESULT_SUCCESS = "0";

    private final GameService service;

    public GameServiceWrapper(GameService service) {
        this.service = service;
    }

    @Override
    public GameLoginDto login() throws HappyServiceException {
        try {
            LOGGER.debug("GameService login");
            GameLoginDto res = service.login();
            if (!RESULT_SUCCESS.equals(res.getResultCode())) {
                LOGGER.error("GameService login error resultCode: {}",
                        res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("login error", e);
        }
    }

    @Override
    public GameInfoDto getInfo() throws HappyServiceException {
        try {
            GameInfoDto res = service.getInfo();
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
            ServerTimeDto res = service.getServerTime();
            if (!RESULT_SUCCESS.equals(res.getResultCode())) {
                LOGGER.error("GameService getServerTime error resultCode: {}",
                        res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("getServerTime error", e);
        }
    }
}
