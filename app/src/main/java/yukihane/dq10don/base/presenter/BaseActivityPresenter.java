package yukihane.dq10don.base.presenter;

import java.util.List;

import yukihane.dq10don.account.Account;

/**
 * Created by yuki on 15/08/06.
 */
public interface BaseActivityPresenter {
    void onCreate(boolean firstBoot);

    void onStart();

    void onDestroy();

    interface View {
        void setAccounts(List<Account> accounts);
    }
}
