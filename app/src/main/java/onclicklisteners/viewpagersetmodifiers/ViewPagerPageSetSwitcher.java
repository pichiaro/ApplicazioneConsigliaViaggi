package onclicklisteners.viewpagersetmodifiers;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import java.util.AbstractList;

import adapters.SimpleViewPagerAdapter;

public class ViewPagerPageSetSwitcher implements OnClickListener {

    private ViewPager viewPager;
    private AbstractList<View> pages;
    private TabLayout tabLayout;
    private AbstractList<String> tabTextsList;
    private int nextCurrentItem;

    public ViewPagerPageSetSwitcher(ViewPager viewPager, AbstractList<View> pageSet, int nextCurrentItem) {
        this.viewPager = viewPager;
        this.pages = pageSet;
        this.nextCurrentItem = nextCurrentItem;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return this.viewPager;
    }

    public void setPages(AbstractList<View> pageSet) {
        this.pages = pageSet;
    }

    public AbstractList<View> getPages() {
        return this.pages;
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

    public void setNextCurrentItem(int nextCurrentItem) {
        this.nextCurrentItem = nextCurrentItem;
    }

    public int getNextCurrentItem() {
        return this.nextCurrentItem;
    }

    @Override
    public void onClick(View view) {
        if (this.pages != null) {
            if (this.viewPager != null) {
                SimpleViewPagerAdapter simpleViewPagerAdapter = new SimpleViewPagerAdapter(this.pages);
                this.viewPager.setAdapter(simpleViewPagerAdapter);
                if (this.tabLayout != null) {
                    if (this.tabTextsList != null) {
                        int tabCount = this.tabLayout.getTabCount();
                        if (this.tabTextsList.size() == tabCount) {
                            if (tabCount == this.pages.size()) {
                                this.tabLayout.setupWithViewPager(this.viewPager);
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
                this.viewPager.setCurrentItem(this.nextCurrentItem);
            }
        }
    }

}
