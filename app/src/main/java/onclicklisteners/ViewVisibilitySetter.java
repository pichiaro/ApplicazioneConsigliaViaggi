package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

public class ViewVisibilitySetter implements OnClickListener {

    private View view;
    private boolean isMakedVisible;

    public ViewVisibilitySetter(View view, boolean isMakedVisible) {
        this.view = view;
        this.isMakedVisible = isMakedVisible;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
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
        if (this.view != null) {
            if (!this.isMakedVisible) {
                this.view.setVisibility(View.INVISIBLE);
            }
            else {
                this.view.setVisibility(View.VISIBLE);
            }
        }
    }

}