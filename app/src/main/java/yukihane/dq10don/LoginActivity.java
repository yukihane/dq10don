package yukihane.dq10don;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import yukihane.dq10don.login.JsonLogin;
import yukihane.dq10don.login.UserIdGetter;


public class LoginActivity extends ActionBarActivity {

    private static final String OAUTH_URL = "https://secure.square-enix.com/oauth/oa/";

    private final Logger logger = LoggerFactory.getLogger(LoginActivity.class);

    private WebView webView;
    private JsonLogin parser;
    private UserIdGetter userIdGetter;

    /**
     * 本Activityでログイン画面を表示する際に、初期情報として設定するユーザーID.
     * この情報は、過去ログインした際の情報が保存されており, そこから取得されます.
     */
    private String userId;

    /**
     * 今回のログイン処理で入力したユーザーID.
     * 正常にログインが完了した場合には正しいユーザーIDです.
     * ログインが失敗するなど、正常に完了しなかった場合には正しいユーザーIDが設定されているとは限りません.
     */
    private String usedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // セッション情報が残っていると前回のログインを引き継いでしまうためログイン処理が行えない
        CookieManager.getInstance().removeAllCookie();

        String url = OAUTH_URL + "oauthauth?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1";

        parser = new JsonLogin(res -> {
            if (res != null) {
                logger.info("login success");
                Intent intent = new Intent();
                intent.putExtra("result", res);
                intent.putExtra("userId", usedUserId);
                setResult(RESULT_OK, intent);
            } else {
                logger.error("login information read error.");
                setResult(RESULT_OK);
            }
            finish();
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        if (userId == null) {
            userId = "";
        }

        userIdGetter = new UserIdGetter(res -> usedUserId = res);

        webView = (WebView) findViewById(R.id.loginWebView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new LoginView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(parser, "HTMLOUT");
        webView.addJavascriptInterface(userIdGetter, "UserIdGetter");
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // トークンアプリがインストールされていなければ(intentを受け取れるアプリが存在しないなら)ボタンをdisableに
        // 参考: https://developer.android.com/training/basics/intents/sending.html
        Intent otpIntent = createOtpIntent();

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(otpIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            getMenuInflater().inflate(R.menu.menu_login, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_tokenapp) {
            Intent intent = createOtpIntent();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createOtpIntent() {
        Uri uri = Uri.parse("sqextoken://appviewotp");
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private class LoginView extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            logger.debug("onPageFinished: {}", url);

            if (url.split("\\?")[0].equals("https://secure.square-enix.com/oauth/oa/oauthlogin")) {
                // ログイン初期画面描画時にはユーザIDを自動入力する
                webView.loadUrl("javascript: (function() {document.getElementById('sqexid').value = '" + userId + "';})();");
            } else if (url.startsWith("https://happy.dqx.jp/capi/login/securelogin/")) {
                // ログイン成功した場合には戻ってきたJSONを処理する
                webView.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }

            super.onPageFinished(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            logger.debug("onPageStarted: {}", url);
            if (url.startsWith("https://secure.square-enix.com/oauth/oa/oauthlogin.send")) {
                // 画面で入力されたユーザーID(正しい値であるかはここではわからない)を保持する
                webView.loadUrl("javascript:window.UserIdGetter.get('onPageStarted ' + document.getElementById('sqexid').value);");
            }
        }
    }
}
