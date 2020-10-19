package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener ;
import android.widget.AbsSpinner;

public class SpinnerSelectionSetter implements OnClickListener {

    private AbsSpinner spinner;
    private int position;

    public SpinnerSelectionSetter(AbsSpinner spinner, int position) {
        this.spinner = spinner;
        this.position = position;
    }

    public void setSpinner(AbsSpinner spinner) {
        this.spinner = spinner;
    }

    public AbsSpinner getSpinner() {
        return this.spinner;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void onClick(View v) {
        if (this.spinner != null) {
            this.spinner.setSelection(this.position);
        }
    }

}
