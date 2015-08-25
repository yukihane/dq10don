package yukihane.dq10don.farm.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import yukihane.dq10don.R;
import yukihane.dq10don.base.view.BaseActivity;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.presenter.FarmPresenter;

public class FarmActivity extends BaseActivity<FarmPresenter, FarmListPagerAdapter> implements FarmPresenter.View {
    @Override
    protected FarmPresenter newPresenter() {
        return new FarmPresenter(this, new DbHelperFactory(this));
    }

    @Override
    protected FarmListPagerAdapter newPagerAdapter(FragmentManager fm) {
        return new FarmListPagerAdapter(fm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // トークンアプリがインストールされていなければ(intentを受け取れるアプリが存在しないなら)ボタンをdisableに
        // 参考: https://developer.android.com/training/basics/intents/sending.html
        Intent otpIntent = createToolsIntent();

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(otpIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            getMenuInflater().inflate(R.menu.menu_farm, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_dqxtools) {
            Intent intent = createToolsIntent();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createToolsIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.square_enix.dqxtools", "com.square_enix.dqxtools_core.MainActivity");
        return intent;
    }
}
