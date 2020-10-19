package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

public class ViewEnabler implements OnClickListener {

    private View view;
    private boolean isMakedEnable;

    public ViewEnabler(View view, boolean isMakedEnable) {
        this.view = view;
        this.isMakedEnable = isMakedEnable;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return this.view;
    }

    public void setMakedEnable(boolean makedEnable) {
        isMakedEnable = makedEnable;
    }

    public boolean isMakedEnable() {
        return this.isMakedEnable;
    }

    @Override
    public void onClick(View view) {
        if (this.view != null) {
            this.view.setEnabled(this.isMakedEnable);
        }
    }

}