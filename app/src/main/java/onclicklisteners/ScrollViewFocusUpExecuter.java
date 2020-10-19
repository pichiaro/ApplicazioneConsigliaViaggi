package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

public class ScrollViewFocusUpExecuter implements OnClickListener {

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
    public void onClick(View view) {
        if (this.scrollView != null) {
            this.scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

}
