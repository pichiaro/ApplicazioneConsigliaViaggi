package ondatechangelisteners;

import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

public class ViewVisibilitySetter implements OnDateChangeListener {

    private View view;
    private boolean isMakedVisible;

    public ViewVisibilitySetter(View view, boolean isMakedVisible) {
        this.view = view;
        this.isMakedVisible = isMakedVisible;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setMakedVisible(boolean makedVisible) {
        isMakedVisible = makedVisible;
    }

    public boolean isMakedVisible() {
        return this.isMakedVisible;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        if (this.view != null) {
            if (!this.isMakedVisible) {
                this.view.setVisibility(View.INVISIBLE);
            }
            else {
                this.view.setVisibility(View.VISIBLE);
            }
        }
    }

}