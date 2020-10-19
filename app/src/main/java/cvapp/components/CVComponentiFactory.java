package cvapp.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.AbstractList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.mail.Authenticator;

import cvapp.R;
import cvapp.adapters.SuggerimentiPaesiAdapter;
import cvapp.adapters.SuggerimentiPosizioneAdapter;
import cvapp.views.RegistrazioneDialog;
import graphiccomponents.SwipeDisabledViewPager;
import onmarkerclicklisteners.OnClickExecuterForTag;
import positionablecomponents.PositionableBuildable;
import utilities.AdapterUtility;
import components.AbstractComponentsFactory;
import positionablecomponents.Positionable;
import positionablecomponents.OnMarkerClickExecuterForPositionableSubView;
import authenticators.UserAuthenticator;
import cvapp.models.AbstractAlbergoModel;
import cvapp.models.AbstractAttrazioneModel;
import cvapp.models.AbstractFNDModel;
import cvapp.models.AbstractRistoranteModel;
import cvapp.views.AlbergoView;
import cvapp.views.AttrazioneView;
import cvapp.views.FNDView;
import cvapp.views.RecensioneView;
import cvapp.views.RistoranteView;
import cvapp.views.AbstractStrutturaView;
import cvapp.views.UtenteView;
import database.AbstractUserCache;
import database.LastUserCache;
import onmapreadycallbacks.CleanMapStyleSetter;
import graphiccomponents.AlternativeCheckBox;
import graphiccomponents.AlternativeRadioButton;
import graphiccomponents.DoubleThumbMultiSlider;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenCalendarDialogBuilder;
import graphiccomponents.FullscreenChoicesDialog;
import graphiccomponents.FullscreenChoicesDialogBuilder;
import graphiccomponents.FullscreenInsertDialog;
import graphiccomponents.FullscreenInsertDialogBuilder;
import graphiccomponents.FullscreenLoginDialog;
import graphiccomponents.FullscreenReviewDialog;
import graphiccomponents.FullscreenSigninDialog;
import graphiccomponents.InputReadableDialog;
import models.AbstractReviewModel;
import models.AbstractUserPublicModel;
import models.BitmapDescriptorDesigner;
import models.JSONArraySimpleHelper;
import models.ObjectsModel;
import onclicklisteners.DialogDismissExecuter;
import onclicklisteners.DialogShowExecuter;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.forcompoundbutton.UniqueMutualTogglerForCompoundButton;
import onclicklisteners.forcompoundbutton.ViewVisibilitySetterForCompoundButton;
import onclicklisteners.forcompoundbutton.ViewsEnablerForCompoundButton;
import onclicklisteners.forcompoundbutton.ViewsVisibilitySetterForCompoundButton;
import onitemselectedlisteners.ViewEnabler;
import onmarkerclicklisteners.GoogleMapCameraAnimator;
import onmarkerclicklisteners.MarkerIconSetter;
import onmarkerclicklisteners.MultipleOnMarkerClickListener;

import utilities.DatesUtility;

public class CVComponentiFactory extends AbstractComponentsFactory implements Observer, PositionableBuildable {

    public final static int COLORE_SFONDO_ICONE = Color.rgb(85, 165, 185);
    public final static int COLORE_SFONDO_SFONDO_ICONE = Color.rgb(185, 115, 165);
    public final static int COLORE_PRINCIPALE = Color.rgb(95, 85, 155);
    private final static int DIMENSIONE_ICON = 100;
    private final static int DIFFERENZA_ICON = 30;
    public final static int COSTO_ALBERGHI = 0;
    public final static int COSTO_RISTORANTI = 1;
    public final static int COSTO_ATTRAZIONI = 2;
    public final static int COSTO_FND = 3;
    public  final static int VOTO_RECENSIONI_MEDIO = 4;
    public  final static int NUMERO_MASSIMO = 5;
    public  final static int DISTANZA = 6;
    public final static int TIPOLOGIE_ATTRAZIONI = 0;
    public final static int SERVIZI_ATTRAZIONI = 1;
    public final static int GUIDE_ATTRAZIONI = 2;
    public final static int TIPOLOGIE_FND = 3;
    public final static int SERVIZI_FND = 4;
    public final static int TIPOLOGIE_CUCINA_RISTORANTI = 5;
    public final static int SERVIZI_RISTORANTI = 6;
    public final static int TIPOLOGIE_ALBERGHI = 7;
    public final static int SERVIZI_ALBERGHI = 8;
    public final static int SERVIZI_RISTORAZIONE_ALBERGHI = 9;
    public final static int STELLE_ALBERGHI = 10;
    public final static int VOTI_RECENSIONI = 11;
    public static final int CHECKIN_ALBERGHI = 0;
    public static final int CHECKOUT_ALBERGHI = 1;
    public static final int DATAORA_RISTORANTI = 2;
    public static final int DATAORA_ATTRAZIONI = 3;
    public static final int DATAORA_FND = 4;
    public static final int DADATA_RECENSIONI = 5;
    public static final int ADATA_RECENSIONI = 6;
    public final static int RICERCA_SPECIFICA = 0;
    public final static int POSIZIONE_ALBERGHI = 1;
    public final static int POSIZIONE_RISTORANTI = 2;
    public final static int POSIZIONE_ATTRAZIONI = 3;
    public final static int POSIZIONE_FND = 4;
    public final static int CODICE_VERIFICA = 5;
    public final static int RECUPERO_PASSWORD = 6;
    public final static int ORDINA_ALBERGHI = 0;
    public final static int ORDINA_RISTORANTI = 1;
    public final static int ORDINA_ATTRAZIONI = 2;
    public final static int ORDINA_FND = 3;
    public final static int ORDINA_RECENSIONI = 4;
    public static int NUM_MAX_NUMBER_PICKER = 25;
    public final static int MIA_POSIZIONE = 0;
    public final static int DA_POSIZIONE = 1;
    public final static int CLICK_ALBERGO = 0;
    public final static int CLICK_RISTORANTE = 1;
    public final static int CLICK_ATTRAZIONE = 2;
    public final static int CLICK_FND = 3;
    public final static int NOCLICK_ALBERGO = 4;
    public final static int NOCLICK_RISTORANTE = 5;
    public final static int NOCLICK_ATTRAZIONE = 6;
    public final static int NOCLICK_FND = 7;
    public final static int MAX_COSTO_ALB_RIS = 1000;
    public final static int COSTO_INCR_ALB_RIS = 50;
    public final static int MAX_COSTO_FND_ATT = 250;
    public final static int COSTO_INCR_FND_ATT = 10;
    public final static int MAX_DISTANZA = 6000;
    public final static int DIST_INCR = 50;
    public final static String PAESI_FILE = "world_cities.json";
    public final static String[] ATTRIBUTI_FILE_PAESI = { "name", "subcountry", "country" };

    private final static int SIZE_CARATTERI = 18;
    private final static int[] BOUNDS_ICONE = new int[4];

    {
        BOUNDS_ICONE[0] = 0;
        BOUNDS_ICONE[1] = 0;
        BOUNDS_ICONE[2] = 100;
        BOUNDS_ICONE[3] = 80;
    }

