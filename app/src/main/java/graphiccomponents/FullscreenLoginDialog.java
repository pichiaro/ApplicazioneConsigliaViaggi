package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import java.util.Vector;

import utilities.StringsUtility;
import onclicklisteners.CompoundButtonsCheckSetter;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.forcompoundbutton.MutualTogglerForCompoundButton;
import onclicklisteners.TextViewTrMetSetter;
import onclicklisteners.forcompoundbutton.TextViewTrMetSetterForCompoundButton;
import onclicklisteners.TextViewsTextSetter;

public class FullscreenLoginDialog extends InputReadableDialog {

    private final PasswordTransformationMethod passwordTransformationMethod = new PasswordTransformationMethod();

    private LinearLayout contentView;

    public FullscreenLoginDialog(Context context) {
        super(context, "", android.R.style.Theme_DeviceDefault_Light_NoActionBar);

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

        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        this.contentView.addView(editText);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        this.contentView.addView(textView);

        editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setSingleLine(true);
        this.contentView.addView(editText);
        editText.setTransformationMethod(this.passwordTransformationMethod);

        layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH));
        layoutParams.gravity = Gravity.CENTER;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT * 0.25));
        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        CheckBox checkBox = new CheckBox(context);
        TextViewTrMetSetterForCompoundButton textViewTrMetSetterForCompoundButton = new TextViewTrMetSetterForCompoundButton(this.getPasswordEditText(), null, this.passwordTransformationMethod);
        checkBox.setOnClickListener(textViewTrMetSetterForCompoundButton);
        linearLayout.addView(checkBox);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        AlternativeRadioButton alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        alternativeRadioButton = new AlternativeRadioButton(context);
        linearLayout.addView(alternativeRadioButton);

        MutualTogglerForCompoundButton mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(4), (AlternativeRadioButton) linearLayout.getChildAt(5));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(3);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(3), (AlternativeRadioButton) linearLayout.getChildAt(5));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(4);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        mutualTogglerForCompoundButton = new MutualTogglerForCompoundButton(new Vector<>());
        mutualTogglerForCompoundButton.addCompoundButtons((AlternativeRadioButton) linearLayout.getChildAt(3), (AlternativeRadioButton) linearLayout.getChildAt(4));
        alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(5);
        alternativeRadioButton.setOnClickListener(mutualTogglerForCompoundButton);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        this.contentView.addView(linearLayout);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        linearLayout = new LinearLayout(context);
        Button button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setClickable(false);
        button.setBackgroundColor(Color.TRANSPARENT);
        linearLayout.addView(button);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        button.setText("Reset");
        linearLayout.addView(button);

        TextViewTrMetSetter textViewTrMetSetter = new TextViewTrMetSetter(this.getPasswordEditText(), this.passwordTransformationMethod);
        TextViewsTextSetter textViewsTextSetter = new TextViewsTextSetter(new Vector<>(), "");
        textViewsTextSetter.addTextViews(this.getUserNameEditText(), this.getPasswordEditText());
        CompoundButtonsCheckSetter compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(checkBox, this.getRememberPasswordRadioButton(), this.getRememberCredentialsRadioButton());
        MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(textViewTrMetSetter, textViewsTextSetter, compoundButtonsCheckSetter);
        button.setOnClickListener(multipleOnClickListener);

        button = new Button(context);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        this.contentView.addView(linearLayout);

        this.setContentView(this.contentView);
    }

    public boolean isPasswordRemembered() {
        AlternativeRadioButton alternativeRadioButton = this.getRememberPasswordRadioButton();
        return alternativeRadioButton.isChecked();
    }

    public boolean areCredentialsRemembered() {
        AlternativeRadioButton alternativeRadioButton = this.getRememberCredentialsRadioButton();
        return alternativeRadioButton.isChecked();
    }

    public boolean isLoginAutomatic() {
        AlternativeRadioButton alternativeRadioButton = this.getMakeLoginAutomaticRadioButton();
        return alternativeRadioButton.isChecked();
    }



    public String getUsername() {
        EditText editText = this.getUserNameEditText();
        Editable editable = editText.getText();
        if (editable != null) {
            String toString = editable.toString();
            toString = StringsUtility.flush(toString);
            return toString;
        }
        return "";
    }

    public String getPassword() {
        EditText editText = this.getPasswordEditText();
        Editable editable = editText.getText();
        if (editable != null) {
            String toString = editable.toString();
            toString = StringsUtility.flush(toString);
            return toString;
        }
        return "";
    }

    public Button getResetButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(6);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    public Button getRightButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(6);
        Button button = (Button) linearLayout.getChildAt(2);
        return button;
    }

    public Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) this.contentView.getChildAt(0);
        return toolbar;
    }

    public TextView getUserNameTextView() {
        TextView textView  = (TextView) this.contentView.getChildAt(1);
        return textView;
    }

    public EditText getUserNameEditText() {
        EditText editText  = (EditText) this.contentView.getChildAt(2);
        return editText;
    }

    public TextView getPasswordTextView() {
        TextView textView  = (TextView) this.contentView.getChildAt(3);
        return textView;
    }

    public EditText getPasswordEditText() {
        EditText editText  = (EditText) this.contentView.getChildAt(4);
        return editText;
    }

    public CheckBox getShowPasswordCheckBox() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(5);
        CheckBox checkBox = (CheckBox) linearLayout.getChildAt(1);
        return checkBox;
    }

    public AlternativeRadioButton getRememberPasswordRadioButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(5);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(3);
        return alternativeRadioButton;
    }

    public AlternativeRadioButton getRememberCredentialsRadioButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(5);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(4);
        return alternativeRadioButton;
    }

    public AlternativeRadioButton getMakeLoginAutomaticRadioButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(5);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(5);
        return alternativeRadioButton;
    }

    public LinearLayout getContentView() {
        return this.contentView;
    }

    public void hidePassword() {
        CheckBox checkBox = this.getShowPasswordCheckBox();
        checkBox.setChecked(false);
        EditText editText = this.getPasswordEditText();
        editText.setTransformationMethod(this.passwordTransformationMethod);
    }

}
