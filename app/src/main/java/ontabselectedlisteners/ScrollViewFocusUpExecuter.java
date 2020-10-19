package ontabselectedlisteners;

import android.widget.ScrollView;

import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

public class ScrollViewFocusUpExecuter implements OnTabSelectedListener {

    private ScrollView scrollView;

    public ScrollViewFocusUpExecuter(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public ScrollView getScrollView() {
        return this.scrollView;
    }

    @Override
    public void onTabSelected(Tab tab) {
        if (this.scrollView != null) {
            this.scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {
        if (this.scrollView != null) {
            this.scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

}
