package yukihane.dq10don.debug;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * onHogehoge系のメソッドにログ出力を仕込んだ{@link Fragment}です.
 * 開発途上でのみ使用されることを想定しており, 正式リリースでは除去されるべきです.
 */
public abstract class DebugLogFragment extends Fragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugLogFragment.class);

    @Override
    public void onHiddenChanged(boolean hidden) {
        LOGGER.debug("onHiddenChanged called {}", toString());
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOGGER.debug("onActivityResult called {}", toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        LOGGER.debug("onInflate called {}", toString());
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        LOGGER.debug("onAttach called {}", toString());
        super.onAttach(activity);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        LOGGER.debug("onCreateAnimation called {}", toString());
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LOGGER.debug("onCreate called {}", toString());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LOGGER.debug("onCreateView called {}", toString());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LOGGER.debug("onViewCreated called {}", toString());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LOGGER.debug("onActivityCreated called {}", toString());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        LOGGER.debug("onViewStateRestored called {}", toString());
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        LOGGER.debug("onStart called {}", toString());
        super.onStart();
    }

    @Override
    public void onResume() {
        LOGGER.debug("onResume called {}", toString());
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LOGGER.debug("onSaveInstanceState called {}", toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LOGGER.debug("onConfigurationChanged called {}", toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        LOGGER.debug("onPause called {}", toString());
        super.onPause();

    }

    @Override
    public void onStop() {
        LOGGER.debug("onStop called {}", toString());
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        LOGGER.debug("onLowMemory called {}", toString());
        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        LOGGER.debug("onDestroyView called {}", toString());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LOGGER.debug("onDestroy called {}", toString());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LOGGER.debug("onDetach called {}", toString());
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        LOGGER.debug("onCreateOptionsMenu called {}", toString());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        LOGGER.debug("onPrepareOptionsMenu called {}", toString());
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDestroyOptionsMenu() {
        LOGGER.debug("onDestroyOptionsMenu called {}", toString());
        super.onDestroyOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LOGGER.debug("onOptionsItemSelected called {}", toString());
        return super.onOptionsItemSelected(item);
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
}
