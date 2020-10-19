package onscrollchangelisteners;

import android.view.View;
import android.view.View.OnScrollChangeListener;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class SlidingUpPanelLayoutScrollableViewSetter implements OnScrollChangeListener {

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
    public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (this.slidingUpPanelLayout != null) {
            this.slidingUpPanelLayout.setScrollableView(view);
        }
    }

}
