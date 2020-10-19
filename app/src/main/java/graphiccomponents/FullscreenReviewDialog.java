package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

import utilities.DatesUtility;
import utilities.StringsUtility;
import onclicklisteners.CalendarViewLocalDateSetter;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.RatingBarRatingSetter;
import onclicklisteners.TextViewsTextSetter;
import ondatechangelisteners.TextViewDateSetter;

public class FullscreenReviewDialog extends InputReadableDialog {

    private LinearLayout contentView;

    public FullscreenReviewDialog(Context context) {
        super(context, "", android.R.style.Theme_DeviceDefault_Light_NoActionBar);

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        Toolbar toolbar = new Toolbar(context);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;

        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        textView = new TextView(context);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);
        linearLayout.addView(textView);

        layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * FullscreenCalendarDialog.CALENDAR_VIEW_HEIGHT));
        CalendarView calendarView = new CalendarView(context);
        calendarView.setLayoutParams(layoutParams);
        TextViewDateSetter textViewDateSetter = new TextViewDateSetter(textView, "/");
        calendarView.setOnDateChangeListener(textViewDateSetter);
        linearLayout.addView(calendarView);

        textView = new TextView(context);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        RatingBar ratingBar = new RatingBar(context);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ratingBar.setLayoutParams(layoutParams);
        linearLayout.addView(ratingBar);

        textView = new TextView(context);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        editText = new EditText(context);
        layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2.25), (int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 3));
        layoutParams.gravity = Gravity.CENTER;
        editText.setLayoutParams(layoutParams);
        editText.setGravity(Gravity.LEFT | Gravity.TOP);
        linearLayout.addView(editText);

        textView = new TextView(context);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        layoutParams = new LayoutParams(widthInPixel, (int)(heightInPixel * LinearLayouts.LAYOUT_HEIGHT));
        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(linearLayout);

        CalendarViewLocalDateSetter calendarViewLocalDateSetter = new CalendarViewLocalDateSetter(calendarView);
        TextViewsTextSetter textViewsTextSetter = new TextViewsTextSetter(new Vector<>(), "");
        textViewsTextSetter.addTextViews((EditText) linearLayout.getChildAt(1), (TextView) linearLayout.getChildAt(3), (EditText) linearLayout.getChildAt(8));
        RatingBarRatingSetter ratingBarRatingSetter = new RatingBarRatingSetter(ratingBar, 0);
        MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(calendarViewLocalDateSetter, textViewsTextSetter, ratingBarRatingSetter);

        linearLayout = new LinearLayout(context);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH),(int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(multipleOnClickListener);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        this.contentView = new LinearLayout(context);
        this.contentView.setOrientation(LinearLayout.VERTICAL);
        this.contentView.addView(toolbar);
        this.contentView.addView(scrollView);
        this.contentView.addView(linearLayout);

        this.setContentView(this.contentView);

    }

    public Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) this.contentView.getChildAt(0);
        return toolbar;
    }

    public RatingBar getRatingBar() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        RatingBar ratingBar = (RatingBar) linearLayout.getChildAt(6);
        return ratingBar;
    }

    public float getReviewRating() {
        RatingBar ratingBar = this.getRatingBar();
        float voto = ratingBar.getRating();
        return voto;
    }

    public EditText getReviewTitleEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(1);
        return editText;
    }

    public String getReviewTitle() {
        EditText editText = this.getReviewTitleEditText();
        Editable editable = editText.getText();
        if (editable != null) {
            String titolo = editable.toString();
            titolo = StringsUtility.flush(titolo);
            return titolo;
        }
        return "";
    }

    public TextView getReviewDateTextView() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        TextView textView = (TextView) linearLayout.getChildAt(3);
        return textView;
    }

    public String getReviewDate() {
        TextView textView = this.getReviewDateTextView();
        CharSequence charSequence = textView.getText();
        if (charSequence != null) {
            String data = charSequence.toString();
            String[] strings = data.split("/");
            if (strings.length == 3) {
                int mese = Integer.parseInt(strings[1]);
                if (mese < 10) {
                    strings[1] = "0" + strings[1];
                }
                int giorno = Integer.parseInt(strings[0]);
                if (giorno < 10) {
                    strings[0] = "0" + strings[0];
                }
                if (strings.length == 3) {
                    data = strings[2] + strings[1] + strings[0];
                }
                return data;
            }
        }
        return "";
    }

    public EditText getReviewTextEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(8);
        return editText;
    }

    public String getReviewText() {
        EditText editText = this.getReviewTextEditText();
        Editable editable = editText.getText();
        if (editable != null) {
            String testo = editable.toString();
            testo = StringsUtility.flush(testo);
            return testo;
        }
        return "";
    }

    @Override
    public void reset() {
        super.reset();
        RatingBar ratingBar = this.getRatingBar();
        ratingBar.setRating(0);

        EditText editText = this.getReviewTitleEditText();
        editText.setText("");

        TextView textView = this.getReviewDateTextView();
        textView.setText("");

        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        CalendarView calendarView = (CalendarView) linearLayout.getChildAt(4);
        DatesUtility.setLocalDate(calendarView);

        editText = this.getReviewTextEditText();
        editText.setText("");
        scrollView.fullScroll(View.FOCUS_UP);
    }

    @Override
    public void dismiss() {
        this.setReadInputDefaultText(this.getReviewDate() + "," + this.getReviewTitle() + "," + this.getReviewRating() + "," + this.getReviewTitleEditText());
        super.dismiss();
    }

    public Button getLeftButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(2);
        Button button = (Button) linearLayout.getChildAt(0);
        return button;
    }

    public Button getResetButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(2);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    public Button getRightButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(2);
        Button button = (Button) linearLayout.getChildAt(2);
        return button;
    }

    public ScrollView getScrollView() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        return scrollView;
    }

    public LinearLayout getContentView() {
        return this.contentView;
    }

}
