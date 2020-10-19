package onclicklisteners.forcompoundbutton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

import java.util.AbstractList;
import java.util.Iterator;

public class ViewsEnablerForCompoundButton implements OnClickListener {

    private AbstractList<View> views;
    private boolean isChecked;
    private boolean isMakedEnabled;

    public ViewsEnablerForCompoundButton(AbstractList<View> views, boolean isChecked,boolean isMakedEnabled) {
        this.views = views;
        this.isChecked = isChecked;
        this.isMakedEnabled = isMakedEnabled;
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

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setMakedEnabled(boolean makedEnabled) {
        isMakedEnabled = makedEnabled;
    }

    public boolean isMakedEnabled() {
        return isMakedEnabled;
    }

    @Override
    public void onClick(View view) {
        if (this.views != null) {
            if (view instanceof CompoundButton) {
                CompoundButton cast = (CompoundButton) view;
                Iterator<View> iterator = this.views.iterator();
                if (cast.isChecked() == this.isChecked) {
                    while (iterator.hasNext()) {
                        View next = iterator.next();
                        if (next != null) {
                            next.setEnabled(this.isMakedEnabled);
                        }
                    }
                }
            }
        }
    }

}