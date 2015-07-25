package yukihane.dq10don.communication;

import org.junit.BeforeClass;
import org.junit.Test;

import yukihane.dq10don.communication.dto.CharaSelectDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.communication.utils.HttpUtil;
import yukihane.dq10don.exception.HappyServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yuki on 15/07/07.
 */
public class HappyServiceTest {

    private static HappyService service;

    @BeforeClass
    public static void beforeClass() throws HappyServiceException {
        HttpUtil util = new HttpUtil();
        service = HappyServiceFactory.getService(util.getSessionId());
        CharaSelectDto sel = service.characterSelect(util.getLoginInfo().getCharacterList().get(0).getWebPcNo());
        assertEquals(0, sel.getResultCode());
    }

    @Test
    public void testGetTobatsu() throws Exception {
        TobatsuDto res = service.getTobatsuList();
        assertNotNull(res);
        System.out.println(res.toString());
    }
}
