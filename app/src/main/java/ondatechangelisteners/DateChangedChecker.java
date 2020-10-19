package ondatechangelisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

public class DateChangedChecker implements OnDateChangeListener {

    private boolean isDateChanged;
    private final DateChangedResetter dateChangedResetter = new DateChangedResetter();

    public boolean isDateChanged() {
        return this.isDateChanged;
    }

    public void resetDateChanged() {
        this.isDateChanged = false;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        this.isDateChanged = true;
    }

    public DateChangedResetter getDateChangedResetter() {
        return this.dateChangedResetter;
    }

    private class DateChangedResetter implements OnClickListener {

        @Override
        public void onClick(View view) {
           resetDateChanged();
        }

    }

}