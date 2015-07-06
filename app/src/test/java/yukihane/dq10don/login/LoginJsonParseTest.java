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

    @Test
    public void testProcessHTML() throws Exception {
        LoginJsonParse parse = new LoginJsonParse();
        parse.processHTML(json);
        LoginAccountDto result = parse.getResult();

        assertEquals(1, result.getAccountType());
    }
}