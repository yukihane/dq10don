package yukihane.dq10don.debug;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * onHogehoge系のメソッドにログ出力を仕込んだ{@link AppCompatActivity}です.
 * 開発途上でのみ使用されることを想定しており, 正式リリースでは除去されるべきです.
 */
public abstract class DebugLogActionBarActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugLogActionBarActivity.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LOGGER.debug("onCreate called {}", toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        LOGGER.debug("onCreate called {}", toString());
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        LOGGER.debug("onStart called {}", toString());
        super.onStart();
    }

    @Override
    protected void onResume() {
        LOGGER.debug("onResume called {}", toString());
        super.onResume();
    }

    @Override
    protected void onPause() {
        LOGGER.debug("onPause called {}", toString());
        super.onPause();
    }

    @Override
    protected void onStop() {
        LOGGER.debug("onStop called {}", toString());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LOGGER.debug("onDestroy called {} ", toString());
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        LOGGER.debug("onRestart called {}", toString());
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LOGGER.debug("onRestoreInstanceState called {}", toString());
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState,
                                       PersistableBundle persistentState) {
        LOGGER.debug("onRestoreInstanceState called {}", toString());
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        LOGGER.debug("onPostCreate called {}", toString());
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState,
                             @Nullable PersistableBundle persistentState) {
        LOGGER.debug("onPostCreate called {}", toString());
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostResume() {
        LOGGER.debug("onPostResume called {}", toString());
        super.onPostResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LOGGER.debug("onNewIntent called {}", toString());
        super.onNewIntent(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LOGGER.debug("onSaveInstanceState called {}", toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        LOGGER.debug("onSaveInstanceState called {}", toString());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onUserLeaveHint() {
        LOGGER.debug("onUserLeaveHint called {}", toString());
        super.onUserLeaveHint();
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        LOGGER.debug("onCreateThumbnail called {}", toString());
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    @Override
    @Nullable
    public CharSequence onCreateDescription() {
        LOGGER.debug("onCreateDescription called {}", toString());
        return super.onCreateDescription();
    }

    @Override
    public void onProvideAssistData(Bundle data) {
        LOGGER.debug("onProvideAssistData called {}", toString());
        super.onProvideAssistData(data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LOGGER.debug("onConfigurationChanged called {}", toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        LOGGER.debug("onLowMemory called {}", toString());
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LOGGER.debug("onTrimMemory called {}", toString());
        super.onTrimMemory(level);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        LOGGER.debug("onAttachFragment called {}", toString());
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LOGGER.debug("onKeyDown called {}", toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        LOGGER.debug("onKeyLongPress called {}", toString());
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LOGGER.debug("onKeyUp called {}", toString());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        LOGGER.debug("onKeyMultiple called {}", toString());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public void onBackPressed() {
        LOGGER.debug("onBackPressed called {}", toString());
        super.onBackPressed();
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        LOGGER.debug("onKeyShortcut called {}", toString());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LOGGER.debug("onTouchEvent called {}", toString());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        LOGGER.debug("onTrackballEvent called {}", toString());
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        LOGGER.debug("onGenericMotionEvent called {}", toString());
        return super.onGenericMotionEvent(event);
    }

    @Override
    public void onUserInteraction() {
        LOGGER.debug("onUserInteraction called {}", toString());
        super.onUserInteraction();
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        LOGGER.debug("onWindowAttributesChanged called {}", toString());
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        LOGGER.debug("onWindowFocusChanged called {}", toString());
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        LOGGER.debug("onAttachedToWindow called {}", toString());
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        LOGGER.debug("onDetachedFromWindow called {}", toString());
        super.onDetachedFromWindow();
    }

    @Override
    @Nullable
    public View onCreatePanelView(int featureId) {
        LOGGER.debug("onCreatePanelView called {}", toString());
        return super.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        LOGGER.debug("onCreatePanelMenu called {}", toString());
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        LOGGER.debug("onPreparePanel called {}", toString());
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        LOGGER.debug("onMenuOpened called {}", toString());
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        LOGGER.debug("onPanelClosed called {}", toString());
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LOGGER.debug("onCreateOptionsMenu called {}", toString());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LOGGER.debug("onPrepareOptionsMenu called {}", toString());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LOGGER.debug("onOptionsItemSelected called {}", toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        LOGGER.debug("onNavigateUp called {}", toString());
        return super.onNavigateUp();
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        LOGGER.debug("onNavigateUpFromChild called {}", toString());
        return super.onNavigateUpFromChild(child);
    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        LOGGER.debug("onCreateNavigateUpTaskStack called {}", toString());
        super.onCreateNavigateUpTaskStack(builder);
    }

    @Override
    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {
        LOGGER.debug("onPrepareNavigateUpTaskStack called {}", toString());
        super.onPrepareNavigateUpTaskStack(builder);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        LOGGER.debug("onOptionsMenuClosed called {}", toString());
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        LOGGER.debug("onCreateContextMenu called {}", toString());
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        LOGGER.debug("onContextItemSelected called {}", toString());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        LOGGER.debug("onContextMenuClosed called {}", toString());
        super.onContextMenuClosed(menu);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        LOGGER.debug("onCreateDialog called {}", toString());
        return super.onCreateDialog(id);
    }

    @Override
    @Nullable
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        LOGGER.debug("onCreateDialog called {}", toString());
        return super.onCreateDialog(id, args);
    }

    @Override
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog) {
        LOGGER.debug("onPrepareDialog called {}", toString());
        super.onPrepareDialog(id, dialog);
    }

    @Override
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        LOGGER.debug("onPrepareDialog called {}", toString());
        super.onPrepareDialog(id, dialog, args);
    }

    @Override
    public boolean onSearchRequested() {
        LOGGER.debug("onSearchRequested called {}", toString());
        return super.onSearchRequested();
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        LOGGER.debug("onApplyThemeResource called {}", toString());
        super.onApplyThemeResource(theme, resid, first);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOGGER.debug("onActivityResult called {}", toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        LOGGER.debug("onActivityReenter called {}", toString());
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        LOGGER.debug("onTitleChanged called {}", toString());
        super.onTitleChanged(title, color);
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        LOGGER.debug("onChildTitleChanged called {}", toString());
        super.onChildTitleChanged(childActivity, title);
    }

    @Override
    @Nullable
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        LOGGER.debug("onCreateView called {}", toString());
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        LOGGER.debug("onCreateView called {}", toString());
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onVisibleBehindCanceled() {
        LOGGER.debug("onVisibleBehindCanceled called {}", toString());
        super.onVisibleBehindCanceled();
    }

    @Override
    public void onEnterAnimationComplete() {
        LOGGER.debug("onEnterAnimationComplete called {}", toString());
        super.onEnterAnimationComplete();
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        LOGGER.debug("onWindowStartingActionMode called {}", toString());
        return super.onWindowStartingActionMode(callback);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        LOGGER.debug("onActionModeStarted called {}", toString());
        super.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        LOGGER.debug("onActionModeFinished called {}", toString());
        super.onActionModeFinished(mode);
    }
}
