package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class SlidingUpPanelLayoutAnchorer implements OnClickListener {

    private SlidingUpPanelLayout slidingUpPanelLayout;
    private float anchorPoint;

    public SlidingUpPanelLayoutAnchorer(SlidingUpPanelLayout slidingUpPanelLayout, float anchorPoint) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
        this.anchorPoint = anchorPoint;
    }

    public void setSlidingUpPanelLayout(SlidingUpPanelLayout slidingUpPanelLayout) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    public SlidingUpPanelLayout getSlidingUpPanelLayout() {
        return this.slidingUpPanelLayout;
    }

    public void setAnchorPoint(float anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public float getAnchorPoint() {
        return this.anchorPoint;
    }

    @Override
    public void onClick(View view) {
        if (this.slidingUpPanelLayout != null) {
            this.slidingUpPanelLayout.setAnchorPoint(this.anchorPoint);
            this.slidingUpPanelLayout.setPanelState(PanelState.ANCHORED);
        }
    }

}
