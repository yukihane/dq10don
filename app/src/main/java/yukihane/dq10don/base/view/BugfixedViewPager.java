package yukihane.dq10don.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * オリジナルには不具合があるため,
 * https://code.google.com/p/android/issues/detail?id=66620#c12 を適用したViewPager.
 */
public class BugfixedViewPager extends ViewPager {

    public BugfixedViewPager(Context context) {
        super(context);
    }

    public BugfixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (getAdapter() == null || getAdapter().getCount() == 0) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getAdapter() == null || getAdapter().getCount() == 0) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
