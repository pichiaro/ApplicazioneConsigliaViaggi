package onclicklisteners.viewpagersetmodifiers;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import java.util.AbstractList;

import adapters.SimpleViewPagerAdapter;

public class ViewPagerCurrentPageSwapper implements OnClickListener {

    private ViewPager viewPager;
    private AbstractList<View> firstPages;
    private AbstractList<View> secondPages;
    private TabLayout tabLayout;
    private AbstractList<String> tabTextsList;

    public ViewPagerCurrentPageSwapper(ViewPager viewPager, AbstractList<View> firstPages, AbstractList<View> secondPages) {
        this.viewPager = viewPager;
        this.firstPages = firstPages;
        this.secondPages = secondPages;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return this.viewPager;
    }

    public void setFirstPages(AbstractList<View> firstPages) {
        this.firstPages = firstPages;
    }

    public AbstractList<View> getFirstPages() {
        return this.firstPages;
    }

    public void setSecondPages(AbstractList<View> secondPages) {
        this.secondPages = secondPages;
    }

    public AbstractList<View> getSecondPages() {
        return this.secondPages;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public TabLayout getTabLayout() {
        return this.tabLayout;
    }

    public void setTabTextsList(AbstractList<String> tabTextsList) {
        this.tabTextsList = tabTextsList;
    }

    public AbstractList<String> getTabTextsList() {
        return this.tabTextsList;
    }

    public View getNextCurrentPage() {
        View currentPage = null;
        if (this.viewPager != null) {
            if (this.secondPages != null) {
                int currentPageIndex = this.viewPager.getCurrentItem();
                currentPage = this.secondPages.get(currentPageIndex);
            }
        }
        return currentPage;
    }

    @Override
    public void onClick(View view) {
        if (this.firstPages != null) {
            if (this.secondPages != null) {
                if (this.viewPager != null) {
                    int index = this.viewPager.getCurrentItem();
                    View swapView = this.secondPages.get(index);
                    View firstSetView = this.firstPages.get(index);
                    this.secondPages.remove(index);
                    this.secondPages.add(index, firstSetView);
                    this.firstPages.remove(index);
                    this.firstPages.add(index, swapView);
                    SimpleViewPagerAdapter pagerAdapter = new SimpleViewPagerAdapter(this.firstPages);
                    this.viewPager.setAdapter(pagerAdapter);
                    if (this.tabLayout != null) {
                        if (this.tabTextsList != null) {
                            int tabCount = tabLayout.getTabCount();
                            if (this.tabTextsList.size() == tabCount) {
                                if (tabCount == this.firstPages.size()) {
                                    this.tabLayout.setupWithViewPager(viewPager);
                                    for (int i = 0; i < tabCount; i++) {
                                        Tab tab = this.tabLayout.getTabAt(i);
                                        String tabText = this.tabTextsList.get(i);
                                        tab.setText(tabText);
                                    }
                                }
                            }
                            this.viewPager.setOffscreenPageLimit(tabCount - 1);
                        }
                    }
                    this.viewPager.setCurrentItem(index);
                }
            }
        }
    }

}
