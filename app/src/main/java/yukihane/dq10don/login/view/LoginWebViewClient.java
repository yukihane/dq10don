package yukihane.dq10don.login.view;


import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.login.presenter.LoginWebPresenter;

public class LoginWebViewClient extends WebViewClient implements LoginWebPresenter.View {

    private static final Logger logger = LoggerFactory.getLogger(LoginWebViewClient.class);

    private final LoginWebPresenter presenter;
    private final WebView webView;

    public LoginWebViewClient(WebView webView, String userId) {
        this.presenter = new LoginWebPresenter(this, userId);
        this.webView = webView;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        logger.debug("onPageFinished: {}", url);
        presenter.onPageFinished(url);


        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        logger.debug("onPageStarted: {}", url);
        presenter.onPageStarted(url);
    }

    @Override
    public void loadJavascript(String scriptStr) {
        webView.loadUrl(scriptStr);
    }
}
