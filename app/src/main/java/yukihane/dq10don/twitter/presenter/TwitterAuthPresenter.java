package yukihane.dq10don.twitter.presenter;

import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by yuki on 15/07/27.
 */
public class TwitterAuthPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterAuthPresenter.class);

    private View view;

    public TwitterAuthPresenter(View view) {
        this.view = view;
    }

    public void onCreate() {
    }

    public void onDestroy() {
        view = null;
    }

    /**
     * Twitterに接続する
     */
    public void connectTwitter() {
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

        view.bind(reqObservable);

        reqObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(requestToken -> {
            String url = requestToken.getAuthorizationURL();
            Uri uri = Uri.parse(url);
            view.goAuthorization(uri);
        }, throwable -> {
            LOGGER.error("", throwable);
        });

    }

    public void authPIN(String pin) {
        if (pin.isEmpty()) {
            view.showMessage(Message.INVALID_PIN);
            return;
        }

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

        view.bind(acObservable);

        acObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(accessToken -> {
            long userId = accessToken.getUserId();
            String screenName = accessToken.getScreenName();
            String token = accessToken.getToken();
            String tokenSecret = accessToken.getTokenSecret();

            view.storeAuthInfo(userId, screenName, token, tokenSecret);
            view.closeActivity();

        }, throwable -> {
            LOGGER.error("", throwable);
            view.showMessage(Message.AUTH_FAILED);
            return;
        });
    }

    public enum Message {
        INVALID_PIN, AUTH_FAILED;
    }

    public interface View {
        void bind(Observable<?> reqObservable);

        void goAuthorization(Uri uri);

        void showMessage(Message invalidPin);

        void storeAuthInfo(long userId, String screenName, String token, String tokenSecret);

        void closeActivity();
    }
}
