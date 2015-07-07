package yukihane.dq10don.login;

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

import yukihane.dq10don.communication.dto.LoginDto;
import yukihane.dq10don.communication.dto.LoginCharacterDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by yuki on 15/07/05.
 */
public class LoginAccountDtoTest {

    private static String jsonSuccess;
    private static String jsonFail = "{\"resultCode\":999}";
    private static String jsonError = "<html><head></head><body>login error</body></html>";

    /**
     * ファイルの最初の1行を読み込みます.
     *
     * @param filename 読み込むファイル名
     * @return 読み込んだ内容
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String readLine(String filename) throws URISyntaxException, IOException {

        URI file = LoginAccountDtoTest.class.getClassLoader().getResource(filename).toURI();

        try (FileReader reader = new FileReader(new File(file))) {
            BufferedReader br = new BufferedReader(reader);
            return br.readLine();
        }
    }

    @BeforeClass
    public static void beforeClass() throws IOException, URISyntaxException {
        jsonSuccess = readLine("login_success.json");
    }

    /**
     * JSONが想定通りPOJOに変換されるかのテスト
     */
    @Test
    public void testSuccess() throws Exception {
        LoginDto result = LoginDto.fromJson(jsonSuccess);

        assertEquals(1, result.getAccountType());
        assertEquals("dummy_cisuserid", result.getCisuserid());
        assertEquals(0, result.getResultCode());
        assertEquals("sessionid_dummy", result.getSessionId());

        List<LoginCharacterDto> cList = result.getCharacterList();
        assertEquals(3, cList.size());

        LoginCharacterDto c1 = cList.get(0);
        assertEquals(c1.getCharacterName(), "キャラ名1");
        assertTrue(c1.getIconUrl().startsWith("http"));
        assertEquals(c1.getJob(), "レンジャー");

        LoginCharacterDto c2 = cList.get(1);
        assertEquals(3, c2.getJobId());
        assertEquals(56, c2.getLv());

        LoginCharacterDto c3 = cList.get(2);
        assertEquals("ZZ777-777", c3.getSmileUniqueNo());
        assertEquals(6666666L, c3.getWebPcNo());
    }

    /**
     * ログイン失敗した場合のjsonが帰ってきた場合のテストケース.
     * 実際にどのようなものが帰ってくるかわからないので, resultCodeが非0であると想定しています.
     */
    @Test
    public void testFail() throws IOException {
        LoginDto result = LoginDto.fromJson(jsonFail);
        assertEquals(999, result.getResultCode());
    }

    @Test(expected = IOException.class)
    public void testLoginError() throws IOException {
        LoginDto result = LoginDto.fromJson(jsonError);
    }
}
