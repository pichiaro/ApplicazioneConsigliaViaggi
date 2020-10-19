package onscrolllisteners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class SlidingUpPanelLayoutScrollableViewSetter implements OnScrollListener {

    private SlidingUpPanelLayout slidingUpPanelLayout;

    public SlidingUpPanelLayoutScrollableViewSetter(SlidingUpPanelLayout slidingUpPanelLayout) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    public void setSlidingUpPanelLayout(SlidingUpPanelLayout slidingUpPanelLayout) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    public SlidingUpPanelLayout getSlidingUpPanelLayout() {
        return this.slidingUpPanelLayout;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.slidingUpPanelLayout != null) {
            this.slidingUpPanelLayout.setScrollableView(absListView);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (this.slidingUpPanelLayout != null) {
            this.slidingUpPanelLayout.setScrollableView(absListView);
        }
    }

}