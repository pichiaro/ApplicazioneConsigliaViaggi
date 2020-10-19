package cvapp.views;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mikhaellopez.circularimageview.CircularImageView;

import cvapp.R;

import models.AbstractUserPublicModel;
import utilities.DatesUtility;

public class UtenteView extends LinearLayout {

    private final static int SPAZIATURA_POST_TOOLBAR = 100;
    private final static int LARGHEZZA_NICKNAME_LABEL = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.60);
    private final static int LARGHEZZA_IMAGE_VIEW = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.40);
    private final static int ALTEZZA_IMAGE_VIEW = 300;

    public UtenteView(Activity activity, AbstractUserPublicModel userPublicModel) {
        super(activity);
        this.setOrientation(LinearLayout.VERTICAL);

        Resources resources = activity.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        int actionBarHeight = 0;
        Theme theme = activity.getTheme();
        if (theme != null) {
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }

        // creazione e settaggio toolbar

        Toolbar toolbar = new Toolbar(activity);
        toolbar.setBackgroundColor(AbstractStrutturaView.COLORE_PRINCIPALE);
        toolbar.setMinimumHeight(actionBarHeight);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Profilo utente");
        toolbar.setNavigationIcon(R.drawable.white_user);
        this.addView(toolbar);

        // get nazionalità

        String naz = userPublicModel.getNationality();

        // get sesso

        String sesso = userPublicModel.getGender();

        // get data nascita

        String dataNascita = userPublicModel.getBornDate();
        dataNascita = DatesUtility.toDDMMYYYYFormat(dataNascita, true);

        // get data registrazione

        String dataRegistrazione = userPublicModel.getRegistrationDate();
        dataRegistrazione = DatesUtility.toDDMMYYYYFormat(dataRegistrazione, true);

        // get nickname

        String nickname = userPublicModel.getUser();

        // get numero recensioni

        String numeroRecensioni = String.valueOf(userPublicModel.getAssociatedsCount());

        LinearLayout linearLayout = new LinearLayout(activity);
        LayoutParams layoutParams = new LayoutParams(displayMetrics.widthPixels, UtenteView.SPAZIATURA_POST_TOOLBAR);
        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);

        // set nickname label

        TextView textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + nickname);
        layoutParams = new LayoutParams(UtenteView.LARGHEZZA_NICKNAME_LABEL, AbstractStrutturaView.ALTEZZA_LABELS);
        textView.setLayoutParams(layoutParams);
        Drawable drawable = resources.getDrawable(R.drawable.nick, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);

        // set immagine profilo image view

        CircularImageView circularImageView = new CircularImageView(activity);
        layoutParams = new LayoutParams(UtenteView.LARGHEZZA_IMAGE_VIEW, UtenteView.ALTEZZA_IMAGE_VIEW);
        circularImageView.setLayoutParams(layoutParams);

        linearLayout = new LinearLayout(activity);
        linearLayout.addView(textView);
        linearLayout.addView(circularImageView);
        this.addView(linearLayout);

        // set nazionalità label

        layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_LABELS);

        if (userPublicModel.isNationalityVisible()) {
            textView = new TextView(activity);
            textView.setText("   " + naz);
            textView.setTextColor(Color.DKGRAY);
            textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
            textView.setLayoutParams(layoutParams);
            textView.setTypeface(null, Typeface.BOLD);
            drawable = resources.getDrawable(R.drawable.flag, null);
            drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
            textView.setCompoundDrawables(drawable, null, null, null);
            this.addView(textView);
        }

        // set sesso label

        if (userPublicModel.isGenderVisible()) {
            textView = new TextView(activity);
            textView.setTextColor(Color.DKGRAY);
            textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("   " + sesso);
            textView.setLayoutParams(layoutParams);
            drawable = resources.getDrawable(R.drawable.generic, null);
            drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
            textView.setCompoundDrawables(drawable, null, null, null);
            this.addView(textView);
        }

        // set età label

        if (userPublicModel.isAgeVisible()) {
            String dataAttuale = DatesUtility.getLocalYYYYMMDDDate(false);
            dataAttuale = DatesUtility.toDDMMYYYYFormat(dataAttuale, false);
            int anni = DatesUtility.getYearsBetweenDDMMYYYY(dataAttuale, dataNascita);
            String eta = String.valueOf(anni);
            textView = new TextView(activity);
            textView.setTextColor(Color.DKGRAY);
            textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("   " + eta);
            textView.setLayoutParams(layoutParams);
            drawable = resources.getDrawable(R.drawable.cake, null);
            drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
            textView.setCompoundDrawables(drawable, null, null, null);
            this.addView(textView);
        }

        // set data registrazione label

        textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + dataRegistrazione + " (registrazione)");
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.date, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView);

        // set numero recensioni label

        textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + numeroRecensioni);
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.review, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView);

    }

    public void setImmagineProfilo(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        LinearLayout linearLayout = (LinearLayout) this.getChildAt(2);
        CircularImageView circularImageView = (CircularImageView) linearLayout.getChildAt(1);
        circularImageView.setImageBitmap(bitmap);
    }

}
