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
import cvapp.models.AbstractFNDModel;

public class FNDView extends AbstractStrutturaView {

    public FNDView(Activity activity, AbstractFNDModel FNDModel) {
        super(activity, FNDModel);

        Resources resources = activity.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();


        // set nome label

        String nome = FNDModel.getName();
        this.setName(nome);
        TextView textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + nome);
        LayoutParams layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_LABELS);
        textView.setLayoutParams(layoutParams);
        Drawable drawable = resources.getDrawable(R.drawable.fnd, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, 0);

        // set indirizzo label

        String indirizzo = FNDModel.getAddress();
        String citta = FNDModel.getCity();
        String paese = FNDModel.getCountry();
        String fullAddress = "   " + indirizzo + ", " + citta + ", " + paese;
        textView = new TextView(activity);
        textView.setText(fullAddress);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(null, Typeface.BOLD);
        int index = 3;
        drawable = resources.getDrawable(R.drawable.location, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set tipologia label

        String tipologia = FNDModel.getTipologia();
        textView = new TextView(activity);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("   " + tipologia);
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.type, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set costo label

        String costo = String.valueOf(FNDModel.getCosto());
        costo = "   " + costo + " (medio)";
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

        // set giorno chiusura label

        String giornoChiusura = FNDModel.getGiornoChiusura();
        giornoChiusura = "   " + giornoChiusura;
        textView = new TextView(activity);
        textView.setText(giornoChiusura);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(layoutParams);
        drawable = resources.getDrawable(R.drawable.close, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set orari label

        String orarioApertura = FNDModel.getOraApertura();
        String orarioChiusura = FNDModel.getOraChiusura();
        textView = new TextView(activity);
        textView.setText("   " + orarioApertura + " - " + orarioChiusura);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(null, Typeface.BOLD);
        drawable = resources.getDrawable(R.drawable.clockwork, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        this.addView(textView, index);
        index++;

        // set servizi label

        AbstractList<String> servizi = FNDModel.getServizi();
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

        // set voto label

        String votoRecensioni = String.valueOf(FNDModel.getRating());
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