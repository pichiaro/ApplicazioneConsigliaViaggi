package ondatechangelisteners;

import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

public class ViewEnabler implements OnDateChangeListener {

    private View view;
    private boolean isMakedEnable;

    public ViewEnabler(View view, boolean isMakedEnable) {
        this.view = view;
        this.isMakedEnable = isMakedEnable;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return this.view;
    }

    public void setMakedEnable(boolean makedEnable) {
        isMakedEnable = makedEnable;
    }

    public boolean isMakedEnable() {
        return this.isMakedEnable;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        if (this.view != null) {
            this.view.setEnabled(this.isMakedEnable);
        }
    }

}