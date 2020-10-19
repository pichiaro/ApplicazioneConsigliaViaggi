package ondatechangelisteners;

import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

public class TextViewDateSetter implements OnDateChangeListener {

    private TextView textView;
    private String separator;

    public TextViewDateSetter(TextView textView, String separator) {
        this.textView = textView;
        this.separator = separator;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return this.separator;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        if (this.textView != null) {
            if (this.textView != null) {
                month = month + 1;
                this.textView.setText(dayOfMonth + this.separator + month + this.separator + year);
            }
        }
    }

}