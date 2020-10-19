package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

import ondatechangelisteners.ViewEnabler;
import ondatechangelisteners.DateChangedChecker;
import ondatechangelisteners.TimePickerScheduleSetter;
import ondatechangelisteners.ViewVisibilitySetter;
import ondatechangelisteners.MultipleOnDateChangeListener;
import ondatechangelisteners.TextViewDateSetter;
import utilities.DatesUtility;

public class FullscreenCalendarDialog extends InputReadableDialog {

    public final static double CALENDAR_VIEW_HEIGHT = 0.48;
    public final static double TIME_PICKER_WIDTH = 0.40;
    public final static double TIME_PICKER_HEIGHT = 0.15;
    public final static double TEXT_VIEW_WIDTH = 0.40;
    public final static double TEXT_VIEW_HEIGHT = 0.0725;

    private LinearLayout contentView;
    private String separator;
    private final MultipleOnDateChangeListener multipleOnDateChangeListener = new MultipleOnDateChangeListener(new Vector<>());

    public FullscreenCalendarDialog(Context context) {
        super(context, "", InputReadableDialog.FULLSCREEN_THEME);

        Resources system = Resources.getSystem();
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        this.contentView = new LinearLayout(context);
        this.contentView.setOrientation(LinearLayout.VERTICAL);

        Toolbar toolbar = new Toolbar(context);
        this.contentView.addView(toolbar);

        LayoutParams layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        this.contentView.addView(textView);

        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        TimePicker timePicker = new TimePicker(contextThemeWrapper);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TIME_PICKER_WIDTH), (int) (heightInPixel * FullscreenCalendarDialog.TIME_PICKER_HEIGHT));
        timePicker.setLayoutParams(layoutParams);

        CalendarView calendarView = new CalendarView(context);
        layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * FullscreenCalendarDialog.CALENDAR_VIEW_HEIGHT));
        calendarView.setLayoutParams(layoutParams);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);

        linearLayout.addView(textView);
        linearLayout.addView(timePicker);
        this.contentView.addView(linearLayout);

        this.contentView.addView(calendarView);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        linearLayout = new LinearLayout(context);
        Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        button.setBackgroundColor(Color.TRANSPARENT);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        this.contentView.addView(linearLayout);

        ViewEnabler viewEnabler = new ViewEnabler(timePicker, true);
        ViewVisibilitySetter viewVisibilitySetter = new ViewVisibilitySetter(timePicker, true);
        TextViewDateSetter textViewDateSetter = new TextViewDateSetter(textView, "");
        DateChangedChecker dateChangedChecker = new DateChangedChecker();
        TimePickerScheduleSetter timePickerScheduleSetter = new TimePickerScheduleSetter(timePicker, 0, 0);
        this.multipleOnDateChangeListener.addOnDateChangeListeners(viewEnabler);
        this.multipleOnDateChangeListener.addOnDateChangeListeners(viewVisibilitySetter);
        this.multipleOnDateChangeListener.addOnDateChangeListeners(textViewDateSetter);
        this.multipleOnDateChangeListener.addOnDateChangeListeners(dateChangedChecker);
        this.multipleOnDateChangeListener.addOnDateChangeListeners(timePickerScheduleSetter);
        calendarView.setOnDateChangeListener(this.multipleOnDateChangeListener);

        this.setContentView(this.contentView);
    }

    @Override
    public void dismiss() {
        DateChangedChecker dateChangedChecker = (DateChangedChecker) this.multipleOnDateChangeListener.getOnDateChangeListener(3);
        if (dateChangedChecker.isDateChanged()) {
            TextView textView = this.getTextView();
            CharSequence charSequence = textView.getText();
            String dateString = charSequence.toString();
            if (dateString.length() > 0) {
                String hourFormat = "";
                TimePicker timePicker = this.getTimePicker();
                if (timePicker.isEnabled()) {
                    hourFormat = DatesUtility.getHHMM(timePicker, true);
                    hourFormat = " - " + hourFormat;
                }
                String[] strings = dateString.split("/");
                if (strings.length == 3) {
                    if (strings[0].length() == 1) {
                        strings[0] = "0" + strings[0];
                    }
                    if (strings[1].length() == 1) {
                        strings[1] = "0" + strings[1];
                    }
                }
                dateString = strings[0] + "/" + strings[1] + "/" + strings[2];
                String stringFormat = dateString + hourFormat;
                this.getReadInputTextView().setText(stringFormat);
            } else {
                this.getReadInputTextView().setText(this.getReadInputDefaultText());
                dateChangedChecker.resetDateChanged();
            }
        } else {
            this.getReadInputTextView().setText(this.getReadInputDefaultText());
            dateChangedChecker.resetDateChanged();
        }
        super.dismiss();
    }

    public Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) this.contentView.getChildAt(0);
        return toolbar;
    }

    public TextView getTextView() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(2);
        TextView textView = (TextView) linearLayout.getChildAt(0);
        return textView;
    }

    public TimePicker getTimePicker() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(2);
        TimePicker timePicker = (TimePicker) linearLayout.getChildAt(1);
        return timePicker;
    }

    public CalendarView getCalendarView() {
        CalendarView calendarView = (CalendarView) this.contentView.getChildAt(3);
        return calendarView;
    }

    void setSeparator(String separator) {
        this.separator = separator;
    }

    public LinearLayout getContentView() {
        return this.contentView;
    }

    public Button getResetButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(4);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    public Button getRightButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(4);
        Button button = (Button) linearLayout.getChildAt(2);
        return button;
    }

    public MultipleOnDateChangeListener getMultipleOnDateChangeListener() {
        return this.multipleOnDateChangeListener;
    }

    @Override
    public void reset() {
        super.reset();
        CalendarView calendarView = this.getCalendarView();
        DatesUtility.setLocalDate(calendarView);
        TimePicker timePicker = this.getTimePicker();
        timePicker.setHour(0);
        timePicker.setMinute(0);
        timePicker.setEnabled(false);
        timePicker.setVisibility(View.INVISIBLE);
        TextView textView = this.getTextView();
        textView.setText("");
        DateChangedChecker dateChangedChecker = (DateChangedChecker) this.multipleOnDateChangeListener.getOnDateChangeListener(3);
        dateChangedChecker.resetDateChanged();
    }

    public String getDateInYYYYMMDDFormat() {
        String date = "";
        DateChangedChecker dateChangedChecker = (DateChangedChecker) this.multipleOnDateChangeListener.getOnDateChangeListener(3);
        if (dateChangedChecker.isDateChanged()) {
            TextView textView = this.getTextView();
            CharSequence charSequence = textView.getText();
            date = charSequence.toString();
            String[] splitted = date.split(this.separator);
            if (splitted.length == 3) {
                if (Integer.parseInt(splitted[0]) < 10) {
                    splitted[0] = "0" + splitted[0];
                }
                if (Integer.parseInt(splitted[1]) < 10) {
                    splitted[1] = "0" + splitted[1];
                }
                date = splitted[2] + splitted[1] + splitted[0];
            }
        }
        return date;
    }

    public String getHourWithMinutes() {
        String hourWithMinutes = "";
        TimePicker timePicker = this.getTimePicker();
        if (timePicker.isEnabled()) {
            return DatesUtility.getHHMM(timePicker, true);
        }
        return hourWithMinutes;
    }

}
