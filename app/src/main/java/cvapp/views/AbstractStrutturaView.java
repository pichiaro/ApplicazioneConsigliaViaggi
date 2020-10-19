package cvapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.model.Marker;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.Vector;

import adapters.SimpleViewPagerAdapter;
import cvapp.R;
import positionablecomponents.Positionable;
import models.AbstractStructureModel;
import onpagechangelisteners.CyclicViewPagerMaker;

public abstract class AbstractStrutturaView extends LinearLayout implements Positionable {

    private String strutturaId;
    private Marker marker;
    private String nome;
    private float latitudine;
    private float longitudine;

    public final static int COLORE_PRINCIPALE = Color.rgb(95, 85, 155);
    public final static double RATING_BAR_SCALE = 0.65;
    public final static int SPAZIATURA_FINALE = 250;
    public final static int ALTEZZA_LABELS = 100;
    public final static int TEXT_SIZE_LABEL = 18;
    public final static int[] LIMITI_LABELS_DRAWABLE = new int[4];
    private final static int[] LIMITI_BUTTONS_DRAWABLE = new int[4];
    private final static double LARGHEZZA_AZIONE_LABELS = 0.70;
    private final static double LARGHEZZA_BUTTON = 0.30;
    private final static int ALTEZZA_VISUALIZZA_MAPPA = 100;
    public final static int ALTEZZA_ANCHOR = 850;
    public final static int ALTEZZA_IMG = (AbstractStrutturaView.ALTEZZA_ANCHOR / 2) + (AbstractStrutturaView.ALTEZZA_VISUALIZZA_MAPPA / 2);

    static {

        LIMITI_LABELS_DRAWABLE[0] = 0;
        LIMITI_LABELS_DRAWABLE[1] = -10;
        LIMITI_LABELS_DRAWABLE[2] = 80;
        LIMITI_LABELS_DRAWABLE[3] = 50;
        LIMITI_BUTTONS_DRAWABLE[0] = -100;
        LIMITI_BUTTONS_DRAWABLE[1] = 0;
        LIMITI_BUTTONS_DRAWABLE[2] = 40;
        LIMITI_BUTTONS_DRAWABLE[3] = 80;

    }

    public AbstractStrutturaView(Activity activity, AbstractStructureModel structureModel) {
        super(activity);
        this.setOrientation(LinearLayout.VERTICAL);

        Resources resources = activity.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        LayoutParams layoutParams;

        // creazione e settaggio view pager con image views

        AbstractList<Bitmap> bitmaps = structureModel.getBitmaps();
        int numeroImmagini = bitmaps.size();

        ViewPager viewPager = new ViewPager(activity);
        if (numeroImmagini > 0) {

            new CyclicViewPagerMaker(viewPager);
            viewPager.setOffscreenPageLimit(numeroImmagini - 1);
            layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_IMG);
            viewPager.setLayoutParams(layoutParams);

            Vector<ImageView> vector = new Vector<>();

            Iterator<Bitmap> iterator = bitmaps.iterator();
            while (iterator.hasNext()) {
                ImageView imageView = new ImageView(activity);
                imageView.setLayoutParams(layoutParams);
                Bitmap bitmap = iterator.next();
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    vector.add(imageView);
                }
            }