    private FullscreenInsertDialogBuilder fullscreenInsertDialogBuilder;
    private FullscreenChoicesDialogBuilder fullscreenChoicesDialogBuilder;
    private FullscreenCalendarDialogBuilder fullscreenCalendarDialogBuilder;
    private Builder builder;
    private BitmapDescriptorDesigner bitmapDescriptorDesigner;
    private JSONArraySimpleHelper jsonArraySimpleHelper;
    private MultipleOnClickListener strutturaListener;
    private MultipleOnClickListener visualizzaProfiloUtenteStarter;
    private MultipleOnClickListener scritturaRecensioneStarter;

    public void setStrutturaListener(MultipleOnClickListener strutturaListener) {
        this.strutturaListener = strutturaListener;
    }

    public void setVisualizzaProfiloUtenteStarter(MultipleOnClickListener visualizzaProfiloUtenteStarter) {
        this.visualizzaProfiloUtenteStarter = visualizzaProfiloUtenteStarter;
    }

    public void setScritturaRecensioneStarter(MultipleOnClickListener scritturaRecensioneStarter) {
        this.scritturaRecensioneStarter = scritturaRecensioneStarter;
    }

    public CVComponentiFactory(Activity activity) {
        this.setActivity(activity);

        this.jsonArraySimpleHelper = new JSONArraySimpleHelper(activity);
        this.builder = new Builder(activity);
        this.builder.setTitle("Attenzione!");
        this.builder.setPositiveButton("chiudi", null);

        this.bitmapDescriptorDesigner = new BitmapDescriptorDesigner(activity, new Vector<>());
        Resources resources = activity.getResources();
        int actionBarHeight = 0;
        Theme theme = activity.getTheme();
        if (theme != null) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }

        this.fullscreenInsertDialogBuilder = new FullscreenInsertDialogBuilder(activity);
        this.fullscreenInsertDialogBuilder.setToolbarBackgroundColor(COLORE_PRINCIPALE);
        this.fullscreenInsertDialogBuilder.setToolbarHeight(actionBarHeight);
        this.fullscreenInsertDialogBuilder.setToolbarTitleTextColor(Color.WHITE);
        this.fullscreenInsertDialogBuilder.setEditTextBackgroundResources(R.drawable.border);
        this.fullscreenInsertDialogBuilder.setEditTextText("");
        this.fullscreenInsertDialogBuilder.setEditTextTextColor(Color.BLACK);
        this.fullscreenInsertDialogBuilder.setEditTextTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        this.fullscreenInsertDialogBuilder.setEditTextTextSize(SIZE_CARATTERI);
        this.fullscreenInsertDialogBuilder.setResetButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenInsertDialogBuilder.setResetButtonTextSize(SIZE_CARATTERI);
        this.fullscreenInsertDialogBuilder.setResetButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenInsertDialogBuilder.setResetButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.fullscreenInsertDialogBuilder.setResetButtonIsAllCaps(false);
        this.fullscreenInsertDialogBuilder.setRightButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenInsertDialogBuilder.setRightButtonTextSize(SIZE_CARATTERI);
        this.fullscreenInsertDialogBuilder.setRightButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenInsertDialogBuilder.setRightButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.fullscreenInsertDialogBuilder.setRightButtonAllCaps(false);

