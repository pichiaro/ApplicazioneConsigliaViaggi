package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.CalendarViewLocalDateSetter;
import onclicklisteners.TextViewTextSetter;
import ondatechangelisteners.DateChangedChecker;
import ondatechangelisteners.ViewEnabler;
import ondatechangelisteners.ViewVisibilitySetter;
import ondatechangelisteners.MultipleOnDateChangeListener;
import ondatechangelisteners.TimePickerScheduleSetter;
import ondatechangelisteners.TextViewDateSetter;

public class FullscreenCalendarDialogBuilder {

    private Context context;

    private int toolbarBackgroundColor;
    private int resetButtonBackgroundColor;
    private int dismissButtonBackgroundColor;
    private String toolbarTitle;
    private int toolbarTitleTextColor;
    private int toolbarHeight;

    private int textViewBackgroundColor;
    private int textViewTextSize;
    private int textViewTextColor;
    private int textViewTextAlignment;

    private float resetButtonTextSize;
    private int resetButtonTextColor;
    private int resetButtonTextAlignment;
    private boolean resetButtonIsAllCaps;
    private String rightButtonText;
    private float rightButtonTextSize;
    private int rightButtonTextColor;
    private int rightButtonTextAlignment;
    private boolean rightButtonAllCaps;
    private boolean isTimePicker24hFormat;
    private int toolBarNavigationIcon;
    private int textViewBackgroundResources;
    private Drawable textViewLeftDrawable;
    private OnClickListener rightButtonOnClickListener;
    private boolean isTimePickerAdded;
    private String dateSeparator;

    public FullscreenCalendarDialogBuilder(Context context) {
        this.context = context;
    }

    public void setToolbarBackgroundColor(int toolbarBackgroundColor) {
        this.toolbarBackgroundColor = toolbarBackgroundColor;
    }

    public void setResetButtonBackgroundColor(int resetButtonBackgroundColor) {
        this.resetButtonBackgroundColor = resetButtonBackgroundColor;
    }

    public void setDismissButtonBackgroundColor(int dismissButtonBackgroundColor) {
        this.dismissButtonBackgroundColor = dismissButtonBackgroundColor;
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    public void setToolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
    }

    public void setToolbarTitleTextColor(int toolbarTitleTextColor) {
        this.toolbarTitleTextColor = toolbarTitleTextColor;
    }

    public void setTextViewTextSize(int textViewTextSize) {
        this.textViewTextSize = textViewTextSize;
    }

    public void setTextViewBackgroundColor(int textViewBackgroundColor) {
        this.textViewBackgroundColor = textViewBackgroundColor;
    }

    public void setTextViewTextColor(int textViewTextColor) {
        this.textViewTextColor = textViewTextColor;
    }

    public void setTextViewTextAlignment(int textViewTextAlignment) {
        this.textViewTextAlignment = textViewTextAlignment;
    }

    public void setResetButtonTextSize(float resetButtonTextSize) {
        this.resetButtonTextSize = resetButtonTextSize;
    }

    public void setResetButtonTextColor(int resetButtonTextColor) {
        this.resetButtonTextColor = resetButtonTextColor;
    }

    public void setResetButtonTextAlignment(int resetButtonTextAlignment) {
        this.resetButtonTextAlignment = resetButtonTextAlignment;
    }

    public void setResetButtonIsAllCaps(boolean resetButtonIsAllCaps) {
        this.resetButtonIsAllCaps = resetButtonIsAllCaps;
    }

    public void setRightButtonText(String rightButtonText) {
        this.rightButtonText = rightButtonText;
    }

    public void setRightButtonTextSize(float rightButtonTextSize) {
        this.rightButtonTextSize = rightButtonTextSize;
    }

    public void setRightButtonTextColor(int rightButtonTextColor) {
        this.rightButtonTextColor = rightButtonTextColor;
    }

    public void setRightButtonTextAlignment(int rightButtonTextAlignment) {
        this.rightButtonTextAlignment = rightButtonTextAlignment;
    }

    public void setRightButtonAllCaps(boolean rightButtonAllCaps) {
        this.rightButtonAllCaps = rightButtonAllCaps;
    }

    public void setTimePicker24hFormat(boolean isTimeChangesCheckablesTimePicker24hFormat) {
        this.isTimePicker24hFormat = isTimeChangesCheckablesTimePicker24hFormat;
    }

    public void setToolBarNavigationIcon(int toolBarNavigationIcon) {
        this.toolBarNavigationIcon = toolBarNavigationIcon;
    }

    public void setTextViewBackgroundResources(int textViewBackgroundResources) {
        this.textViewBackgroundResources = textViewBackgroundResources;
    }

    public void setTextViewLeftDrawable(Drawable textViewLeftDrawable) {
        this.textViewLeftDrawable = textViewLeftDrawable;
    }

    public void setRightButtonOnClickListener(OnClickListener rightButtonOnClickListener) {
        this.rightButtonOnClickListener = rightButtonOnClickListener;
    }

    public void setTimePickerAdded(boolean timePickerAdded) {
        isTimePickerAdded = timePickerAdded;
    }