            SimpleViewPagerAdapter simpleViewPagerAdapter = new SimpleViewPagerAdapter((AbstractList) vector);
            viewPager.setAdapter(simpleViewPagerAdapter);
        }
        this.addView(viewPager);

        String strutturaId = structureModel.getID();
        this.setPositionID(strutturaId);

        float longitudine = structureModel.getLongitude();
        if (longitudine >= 0 && longitudine <= 180) {
            this.setLongitude(longitudine);
        }

        float latitude = structureModel.getLatitude();
        if (latitude >= 0 && latitude <= 90) {
            this.setLatitude(latitude);
        }

        // creazione e settaggio componente visualizza mappa (label + button)

        TextView textView = new TextView(activity);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("Visualizza sulla mappa");
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.LTGRAY);
        textView.setWidth((int) (displayMetrics.widthPixels * AbstractStrutturaView.LARGHEZZA_AZIONE_LABELS));
        Drawable drawable = resources.getDrawable(R.drawable.right_arrow, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1] + 10, AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(null, null, drawable, null);

        Button button = new Button(activity);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setWidth((int) (displayMetrics.widthPixels * AbstractStrutturaView.LARGHEZZA_BUTTON));
        drawable = resources.getDrawable(R.drawable.onmap, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[0], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[1], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[2], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[3]);
        button.setCompoundDrawables(null, null, drawable, null);

        LinearLayout linearLayout = new LinearLayout(activity);
        layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_VISUALIZZA_MAPPA);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.addView(textView);
        linearLayout.addView(button);
        this.addView(linearLayout);

        // creazione e settaggio componente scrivi recensione (label + button)

        textView = new TextView(activity);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("Scrivi una recensione");
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.LTGRAY);
        drawable = resources.getDrawable(R.drawable.right_arrow, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1] + 10, AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(null, null, drawable, null);
        textView.setWidth((int) (displayMetrics.widthPixels * AbstractStrutturaView.LARGHEZZA_AZIONE_LABELS));

        button = new Button(activity);
        button.setBackgroundColor(Color.TRANSPARENT);

        button.setWidth((int) (displayMetrics.widthPixels * AbstractStrutturaView.LARGHEZZA_BUTTON));
        drawable = resources.getDrawable(R.drawable.review, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[0], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[1], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[2], AbstractStrutturaView.LIMITI_BUTTONS_DRAWABLE[3]);
        button.setCompoundDrawables(null, null, drawable, null);
        linearLayout = new LinearLayout(activity);

        linearLayout.addView(textView);
        linearLayout.addView(button);
        this.addView(linearLayout);

        linearLayout = new LinearLayout (activity);
        layoutParams = new LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.SPAZIATURA_FINALE);
        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);
    }

    public void addDistanceFrom(String posizione, int metri){
        Context context = this.getContext();
        String distanza = "   " + metri + "m";
        if (posizione.length() > 0) {
            distanza = distanza + " (da " + posizione + ")";
        } else {
            distanza = distanza + " (dalla tua posizione attuale)";
        }
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        TextView textView = new TextView(context);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(displayMetrics.widthPixels, AbstractStrutturaView.ALTEZZA_LABELS * 2);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(AbstractStrutturaView.TEXT_SIZE_LABEL);
        textView.setTypeface(null, Typeface.BOLD);
        Drawable drawable = resources.getDrawable(R.drawable.near, null);
        drawable.setBounds(AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[0], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[1], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[2], AbstractStrutturaView.LIMITI_LABELS_DRAWABLE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setText(distanza);
        this.addView(textView, 5);
    }

    @Override
    public void setPositionID(String strutturaId) {
        this.strutturaId = strutturaId;
    }

    @Override
    public String getPositionID() {
        return this.strutturaId;
    }

    @Override
    public View getVisualizeOnMapClickableView() {
        LinearLayout linearLayout = (LinearLayout) this.getChildAt(2);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }

    @Override
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public Marker getMarker() {
        return this.marker;
    }

    @Override
    public void setName(String nome) {
        this.nome = nome;
    }

    @Override
    public String getName() {
        return this.nome;
    }

    @Override
    public void setLatitude(float latitude) {
        this.latitudine = latitude;
    }

    @Override
    public float getLatitude() {
        return this.latitudine;
    }

    @Override
    public void setLongitude(float longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public float getLongitude() {
        return this.longitudine;
    }

    @Override
    public View getInsertClickableView() {
        int childCount = this.getChildCount();
        LinearLayout linearLayout = (LinearLayout) this.getChildAt(childCount - 2);
        Button button = (Button) linearLayout.getChildAt(1);
        return button;
    }


}
