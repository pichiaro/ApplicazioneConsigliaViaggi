package onitemselectedlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewEnabler implements OnItemSelectedListener {

    private View view;
    private String noSelection;
    private boolean isEnabled;

    public ViewEnabler(View view, String noSelection, boolean isEnabled) {
        this.view = view;
        this.noSelection = noSelection;
        this.isEnabled = isEnabled;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setNoSelection(String noSelection) {
        this.noSelection = noSelection;
    }

    public String getNoSelection() {
        return noSelection;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Object selectedItem = parent.getItemAtPosition(pos);
        String selected = selectedItem.toString();
        if (selected.compareTo(this.noSelection) == 0) {
            this.view.setEnabled(this.isEnabled);
        } else {
            this.view.setEnabled(!this.isEnabled);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}