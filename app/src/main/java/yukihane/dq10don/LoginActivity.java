package yukihane.dq10don;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends ActionBarActivity {

    private static final String OAUTH_URL = "https://secure.square-enix.com/oauth/oa/";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CookieManager.getInstance().removeAllCookie();

        String url = OAUTH_URL + "oauthauth?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1";

        webView = (WebView) findViewById(R.id.loginWebView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new LoginView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsonParser(), "HTMLOUT");
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // トークンアプリがインストールされていなければ(intentを受け取れるアプリが存在しないなら)　ボタンをdisableに
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

            if (url.startsWith("https://happy.dqx.jp/capi/login/securelogin/")) {
                Log.d("methodcalled", "onPageFinished: " + url);
                webView.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }

            super.onPageFinished(view, url);
        }
    }

    private class LoginJsonParser {

        private final Pattern pattern = Pattern.compile("\\{.+\\}");

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                String res = matcher.group();
                Log.d("result", res);
            }
        }
    }
}
