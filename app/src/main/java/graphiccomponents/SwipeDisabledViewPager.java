package graphiccomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class SwipeDisabledViewPager extends ViewPager {

    private boolean isSwipeEnabled;

    public SwipeDisabledViewPager(Context context, AttributeSet attrs, boolean isSwipeEnabled) {
        super(context, attrs);
        this.isSwipeEnabled = isSwipeEnabled;
    }

    public SwipeDisabledViewPager(Context context, boolean isSwipeEnabled) {
        super(context);
        this.isSwipeEnabled = isSwipeEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSwipeEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isSwipeEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.isSwipeEnabled = swipeEnabled;
    }

    public boolean isSwipeEnabled() {
        return this.isSwipeEnabled;
    }

}
