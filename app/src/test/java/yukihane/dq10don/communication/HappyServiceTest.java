package yukihane.dq10don.communication;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import yukihane.dq10don.communication.utils.HttpUtil;

/**
 * Created by yuki on 15/07/07.
 */
public class HappyServiceTest {

    private static String sessionId;
    private HappyService service;

    @BeforeClass
    public static void beforeClass() {
        sessionId = HttpUtil.readSessionId();
    }

    @Before
    public void setUp() {
        service = HappyServiceFactory.getService(sessionId);
    }

    @Test
    public void testGetTobatsu() throws Exception {
        String res = new HttpUtil().getTobatsu();
        System.out.println(res);
    }
}