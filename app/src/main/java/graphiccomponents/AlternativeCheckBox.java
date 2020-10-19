package graphiccomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class AlternativeCheckBox extends CheckBox {

    public AlternativeCheckBox(Context context) {
        super(context);
    }

    public AlternativeCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        if (!isEnabled) {
            this.setChecked(false);
        }
        super.setEnabled(isEnabled);
    }

}
