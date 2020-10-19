package onclicklisteners.forcompoundbutton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

import java.util.AbstractList;
import java.util.Iterator;

public class MutualTogglerForCompoundButton implements OnClickListener {

    private AbstractList<CompoundButton> compoundButtons;

    public MutualTogglerForCompoundButton(AbstractList<CompoundButton> compoundButtons) {
        this.compoundButtons = compoundButtons;
    }

    public void setCompoundButtons(AbstractList<CompoundButton> compoundButton) {
        this.compoundButtons = compoundButton;
    }

    public AbstractList<CompoundButton> getCompoundButtons() {
        return this.compoundButtons;
    }

    public void addCompoundButtons(CompoundButton...compoundButtons) {
        if (this.compoundButtons != null) {
            if (compoundButtons != null) {
                for (int index = 0; index < compoundButtons.length; index++) {
                    if (compoundButtons[index] != null) {
                        if (compoundButtons[index] != null) {
                            this.compoundButtons.add(compoundButtons[index]);
                        }
                    }
                }
            }
        }
    }

    public CompoundButton getCompoundButton(int index) {
        CompoundButton compoundButton = null;
        if (this.compoundButtons != null) {
            if (index >= 0) {
                if (index < this.compoundButtons.size()) {
                    compoundButton = this.compoundButtons.get(index);
                }
            }
        }
        return compoundButton;
    }

    public CompoundButton removeCompoundButton(int index) {
        CompoundButton compoundButton = null;
        if (this.compoundButtons != null) {
            if (index >= 0) {
                if (index < this.compoundButtons.size()) {
                    compoundButton = this.compoundButtons.get(index);
                }
            }
        }
        return compoundButton;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CompoundButton) {
            CompoundButton cast = (CompoundButton) view;
            if (this.compoundButtons != null) {
                Iterator<CompoundButton> iterator = this.compoundButtons.iterator();
                while (iterator.hasNext()) {
                    CompoundButton compoundButton = iterator.next();
                    if (compoundButton != null) {
                        if (compoundButton.isChecked()) {
                            compoundButton.setChecked(!cast.isChecked());
                        }
                    }
                }
            }
        }
    }

}
