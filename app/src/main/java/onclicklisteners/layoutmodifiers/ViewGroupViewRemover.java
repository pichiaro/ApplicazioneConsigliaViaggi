package onclicklisteners.layoutmodifiers;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ViewGroupViewRemover implements OnClickListener {

    private ViewGroup parent;
    private int removeIndex;

    public ViewGroupViewRemover(ViewGroup parent, int removeIndex) {
        this.parent = parent;
        this.removeIndex = removeIndex;
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    public ViewGroup getParent() {
        return this.parent;
    }

    public void setRemoveIndex(int removeIndex) {
        this.removeIndex = removeIndex;
    }

    public int getRemoveIndex() {
        return this.removeIndex;
    }

    @Override
    public void onClick(View view) {
        if (this.parent != null) {
            int childCount = this.parent.getChildCount();
            if (childCount > 0) {
                if (this.removeIndex >= 0 && this.removeIndex < childCount) {
                    this.parent.removeViewAt(this.removeIndex);
                }
            }
        }
    }

}
