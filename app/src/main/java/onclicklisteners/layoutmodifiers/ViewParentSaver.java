package onclicklisteners.layoutmodifiers;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewParentSaver implements OnClickListener {

    private int containerLevel;
    private final ViewGroupSettedViewAdder restoreViewParentOnClickListener = new ViewGroupSettedViewAdder(null,null,0);

    public ViewParentSaver(int containerLevel) {
        this.containerLevel = containerLevel;
    }

    public void setContainerLevel(int containerLevel) {
        this.containerLevel = containerLevel;
    }

    public int getContainerLevel() {
        return this.containerLevel;
    }

    @Override
    public void onClick(View view) {
        int containerLevel = this.containerLevel;
        while (containerLevel > 0) {
            ViewParent viewParent = view.getParent();
            if (viewParent == null || !(viewParent instanceof ViewGroup)) {
                return;
            }
            view = (View) viewParent;
            containerLevel--;
        }
        ViewParent parent = view.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ViewGroup cast = (ViewGroup) parent;
                int childCount = cast.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View son = cast.getChildAt(i);
                    if (son.equals(view)) {
                        this.restoreViewParentOnClickListener.setAddIndex(i);
                        break;
                    }
                }
                this.restoreViewParentOnClickListener.setNewParent(cast);
                this.restoreViewParentOnClickListener.setSettedView(view);
            }
        }
    }

    public ViewGroupSettedViewAdder getRestoreViewParentOnClickListener() {
        return this.restoreViewParentOnClickListener;
    }

}
