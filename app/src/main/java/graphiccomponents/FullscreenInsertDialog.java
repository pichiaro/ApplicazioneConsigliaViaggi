package graphiccomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import utilities.StringsUtility;
import onitemclicklisteners.EditTextSelectionSetter;

public class FullscreenInsertDialog extends InputReadableDialog {

    private LinearLayout contentView;

    public FullscreenInsertDialog(Context context) {
        super(context, "", InputReadableDialog.FULLSCREEN_THEME);

        Resources system = Resources.getSystem();
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        this.contentView = new LinearLayout(context);
        this.contentView.setOrientation(LinearLayout.VERTICAL);

        Toolbar toolbar = new Toolbar(context);

        LayoutParams layoutParams = new LayoutParams((int)(widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2), (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);

        AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(context);
        autoCompleteTextView.setLayoutParams(layoutParams);
        autoCompleteTextView.setSingleLine(true);
        autoCompleteTextView.setThreshold(1);

        this.contentView.addView(toolbar);
        this.contentView.addView(textView);
        this.contentView.addView(autoCompleteTextView);

        layoutParams = new LayoutParams((int) (widthInPixel * LinearLayouts.BUTTON_WIDTH), (int) (heightInPixel * LinearLayouts.BUTTON_HEIGHT));

        LinearLayout linearLayout = new LinearLayout(context);
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

        EditTextSelectionSetter editTextSelectionSetter = new EditTextSelectionSetter(autoCompleteTextView, 0);
        autoCompleteTextView.setOnItemClickListener(editTextSelectionSetter);

        this.setContentView(this.contentView);
    }

    public Toolbar getToolbar() {
        Toolbar toolbar = (Toolbar) this.contentView.getChildAt(0);
        return toolbar;
    }

    public AutoCompleteTextView getAutoCompleteTextView() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.contentView.getChildAt(2);
        return autoCompleteTextView;
    }

    public Button getResetButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(3);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    public Button getRightButton() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.getChildAt(3);
        Button button = (Button) linearLayout.getChildAt(2);
        return button;
    }

    public LinearLayout getContentView() {
        return this.contentView;
    }

    @Override
    public void dismiss() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.contentView.getChildAt(2);
        Editable editable = autoCompleteTextView.getText();
        String string = editable.toString();
        if (string.length() == 0) {
            this.getReadInputTextView().setText(this.getReadInputDefaultText());
        } else {
            this.getReadInputTextView().setText(string);
        }
        super.dismiss();
    }

    @Override
    public void reset() {
        super.reset();
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.contentView.getChildAt(2);
        autoCompleteTextView.setText("");
    }

    public String getEditedText() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.contentView.getChildAt(2);
        Editable editable = autoCompleteTextView.getText();
        String editedText = editable.toString();
        editedText = StringsUtility.flush(editedText);
        return editedText;
    }

}
