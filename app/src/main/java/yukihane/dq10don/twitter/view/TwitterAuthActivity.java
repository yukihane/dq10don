package yukihane.dq10don.twitter.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import yukihane.dq10don.R;

public class TwitterAuthActivity extends ActionBarActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterAuthActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_auth);

        Button button = (Button) findViewById(R.id.authTwetterButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectTwitter();
            }
        });
        Button authBtn = (Button) findViewById(R.id.sendPinButton);
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPIN();
            }
        });
    }

    /**
     * Twitterに接続する
     */
    private void connectTwitter() {
        Observable<RequestToken> reqObservable = Observable.create(
                (Subscriber<? super RequestToken> subscriber) -> {
                    subscriber.onStart();
                    // リクエストトークンの作成
                    try {
                        Twitter twitter = TwitterFactory.getSingleton();
                        RequestToken requestToken = twitter.getOAuthRequestToken();
                        subscriber.onNext(requestToken);
                        subscriber.onCompleted();
                    } catch (TwitterException e) {
                        subscriber.onError(e);
                    }
                }
        ).subscribeOn(Schedulers.io());

        AppObservable.bindActivity(this, reqObservable);

        reqObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(requestToken -> {
            String url = requestToken.getAuthorizationURL();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }, throwable -> {
            LOGGER.error("", throwable);
        });

    }

    private void authPIN() {
        EditText et = (EditText) findViewById(R.id.et_input_pin);
        if (et.getText().toString().equals("")) {
            Toast.makeText(this, R.string.err_invalid_pin, Toast.LENGTH_LONG);
            return;
        }

        final String pin = et.getText().toString();

        Observable<AccessToken> acObservable = Observable.create(
                (Subscriber<? super AccessToken> subscriber) -> {
                    subscriber.onStart();
                    try {
                        Twitter twitter = TwitterFactory.getSingleton();
                        AccessToken accessToken = twitter.getOAuthAccessToken(pin);
                        subscriber.onNext(accessToken);
                        subscriber.onCompleted();
                    } catch (TwitterException e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.io());

        AppObservable.bindActivity(this, acObservable);

        acObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(accessToken -> {
            SharedPreferences sp = getSharedPreferences("twitter_access", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("access_token", accessToken.getToken());
            edit.putString("access_token_secret", accessToken.getTokenSecret());
            edit.commit();
            finish();
        }, throwable -> {
            LOGGER.error("", throwable);
            Toast.makeText(TwitterAuthActivity.this, R.string.err_failed_auth, Toast.LENGTH_LONG);
            return;
        });
    }
}
