package yukihane.dq10don;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.List;


public class LoginActivity extends ActionBarActivity {

    private static final String OAUTH_URL = "https://secure.square-enix.com/oauth/oa/";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // トークンアプリがインストールされていなければ(intentを受け取れるアプリが存在しないなら)　ボタンをdisableに
        // 参考: https://developer.android.com/training/basics/intents/sending.html
        Intent otpIntent = createOtpIntent();

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(otpIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        Button button = (Button) findViewById(R.id.launchOtpButton);
        button.setEnabled(isIntentSafe);

        webView = (WebView) findViewById(R.id.loginWebView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new LoginView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(OAUTH_URL + "oauthauth?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1");
    }

    private Intent createOtpIntent() {
        Uri uri = Uri.parse("sqextoken://appviewotp");
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public void launchOtpApp(View view) {
        Intent intent = createOtpIntent();
        startActivity(intent);
    }

    private class LoginView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("methodcalled", "shouldOverrideUrlLoading");
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.d("methodcalled", "onReceivedError");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("methodcalled", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("methodcalled", "onPageFinished");
            super.onPageFinished(view, url);
        }
    }

}
