package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;

import java.util.AbstractList;
import java.util.Iterator;

public class CompoundButtonsCheckSetter implements OnClickListener {

    private AbstractList<CompoundButton> compoundButtons;
    private boolean isChecked;

    public CompoundButtonsCheckSetter(AbstractList<CompoundButton> compoundButtons, boolean isChecked) {
        this.compoundButtons = compoundButtons;
        this.isChecked = isChecked;
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

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void onClick(View view) {
        if (this.compoundButtons != null) {
            Iterator<CompoundButton> iterator = this.compoundButtons.iterator();
            while (iterator.hasNext()) {
                CompoundButton compoundButton = iterator.next();
                if (compoundButton != null) {
                    compoundButton.setChecked(this.isChecked);
                }
            }
        }
    }

}
