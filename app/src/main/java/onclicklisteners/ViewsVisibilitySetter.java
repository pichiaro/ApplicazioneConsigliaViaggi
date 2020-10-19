package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;
import java.util.Iterator;

public class ViewsVisibilitySetter implements OnClickListener {

    private AbstractList<View> views;
    private boolean isMakedVisible;

    public ViewsVisibilitySetter(AbstractList<View> views, boolean isMakedVisible) {
        this.views = views;
        this.isMakedVisible = isMakedVisible;
    }

    public void setViews(AbstractList<View> views) {
        this.views = views;
    }

    public AbstractList<View> getViews() {
        return this.views;
    }

    public void addViews(View...views) {
        if (this.views != null) {
            if (views != null) {
                for (int index = 0; index < views.length; index++) {
                    if (views[index] != null) {
                        this.views.add(views[index]);
                    }
                }
            }
        }
    }

    public View getView(int index) {
        View view = null;
        if (this.views != null) {
            if (index >= 0) {
                if (index < this.views.size()) {
                    view = this.views.get(index);
                }
            }
        }
        return view;
    }

    public View removeView(int index) {
        View view = null;
        if (this.views != null) {
            if (index >= 0) {
                if (index < this.views.size()) {
                    view = this.views.remove(index);
                }
            }
        }
        return view;
    }

    public void setMakedVisible(boolean makedVisible) {
        isMakedVisible = makedVisible;
    }

    public boolean isMakedVisible() {
        return this.isMakedVisible;
    }

    @Override
    public void onClick(View view) {
        if (this.views != null) {
            Iterator<View> iterator = this.views.iterator();
            if (this.isMakedVisible) {
                while (iterator.hasNext()) {
                    View next = iterator.next();
                    if (next != null) {
                        next.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                while (iterator.hasNext()) {
                    View next = iterator.next();
                    if (next != null) {
                        next.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

}