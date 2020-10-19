package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;

import java.time.LocalDate;
import java.util.Calendar;

public class CalendarViewLocalDateSetter implements OnClickListener {

    private CalendarView calendarView;
    private Calendar calendar = Calendar.getInstance();

    public CalendarViewLocalDateSetter(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public CalendarView getCalendarView() {
        return this.calendarView;
    }

    @Override
    public void onClick(View view) {
        if (this.calendarView != null) {
            LocalDate localDate = LocalDate.now();
            String date = localDate.toString();
            String parts[] = date.split("-");
            int day = Integer.parseInt(parts[2]);
            int month = Integer.parseInt(parts[1]);
            month = month - 1;
            int year = Integer.parseInt(parts[0]);
            this.calendar.set(Calendar.YEAR, year);
            this.calendar.set(Calendar.MONTH, month);
            this.calendar.set(Calendar.DAY_OF_MONTH, day);
            long milliseconds = this.calendar.getTimeInMillis();
            this.calendarView.setDate(milliseconds, true, true);
        }
    }

}
