package yukihane.dq10don;

/**
 * Created by yuki on 15/07/14.
 */
public class LoginWebPresenter {

    private final View view;
    private final String userId;

    public LoginWebPresenter(View view, String userId) {
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.userId = userId;
    }

    public void onPageFinished(String url) {
        if (url.split("\\?")[0].equals("https://secure.square-enix.com/oauth/oa/oauthlogin")) {
            // ログイン初期画面描画時にはユーザIDを自動入力する
            view.loadJavascript("javascript: (function() {document.getElementById('sqexid').value = '" + userId + "';})();");
        } else if (url.startsWith("https://happy.dqx.jp/capi/login/securelogin/")) {
            // ログイン成功した場合には戻ってきたJSONを処理する
            view.loadJavascript("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        }
    }

    public void onPageStarted(String url) {
        if (url.startsWith("https://secure.square-enix.com/oauth/oa/oauthlogin.send")) {
            // 画面で入力されたユーザーID(正しい値であるかはここではわからない)を保持する
            view.loadJavascript("javascript:window.UserIdGetter.get(document.getElementById('sqexid').value);");
        }
    }

    public interface View {
        void loadJavascript(String scriptStr);
    }
}
