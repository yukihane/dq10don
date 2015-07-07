package yukihane.dq10don.communication;

import android.annotation.TargetApi;
import android.os.Build;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import yukihane.dq10don.communication.utils.HttpUtil;

import static org.junit.Assert.*;

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