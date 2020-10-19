package onclicklisteners.layoutmodifiers;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewGroupClickedViewAdder implements OnClickListener {

    private int containerLevel;
    private ViewGroup newParent;
    private int addIndex;

    public ViewGroupClickedViewAdder(int containerLevel, ViewGroup newParent, int addIndex) {
        this.containerLevel = containerLevel;
        this.newParent = newParent;
        this.addIndex = addIndex;
    }

    public void setContainerLevel(int containerLevel) {
        this.containerLevel = containerLevel;
    }

    public int getContainerLevel() {
        return this.containerLevel;
    }

    public void setNewParent(ViewGroup newParent) {
        this.newParent = newParent;
    }

    public ViewGroup getNewParent() {
        return this.newParent;
    }

    public void setAddIndex(int addIndex) {
        this.addIndex = addIndex;
    }

    public int getAddIndex() {
        return this.addIndex;
    }

    @Override
    public void onClick(View view) {
        int containerLevel = this.containerLevel;
        while (containerLevel > 0) {
            ViewParent viewParent = view.getParent();
            if (viewParent == null) {
                return;
            }
            if (!(viewParent instanceof ViewGroup)) {
                return;
            }
            view = (View) viewParent;
            containerLevel--;
        }
        if (this.newParent != null) {
            if (this.addIndex >= 0) {
                ViewParent parent = view.getParent();
                if (parent != null) {
                    if (parent instanceof ViewGroup) {
                        ViewGroup cast = (ViewGroup) parent;
                        cast.removeView(view);
                    }
                }
                int childCount = this.newParent.getChildCount();
                if (this.addIndex < childCount) {
                    this.newParent.addView(view, this.addIndex);
                } else {
                    if (this.addIndex == 0) {
                        this.newParent.addView(view);
                    }
                }
            }
        }
    }

}
