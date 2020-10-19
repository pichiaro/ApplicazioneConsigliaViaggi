package adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.AbstractList;

public class SimpleViewPagerAdapter extends PagerAdapter {

    private AbstractList<View> views;

    public SimpleViewPagerAdapter(AbstractList<View> views) {
        super();
        this.views = views;
    }

    public AbstractList<View> getViews() {
        return this.views;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        if (this.views != null) {
            View view = this.views.get(position);
            viewGroup.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object) {
        if (object instanceof View) {
            View viewCast = (View) object;
            viewGroup.removeView(viewCast);
        }
    }

    @Override
    public int getCount() {
        if (this.views != null) {
            return this.views.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
