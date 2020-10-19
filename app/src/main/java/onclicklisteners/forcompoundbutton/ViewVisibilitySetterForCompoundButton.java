package onclicklisteners.forcompoundbutton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

public class ViewVisibilitySetterForCompoundButton implements OnClickListener {

    private View view;
    private boolean isChecked;
    private boolean isMakedVisible;

    public ViewVisibilitySetterForCompoundButton(View view, boolean isChecked, boolean isMakedVisible) {
        this.view = view;
        this.isChecked = isChecked;
        this.isMakedVisible = isMakedVisible;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return this.view;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setMakedVisible(boolean makedVisible) {
        isMakedVisible = makedVisible;
    }

    public boolean isMakedVisible() {
        return isMakedVisible;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CompoundButton) {
            if (this.view != null) {
                CompoundButton cast = (CompoundButton) view;
                if (cast.isChecked() == this.isChecked) {
                    if (this.isMakedVisible) {
                        this.view.setVisibility(View.VISIBLE);
                    } else {
                        this.view.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

}