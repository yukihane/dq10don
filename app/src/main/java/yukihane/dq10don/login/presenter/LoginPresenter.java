package yukihane.dq10don.login.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.login.model.JsonLogin;
import yukihane.dq10don.login.model.UserIdGetter;

/**
 * Created by yuki on 15/07/14.
 */
public class LoginPresenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPresenter.class);
    private static final String OAUTH_URL = "https://secure.square-enix.com/oauth/oa/";


    private final View view;

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

    public LoginPresenter(View view) {
        this.view = view;
    }

    public void onCreate(String userId) {
        this.userId = (userId == null ? "" : userId);

        String url = OAUTH_URL + "oauthauth?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1";

        JsonLogin parser = new JsonLogin(res -> {
            if (res != null) {
                view.loginSuccess(res, usedUserId);
                LOGGER.info("login success");
            } else {
                view.loginFail();
                LOGGER.error("login information read error.");
            }
        });

        UserIdGetter userIdGetter = new UserIdGetter(res -> usedUserId = res);


        view.initializeWebView(url, userId, parser, userIdGetter);
    }

    public interface View {
        void loginSuccess(String res, String usedUserId);

        void loginFail();

        void initializeWebView(String url, String userId, JsonLogin parser, UserIdGetter userIdGetter);
    }
}
