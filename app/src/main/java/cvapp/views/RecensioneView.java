package cvapp.views;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.mikhaellopez.circularimageview.CircularImageView;

import cvapp.R;
import models.AbstractReviewModel;
import utilities.DatesUtility;

public class RecensioneView extends LinearLayout {

    private String nickname;

    public final static int LARGHEZZA_IMG_PROFILO = (int)(Resources.getSystem().getDisplayMetrics().widthPixels * 0.28);
    public final static int ALTEZZA_IMG_PROFILO = 200;
    private final static int SPAZIATURA_INIZIALE = 100;
    private final static int LARGHEZZA_NICKNAME_LABEL = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.68);
    private final static int LARGHEZZA_TESTO = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.9);
    private final static int ALTEZZA_TESTO = 750;

    public RecensioneView(Activity activity, AbstractReviewModel reviewModel) {
        super(activity);
        this.setOrientation(LinearLayout.VERTICAL);

        Resources resources = activity.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        LinearLayout linearLayout = new LinearLayout(activity);
        LayoutParams layoutParams = new LayoutParams(displayMetrics.widthPixels, RecensioneView.SPAZIATURA_INIZIALE);
        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);

        // set nickname e get immagine profilo

        Bitmap bitmap = reviewModel.getImage();

        this.nickname = reviewModel.getUser();

        // set nickname label

        TextView textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + this.nickname);
        layoutParams = new LayoutParams(RecensioneView.LARGHEZZA_NICKNAME_LABEL, AbstractStrutturaView.ALTEZZA_LABELS);
        textView.setLayoutParams(layoutParams);
        Drawable drawable = resources.getDrawable(R.drawable.nick, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);

        // set imagine profilo image view

        CircularImageView circularImageView = new CircularImageView(activity);
        circularImageView.setImageBitmap(bitmap);
        layoutParams = new LayoutParams(RecensioneView.LARGHEZZA_IMG_PROFILO, RecensioneView.ALTEZZA_IMG_PROFILO);
        circularImageView.setLayoutParams(layoutParams);

        linearLayout = new LinearLayout(activity);
        linearLayout.addView(textView);
        linearLayout.addView(circularImageView);
        this.addView(linearLayout);

        // set data recensione label

        String data = reviewModel.getDate();
        data = DatesUtility.toDDMMYYYYFormat(data, true);
        textView = new TextView(activity);
        textView.setText("   " + data);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_LABELS);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(null, Typeface.BOLD);
        drawable = resources.getDrawable(R.drawable.date, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView);

        // set titolo label

        String titolo = reviewModel.getTitle();
        textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + "\"" + titolo + "\"");
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.title, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView);

        // set voto label

        String votoRecensioni = String.valueOf(reviewModel.getRating());
        RatingBar ratingBar = new RatingBar(activity);
        ratingBar.setStepSize((float) AbstractStrutturaView.RATING_BAR_SCALE);
        ratingBar.setScaleX((float) AbstractStrutturaView.RATING_BAR_SCALE);
        ratingBar.setScaleY((float) AbstractStrutturaView.RATING_BAR_SCALE);
        ratingBar.setRating(Float.parseFloat(votoRecensioni));
        ratingBar.setEnabled(false);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        drawable = layerDrawable.getDrawable(2);
        drawable.setColorFilter(Color.RED, Mode.SRC_ATOP);
        drawable = layerDrawable.getDrawable(1);
        drawable.setColorFilter(Color.RED, Mode.SRC_ATOP);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ratingBar.setLayoutParams(layoutParams);
        this.addView(ratingBar);

        // set testo label

        String testo = reviewModel.getText();
        textView = new TextView(activity);
        layoutParams = new LayoutParams(RecensioneView.LARGHEZZA_TESTO, RecensioneView.ALTEZZA_TESTO);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        textView.setText(testo);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        this.addView(textView);

        linearLayout = new LinearLayout (activity);
        layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.SPAZIATURA_FINALE);
        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);
    }

    public String getNickname() {
        return this.nickname;
    }

    public Bitmap getImmagineProfilo() {
        CircularImageView circularImageView = this.getCircularImageView();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) circularImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    public CircularImageView getCircularImageView() {
        LinearLayout linearLayout = (LinearLayout) this.getChildAt(1);
        CircularImageView circularImageView = (CircularImageView) linearLayout.getChildAt(1);
        return circularImageView;
    }

    public TextView getNicknameTextView() {
        LinearLayout linearLayout = (LinearLayout) this.getChildAt(1);
        TextView textView = (TextView) linearLayout.getChildAt(0);
        return textView;
    }

}
