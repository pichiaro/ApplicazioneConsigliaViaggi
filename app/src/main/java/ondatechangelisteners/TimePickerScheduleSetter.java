package ondatechangelisteners;

import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TimePicker;

public class TimePickerScheduleSetter implements OnDateChangeListener {

    private TimePicker timePicker;
    private int hour;
    private int minute;

    public TimePickerScheduleSetter(TimePicker timePicker, int hour, int minute) {
        this.timePicker = timePicker;
        this.hour = hour;
        this.minute = minute;
    }

    public void setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
    }

    public TimePicker getTimePicker() {
        return this.timePicker;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return this.hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return this.minute;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        if (this.timePicker != null) {
            if (this.hour >= 0) {
                if (this.hour <= 23) {
                    if (this.minute >= 0) {
                        if (this.minute <= 59) {
                            this.timePicker.setHour(this.hour);
                            this.timePicker.setMinute(this.minute);
                        }
                    }
                }
            }
        }
    }

}
