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


public class LoginActivity extends ActionBarActivity implements LoginPresenter.View {

    private final Logger logger = LoggerFactory.getLogger(LoginActivity.class);

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        presenter.onCreate(userId);
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

    @Override
    public void loginSuccess(String res, String usedUserId) {
        Intent intent = new Intent();
        intent.putExtra("result", res);
        intent.putExtra("userId", usedUserId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loginFail() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void initializeWebView(String url, String userId, JsonLogin parser, UserIdGetter userIdGetter) {
        // セッション情報が残っていると前回のログインを引き継いでしまうためログイン処理が行えない
        CookieManager.getInstance().removeAllCookie();

        WebView webView = (WebView) findViewById(R.id.loginWebView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new LoginView(webView, userId));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(parser, "HTMLOUT");
        webView.addJavascriptInterface(userIdGetter, "UserIdGetter");
        webView.loadUrl(url);
    }

    private static class LoginView extends WebViewClient {

        private static final Logger logger = LoggerFactory.getLogger(LoginView.class);

        private final WebView webView;
        private final String userId;

        public LoginView(WebView webView, String userId) {
            this.webView = webView;
            this.userId = userId;
        }

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
                webView.loadUrl("javascript:window.UserIdGetter.get(document.getElementById('sqexid').value);");
            }
        }
    }
}
