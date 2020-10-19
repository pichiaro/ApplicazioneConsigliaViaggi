package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

import onclicklisteners.forcompoundbutton.MutualTogglerForCompoundButton;
import onclicklisteners.forcompoundbutton.TextViewTrMetSetterForCompoundButton;
import ondatechangelisteners.TextViewDateSetter;
import onvaluechangelistener.YearSetter;
import utilities.DatesUtility;
import utilities.StringsUtility;

public class FullscreenSigninDialog extends InputReadableDialog {

    private LinearLayout contentView;
    private final PasswordTransformationMethod passwordTransformationMethod = new PasswordTransformationMethod();

    public FullscreenSigninDialog(Context context) {
        super(context, "", android.R.style.Theme_DeviceDefault_Light_NoActionBar);

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        Toolbar toolbar = new Toolbar(context);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;

        TextView textView = new TextView(context); //nome label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        EditText editText = new EditText(context); // nome edit
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);

        textView = new TextView(context);   // cognome label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        editText = new EditText(context); // cognome edit
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        textView = new TextView(context);  // datanascita view
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);
        linearLayout.addView(textView);


        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        NumberPicker numberPicker = new NumberPicker(contextThemeWrapper);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TIME_PICKER_WIDTH), (int) (heightInPixel * FullscreenCalendarDialog.TIME_PICKER_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        numberPicker.setLayoutParams(layoutParams);
        DatesUtility.setNumberPicker(numberPicker, 1930, 2020);
        numberPicker.setValue(2020);
        linearLayout.addView(numberPicker);

        layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * FullscreenCalendarDialog.CALENDAR_VIEW_HEIGHT));
        CalendarView calendarView = new CalendarView(context); // data nascica calendar
        calendarView.setLayoutParams(layoutParams);
        TextViewDateSetter textViewDateSetter = new TextViewDateSetter(textView, "/");
        calendarView.setOnDateChangeListener(textViewDateSetter);
        linearLayout.addView(calendarView);

        YearSetter yearSetter = new YearSetter(calendarView,1,1);
        numberPicker.setOnValueChangedListener(yearSetter);

        textView = new TextView(context);
        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;   // naz label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(context);
        autoCompleteTextView.setLayoutParams(layoutParams); // naz edit
        autoCompleteTextView.setSingleLine(true);
        autoCompleteTextView.setThreshold(1);
        linearLayout.addView(autoCompleteTextView);

        linearLayout.addView(new LinearLayout(context));

        layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2.5));
        layoutParams.gravity = Gravity.CENTER;
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);
        textView = new TextView(context);  // sex label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        AlternativeRadioButton alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        MutualTogglerForCompoundButton mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(3), (AlternativeRadioButton) linearLayout.getChildAt(4));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(2);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(2), (AlternativeRadioButton) linearLayout.getChildAt(4));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(3);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(2), (AlternativeRadioButton) linearLayout.getChildAt(3));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(4);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);
        textView = new TextView(context);  // privacy label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        AlternativeCheckBox alternativeCheckBox = new AlternativeCheckBox(context);
        linearLayout.addView(alternativeCheckBox);

        alternativeCheckBox = new AlternativeCheckBox(context);
        linearLayout.addView(alternativeCheckBox);

        alternativeCheckBox = new AlternativeCheckBox(context);
        linearLayout.addView(alternativeCheckBox);

        linearLayout = (LinearLayout) linearLayout.getParent();

        textView = new TextView(context);  // user id label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        editText = new EditText(context); // user id edit
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);

        textView = new TextView(context);   // pass label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        editText = new EditText(context);  //pass edit
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);
        editText.setTransformationMethod(this.passwordTransformationMethod);

        textView = new TextView(context);   // pass label
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        editText = new EditText(context);  //pass edit
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        linearLayout.addView(editText);
        editText.setTransformationMethod(this.passwordTransformationMethod);

        linearLayout.addView(new LinearLayout(context));
        layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 0.50));
        layoutParams.gravity = Gravity.CENTER;
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        alternativeCheckBox = new AlternativeCheckBox(context);
        TextViewTrMetSetterForCompoundButton textViewTrMetSetterForCompoundButton = new TextViewTrMetSetterForCompoundButton(editText, null, this.passwordTransformationMethod);
        alternativeCheckBox.setOnClickListener(textViewTrMetSetterForCompoundButton);
        linearLayout.addView(alternativeCheckBox);

        linearLayout = (LinearLayout) linearLayout.getParent();
        linearLayout.addView(new LinearLayout(context));
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
        layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * 0.40));
        linearLayout.setLayoutParams(layoutParams);
        linearLayout = (LinearLayout) linearLayout.getParent();

        layoutParams = new LayoutParams(widthInPixel, (int)(heightInPixel * LinearLayouts.LAYOUT_HEIGHT));
        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(layoutParams);
        scrollView.addView(linearLayout);

        linearLayout = new LinearLayout(context);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH),(int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        linearLayout.addView(button);

        button = new Button(context);
        ResettableResetExecuter resettableResetExecuter = new ResettableResetExecuter(this);
        button.setOnClickListener(resettableResetExecuter);
        button.setLayoutParams(layoutParams);
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

    public EditText getNameEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(1);
        return editText;
    }

    public String getName() {
        EditText editText = this.getNameEditText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    public EditText getSurnameEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(3);
        return editText;
    }

    public String getSurname() {
        EditText editText = this.getSurnameEditText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    public TextView getBornDateTextView() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        TextView textView = (TextView) linearLayout.getChildAt(5);
        return textView;
    }

    public String getBornDate() {
        TextView textView = this.getBornDateTextView();
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

    public AutoCompleteTextView getNationalityEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) linearLayout.getChildAt(9);
        return autoCompleteTextView;
    }

    public String getNationality() {
        AutoCompleteTextView autoCompleteTextView = this.getNationalityEditText();
        Editable editable = autoCompleteTextView.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }


    public TextView getGenderTextView() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        TextView textView = (TextView) linearLayout.getChildAt(1);
        return textView;
    }

    public AlternativeRadioButton getMaleRadioButton() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(2);
        return alternativeRadioButton;
    }

    public AlternativeRadioButton getFemaleRadioButton() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(3);
        return alternativeRadioButton;
    }

    public AlternativeRadioButton getNoGenderRadioButton() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(4);
        return alternativeRadioButton;
    }

    public TextView getPrivacyTextView() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        TextView textView = (TextView) linearLayout.getChildAt(6);
        return textView;
    }

    public AlternativeCheckBox getGenderCheckBox() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeCheckBox alternativeCheckBox = (AlternativeCheckBox) linearLayout.getChildAt(7);
        return alternativeCheckBox;
    }

    public AlternativeCheckBox getAgeCheckBox() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeCheckBox alternativeCheckBox = (AlternativeCheckBox) linearLayout.getChildAt(8);
        return alternativeCheckBox;
    }

    public AlternativeCheckBox getNationalityCheckBox() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(10);
        AlternativeCheckBox alternativeCheckBox = (AlternativeCheckBox) linearLayout.getChildAt(9);
        return alternativeCheckBox;
    }

    public EditText getUserIDEditText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(12);
        return editText;
    }

    public String getUserID() {
        EditText editText = this.getUserIDEditText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    public EditText getPasswordEdiText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(14);
        return editText;
    }

    public String getPassword() {
        EditText editText = this.getPasswordEdiText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    public EditText getConfirmPasswordEdiText() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        EditText editText = (EditText) linearLayout.getChildAt(16);
        return editText;
    }

    public String getConfirmPassword() {
        EditText editText = this.getConfirmPasswordEdiText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    public AlternativeCheckBox getShowPasswordCheckBox() {
        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(17);
        AlternativeCheckBox alternativeCheckBox = (AlternativeCheckBox) linearLayout.getChildAt(0);
        return alternativeCheckBox;
    }

    @Override
    public void reset() {
        super.reset();
        EditText editText = this.getNameEditText();
        editText.setText("");

        editText = this.getSurnameEditText();
        editText.setText("");

        TextView textView = this.getBornDateTextView();
        textView.setText("");

        ScrollView scrollView = (ScrollView) this.contentView.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        CalendarView calendarView = (CalendarView) linearLayout.getChildAt(7);
        DatesUtility.setLocalDate(calendarView);

        NumberPicker numberPicker = (NumberPicker) linearLayout.getChildAt(6);
        numberPicker.setValue(2020);

        AutoCompleteTextView autoCompleteTextView = this.getNationalityEditText();
        autoCompleteTextView.setText("");

        AlternativeRadioButton alternativeRadioButton = this.getMaleRadioButton();
        alternativeRadioButton.setChecked(false);

        alternativeRadioButton = this.getFemaleRadioButton();
        alternativeRadioButton.setChecked(false);

        alternativeRadioButton = this.getNoGenderRadioButton();
        alternativeRadioButton.setChecked(false);

        AlternativeCheckBox alternativeCheckBox = this.getGenderCheckBox();
        alternativeCheckBox.setChecked(false);

        alternativeCheckBox = this.getNationalityCheckBox();
        alternativeCheckBox.setChecked(false);

        alternativeCheckBox = this.getAgeCheckBox();
        alternativeCheckBox.setChecked(false);

        editText = this.getUserIDEditText();
        editText.setText("");

        editText = this.getPasswordEdiText();
        editText.setText("");

        editText = this.getConfirmPasswordEdiText();
        editText.setTransformationMethod(this.passwordTransformationMethod);
        editText.setText("");

        alternativeCheckBox = this.getShowPasswordCheckBox();
        alternativeCheckBox.setChecked(false);

        scrollView.fullScroll(View.FOCUS_UP);
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

    @Override
    public void dismiss() {
        this.setReadInputDefaultText(this.getName() + "," + this.getSurname() + "," + this.getBornDate() + "," + this.getNationality() + ", " + getUserID() + ", " + getPassword());
        super.dismiss();
    }

}