    public void setDateSeparator(String dateSeparator) {
        this.dateSeparator = dateSeparator;
    }

    public FullscreenCalendarDialog create() {
        FullscreenCalendarDialog fullscreenCalendarDialog = null;
        if (this.context != null) {

            fullscreenCalendarDialog = new FullscreenCalendarDialog(this.context);

            Toolbar toolbar = fullscreenCalendarDialog.getToolbar();
            Resources system = Resources.getSystem();
            DisplayMetrics displayMetrics = system.getDisplayMetrics();
            int widthInPixel = displayMetrics.widthPixels;
            LayoutParams toolbarLayoutParams = new LayoutParams(widthInPixel, this.toolbarHeight);
            toolbar.setLayoutParams(toolbarLayoutParams);
            toolbar.setBackgroundColor(this.toolbarBackgroundColor);
            toolbar.setTitleTextColor(this.toolbarTitleTextColor);
            toolbar.setTitle(this.toolbarTitle);
            toolbar.setNavigationIcon(this.toolBarNavigationIcon);

            TimePicker timePicker = fullscreenCalendarDialog.getTimePicker();
            timePicker.setIs24HourView(this.isTimePicker24hFormat);
            timePicker.setEnabled(false);
            timePicker.setVisibility(TextView.INVISIBLE);

            TextView textView = fullscreenCalendarDialog.getTextView();
            textView.setBackgroundColor(this.textViewBackgroundColor);
            textView.setTextSize(this.textViewTextSize);
            textView.setTextColor(this.textViewTextColor);
            textView.setTextAlignment(this.textViewTextAlignment);
            textView.setBackgroundResource(this.textViewBackgroundResources);
            if (this.textViewLeftDrawable != null) {
                textView.setCompoundDrawables(this.textViewLeftDrawable, null, null, null);
            }

            Button button = fullscreenCalendarDialog.getRightButton();
            button.setBackgroundColor(this.dismissButtonBackgroundColor);
            button.setText(this.rightButtonText);
            button.setTextSize(this.rightButtonTextSize);
            button.setTextColor(this.rightButtonTextColor);
            button.setTextAlignment(this.rightButtonTextAlignment);
            button.setAllCaps(this.rightButtonAllCaps);
            if (this.rightButtonOnClickListener != null) {
                button.setOnClickListener(this.rightButtonOnClickListener);
            }

            button = fullscreenCalendarDialog.getResetButton();
            button.setBackgroundColor(this.resetButtonBackgroundColor);
            button.setText("Reset");
            button.setTextSize(this.resetButtonTextSize);
            button.setTextColor(this.resetButtonTextColor);
            button.setTextAlignment(this.resetButtonTextAlignment);
            button.setAllCaps(this.resetButtonIsAllCaps);

            MultipleOnDateChangeListener multipleOnDateChangeListener = fullscreenCalendarDialog.getMultipleOnDateChangeListener();
            TextViewDateSetter textViewDateSetter = (TextViewDateSetter) multipleOnDateChangeListener.getOnDateChangeListener(2);
            textViewDateSetter.setSeparator(this.dateSeparator);
            fullscreenCalendarDialog.setSeparator(this.dateSeparator);
            DateChangedChecker dateChangedChecker = (DateChangedChecker) multipleOnDateChangeListener.getOnDateChangeListener(3);
            OnClickListener onClickListener = dateChangedChecker.getDateChangedResetter();
            onclicklisteners.ViewVisibilitySetter viewVisibilitySetter = null;
            onclicklisteners.ViewEnabler viewEnabler = null;
            if (this.isTimePickerAdded) {
                viewVisibilitySetter = new onclicklisteners.ViewVisibilitySetter(timePicker, false);
                viewEnabler = new onclicklisteners.ViewEnabler(timePicker, false);
            }
            else {
                ViewEnabler makeEnableViewOnDateChangeListener = (ViewEnabler) multipleOnDateChangeListener.getOnDateChangeListener(0);
                makeEnableViewOnDateChangeListener.setView(null);
                ViewVisibilitySetter makeVisibleViewOnDateChangeListener = (ViewVisibilitySetter) multipleOnDateChangeListener.getOnDateChangeListener(1);
                makeVisibleViewOnDateChangeListener.setView(null);
                TimePickerScheduleSetter timePickerScheduleSetter = (TimePickerScheduleSetter) multipleOnDateChangeListener.getOnDateChangeListener(4);
                timePickerScheduleSetter.setTimePicker(null);
            }
            TextViewTextSetter textViewTextSetter = new TextViewTextSetter(textView, "");
            CalendarView calendarView = fullscreenCalendarDialog.getCalendarView();
            CalendarViewLocalDateSetter calendarViewLocalDateSetter = new CalendarViewLocalDateSetter(calendarView);
            MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
            multipleOnClickListener.addOnClickListeners(viewEnabler, viewVisibilitySetter, onClickListener, textViewTextSetter, calendarViewLocalDateSetter);
            button.setOnClickListener(multipleOnClickListener);
        }
        return fullscreenCalendarDialog;
    }

}
