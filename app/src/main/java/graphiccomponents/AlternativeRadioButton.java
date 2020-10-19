package graphiccomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class AlternativeRadioButton extends RadioButton {

    public AlternativeRadioButton(Context context) {
        super(context);
    }

    public AlternativeRadioButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void toggle() {
        if (!this.isChecked()) {
            super.toggle();
        }
        else {
            this.setChecked(false);
        }
    }

}
