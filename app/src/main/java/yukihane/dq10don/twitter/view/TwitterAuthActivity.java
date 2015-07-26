package yukihane.dq10don.twitter.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.twitter.presenter.TwitterAuthPresenter;

import static yukihane.dq10don.twitter.Constants.PREF_NAME;
import static yukihane.dq10don.twitter.Constants.SCREEN_NAME;
import static yukihane.dq10don.twitter.Constants.TOKEN;
import static yukihane.dq10don.twitter.Constants.TOKEN_SECRET;
import static yukihane.dq10don.twitter.Constants.USER_ID;

public class TwitterAuthActivity extends ActionBarActivity implements TwitterAuthPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterAuthActivity.class);

    private TwitterAuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_auth);

        presenter = new TwitterAuthPresenter(this);
        presenter.onCreate();

        Button button = (Button) findViewById(R.id.authTwetterButton);
        button.setOnClickListener(v -> {
            presenter.connectTwitter();
        });

        Button authBtn = (Button) findViewById(R.id.sendPinButton);
        authBtn.setOnClickListener(v -> {
            EditText et = (EditText) findViewById(R.id.et_input_pin);
            String pin = et.getText().toString();
            presenter.authPIN(pin);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindActivity(this, observable);
    }

    @Override
    public void goAuthorization(Uri uri) {
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public void showMessage(TwitterAuthPresenter.Message message) {
        final String text;
        switch (message) {
            case INVALID_PIN:
                text = getString(R.string.err_invalid_pin);
                break;
            case AUTH_FAILED:
                text = getString(R.string.err_failed_auth);
                break;
            default:
                throw new IllegalArgumentException("メッセージが実装されていません: " + message);
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG);
    }

    @Override
    public void storeAuthInfo(long userId, String screenName, String token, String tokenSecret) {
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(USER_ID, userId);
        edit.putString(SCREEN_NAME, screenName);
        edit.putString(TOKEN, token);
        edit.putString(TOKEN_SECRET, tokenSecret);
        edit.commit();
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
