package yukihane.dq10don.login;

import static org.junit.Assert.*;

import android.annotation.TargetApi;
import android.os.Build;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import yukihane.dq10don.login.LoginAccountDto;
import yukihane.dq10don.login.LoginJsonParse;

/**
 * Created by yuki on 15/07/05.
 */
public class LoginJsonParseTest {

    private static String json;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @BeforeClass
    public static void beforeClass() throws IOException, URISyntaxException {

        URI file = LoginJsonParseTest.class.getClassLoader().getResource("oauth.json").toURI();

        try (FileReader reader = new FileReader(new File(file))) {
            BufferedReader br = new BufferedReader(reader);
            json = br.readLine();
        }
    }

    /**
     * JSONが想定通りPOJOに変換されるかのテスト
     */
    @Test
    public void testProcessHTML() throws Exception {
        LoginJsonParse parse = new LoginJsonParse();
        parse.processHTML(json);
        LoginAccountDto result = parse.getResult();

        assertEquals(1, result.getAccountType());
        assertEquals("dummy_cisuserid", result.getCisuserid());
        assertEquals(0, result.getResultCode());
        assertEquals("sessionid_dummy", result.getSessionId());

        List<CharacterDto> cList = result.getCharacterList();
        assertEquals(3, cList.size());

        CharacterDto c1 = cList.get(0);
        assertEquals(c1.getCharacterName(), "キャラ名1");
        assertTrue(c1.getIconUrl().startsWith("http"));
        assertEquals(c1.getJob(), "レンジャー");

        CharacterDto c2 = cList.get(1);
        assertEquals(3, c2.getJobId());
        assertEquals(56, c2.getLv());

        CharacterDto c3 = cList.get(2);
        assertEquals("ZZ777-777", c3.getSmileUniqueNo());
        assertEquals(6666666L, c3.getWebPcNo());

    }
}
