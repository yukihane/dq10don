package yukihane.dq10don.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * onHogehoge系のメソッドにログ出力を仕込んだ{@link ActionBarActivity}です.
 * 開発途上でのみ使用されることを想定しており, 正式リリースでは除去されるべきです.
 */
public abstract class DebugLogActionBarActivity extends ActionBarActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugLogActionBarActivity.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LOGGER.debug("onCreate called");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        LOGGER.debug("onStart called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LOGGER.debug("onResume called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LOGGER.debug("onPause called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LOGGER.debug("onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LOGGER.debug("onDestroy called");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        LOGGER.debug("onDestroy called");
        super.onDestroy();
    }
}
