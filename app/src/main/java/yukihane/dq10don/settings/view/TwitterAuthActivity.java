package yukihane.dq10don.settings.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.settings.presenter.TwitterAuthPresenter;

public class TwitterAuthActivity extends AppCompatActivity implements TwitterAuthPresenter.View {

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
            case GET_PIN_FAILED:
                text = getString(R.string.text_get_pin_failed);
                break;
            case INVALID_PIN:
                text = getString(R.string.text_empty_pin);
                break;
            case AUTH_FAILED:
                text = getString(R.string.text_auth_failed);
                break;
            default:
                throw new IllegalArgumentException("メッセージが実装されていません: " + message);
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void storeAuthInfo(long userId, String screenName, String token, String tokenSecret) {
        new PrefUtils(this).storeAuthInfo(userId, screenName, token, tokenSecret);
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
