package onclicklisteners.layoutmodifiers;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewGroupSettedViewAdder implements OnClickListener {

    private ViewGroup newParent;
    private int addIndex;
    private View settedView;

    public ViewGroupSettedViewAdder(View settedView, ViewGroup newParent, int addIndex) {
        this.newParent = newParent;
        this.addIndex = addIndex;
        this.settedView = settedView;
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

    public void setSettedView(View settedView) {
        this.settedView = settedView;
    }

    public View getSettedView() {
        return this.settedView;
    }

    @Override
    public void onClick(View view) {
        if (this.newParent != null) {
            if (this.settedView != null) {
                if (this.addIndex >= 0) {
                    ViewParent parent = this.settedView.getParent();
                    if (parent != null) {
                        if (parent instanceof ViewGroup) {
                            ViewGroup cast = (ViewGroup) parent;
                            cast.removeView(this.settedView);
                        }
                    }
                    int childCount = this.newParent.getChildCount();
                    if (this.addIndex < childCount) {
                        this.newParent.addView(this.settedView, this.addIndex);
                    } else {
                        if (this.addIndex == 0) {
                            this.newParent.addView(this.settedView);
                        }
                    }
                }
            }
        }
    }

}
