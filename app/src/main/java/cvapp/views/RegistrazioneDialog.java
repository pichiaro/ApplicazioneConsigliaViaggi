package cvapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

import cvapp.R;
import graphiccomponents.AlternativeCheckBox;
import graphiccomponents.AlternativeRadioButton;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenSigninDialog;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.forcompoundbutton.UniqueMutualTogglerForCompoundButton;
import onclicklisteners.forcompoundbutton.ViewVisibilitySetterForCompoundButton;
import utilities.StringsUtility;

public class RegistrazioneDialog extends FullscreenSigninDialog {

    public RegistrazioneDialog(Context context) {
        super(context);

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        int actionBarHeight = 0;
        Theme theme = context.getTheme();
        if (theme != null) {
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }

        LayoutParams layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2) , (int) (heightInPixel * 0.35));
        layoutParams.gravity = Gravity.CENTER;

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        layoutParams = new LayoutParams((int) (widthInPixel * FullscreenCalendarDialog.TEXT_VIEW_WIDTH * 2) , (int) (heightInPixel * FullscreenCalendarDialog.TEXT_VIEW_HEIGHT));
        layoutParams.gravity = Gravity.CENTER;
        TextView textView = new TextView(context);
        textView.setText("Imposta il tuo nickname");
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        AlternativeRadioButton alternativeRadioButton = new AlternativeRadioButton(context);
        alternativeRadioButton.setTextSize(18);
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setText("Utilizza nome e cognome");
        linearLayout.addView(alternativeRadioButton);

        alternativeRadioButton = new AlternativeRadioButton(context);
        alternativeRadioButton.setTextSize(18);
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setText("Inseriscilo");
        linearLayout.addView(alternativeRadioButton);

        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);

        EditText editText = new EditText(context);
        editText.setSingleLine(true);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);
        editText.setHint("Il mio nickname...");
        editText.setBackgroundResource(R.drawable.border);
        Drawable drawable = resources.getDrawable(R.drawable.nick, null);
        drawable.setBounds(0, 0, 100, 80);
        editText.setCompoundDrawables(drawable, null, null, null);
        editText.setLayoutParams(layoutParams);
        linearLayout.addView(editText);

        ScrollView scrollView = this.getScrollView();
        scrollView.getLayoutParams().height = scrollView.getLayoutParams().height - actionBarHeight;
        int index = ((LinearLayout) scrollView.getChildAt(0)).getChildCount() - 1;
        ((LinearLayout) scrollView.getChildAt(0)).addView(linearLayout, index);

        Toolbar toolbar = this.getToolbar();
        toolbar.setMinimumHeight(actionBarHeight);
        toolbar.setTitle("Registrazione");
        toolbar.setNavigationIcon(R.drawable.white_registration);
        toolbar.setBackgroundColor(AbstractStrutturaView.COLORE_PRINCIPALE);
        toolbar.setTitleTextColor(Color.WHITE);

        editText = this.getNameEditText();
        editText.setHint(" il mio nome...");
        editText.setBackgroundResource(R.drawable.border);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);

        editText = this.getSurnameEditText();
        editText.setHint(" il mio cognome...");
        editText.setBackgroundResource(R.drawable.border);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);

        textView = this.getBornDateTextView();
        textView.setBackgroundResource(R.drawable.border);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        drawable = resources.getDrawable(R.drawable.cake, null);
        drawable.setBounds(0, 0, 100, 80);
        textView.setCompoundDrawables(drawable, null, null, null);

        AutoCompleteTextView autoCompleteTextView = this.getNationalityEditText();
        autoCompleteTextView.setBackgroundResource(R.drawable.border);
        autoCompleteTextView.setTextSize(18);
        autoCompleteTextView.setTextColor(Color.BLACK);
        autoCompleteTextView.setHint(" il mio paese...");
        drawable = resources.getDrawable(R.drawable.flag, null);
        drawable.setBounds(0, 0, 100, 80);
        autoCompleteTextView.setCompoundDrawables(drawable, null, null, null);

        textView = this.getGenderTextView();
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setText("Seleziona il genere");

        alternativeRadioButton = this.getMaleRadioButton();
        alternativeRadioButton.setTextSize(18);
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setText("Uomo");

        alternativeRadioButton = this.getFemaleRadioButton();
        alternativeRadioButton.setTextSize(18);
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setText("Donna");

        alternativeRadioButton = this.getNoGenderRadioButton();
        alternativeRadioButton.setTextSize(18);
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setText("Non dichiaro");

        textView = this.getPrivacyTextView();
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setText("Imposta privacy");

        AlternativeCheckBox alternativeCheckBox = this.getAgeCheckBox();
        alternativeCheckBox.setText("Non mostrare età");
        alternativeCheckBox.setTextColor(Color.BLACK);
        alternativeCheckBox.setTextSize(18);

        alternativeCheckBox = this.getNationalityCheckBox();
        alternativeCheckBox.setText("Non mostrare nazionalità");
        alternativeCheckBox.setTextColor(Color.BLACK);
        alternativeCheckBox.setTextSize(18);

        alternativeCheckBox = this.getGenderCheckBox();
        alternativeCheckBox.setText("Non mostrare genere");
        alternativeCheckBox.setTextColor(Color.BLACK);
        alternativeCheckBox.setTextSize(18);

        editText = this.getUserIDEditText();
        editText.setBackgroundResource(R.drawable.border);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);
        editText.setHint(" il mio user ID (indirizzio e-mail)...");
        drawable = resources.getDrawable(R.drawable.user, null);
        drawable.setBounds(0, 0, 100, 80);
        editText.setCompoundDrawables(drawable, null, null, null);

        editText = this.getPasswordEdiText();
        editText.setBackgroundResource(R.drawable.border);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);
        editText.setHint(" password...");
        drawable = resources.getDrawable(R.drawable.security, null);
        drawable.setBounds(0, 0, 100, 80);
        editText.setCompoundDrawables(drawable, null, null, null);

        editText = this.getConfirmPasswordEdiText();
        editText.setBackgroundResource(R.drawable.border);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);
        editText.setHint(" conferma password...");
        drawable = resources.getDrawable(R.drawable.security, null);
        drawable.setBounds(0, 0, 100, 80);
        editText.setCompoundDrawables(drawable, null, null, null);

        alternativeCheckBox = this.getShowPasswordCheckBox();
        alternativeCheckBox.setText("Mostra");
        alternativeCheckBox.setTextColor(Color.BLACK);
        alternativeCheckBox.setTextSize(18);

        MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(new UniqueMutualTogglerForCompoundButton(this.getNomeCognomeNicknameRadioButton()), new ViewVisibilitySetterForCompoundButton(this.getNicknameEditText(), true, true), new ViewVisibilitySetterForCompoundButton(this.getNicknameEditText(), false, false));
        this.getInserisciNicknameRadioButton().setOnClickListener(multipleOnClickListener);

        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(new UniqueMutualTogglerForCompoundButton(this.getInserisciNicknameRadioButton()), new ViewVisibilitySetterForCompoundButton(this.getNicknameEditText(), true, false));
        this.getNomeCognomeNicknameRadioButton().setOnClickListener(multipleOnClickListener);

        this.getNicknameEditText().setVisibility(View.INVISIBLE);

        Button button = this.getLeftButton();
        button.setBackgroundColor(AbstractStrutturaView.COLORE_PRINCIPALE);
        button.setTextSize(18);
        button = this.getResetButton();
        button.setBackgroundColor(AbstractStrutturaView.COLORE_PRINCIPALE);
        button.setTextSize(18);
        button.setText("Resetta");
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);
        button = this.getRightButton();
        button.setBackgroundColor(AbstractStrutturaView.COLORE_PRINCIPALE);
        button.setTextSize(18);
        button.setText("Invia");
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);

    }

    public AlternativeRadioButton getNomeCognomeNicknameRadioButton() {
        ScrollView scrollView = this.getScrollView();
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 2);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(1);
        return alternativeRadioButton;
    }

    public AlternativeRadioButton getInserisciNicknameRadioButton() {
        ScrollView scrollView = this.getScrollView();
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 2);
        AlternativeRadioButton alternativeRadioButton = (AlternativeRadioButton) linearLayout.getChildAt(2);
        return alternativeRadioButton;
    }

    public EditText getNicknameEditText() {
        ScrollView scrollView = this.getScrollView();
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        linearLayout = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 2);
        EditText editText = (EditText) linearLayout.getChildAt(4);
        return editText;
    }

    public String getNickname() {
        EditText editText = this.getNicknameEditText();
        Editable editable = editText.getText();
        if (editable == null) {
            return "";
        }
        return StringsUtility.flush(editable.toString());
    }

    @Override
    public void reset() {
        super.reset();

        AlternativeRadioButton alternativeRadioButton = this.getNomeCognomeNicknameRadioButton();
        alternativeRadioButton.setChecked(false);

        alternativeRadioButton = this.getInserisciNicknameRadioButton();
        alternativeRadioButton.setChecked(false);

        EditText editText = this.getNicknameEditText();
        editText.setVisibility(View.INVISIBLE);
        editText.setText("");

    }


}
