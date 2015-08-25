package yukihane.dq10don.communication_game;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import yukihane.dq10don.communication.utils.SessionProperties;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.exception.HappyServiceException;

import static org.junit.Assert.*;

/**
 * Created by yuki on 15/08/21.
 */
public class GameServiceWrapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceWrapperTest.class);

    private static final String SUCCESS = "0";

    private static String sessionId;

    private GameService service;

    @BeforeClass
    public static void beforeClass() throws IOException {
        sessionId = new SessionProperties().getSessionId();
    }

    @Before
    public void setUp() throws HappyServiceException {
        service = GameServiceFactory.getService(sessionId);
        GameLoginDto res = service.login();
        assertEquals(SUCCESS, res.getResultCode());
    }

    @Test
    public void testGetInfo() throws Exception {
        GameInfoDto res = service.getInfo();
        assertEquals(SUCCESS, res.getResultCode());
        assertNotNull(res.getData());
        LOGGER.info("getinfo: {}", res.getData().getMeatCount());
    }

    @Test
    public void testGetServerTime() throws Exception {
        ServerTimeDto res = service.getServerTime();
        assertEquals(SUCCESS, res.getResultCode());
        assertNotNull(res.getData());
        LOGGER.info("servertime: {}", res.getData().getTime());
    }
}