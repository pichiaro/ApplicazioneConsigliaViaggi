package onclicklisteners.forcompoundbutton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

public class UniqueMutualTogglerForCompoundButton implements OnClickListener {

    private CompoundButton compoundButton;

    public UniqueMutualTogglerForCompoundButton(CompoundButton compoundButton) {
        this.compoundButton = compoundButton;
    }

    public void setCompoundButton(CompoundButton compoundButton) {
        this.compoundButton = compoundButton;
    }

    public CompoundButton getCompoundButton() {
        return this.compoundButton;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CompoundButton) {
            CompoundButton cast = (CompoundButton) view;
            if (this.compoundButton != null) {
                if (this.compoundButton.isChecked()) {
                    this.compoundButton.setChecked(!cast.isChecked());
                }
            }
        }
    }

}
