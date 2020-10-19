package onclicklisteners.viewpagersetmodifiers;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.AbstractList;

import adapters.SimpleViewPagerAdapter;

public class ViewPagerCurrentPageSwapperOnIdCondition implements View.OnClickListener {

    private ViewPager viewPager;
    private AbstractList<View> firstPages;
    private AbstractList<View> secondPages;
    private TabLayout tabLayout;
    private AbstractList<String> tabTextsList;
    private int id;
    private int pageIndex;
    private int nextCurrentItem;

    public ViewPagerCurrentPageSwapperOnIdCondition(ViewPager viewPager, AbstractList<View> firstPages, AbstractList<View> secondPages, int id, int pageIndex, int nextCurrentItem) {
        this.viewPager = viewPager;
        this.firstPages = firstPages;
        this.secondPages = secondPages;
        this.id = id;
        this.pageIndex = pageIndex;
        this.nextCurrentItem = nextCurrentItem;
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

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setNextCurrentItem(int nextCurrentItem) {
        this.nextCurrentItem = nextCurrentItem;
    }

    public int getNextCurrentItem() {
        return this.nextCurrentItem;
    }

    @Override
    public void onClick(View view) {
        if (this.firstPages != null) {
            if (this.secondPages != null) {
                if (this.viewPager != null) {
                    if (this.pageIndex >= 0) {
                        if (this.pageIndex < this.firstPages.size()) {
                            View firstSetView = this.firstPages.get(this.pageIndex);
                            if (this.id == firstSetView.getId()) {
                                View swapView = this.secondPages.get(this.pageIndex);
                                this.secondPages.remove(this.pageIndex);
                                this.secondPages.add(this.pageIndex, firstSetView);
                                this.firstPages.remove(this.pageIndex);
                                this.firstPages.add(this.pageIndex, swapView);
                                SimpleViewPagerAdapter pagerAdapter = new SimpleViewPagerAdapter(this.firstPages);
                                this.viewPager.setAdapter(pagerAdapter);
                                if (this.tabLayout != null) {
                                    if (this.tabTextsList != null) {
                                        int tabCount = tabLayout.getTabCount();
                                        if (this.tabTextsList.size() == tabCount) {
                                            if (tabCount == this.firstPages.size()) {
                                                this.tabLayout.setupWithViewPager(viewPager);
                                                for (int i = 0; i < tabCount; i++) {
                                                    TabLayout.Tab tab = this.tabLayout.getTabAt(i);
                                                    String tabText = this.tabTextsList.get(i);
                                                    tab.setText(tabText);
                                                }
                                            }
                                        }
                                        this.viewPager.setOffscreenPageLimit(tabCount - 1);
                                    }
                                }
                                this.viewPager.setCurrentItem(this.nextCurrentItem);
                            }
                        }
                    }
                }
            }
        }
    }

}
