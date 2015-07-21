package yukihane.dq10don.login.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import yukihane.dq10don.login.presenter.LoginPresenter;
import yukihane.dq10don.R;
import yukihane.dq10don.login.model.JsonLogin;
import yukihane.dq10don.login.model.UserIdGetter;


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

        CookieSyncManager.createInstance(this);

        presenter.onCreate(userId);
    }

    @Override
    public void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
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
        webView.setWebViewClient(new LoginWebViewClient(webView, userId));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(parser, "HTMLOUT");
        webView.addJavascriptInterface(userIdGetter, "UserIdGetter");
        webView.loadUrl(url);
    }
}
