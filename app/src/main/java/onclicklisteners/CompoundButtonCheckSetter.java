package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

public class CompoundButtonCheckSetter implements OnClickListener {

    private CompoundButton compoundButton;
    private boolean isChecked;

    public CompoundButtonCheckSetter(CompoundButton compoundButton, boolean isChecked) {
        this.compoundButton = compoundButton;
        this.isChecked = isChecked;
    }

    public void setCompoundButton(CompoundButton compoundButton) {
        this.compoundButton = compoundButton;
    }

    public CompoundButton getCompoundButton() {
        return this.compoundButton;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void onClick(View view) {
        if (this.compoundButton != null) {
            this.compoundButton.setChecked(this.isChecked);
        }
    }

}