        this.fullscreenChoicesDialogBuilder = new FullscreenChoicesDialogBuilder(activity);
        this.fullscreenChoicesDialogBuilder.setToolbarBackgroundColor(COLORE_PRINCIPALE);
        this.fullscreenChoicesDialogBuilder.setToolbarHeight(actionBarHeight);
        this.fullscreenChoicesDialogBuilder.setToolbarTitleTextColor(Color.WHITE);
        this.fullscreenChoicesDialogBuilder.setCheckBoxesTextColor(Color.BLACK);
        this.fullscreenChoicesDialogBuilder.setCheckBoxesTextSize(SIZE_CARATTERI);
        this.fullscreenChoicesDialogBuilder.setResetButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenChoicesDialogBuilder.setResetButtonTextSize(SIZE_CARATTERI);
        this.fullscreenChoicesDialogBuilder.setResetButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenChoicesDialogBuilder.setResetButtonIsAllCaps(false);
        this.fullscreenChoicesDialogBuilder.setResetButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.fullscreenChoicesDialogBuilder.setRightButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenChoicesDialogBuilder.setRightButtonText("Inserisci");
        this.fullscreenChoicesDialogBuilder.setRightButtonTextSize(SIZE_CARATTERI);
        this.fullscreenChoicesDialogBuilder.setRightButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenChoicesDialogBuilder.setRightButtonAllCaps(false);
        this.fullscreenChoicesDialogBuilder.setRightButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        this.fullscreenCalendarDialogBuilder = new FullscreenCalendarDialogBuilder(activity);
        this.fullscreenCalendarDialogBuilder.setToolbarBackgroundColor(COLORE_PRINCIPALE);
        this.fullscreenCalendarDialogBuilder.setTimePickerAdded(true);
        this.fullscreenCalendarDialogBuilder.setToolbarTitleTextColor(Color.WHITE);
        this.fullscreenCalendarDialogBuilder.setTextViewBackgroundResources(R.drawable.border);
        Drawable drawable = resources.getDrawable(R.drawable.date, null);
        drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
        this.fullscreenCalendarDialogBuilder.setTextViewLeftDrawable(drawable);
        this.fullscreenCalendarDialogBuilder.setTextViewTextColor(Color.BLACK);
        this.fullscreenCalendarDialogBuilder.setTextViewTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        this.fullscreenCalendarDialogBuilder.setTextViewTextSize(SIZE_CARATTERI);
        this.fullscreenCalendarDialogBuilder.setTimePicker24hFormat(true);
        this.fullscreenCalendarDialogBuilder.setResetButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenCalendarDialogBuilder.setResetButtonTextSize(SIZE_CARATTERI);
        this.fullscreenCalendarDialogBuilder.setResetButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenCalendarDialogBuilder.setResetButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.fullscreenCalendarDialogBuilder.setResetButtonIsAllCaps(false);
        this.fullscreenCalendarDialogBuilder.setDismissButtonBackgroundColor(Color.TRANSPARENT);
        this.fullscreenCalendarDialogBuilder.setRightButtonText("Inserisci");
        this.fullscreenCalendarDialogBuilder.setRightButtonTextSize(18);
        this.fullscreenCalendarDialogBuilder.setRightButtonTextColor(COLORE_PRINCIPALE);
        this.fullscreenCalendarDialogBuilder.setRightButtonTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.fullscreenCalendarDialogBuilder.setRightButtonAllCaps(false);
        this.fullscreenCalendarDialogBuilder.setToolbarHeight(actionBarHeight);
        this.fullscreenCalendarDialogBuilder.setDateSeparator("/");
    }

    @Override
    public FullscreenSigninDialog buildFullscreenSigninDialog() {
        RegistrazioneDialog registrazioneDialog = new RegistrazioneDialog(this.getActivity());
        List<String> paesi = this.jsonArraySimpleHelper.toStrings("world_cities.json","", "country");
        SuggerimentiPaesiAdapter suggerimentiPaesiAdapter = new SuggerimentiPaesiAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line, (AbstractList) paesi, 20, "");
        AutoCompleteTextView autoCompleteTextView = registrazioneDialog.getNationalityEditText();
        autoCompleteTextView.setAdapter(suggerimentiPaesiAdapter);
        return registrazioneDialog;
    }

    @Override
    public FullscreenLoginDialog buildFullscreenLoginDialog() {
        FullscreenLoginDialog fullscreenLoginDialog = new FullscreenLoginDialog(this.getActivity());
        Resources resources = this.getActivity().getResources();
        int actionBarHeight = 0;
        Theme theme = this.getActivity().getTheme();
        if (theme != null) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }
        Button button = fullscreenLoginDialog.getRightButton();
        this.applicaNeutralButtonStyle(button, "Autentica");
        button = fullscreenLoginDialog.getResetButton();
        this.applicaNeutralButtonStyle(button, "Reset");
        Toolbar toolbar = fullscreenLoginDialog.getToolbar();
        toolbar.setMinimumHeight(actionBarHeight);
        toolbar.setTitle("Autenticazione utente");
        toolbar.setNavigationIcon(R.drawable.white_user);
        toolbar.setBackgroundColor(COLORE_PRINCIPALE);
        toolbar.setTitleTextColor(Color.WHITE);
        TextView textView = fullscreenLoginDialog.getUserNameTextView();
        textView.setText("\nInserisci UserID:");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        EditText editText = fullscreenLoginDialog.getUserNameEditText();
        Drawable drawable = resources.getDrawable(R.drawable.user, null);
        drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
        editText.setCompoundDrawables(drawable, null, null, null);
        editText.setHint(" Il mio user ID...");
        editText.setTextSize(SIZE_CARATTERI);
        editText.setTextColor(Color.BLACK);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        editText.setBackgroundResource(R.drawable.border);
        textView = fullscreenLoginDialog.getPasswordTextView();
        textView.setText("\nInserisci Password:");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        editText = fullscreenLoginDialog.getPasswordEditText();
        drawable = resources.getDrawable(R.drawable.security, null);
        drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
        editText.setCompoundDrawables(drawable, null, null, null);
        editText.setHint(" La mia password...");
        editText.setTextColor(Color.BLACK);
        editText.setTextSize(SIZE_CARATTERI);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        editText.setBackgroundResource(R.drawable.border);
        CheckBox checkBox = fullscreenLoginDialog.getShowPasswordCheckBox();
        checkBox.setText("Mostra");
        checkBox.setTextColor(Color.BLACK);
        checkBox.setTextSize(SIZE_CARATTERI);
        AlternativeRadioButton alternativeRadioButton = fullscreenLoginDialog.getRememberCredentialsRadioButton();
        alternativeRadioButton.setText("Ricorda credenziali");
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setTextSize(SIZE_CARATTERI);
        alternativeRadioButton = fullscreenLoginDialog.getRememberPasswordRadioButton();
        alternativeRadioButton.setText("Ricorda solo password");
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setTextSize(SIZE_CARATTERI);
        alternativeRadioButton = fullscreenLoginDialog.getMakeLoginAutomaticRadioButton();
        alternativeRadioButton.setText("Login automatico");
        alternativeRadioButton.setTextColor(Color.BLACK);
        alternativeRadioButton.setTextSize(SIZE_CARATTERI);
        return fullscreenLoginDialog;
    }

    private final static double[] BOUNDS_RATINGBAR = new double[3];

    {
        BOUNDS_RATINGBAR[0] = 0.50;
        BOUNDS_RATINGBAR[1] = 0.80;
        BOUNDS_RATINGBAR[2] = 0.80;
    }

    @Override
    public FullscreenReviewDialog buildFullscreenReviewDialog() {
        FullscreenReviewDialog fullscreenReviewDialog = new FullscreenReviewDialog(this.getActivity());
        Resources resources = this.getActivity().getResources();
        int actionBarHeight = 0;
        Theme theme = this.getActivity().getTheme();
        if (theme != null) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
            }
        }
        Toolbar toolbar = fullscreenReviewDialog.getToolbar();
        toolbar.setBackgroundColor(COLORE_PRINCIPALE);
        toolbar.setMinimumHeight(actionBarHeight);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Recensione");
        toolbar.setNavigationIcon(R.drawable.white_review);
        EditText editText = fullscreenReviewDialog.getReviewTitleEditText();
        editText.setHint("Titolo...");
        Drawable drawable = resources.getDrawable(R.drawable.title, null);
        drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
        editText.setCompoundDrawables(drawable, null, null, null);
        editText.setTextSize(SIZE_CARATTERI);
        editText.setTextColor(Color.BLACK);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        editText.setBackgroundResource(R.drawable.border);
        TextView textView = fullscreenReviewDialog.getReviewDateTextView();
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        drawable = resources.getDrawable(R.drawable.date, null);
        drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setBackgroundResource(R.drawable.border);
        RatingBar ratingBar = fullscreenReviewDialog.getRatingBar();
        ratingBar.setStepSize((float) BOUNDS_RATINGBAR[0]);
        ratingBar.setScaleX((float) BOUNDS_RATINGBAR[1]);
        ratingBar.setScaleY((float) BOUNDS_RATINGBAR[2]);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        drawable = layerDrawable.getDrawable(2);
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        drawable = layerDrawable.getDrawable(1);
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        editText = fullscreenReviewDialog.getReviewTextEditText();
        editText.setHint(" Il testo della mia recensione...");
        editText.setTextSize(SIZE_CARATTERI);
        editText.setTextColor(Color.BLACK);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        editText.setBackgroundResource(R.drawable.border);
        Button button = fullscreenReviewDialog.getLeftButton();
        this.applicaFakeButtonStyle(button);
        button = fullscreenReviewDialog.getResetButton();
        this.applicaButtonStyle(button, "Resetta");
        button = fullscreenReviewDialog.getRightButton();
        this.applicaButtonStyle(button, "Inserisci");
        ScrollView scrollView = fullscreenReviewDialog.getScrollView();
        LayoutParams layoutParams = scrollView.getLayoutParams();
        layoutParams.height = layoutParams.height - actionBarHeight;
        return fullscreenReviewDialog;
    }

    public void applicaFakeButtonStyle(Button button) {
        button.setText("Fake");
        button.setTextSize(SIZE_CARATTERI);
        button.setTextColor(COLORE_PRINCIPALE);
        button.setBackgroundColor(COLORE_PRINCIPALE);
        button.setClickable(false);
    }

    public void applicaButtonStyle(Button button, String text) {
        button.setText(text);
        button.setTextSize(SIZE_CARATTERI);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(COLORE_PRINCIPALE);
        button.setAllCaps(false);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    public void applicaNeutralButtonStyle(Button button, String text) {
        button.setText(text);
        button.setTextSize(SIZE_CARATTERI);
        button.setTextColor(COLORE_PRINCIPALE);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setAllCaps(false);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    public View buildView(ObjectsModel objectsModel) {
        if (objectsModel instanceof AbstractUserPublicModel) {
            return new UtenteView(this.getActivity(), (AbstractUserPublicModel) objectsModel);
        }
        else {
            if (objectsModel instanceof AbstractReviewModel) {
                RecensioneView recensioneView = new RecensioneView(this.getActivity(), (AbstractReviewModel) objectsModel);
                CircularImageView circularImageView = recensioneView.getCircularImageView();
                circularImageView.setOnClickListener(this.visualizzaProfiloUtenteStarter);
                TextView textView = recensioneView.getNicknameTextView();
                textView.setOnClickListener(this.visualizzaProfiloUtenteStarter);
                return recensioneView;
            }
        }
        return null;
    }

    @Override
    public Dialog buildDialog() {
        return new Dialog(this.getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }

    @Override
    public AlertDialog buildAlertDialog() {
        return this.builder.create();
    }

    @Override
    public AlertDialog buildWaitingDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle("Attendere...");
        progressDialog.setMessage("Sto eseguendo l' operazione, dammi qualche attimo...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    @Override
    public DoubleThumbMultiSlider buildDoubleThumbMultiSlider(int option) {
        int min = 0;
        int max = 0;
        boolean hasLeftThumb = false;
        int increment = 1;
        String unitOfMeasure = "";
        switch (option) {
            case CVComponentiFactory.COSTO_ALBERGHI:
                max = MAX_COSTO_ALB_RIS;
                unitOfMeasure = "€";
                hasLeftThumb = true;
                increment = COSTO_INCR_ALB_RIS;
                break;
            case CVComponentiFactory.COSTO_RISTORANTI:
                max = MAX_COSTO_ALB_RIS;
                unitOfMeasure = "€";
                hasLeftThumb = true;
                increment = COSTO_INCR_ALB_RIS;
                break;
            case CVComponentiFactory.COSTO_ATTRAZIONI:
                max = MAX_COSTO_FND_ATT;
                unitOfMeasure = "€";
                hasLeftThumb = true;
                increment = COSTO_INCR_FND_ATT;
                break;
            case CVComponentiFactory.COSTO_FND:
                max = MAX_COSTO_FND_ATT;
                unitOfMeasure = "€";
                hasLeftThumb = true;
                increment = COSTO_INCR_FND_ATT;
                break;
            case CVComponentiFactory.VOTO_RECENSIONI_MEDIO:
                min = 1;
                max = 5;
                hasLeftThumb = true;
                break;
            case CVComponentiFactory.NUMERO_MASSIMO:
                min = 1;
                max = 100;
                break;
            case CVComponentiFactory.DISTANZA:
                min = 0;
                max = MAX_DISTANZA;
                unitOfMeasure = "m";
                increment = DIST_INCR;
                break;
        }
        DoubleThumbMultiSlider doubleThumbMultiSlider = new DoubleThumbMultiSlider(this.getActivity(), unitOfMeasure, increment);
        doubleThumbMultiSlider.setMin(min);
        doubleThumbMultiSlider.setMax(max);
        doubleThumbMultiSlider.setHasLeftThumb(hasLeftThumb);
        return doubleThumbMultiSlider;
    }

    @Override
    public FullscreenChoicesDialog buildFullscreenChoicesDialog(int opzione) {
        switch (opzione) {
            case CVComponentiFactory.TIPOLOGIE_ATTRAZIONI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_museum);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Tipologie");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.TIPO_ATTRAZIONI);
                break;
            case CVComponentiFactory.SERVIZI_ATTRAZIONI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_museum);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Servizi");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.SERVIZI_ATTRAZIONI);
                break;
            case CVComponentiFactory.GUIDE_ATTRAZIONI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_museum);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Tipologie guida");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.GUIDE_ATTRAZIONI);
                break;
            case CVComponentiFactory.TIPOLOGIE_FND:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_fnd);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Tipologie");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.TIPO_FND);
                break;
            case CVComponentiFactory.SERVIZI_FND:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_fnd);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Servizi");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.SERVIZI_FND);
                break;
            case CVComponentiFactory.TIPOLOGIE_CUCINA_RISTORANTI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_restaurant);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Tipologie cucina");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.CUCINE_RISTORANTI);
                break;
            case CVComponentiFactory.SERVIZI_RISTORANTI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_restaurant);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Servizi");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.SERVIZI_RISTORANTI);
                break;
            case CVComponentiFactory.TIPOLOGIE_ALBERGHI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Tipologie");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.TIPO_ALBERGO);
                break;
            case CVComponentiFactory.STELLE_ALBERGHI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Stelle");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.STELLE_ALBERGHI);
                break;
            case CVComponentiFactory.SERVIZI_RISTORAZIONE_ALBERGHI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Servizi  ristorazione");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.SERVIZI_RIST_ALBERGHI);
                break;
            case CVComponentiFactory.SERVIZI_ALBERGHI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Servizi");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.SERVIZI_ALBERGHI);
                break;
            case CVComponentiFactory.VOTI_RECENSIONI:
                this.fullscreenChoicesDialogBuilder.setToolbarNavigationIcon(R.drawable.white_review);
                this.fullscreenChoicesDialogBuilder.setToolbarTitle("Voti");
                this.fullscreenChoicesDialogBuilder.setChoices(InterfacciaQuery.VOTI_RECENSIONI);
                break;
        }
        DialogDismissExecuter dialogDismissExecuter = new DialogDismissExecuter(null);
        this.fullscreenChoicesDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
        FullscreenChoicesDialog fullscreenChoicesDialog = this.fullscreenChoicesDialogBuilder.create();
        dialogDismissExecuter.setDialog(fullscreenChoicesDialog);
        return fullscreenChoicesDialog;
    }

    @Override
    public FullscreenCalendarDialog buildFullscreenCalendarDialog(int opzione) {
        switch (opzione) {
            case CVComponentiFactory.CHECKIN_ALBERGHI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Check in");
                break;
            case CVComponentiFactory.CHECKOUT_ALBERGHI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Check out");
                break;
            case CVComponentiFactory.DATAORA_RISTORANTI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_restaurant);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Data & ora");
                break;
            case CVComponentiFactory.DATAORA_ATTRAZIONI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_museum);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Data & ora");
                break;
            case CVComponentiFactory.DATAORA_FND:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_fnd);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Data & ora");
                break;
            case CVComponentiFactory.DADATA_RECENSIONI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_review);
                this.fullscreenCalendarDialogBuilder.setTimePickerAdded(false);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("Da data");
                break;
            case CVComponentiFactory.ADATA_RECENSIONI:
                this.fullscreenCalendarDialogBuilder.setToolBarNavigationIcon(R.drawable.white_review);
                this.fullscreenCalendarDialogBuilder.setTimePickerAdded(false);
                this.fullscreenCalendarDialogBuilder.setToolbarTitle("A data");
                break;
        }
        DialogDismissExecuter dialogDismissExecuter = new DialogDismissExecuter(null);
        this.fullscreenCalendarDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
        FullscreenCalendarDialog fullscreenCalendarDialog = this.fullscreenCalendarDialogBuilder.create();
        dialogDismissExecuter.setDialog(fullscreenCalendarDialog);
        return fullscreenCalendarDialog;
    }


    @Override
    public FullscreenInsertDialog buildFullscreenInsertDialog(int opzione) {
        FullscreenInsertDialog fullscreenInsertDialog = null;
        SuggerimentiPosizioneAdapter suggerimentiPosizioneAdapter;
        DialogDismissExecuter dialogDismissExecuter;
        switch (opzione) {
            case CVComponentiFactory.RICERCA_SPECIFICA:
                Drawable drawable = this.getActivity().getResources().getDrawable(R.drawable.search, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Cerca struttura");
                this.fullscreenInsertDialogBuilder.setRightButtonText("Vai");
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci...");
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_search);
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                break;
            case CVComponentiFactory.POSIZIONE_ALBERGHI:
                dialogDismissExecuter = new DialogDismissExecuter(null);
                drawable = this.getActivity().getResources().getDrawable(R.drawable.search, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                this.fullscreenInsertDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
                List<String> locazioni = this.jsonArraySimpleHelper.toStrings(PAESI_FILE,", ", ATTRIBUTI_FILE_PAESI);
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_hotel);
                this.fullscreenInsertDialogBuilder.setRightButtonText("Inserisci");
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci...");
                suggerimentiPosizioneAdapter = new SuggerimentiPosizioneAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line, (AbstractList) locazioni, 20, ",");
                this.fullscreenInsertDialogBuilder.setArrayAdapter(suggerimentiPosizioneAdapter);
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Posizione");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                dialogDismissExecuter.setDialog(fullscreenInsertDialog);
                break;
            case CVComponentiFactory.POSIZIONE_RISTORANTI:
                drawable = this.getActivity().getResources().getDrawable(R.drawable.search, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                dialogDismissExecuter = new DialogDismissExecuter(null);
                this.fullscreenInsertDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
                locazioni = this.jsonArraySimpleHelper.toStrings(PAESI_FILE,", ", ATTRIBUTI_FILE_PAESI);
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_restaurant);
                this.fullscreenInsertDialogBuilder.setRightButtonText("Inserisci");
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci...");
                suggerimentiPosizioneAdapter = new SuggerimentiPosizioneAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line, (AbstractList) locazioni, 20, ",");
                this.fullscreenInsertDialogBuilder.setArrayAdapter(suggerimentiPosizioneAdapter);
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Posizione");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                dialogDismissExecuter.setDialog(fullscreenInsertDialog);
                break;
            case CVComponentiFactory.POSIZIONE_ATTRAZIONI:
                drawable = this.getActivity().getResources().getDrawable(R.drawable.search, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                dialogDismissExecuter = new DialogDismissExecuter(null);
                this.fullscreenInsertDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
                locazioni = this.jsonArraySimpleHelper.toStrings(PAESI_FILE,", ", ATTRIBUTI_FILE_PAESI);
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_museum);
                this.fullscreenInsertDialogBuilder.setRightButtonText("Inserisci");
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci...");
                suggerimentiPosizioneAdapter = new SuggerimentiPosizioneAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line, (AbstractList) locazioni, 20, ",");
                this.fullscreenInsertDialogBuilder.setArrayAdapter(suggerimentiPosizioneAdapter);
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Posizione");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                dialogDismissExecuter.setDialog(fullscreenInsertDialog);
                break;
            case CVComponentiFactory.POSIZIONE_FND:
                drawable = this.getActivity().getResources().getDrawable(R.drawable.search, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                dialogDismissExecuter = new DialogDismissExecuter(null);
                this.fullscreenInsertDialogBuilder.setRightButtonOnClickListener(dialogDismissExecuter);
                locazioni = this.jsonArraySimpleHelper.toStrings(PAESI_FILE,", ", ATTRIBUTI_FILE_PAESI);
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_fnd);
                this.fullscreenInsertDialogBuilder.setRightButtonText("Inserisci");
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci...");
                suggerimentiPosizioneAdapter = new SuggerimentiPosizioneAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line, (AbstractList) locazioni, 20, ",");
                this.fullscreenInsertDialogBuilder.setArrayAdapter(suggerimentiPosizioneAdapter);
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Posizione");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                dialogDismissExecuter.setDialog(fullscreenInsertDialog);
                break;
            case CVComponentiFactory.CODICE_VERIFICA:
                this.fullscreenInsertDialogBuilder.setRightButtonText("Vai");
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_credentials);
                drawable = this.getActivity().getResources().getDrawable(R.drawable.credentials, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                this.fullscreenInsertDialogBuilder.setEditTextHint("Inserisci codice...");
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Codice di verifica");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                break;
            case CVComponentiFactory.RECUPERO_PASSWORD:
                this.fullscreenInsertDialogBuilder.setRightButtonText("Vai");
                this.fullscreenInsertDialogBuilder.setToolBarNavigationIcon(R.drawable.white_user);
                drawable = this.getActivity().getResources().getDrawable(R.drawable.user, null);
                drawable.setBounds(BOUNDS_ICONE[0], BOUNDS_ICONE[1], BOUNDS_ICONE[2], BOUNDS_ICONE[3]);
                this.fullscreenInsertDialogBuilder.setEditTextLeftDrawable(drawable);
                this.fullscreenInsertDialogBuilder.setEditTextHint("Il mio user ID...");
                this.fullscreenInsertDialogBuilder.setToolbarTitle("Recupero password");
                fullscreenInsertDialog = this.fullscreenInsertDialogBuilder.create();
                break;
        }
        return fullscreenInsertDialog;
    }

    @Override
    public Spinner buildSpinner(int opzione, View view) {
        Spinner spinner = null;
        ViewEnabler viewEnabler;
        switch (opzione) {
            case CVComponentiFactory.ORDINA_ALBERGHI:
                spinner = new Spinner(this.getActivity());
                AdapterUtility.adapt(spinner, this.getActivity(), InterfacciaQuery.SORT_ALBERGHI);
                viewEnabler = new ViewEnabler(view, "Nessuno", false);
                spinner.setOnItemSelectedListener(viewEnabler);
                break;
            case CVComponentiFactory.ORDINA_RISTORANTI:
                spinner = new Spinner(this.getActivity());
                AdapterUtility.adapt(spinner, this.getActivity(), InterfacciaQuery.SORT_RISTORANTI);
                viewEnabler = new ViewEnabler(view, "Nessuno", false);
                spinner.setOnItemSelectedListener(viewEnabler);
                break;
            case CVComponentiFactory.ORDINA_ATTRAZIONI:
                spinner = new Spinner(this.getActivity());
                AdapterUtility.adapt(spinner, this.getActivity(),InterfacciaQuery.SORT_ATTRAZIONI);
                viewEnabler = new ViewEnabler(view, "Nessuno", false);
                spinner.setOnItemSelectedListener(viewEnabler);
                break;
            case CVComponentiFactory.ORDINA_FND:
                spinner = new Spinner(this.getActivity());
                AdapterUtility.adapt(spinner, this.getActivity(), InterfacciaQuery.SORT_FND);
                viewEnabler = new ViewEnabler(view, "Nessuno", false);
                spinner.setOnItemSelectedListener(viewEnabler);
                break;
            case CVComponentiFactory.ORDINA_RECENSIONI:
                spinner = new Spinner(this.getActivity());
                AdapterUtility.adapt(spinner, this.getActivity(), InterfacciaQuery.SORT_RECENSIONI);
                viewEnabler = new ViewEnabler(view, "Nessuno", false);
                spinner.setOnItemSelectedListener(viewEnabler);
                break;
        }
        return spinner;
    }

    @Override
    public NumberPicker buildNumberPicker() {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        NumberPicker numberPicker = new NumberPicker(contextThemeWrapper);
        DatesUtility.setNumberPicker(numberPicker, 0, CVComponentiFactory.NUM_MAX_NUMBER_PICKER);
        return numberPicker;
    }

    @Override
    public CompoundButton buildCompoundButton() {
        AlternativeCheckBox alternativeCheckBox = new AlternativeCheckBox(this.getActivity());
        alternativeCheckBox.setText("Decrescente");
        return alternativeCheckBox;
    }

    @Override
    public CompoundButton buildCompoundButton(int opzione) {
        AlternativeRadioButton alternativeRadioButton = null;
        if (opzione == CVComponentiFactory.MIA_POSIZIONE) {
            alternativeRadioButton = new AlternativeRadioButton(this.getActivity());
            alternativeRadioButton.setText("Dalla mia posizione");
        }
        else {
            if (opzione == CVComponentiFactory.DA_POSIZIONE) {
                alternativeRadioButton = new AlternativeRadioButton(this.getActivity());
                alternativeRadioButton.setText("Dalla posizione");
            }
        }
        return alternativeRadioButton;
    }

    public LinearLayout creaLayoutTipoUno(int widthInPixel, InputReadableDialog dialog, String buttonName, String labelName) {
        Button button = new Button(this.getActivity());
        DialogShowExecuter dialogShowExecuter = new DialogShowExecuter(dialog, true);
        button.setOnClickListener(dialogShowExecuter);
        button.setText(buttonName);
        button.setTextSize(SIZE_CARATTERI);
        button.setAllCaps(false);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setTextColor(Color.BLACK);
        if (dialog instanceof FullscreenInsertDialog) {
            dialog.setReadInputDefaultText("Nessuna");
        } else {
            dialog.setReadInputDefaultText("Qualsiasi");
        }
        TextView textView = new TextView(this.getActivity());
        textView.setText(labelName);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textView.setTextColor(Color.BLACK);
        button.setWidth((int) (widthInPixel * 0.30));
        button.setBackgroundColor(Color.LTGRAY);
        textView.setWidth((int) (widthInPixel * 0.70));
        Resources resources = this.getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.right_arrow, null);
        drawable.setBounds(0, 0, 60, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        LinearLayout linearLayout = new LinearLayout(this.getActivity());
        linearLayout.setBackgroundColor(Color.LTGRAY);
        linearLayout.addView(textView);
        linearLayout.addView(button);
        textView = dialog.getReadInputTextView();
        textView.setTextColor(COLORE_PRINCIPALE);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout backgroundLinearLayout = new LinearLayout(this.getActivity());
        backgroundLinearLayout.setOrientation(LinearLayout.VERTICAL);
        backgroundLinearLayout.addView(linearLayout);
        backgroundLinearLayout.addView(textView);
        return backgroundLinearLayout;
    }

    private Vector<View> daResettare = new Vector<>();

    public LinearLayout creaLayoutTipoDue(int widthInPixel, FullscreenInsertDialog dialog, DoubleThumbMultiSlider seekBar, AlternativeRadioButton dallaMiaPosizione, AlternativeRadioButton daPosizione, String buttonName) {
        Button button = new Button(this.getActivity());
        DialogShowExecuter dialogShowExecuter = new DialogShowExecuter(dialog, true);
        button.setOnClickListener(dialogShowExecuter);
        button.setBackgroundColor(Color.LTGRAY);
        button.setText(buttonName);
        button.setTextSize(SIZE_CARATTERI);
        button.setAllCaps(false);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setTextColor(Color.DKGRAY);
        button.setWidth((int) (widthInPixel * 0.30));
        this.daResettare.add(button);
        dialog.setReadInputDefaultText("Nessuna");
        daPosizione.setWidth((int) (widthInPixel * 0.70));
        daPosizione.setBackgroundColor(Color.WHITE);
        dallaMiaPosizione.setTextColor(Color.BLACK);
        dallaMiaPosizione.setTextSize(SIZE_CARATTERI);
        daPosizione.setTextColor(Color.BLACK);
        daPosizione.setTextSize(SIZE_CARATTERI);
        Resources resources = this.getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.right_arrow, null);
        drawable.setBounds(0, 0, 60, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        LinearLayout linearLayout = new LinearLayout(this.getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.LTGRAY);
        linearLayout.addView(dallaMiaPosizione);
        LinearLayout daPosizionePlusInserisciPosizioneButtonLinearLayout = new LinearLayout(this.getActivity());
        daPosizionePlusInserisciPosizioneButtonLinearLayout.addView(daPosizione);
        daPosizionePlusInserisciPosizioneButtonLinearLayout.addView(button);
        linearLayout.addView(daPosizionePlusInserisciPosizioneButtonLinearLayout);
        linearLayout.setBackgroundColor(Color.WHITE);
        TextView textView = dialog.getReadInputTextView();
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(COLORE_PRINCIPALE);
        textView.setTextSize(SIZE_CARATTERI);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.daResettare.add(textView);
        LinearLayout backgroundLinearLayout = new LinearLayout(this.getActivity());
        backgroundLinearLayout.setOrientation(LinearLayout.VERTICAL);
        backgroundLinearLayout.addView(linearLayout);
        backgroundLinearLayout.addView(textView);
        seekBar.setVisibility(View.INVISIBLE);
        button.setEnabled(false);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        ViewsEnablerForCompoundButton viewsEnablerForCompoundButtonOne = new ViewsEnablerForCompoundButton(new Vector<>(), true, false);
        viewsEnablerForCompoundButtonOne.addViews(button, textView);
        ViewsVisibilitySetterForCompoundButton viewsVisibilitySetterForCompoundButtonOne = new ViewsVisibilitySetterForCompoundButton(new Vector<>(), true, false);
        viewsVisibilitySetterForCompoundButtonOne.addViews(button, textView);
        UniqueMutualTogglerForCompoundButton uniqueMutualTogglerForCompoundButton = new UniqueMutualTogglerForCompoundButton(daPosizione);
        MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        ViewsVisibilitySetterForCompoundButton viewsVisibilitySetterForCompoundButtonThree = new ViewsVisibilitySetterForCompoundButton(new Vector<>(), false, false);
        viewsVisibilitySetterForCompoundButtonThree.addViews(seekBar);
        ViewVisibilitySetterForCompoundButton viewVisibilitySetterForCompoundButton = new ViewVisibilitySetterForCompoundButton(seekBar, true, true);
        multipleOnClickListener.addOnClickListeners(uniqueMutualTogglerForCompoundButton, viewsEnablerForCompoundButtonOne, viewsVisibilitySetterForCompoundButtonOne, viewsVisibilitySetterForCompoundButtonThree, viewVisibilitySetterForCompoundButton);
        dallaMiaPosizione.setOnClickListener(multipleOnClickListener);

        viewsEnablerForCompoundButtonOne = new ViewsEnablerForCompoundButton(new Vector<>(), true, true);
        viewsEnablerForCompoundButtonOne.addViews(button, textView);
        viewsVisibilitySetterForCompoundButtonOne = new ViewsVisibilitySetterForCompoundButton(new Vector<>(), true, true);
        viewsVisibilitySetterForCompoundButtonOne.addViews(button, textView);
        ViewsEnablerForCompoundButton viewsEnablerForCompoundButtonTwo = new ViewsEnablerForCompoundButton(new Vector<>(), false, false);
        viewsEnablerForCompoundButtonTwo.addViews(button, textView);
        ViewsVisibilitySetterForCompoundButton viewsVisibilitySetterForCompoundButtonTwo = new ViewsVisibilitySetterForCompoundButton(new Vector<>(), false, false);
        viewsVisibilitySetterForCompoundButtonTwo.addViews(button, textView);
        uniqueMutualTogglerForCompoundButton = new UniqueMutualTogglerForCompoundButton(dallaMiaPosizione);

        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(uniqueMutualTogglerForCompoundButton, viewsEnablerForCompoundButtonOne, viewsVisibilitySetterForCompoundButtonOne, viewsEnablerForCompoundButtonTwo, viewsVisibilitySetterForCompoundButtonTwo, viewsVisibilitySetterForCompoundButtonThree, viewVisibilitySetterForCompoundButton);
        daPosizione.setOnClickListener(multipleOnClickListener);
        return backgroundLinearLayout;
    }

    public LinearLayout creaLayoutTipoTre(int widthInPixel, int heightInPixel, DoubleThumbMultiSlider doubleThumbMultiSlider, String label) {
        TextView labelTextView = new TextView(this.getActivity());
        labelTextView.setText("\n" + label);
        labelTextView.setTextSize(SIZE_CARATTERI);
        labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        labelTextView.setBackgroundColor(Color.LTGRAY);
        labelTextView.setTextColor(Color.BLACK);
        TextView minValueTextView = doubleThumbMultiSlider.getMinValueTextView();
        TextView maxValueTextView = doubleThumbMultiSlider.getMaxValueTextView();
        LinearLayout.LayoutParams multiSliderLayoutParams = new LinearLayout.LayoutParams((int) (Math.ceil(widthInPixel * 0.70)), (int) (Math.ceil(heightInPixel * 0.10)));
        LinearLayout.LayoutParams minAndmaxTextViewLayoutParams = new LinearLayout.LayoutParams((int) (Math.ceil(widthInPixel * 0.15)), (int) (Math.ceil(heightInPixel * 0.10)));
        minValueTextView.setLayoutParams(minAndmaxTextViewLayoutParams);
        minValueTextView.setTextColor(Color.BLACK);
        minValueTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        maxValueTextView.setLayoutParams(minAndmaxTextViewLayoutParams);
        maxValueTextView.setTextColor(Color.BLACK);
        doubleThumbMultiSlider.setLayoutParams(multiSliderLayoutParams);
        LinearLayout seekBarLinearLayout = new LinearLayout(this.getActivity());
        seekBarLinearLayout.addView(minValueTextView);
        seekBarLinearLayout.addView(doubleThumbMultiSlider);
        seekBarLinearLayout.addView(maxValueTextView);
        LinearLayout containerLayout = new LinearLayout(this.getActivity());
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.addView(labelTextView);
        containerLayout.addView(seekBarLinearLayout);
        return containerLayout;
    }

    public LinearLayout creaLayoutTipoQuattro(int widthInPixel, int heightInPixel, Spinner ordinaSpinner, CheckBox decrescenteCheckBox) {
        TextView ordinaLabelTextView = new TextView(this.getActivity());
        ordinaLabelTextView.setText("\nOrdina");
        ordinaLabelTextView.setTextSize(SIZE_CARATTERI);
        ordinaLabelTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        ordinaLabelTextView.setBackgroundColor(Color.LTGRAY);
        ordinaLabelTextView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams ordinaAlberghiLayoutParams = new LinearLayout.LayoutParams((int) (Math.ceil(widthInPixel * 0.60)), (int) (Math.ceil(heightInPixel * 0.075)));
        ordinaSpinner.setLayoutParams(ordinaAlberghiLayoutParams);
        decrescenteCheckBox.setTextSize(SIZE_CARATTERI);
        decrescenteCheckBox.setTextColor(Color.BLACK);
        LinearLayout ordinaAlberghiLayout = new LinearLayout(this.getActivity());
        ordinaAlberghiLayout.addView(ordinaSpinner);
        ordinaAlberghiLayout.addView(decrescenteCheckBox);
        LinearLayout backgroundLayout = new LinearLayout(this.getActivity());
        backgroundLayout.setOrientation(LinearLayout.VERTICAL);
        backgroundLayout.addView(ordinaLabelTextView);
        backgroundLayout.addView(ordinaAlberghiLayout);
        return backgroundLayout;
    }

    public LinearLayout creaLayoutTipoCinque(int widthInPixel, NumberPicker numeroAdultiPicker, NumberPicker numeroBambiniPicker) {
        TextView labelTextView = new TextView(this.getActivity());
        labelTextView.setText("\n" + "Turisti");
        labelTextView.setTextSize(SIZE_CARATTERI);
        labelTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        labelTextView.setBackgroundColor(Color.LTGRAY);
        labelTextView.setTextColor(Color.BLACK);
        TextView avvisoQualsiasiNumero = new TextView(this.getActivity());
        avvisoQualsiasiNumero.setText("Seleziona il valore 0 per \"Qualsiasi\".");
        avvisoQualsiasiNumero.setTextSize(SIZE_CARATTERI);
        avvisoQualsiasiNumero.setTextColor(Color.BLACK);
        TextView numeroAdultiLabel = new TextView(this.getActivity());
        numeroAdultiLabel.setWidth((int) (Math.ceil(widthInPixel * 0.25)));
        numeroAdultiLabel.setTextColor(Color.BLACK);
        numeroAdultiLabel.setText("\n\n\n\nAdulti: ");
        numeroAdultiLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        numeroAdultiLabel.setTextSize(SIZE_CARATTERI);
        TextView numeroBambiniLabel = new TextView(this.getActivity());
        numeroBambiniLabel.setWidth((int) (Math.ceil(widthInPixel * 0.25)));
        numeroBambiniLabel.setTextColor(Color.BLACK);
        numeroBambiniLabel.setText("\n\n\n\nBambini: ");
        numeroBambiniLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        numeroBambiniLabel.setTextSize(SIZE_CARATTERI);
        LinearLayout turistiLinearLayout = new LinearLayout(this.getActivity());
        turistiLinearLayout.addView(numeroAdultiLabel);
        turistiLinearLayout.addView(numeroAdultiPicker);
        turistiLinearLayout.addView(numeroBambiniLabel);
        turistiLinearLayout.addView(numeroBambiniPicker);
        LinearLayout finalTuristiLinearLayout = new LinearLayout(this.getActivity());
        finalTuristiLinearLayout.setOrientation(LinearLayout.VERTICAL);
        finalTuristiLinearLayout.addView(labelTextView);
        finalTuristiLinearLayout.addView(avvisoQualsiasiNumero);
        finalTuristiLinearLayout.addView(turistiLinearLayout);
        return finalTuristiLinearLayout;
    }


    @Override
    public BitmapDescriptor buildBitmapDescriptor(Positionable positionable) {
        if (positionable instanceof AlbergoView) {
            return this.creaBitmapDescriptorAlbergo();
        }
        if (positionable instanceof RistoranteView) {
            return this.creaBitmapDescriptorRistorante();
        }
        if (positionable instanceof AttrazioneView) {
            return this.creaBitmapDescriptorAttrazione();
        }
        if (positionable instanceof FNDView) {
            return this.creaBitmapDescriptorFND();
        }
        return null;
    }

    @Override
    public BitmapDescriptor buildBitmapDescriptor(int opzione) {
        switch (opzione) {
            case CVComponentiFactory.CLICK_ALBERGO:
            return this.creaBitmapDescriptorAlbergoCliccato();
            case CVComponentiFactory.CLICK_RISTORANTE:
            return this.creaBitmapDescriptorRistoranteCliccato();
            case CVComponentiFactory.CLICK_ATTRAZIONE:
            return this.creaBitmapDescriptorAttrazioneCliccato();
            case CVComponentiFactory.CLICK_FND:
            return this.creaBitmapDescriptorFNDCliccato();
            case CVComponentiFactory.NOCLICK_ALBERGO:
                return this.creaBitmapDescriptorAlbergo();
            case CVComponentiFactory.NOCLICK_RISTORANTE:
                return this.creaBitmapDescriptorRistorante();
            case CVComponentiFactory.NOCLICK_ATTRAZIONE:
                return this.creaBitmapDescriptorAttrazione();
            case CVComponentiFactory.NOCLICK_FND:
                return this.creaBitmapDescriptorFND();
        }
        return null;
    }

    @Override
    public String buildTitle(Positionable positionable) {
        if (positionable instanceof AlbergoView) {
            return "Albergo";
        }
        if (positionable instanceof RistoranteView) {
            return "Ristorante";
        }
        if (positionable instanceof AttrazioneView) {
            return "Attrazione";
        }
        if (positionable instanceof FNDView) {
            return "FND";
        }
        return null;
    }

    @Override
    public String buildTitle(int opzione) {
        switch(opzione) {
            case CVComponentiFactory.CLICK_ALBERGO:
                return "Albergo";
            case CVComponentiFactory.CLICK_RISTORANTE:
                return "Ristorante";
            case CVComponentiFactory.CLICK_ATTRAZIONE:
                return "Attrazione";
            case CVComponentiFactory.CLICK_FND:
                return "FND";
        }
        return null;
    }

    public BitmapDescriptor creaBitmapDescriptorAlbergo() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_SFONDO_ICONE, -DIFFERENZA_ICON, -DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.hotel, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorRistorante() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_SFONDO_ICONE, -DIFFERENZA_ICON, -DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.restaurant, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorAttrazione() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_SFONDO_ICONE, -DIFFERENZA_ICON, -DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.museum, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorFND() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_SFONDO_ICONE, -DIFFERENZA_ICON, -DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON, DIMENSIONE_ICON + DIFFERENZA_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.fnd, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON, DIMENSIONE_ICON);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON, DIMENSIONE_ICON);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorAlbergoCliccato() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, Color.WHITE, (-DIFFERENZA_ICON) * 2, (-DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.hotel, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorRistoranteCliccato() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, Color.WHITE, (-DIFFERENZA_ICON) * 2, (-DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.restaurant, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorAttrazioneCliccato() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, Color.WHITE, (-DIFFERENZA_ICON) * 2, (-DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.museum, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    public BitmapDescriptor creaBitmapDescriptorFNDCliccato() {
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, Color.WHITE, (-DIFFERENZA_ICON) * 2, (-DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2, (DIMENSIONE_ICON + DIFFERENZA_ICON) * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.lens, COLORE_SFONDO_ICONE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.drawDrawableFromID(R.drawable.fnd, COLORE_PRINCIPALE, 0, 0, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        BitmapDescriptor bitmapDescriptor = this.bitmapDescriptorDesigner.getBitmapDescriptor(DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2, DIMENSIONE_ICON * 2);
        this.bitmapDescriptorDesigner.resetDrawables();
        return bitmapDescriptor;
    }

    @Override
    public Positionable buildPositionable(ObjectsModel objectsModel) {
        AbstractStrutturaView strutturaView = null;
        if (objectsModel instanceof AbstractAlbergoModel) {
            strutturaView = new AlbergoView(this.getActivity(), (AbstractAlbergoModel) objectsModel);
        } else {
            if (objectsModel instanceof AbstractRistoranteModel) {
                strutturaView = new RistoranteView(this.getActivity(), (AbstractRistoranteModel) objectsModel);
            }
            else {
                if (objectsModel instanceof AbstractAttrazioneModel) {
                    strutturaView = new AttrazioneView(this.getActivity(), (AbstractAttrazioneModel) objectsModel);
                }
                else {
                    if (objectsModel instanceof AbstractFNDModel) {
                        strutturaView = new FNDView(this.getActivity(), (AbstractFNDModel) objectsModel);
                    }
                }
            }
        }
        if (this.strutturaListener != null && this.scritturaRecensioneStarter != null) {
            Button button = (Button) strutturaView.getVisualizeOnMapClickableView();
            button.setOnClickListener(this.strutturaListener);
            TextView textView = (TextView) strutturaView.getInsertClickableView();
            textView.setOnClickListener(this.scritturaRecensioneStarter);
        }
        return strutturaView;
    }

    public Vector<View> getDaResettare() {
        return this.daResettare;
    }

    @Override
    public void update(Observable observable, Object object) {
        CleanMapStyleSetter cleanMapStyleSetter = (CleanMapStyleSetter) observable;
        GoogleMap googleMap = cleanMapStyleSetter.getGoogleMap();
        GoogleMapCameraAnimator googleMapCameraAnimator = new GoogleMapCameraAnimator(googleMap, 0, 0, 13);
        OnClickExecuterForTag onClickExecuterForTag = new OnClickExecuterForTag(this.strutturaListener);
        MarkerIconSetter markerIconSetter = new MarkerIconSetter();
        BitmapDescriptor clicked = this.buildBitmapDescriptor(CVComponentiFactory.CLICK_ALBERGO);
        BitmapDescriptor notClicked = this.buildBitmapDescriptor(CVComponentiFactory.NOCLICK_ALBERGO);
        String titolo = this.buildTitle(CVComponentiFactory.CLICK_ALBERGO);
        markerIconSetter.setAssociation(titolo, clicked, notClicked);
        clicked = this.buildBitmapDescriptor(CVComponentiFactory.CLICK_RISTORANTE);
        notClicked = this.buildBitmapDescriptor(CVComponentiFactory.NOCLICK_RISTORANTE);
        titolo = this.buildTitle(CVComponentiFactory.CLICK_RISTORANTE);
        markerIconSetter.setAssociation(titolo, clicked, notClicked);
        clicked = this.buildBitmapDescriptor(CVComponentiFactory.CLICK_ATTRAZIONE);
        notClicked = this.buildBitmapDescriptor(CVComponentiFactory.NOCLICK_ATTRAZIONE);
        titolo = this.buildTitle(CVComponentiFactory.CLICK_ATTRAZIONE);
        markerIconSetter.setAssociation(titolo, clicked, notClicked);
        clicked = this.buildBitmapDescriptor(CVComponentiFactory.CLICK_FND);
        notClicked = this.buildBitmapDescriptor(CVComponentiFactory.NOCLICK_FND);
        titolo = this.buildTitle(CVComponentiFactory.CLICK_FND);
        markerIconSetter.setAssociation(titolo, clicked, notClicked);
        MultipleOnMarkerClickListener multipleOnMarkerClickListener = new MultipleOnMarkerClickListener(new Vector<>());
        multipleOnMarkerClickListener.addOnMarkerClickListeners(googleMapCameraAnimator, markerIconSetter);
        OnMarkerClickExecuterForPositionableSubView onMarkerClickExecuterForPositionableSubView = new OnMarkerClickExecuterForPositionableSubView(multipleOnMarkerClickListener);
        this.strutturaListener.addOnClickListeners(onMarkerClickExecuterForPositionableSubView);
        googleMap.setOnMarkerClickListener(onClickExecuterForTag);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
    }

    @Override
    public String buildBarTitle(String name) {
        if (name != null) {
            if (name.length() > 0) {
                return "Bentornato, " + name + "!";
            }
        }
        return "";
    }

    @Override
    public AbstractUserCache buildUserCache() {
        return new LastUserCache(this.getActivity());
    }

    @Override
    public Authenticator buildAuthenticator() {
        Resources resources = this.getActivity().getResources();
        String email = resources.getString(R.string.backendEmail);
        String password = resources.getString(R.string.backendPassword);
        return new UserAuthenticator(email,password);
    }

    @Override
    public ViewGroup buildViewGroup() {
        return new SwipeDisabledViewPager(this.getActivity(), false);
    }

}