package ontabselectedlisteners;

import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class SlidingUpPanelLayoutExpander implements OnTabSelectedListener {

    private SlidingUpPanelLayout slidingUpPanelLayout;

    public SlidingUpPanelLayoutExpander(SlidingUpPanelLayout slidingUpPanelLayout) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    public void setSlidingUpPanelLayout(SlidingUpPanelLayout slidingUpPanelLayout) {
        this.slidingUpPanelLayout = slidingUpPanelLayout;
    }

    public SlidingUpPanelLayout getSlidingUpPanelLayout() {
        return this.slidingUpPanelLayout;
    }

    @Override
    public void onTabSelected(Tab tab) {
        if (this.slidingUpPanelLayout != null) {
            PanelState panelState = this.slidingUpPanelLayout.getPanelState();
            if (panelState == SlidingUpPanelLayout.PanelState.COLLAPSED || panelState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                this.slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {
        if (this.slidingUpPanelLayout != null) {
            PanelState panelState = this.slidingUpPanelLayout.getPanelState();
            if (panelState == SlidingUpPanelLayout.PanelState.COLLAPSED || panelState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                this.slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
            }
        }
    }

}
