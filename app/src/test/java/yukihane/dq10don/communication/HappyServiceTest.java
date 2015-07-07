package yukihane.dq10don.communication;

import org.junit.BeforeClass;
import org.junit.Test;

import yukihane.dq10don.communication.dto.CharaSelectDto;
import yukihane.dq10don.communication.utils.HttpUtil;

import static org.junit.Assert.assertEquals;
import static yukihane.dq10don.communication.utils.HttpUtil.ENDPOINT;

/**
 * Created by yuki on 15/07/07.
 */
public class HappyServiceTest {

    private static HappyService service;

    @BeforeClass
    public static void beforeClass() {
        HttpUtil util = new HttpUtil();
        service = HappyServiceFactory.getService(util.getSessionId());
        CharaSelectDto sel = service.characterSelect(util.getLoginInfo().getCharacterList().get(0).getWebPcNo());
        assertEquals(0, sel.getResultCode());
    }

    @Test
    public void testGetTobatsu() throws Exception {
        System.out.println("test executed");
//        HttpUtil util = new HttpUtil();
//        long webPcNo = util.getLoginInfo().getCharacterList().get(0).getWebPcNo();
//        String res = util.get(ENDPOINT + "/login/characterselect/"+webPcNo+"/");
//        System.out.println(res);
    }
}