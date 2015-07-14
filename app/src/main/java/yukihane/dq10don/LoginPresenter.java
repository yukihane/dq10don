package yukihane.dq10don;

import yukihane.dq10don.login.JsonLogin;

/**
 * Created by yuki on 15/07/14.
 */
public class LoginPresenter {
    private final View view;
    private JsonLogin parser;

    public LoginPresenter(View view) {
        this.view = view;
    }

    public interface View {
    }
}
