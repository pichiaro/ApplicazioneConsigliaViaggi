package onvaluechangelistener;

import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import utilities.DatesUtility;

public class YearSetter implements OnValueChangeListener {

    private CalendarView calendarView;
    private int day;
    private int month;

    public YearSetter(CalendarView calendarView, int day, int month) {
        this.calendarView = calendarView;
        this.day = day;
        this.month = month;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public CalendarView getCalendarView() {
        return this.calendarView;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return this.day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return this.month;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        DatesUtility.setDate(this.calendarView, this.day, this.month, picker.getValue());
    }

}
