package cvapp.views;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.Iterator;

import cvapp.R;
import cvapp.models.AbstractAlbergoModel;

public class AlbergoView extends AbstractStrutturaView {

    public AlbergoView(Activity activity, AbstractAlbergoModel albergoModel) {
        super(activity, albergoModel);

        Resources resources = activity.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        // set nome label

        String nome = albergoModel.getName();
        this.setName(nome);
        TextView textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + nome);
        LayoutParams layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_LABELS);
        textView.setLayoutParams(layoutParams);
        Drawable drawable = resources.getDrawable(R.drawable.hotel, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, 0);

        // set indirizzo label

        String indirizzo = albergoModel.getAddress();
        String citta = albergoModel.getCity();
        String paese = albergoModel.getCountry();
        String fullAddress = "   " + indirizzo + ", " + citta + ", " + paese;
        textView = new TextView(activity);
        textView.setText(fullAddress);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(null, Typeface.BOLD);
        drawable = resources.getDrawable(R.drawable.location, null);
        int index = 3;
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set tipologia label

        String tipologia = albergoModel.getTipologia();
        textView = new TextView(activity);
        textView.setText("   " + tipologia);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(null, Typeface.BOLD);
        drawable = resources.getDrawable(R.drawable.type, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set stelle label

        String stelle = String.valueOf(albergoModel.getStelle());
        if (stelle.compareTo("0") != 0) {
            textView = new TextView(activity);
            textView.setText("   " + stelle);
            textView.setTextColor(Color.DKGRAY);
            textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
            textView.setTypeface(null, Typeface.BOLD);
            drawable = resources.getDrawable(R.drawable.star, null);
            drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
            textView.setCompoundDrawables(drawable, null, null, null);
            this.addView(textView, index);
            index++;
        }

        // set costo label

        String costo = String.valueOf(albergoModel.getCosto());
        costo = "   " + costo + " (minimo)";
        textView = new TextView(activity);
        textView.setText(costo);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.euro, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set servizi label

        AbstractList<String> servizi = albergoModel.getServiziAndServiziRistorazione();
        String concaten = "";
        Iterator<String> iterator = servizi.iterator();
        while (iterator.hasNext()) {
            concaten = concaten + "\n  -  " + iterator.next();
        }
        textView = new TextView(activity);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTextColor(Color.DKGRAY);
        textView.setText(concaten + "\n");
        this.addView(textView, index);
        index++;

        // set voto recensioni label

        String votoRecensioni = String.valueOf(albergoModel.getRating());
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
        this.addView(ratingBar, index);
    }

}
