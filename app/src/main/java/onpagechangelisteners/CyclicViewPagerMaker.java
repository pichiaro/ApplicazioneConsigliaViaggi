package onpagechangelisteners;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

public class CyclicViewPagerMaker implements OnPageChangeListener {

    private ViewPager viewPager;
    private int lastState;

    public CyclicViewPagerMaker(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (this.viewPager != null) {
            this.viewPager.addOnPageChangeListener(this);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (this.viewPager != null) {
            this.viewPager.addOnPageChangeListener(this);
        }
    }

    public ViewPager getViewPager() {
        return this.viewPager;
    }

    public int getLastState() {
        return this.lastState;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (this.viewPager != null) {
            int currentPageIndex = this.viewPager.getCurrentItem();
            int finalPageIndex = this.viewPager.getOffscreenPageLimit();
            if (this.lastState == ViewPager.SCROLL_STATE_DRAGGING) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPageIndex == 0) {
                        this.viewPager.setCurrentItem(finalPageIndex);
                    } else {
                        if (state == ViewPager.SCROLL_STATE_IDLE) {
                            if (currentPageIndex == finalPageIndex) {
                                this.viewPager.setCurrentItem(0);
                            }
                        }
                    }
                } else {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        if (currentPageIndex == finalPageIndex) {
                            this.viewPager.setCurrentItem(0);
                        }
                    }
                }
            }
            this.lastState = state;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

}
