package cvapp.activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import cvapp.components.InterfacciaQuery;
import cvapp.handlers.AutenticazioneUtenteHandler;
import cvapp.components.CVComponentiFactory;
import cvapp.handlers.DisabilitazioneUtenteHandler;
import cvapp.handlers.FiltraggioAlberghiHandler;
import cvapp.handlers.FiltraggioAttrazioniHandler;
import cvapp.handlers.FiltraggioFNDHandler;
import cvapp.handlers.FiltraggioRecensioniHandler;
import cvapp.handlers.FiltraggioRistorantiHandler;
import cvapp.handlers.FiltraggioStruttureVicineHandler;
import cvapp.components.InterfacciaBackEndStub;
import cvapp.observables.ProfiloUtenteObservable;
import cvapp.R;
import cvapp.observables.RecensioniObservable;
import cvapp.handlers.ScritturaRecensioneHandler;
import cvapp.handlers.VisualizzazioneProfiloUtenteHandler;
import utilities.AdapterUtility;
import adapters.SimpleViewPagerAdapter;

import positionablecomponents.Positionable;
import positionablecomponents.LastClickedPositionableSaver;
import positionablecomponents.PositionableTransformer;
import cvapp.views.AlbergoView;
import cvapp.views.AttrazioneView;
import cvapp.views.FNDView;
import cvapp.views.RecensioneView;
import cvapp.views.RistoranteView;
import cvapp.views.AbstractStrutturaView;
import activities.BackgroundMapViewActivity;
import onmapreadycallbacks.CleanMapStyleSetter;
import graphiccomponents.AlternativeRadioButton;
import graphiccomponents.FullscreenLoginDialog;
import graphiccomponents.FullscreenReviewDialog;
import graphiccomponents.LinearLayouts;
import graphiccomponents.ResettablesResetExecuter;
import graphiccomponents.SwipeDisabledViewPager;
import utilities.PositionUtility;
import onclicklisteners.CompoundButtonCheckSetter;
import onclicklisteners.CompoundButtonsCheckSetter;
import onclicklisteners.MarkersRemover;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.NumberPickersValueSetter;
import onclicklisteners.ScrollViewFocusUpExecuter;
import onclicklisteners.SlidingUpPanelLayoutAnchorer;
import onclicklisteners.SpinnerSelectionSetter;
import onclicklisteners.ViewEnabler;
import onclicklisteners.layoutmodifiers.ViewGroupClickedViewAdder;
import onclicklisteners.layoutmodifiers.ViewGroupSettedViewAdder;
import onclicklisteners.layoutmodifiers.ViewGroupViewRemover;
import onclicklisteners.viewpagersetmodifiers.ViewPagerCurrentPageSwapper;
import onclicklisteners.viewpagersetmodifiers.ViewPagerCurrentPageSwapperOnIdCondition;
import onclicklisteners.viewpagersetmodifiers.ViewPagerPageSetSwitcher;
import onclicklisteners.layoutmodifiers.ViewParentSaver;
import onclicklisteners.ViewsVisibilitySetter;
import onscrollchangelisteners.SlidingUpPanelLayoutScrollableViewSetter;
import ontabselectedlisteners.MultipleOnTabSelectedListener;
import ontabselectedlisteners.OnClickExecuter;
import ontabselectedlisteners.SlidingUpPanelLayoutExpander;

public class MappaActivity extends BackgroundMapViewActivity {

    private final static String MAP_VIEW_BUNDLE_KEY = "AIzaSyAWkn7A7v0NzrPhUS46kK8ohkmthMAsp6Y";
    public final static int PG_ALBERGHI_FILTRI = 0;
    public final static int PG_RISTORANTI_FILTRI = 1;
    public final static int PG_ATTRAZIONI_FILTRI = 2;
    public final static int PG_FND_FILTRI = 3;
    public final static int PG_ALBERGHI_DESCR = 4;
    public final static int PG_RISTORANTI_DESCR = 5;
    public final static int PG_ATTRAZIONI_DESCR = 6;
    public final static int PG_FND_DESCR = 7;

    public final static int FAKE_PG = -1;
    public final static int PG_STRUTTURA = 8;
    public final static int PG_RECENSIONI_FILTRI = 9;
    public final static int PG_VICINE_FILTRI = 10;
    public final static int PG_RECENSIONI_DESCR = 11;
    public final static int PG_VICINE_DESCR = 12;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_mappa_activity, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        this.finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                this.autenticazioneUtenteHandler.showLoginDialog();
                return true;
            case R.id.logout:
                this.disabilitazioneUtenteHandler.disableUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AutenticazioneUtenteHandler autenticazioneUtenteHandler;
    private DisabilitazioneUtenteHandler disabilitazioneUtenteHandler;

    private ScritturaRecensioneHandler scritturaRecensioneHandler;
    private FiltraggioAlberghiHandler filtraggioAlberghiHandler;
    private FiltraggioRistorantiHandler filtraggioRistorantiHandler;
    private FiltraggioAttrazioniHandler filtraggioAttrazioniHandler;
    private FiltraggioFNDHandler filtraggioFNDHandler;
    private FiltraggioRecensioniHandler filtraggioRecensioniHandler;
    private VisualizzazioneProfiloUtenteHandler visualizzazioneProfiloUtenteHandler;
    private FiltraggioStruttureVicineHandler filtraggioStruttureVicineHandler;

    private PositionUtility positionUtility;
    private CleanMapStyleSetter cleanMapStyleSetter;

    @Override
    protected void onCreate(Bundle stateBundle) {
        super.onCreate(stateBundle);

        this.positionUtility = new PositionUtility();
        PositionUtility.isAccessFineLocationGranted(this);
        this.positionUtility.assignLocationRequest(this);
        this.positionUtility.createGeocoder(this, Locale.ITALIAN);

        this.setMapViewBundleKey(MappaActivity.MAP_VIEW_BUNDLE_KEY);
        this.initializeCleanGoogleMapMapView(stateBundle);

        this.cleanMapStyleSetter = new CleanMapStyleSetter();
        MapView mapView = this.getMapView();
        mapView.getMapAsync(this.cleanMapStyleSetter);

        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthInPixel = displayMetrics.widthPixels;
        int widthInDp = (int) ((widthInPixel) / (displayMetrics.density));

        CVComponentiFactory componentiFactory = new CVComponentiFactory(this);
        InterfacciaQuery interfacciaQuery = new InterfacciaBackEndStub(this, "backendconfig.properties");

        PositionableTransformer positionableTransformer = new PositionableTransformer(componentiFactory, widthInDp);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
        this.filtraggioAlberghiHandler = new FiltraggioAlberghiHandler(this, positionUtility, interfacciaQuery, componentiFactory,positionableTransformer);
        positionableTransformer = new PositionableTransformer(componentiFactory, widthInDp);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
        this.filtraggioRistorantiHandler = new FiltraggioRistorantiHandler(this, positionUtility, interfacciaQuery, componentiFactory, positionableTransformer);
        positionableTransformer = new PositionableTransformer(componentiFactory, widthInDp);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
        this.filtraggioAttrazioniHandler = new FiltraggioAttrazioniHandler(this, positionUtility, interfacciaQuery, componentiFactory, positionableTransformer);
        positionableTransformer = new PositionableTransformer(componentiFactory, widthInDp);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
        this.filtraggioFNDHandler = new FiltraggioFNDHandler(this, positionUtility, interfacciaQuery, componentiFactory, positionableTransformer);

        RecensioniObservable recensioniObservable = new RecensioniObservable(componentiFactory);
        this.filtraggioRecensioniHandler = new FiltraggioRecensioniHandler(this, interfacciaQuery, componentiFactory, recensioniObservable);
        ProfiloUtenteObservable profiloUtenteObservable = new ProfiloUtenteObservable(componentiFactory);
        this.visualizzazioneProfiloUtenteHandler = new VisualizzazioneProfiloUtenteHandler(this, interfacciaQuery, componentiFactory, profiloUtenteObservable, profiloUtenteObservable);
        ProfiloUtenteObserver profiloUtenteObserver = new ProfiloUtenteObserver(this.visualizzazioneProfiloUtenteHandler);
        profiloUtenteObservable.addObserver(profiloUtenteObserver);
        this.autenticazioneUtenteHandler = new AutenticazioneUtenteHandler(this, interfacciaQuery, componentiFactory, new Observable());
        this.scritturaRecensioneHandler = new ScritturaRecensioneHandler(this, interfacciaQuery, componentiFactory, this.autenticazioneUtenteHandler);
        this.disabilitazioneUtenteHandler = new DisabilitazioneUtenteHandler(this, componentiFactory, this.autenticazioneUtenteHandler, new Observable());
        positionableTransformer = new PositionableTransformer(componentiFactory, widthInDp);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
        this.filtraggioStruttureVicineHandler = new FiltraggioStruttureVicineHandler(this, interfacciaQuery, componentiFactory, positionableTransformer);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarHeight = this.getActionBarHeight();

        ColorDrawable colorDrawable = new ColorDrawable(CVComponentiFactory.COLORE_PRINCIPALE);
        actionBar.setTitle("Mappa");
        actionBar.setBackgroundDrawable(colorDrawable);

        int heightInPixel = displayMetrics.heightPixels;
        LayoutParams layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * 0.0625));
        int minus = actionBarHeight + layoutParams.height;

        Vector<View> categoriePrimoSet = new Vector<>();

        //creazione e aggiunta pagina filtri alberghi

        Button fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        Button resettaButtonAlberghi = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonAlberghi, "Resetta");
        Button filtraButtonAlberghi = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonAlberghi, "Filtra");
        LinearLayout paginaFiltriAlberghi = LinearLayouts.buildLayout(this, fakeButtonOne, resettaButtonAlberghi, filtraButtonAlberghi, minus);
        LinearLayout linearLayout = (LinearLayout) paginaFiltriAlberghi.getChildAt(0);
        LinearLayout tipologieAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getTipologieDialog(), "Seleziona", "Tipologie");
        LinearLayout stelleAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getStelleDialog(), "Seleziona", "Stelle");
        LinearLayout serviziRistorazioneAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getServiziRistorazioneDialog(), "Seleziona", "Servizi ristorazione");
        LinearLayout serviziAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getChoicesDialog(), "Seleziona", "Servizi");
        LinearLayout arrivoAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getCheckInDialog(), "Seleziona", "Check in");
        LinearLayout partenzaAlberghiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAlberghiHandler.getCheckOutDialog(), "Seleziona", "Check out");
        LinearLayout numeroTuristiAlberghiLayout = componentiFactory.creaLayoutTipoCinque(widthInPixel, this.filtraggioAlberghiHandler.getNumeroAdultiNumberPicker(), this.filtraggioAlberghiHandler.getNumeroBambiniNumberPicker());
        LinearLayout costoAlberghiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAlberghiHandler.getPriceMultiSlider(), "Costo camera");
        LinearLayout votoRecensioniMedioAlberghiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAlberghiHandler.getRatingMultiSlider(), "Voto recensioni medio");
        LinearLayout distanzaAlberghiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAlberghiHandler.getDistanceMultiSlider(), "Distanza");
        LinearLayout posizioneAlberghiLinearLayout = componentiFactory.creaLayoutTipoDue(widthInPixel, this.filtraggioAlberghiHandler.getPositionDialog(), this.filtraggioAlberghiHandler.getDistanceMultiSlider(), (AlternativeRadioButton) this.filtraggioAlberghiHandler.getActualPositionCompoundButton(), (AlternativeRadioButton) this.filtraggioAlberghiHandler.getPositionCompoundButton(), "Inserisci");
        LinearLayout numeroMassimoAlberghiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel,this.filtraggioAlberghiHandler.getNumberMultiSlider(), "Numero massimo");
        LinearLayout ordinaAlberghiLinearLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioAlberghiHandler.getSortSpinner(), (CheckBox) this.filtraggioAlberghiHandler.getReverseSortCompoundButton());
        LinearLayout linearLayoutCodaAlberghi = new LinearLayout(this);
        linearLayoutCodaAlberghi.setMinimumHeight((int) (Math.ceil(heightInPixel * 0.15)));
        ListView listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, tipologieAlberghiLayout, stelleAlberghiLayout, serviziRistorazioneAlberghiLayout, serviziAlberghiLayout, numeroTuristiAlberghiLayout, arrivoAlberghiLayout, partenzaAlberghiLayout, costoAlberghiLinearLayout, votoRecensioniMedioAlberghiLinearLayout,distanzaAlberghiLinearLayout ,posizioneAlberghiLinearLayout ,numeroMassimoAlberghiLinearLayout, ordinaAlberghiLinearLayout ,linearLayoutCodaAlberghi);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaFiltriAlberghi.setId(PG_ALBERGHI_FILTRI);
        categoriePrimoSet.add(paginaFiltriAlberghi);

        //creazione e aggoiunta pagina filtri ristoranti

        fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        Button resettaButtonRistoranti = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonRistoranti, "Resetta");
        Button filtraButtonRistoranti = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonRistoranti, "Filtra");
        LinearLayout paginaFiltriRistoranti = LinearLayouts.buildLayout(this, fakeButtonOne, resettaButtonRistoranti, filtraButtonRistoranti, minus);
        linearLayout = (LinearLayout) paginaFiltriRistoranti.getChildAt(0);
        LinearLayout tipologieCucinaRistorantiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRistorantiHandler.getCucinaDialog(), "Seleziona", "Tipologie cucina");
        LinearLayout serviziRistorantiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRistorantiHandler.getChoicesDialog(), "Seleziona", "Servizi");
        LinearLayout numeroTuristiRistorantiLayout = componentiFactory.creaLayoutTipoCinque(widthInPixel, this.filtraggioRistorantiHandler.getNumeroAdultiNumberPicker(), this.filtraggioRistorantiHandler.getNumeroBambiniNumberPicker());
        LinearLayout dataOraRistorantiLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRistorantiHandler.getDataOraDialog(), "Seleziona", "Data & ora");
        LinearLayout costoRistorantiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRistorantiHandler.getPriceMultiSlider(), "Costo (medio)");
        LinearLayout votoRecensioniMedioRistorantiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRistorantiHandler.getRatingMultiSlider(), "Voto recensioni medio");
        LinearLayout distanzaRistorantiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRistorantiHandler.getDistanceMultiSlider(), "Distanza");
        LinearLayout posizioneRistorantiLinearLayout = componentiFactory.creaLayoutTipoDue(widthInPixel, this.filtraggioRistorantiHandler.getPositionDialog(), this.filtraggioRistorantiHandler.getDistanceMultiSlider(), (AlternativeRadioButton) this.filtraggioRistorantiHandler.getActualPositionCompoundButton(), (AlternativeRadioButton) this.filtraggioRistorantiHandler.getPositionCompoundButton(), "Inserisci");
        LinearLayout numeroMassimoRistorantiLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRistorantiHandler.getNumberMultiSlider(), "Numero massimo");
        LinearLayout ordinaRistorantiLinearLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioRistorantiHandler.getSortSpinner(), (CheckBox) this.filtraggioRistorantiHandler.getReverseSortCompoundButton());
        LinearLayout linearLayoutCodaRistoranti = new LinearLayout(this);
        linearLayoutCodaRistoranti.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, tipologieCucinaRistorantiLayout, serviziRistorantiLayout, numeroTuristiRistorantiLayout, dataOraRistorantiLayout, costoRistorantiLinearLayout, votoRecensioniMedioRistorantiLinearLayout, distanzaRistorantiLinearLayout, posizioneRistorantiLinearLayout, numeroMassimoRistorantiLinearLayout, ordinaRistorantiLinearLayout, linearLayoutCodaRistoranti);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaFiltriRistoranti.setId(PG_RISTORANTI_FILTRI);
        categoriePrimoSet.add(paginaFiltriRistoranti);

        //creazione e aggiunta pagina filtri attrazioni

        fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        Button resettaButtonAttrazioni = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonAttrazioni, "Resetta");
        Button filtraButtonAttrazioni = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonAttrazioni, "Filtra");
        LinearLayout paginaFiltriAttrazioni = LinearLayouts.buildLayout(this, fakeButtonOne, resettaButtonAttrazioni, filtraButtonAttrazioni, minus);
        linearLayout = (LinearLayout) paginaFiltriAttrazioni.getChildAt(0);
        LinearLayout tipologieAttrazioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAttrazioniHandler.getTipologieDialog(), "Seleziona", "Tipologie");
        LinearLayout serviziAttrazioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAttrazioniHandler.getChoicesDialog(), "Seleziona", "Servizi");
        LinearLayout tipologieGuidaAttrazioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAttrazioniHandler.getGuidaDialog(), "Seleziona", "Tipologie guida");
        LinearLayout dataOraAttrazioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioAttrazioniHandler.getDataOraDialog(), "Seleziona", "Data & ora");
        LinearLayout costoAttrazioniLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel,this.filtraggioAttrazioniHandler.getPriceMultiSlider(), "Costo biglietto");
        LinearLayout votoRecensioniMedioAttrazioniLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAttrazioniHandler.getRatingMultiSlider(), "Voto recensioni medio");
        LinearLayout distanzaAttrazioniLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAttrazioniHandler.getDistanceMultiSlider(), "Distanza");
        LinearLayout posizioneAttrazioniLinearLayout = componentiFactory.creaLayoutTipoDue(widthInPixel, this.filtraggioAttrazioniHandler.getPositionDialog(), this.filtraggioAttrazioniHandler.getDistanceMultiSlider(), (AlternativeRadioButton) this.filtraggioAttrazioniHandler.getActualPositionCompoundButton(), (AlternativeRadioButton) this.filtraggioAttrazioniHandler.getPositionCompoundButton(), "Inserisci");
        LinearLayout numeroMassimoAttrazioniLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioAttrazioniHandler.getNumberMultiSlider(), "Numero massimo");
        LinearLayout ordinaAttrazioniLinearLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioAttrazioniHandler.getSortSpinner(), (CheckBox) this.filtraggioAttrazioniHandler.getReverseSortCompoundButton());
        LinearLayout linearLayoutCodaAttrazioni = new LinearLayout(this);
        linearLayoutCodaAttrazioni.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, tipologieAttrazioniLayout, serviziAttrazioniLayout, tipologieGuidaAttrazioniLayout, dataOraAttrazioniLayout, costoAttrazioniLinearLayout, votoRecensioniMedioAttrazioniLinearLayout, distanzaAttrazioniLinearLayout, posizioneAttrazioniLinearLayout, numeroMassimoAttrazioniLinearLayout, ordinaAttrazioniLinearLayout, linearLayoutCodaAttrazioni);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaFiltriAttrazioni.setId(PG_ATTRAZIONI_FILTRI);
        categoriePrimoSet.add(paginaFiltriAttrazioni);

        //creazione e aggiunta pagina filtri fnd

        fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        Button resettaButtonFND = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonFND, "Resetta");
        Button filtraButtonFND = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonFND, "Filtra");
        LinearLayout paginaFiltriFND = LinearLayouts.buildLayout(this, fakeButtonOne, resettaButtonFND, filtraButtonFND, minus);
        linearLayout = (LinearLayout) paginaFiltriFND.getChildAt(0);
        LinearLayout tipologieFNDLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioFNDHandler.getTipologieDialog(), "Seleziona", "Tipologie");
        LinearLayout serviziFNDLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioFNDHandler.getChoicesDialog(), "Seleziona", "Servizi");
        LinearLayout numeroTuristiFNDLayout = componentiFactory.creaLayoutTipoCinque(widthInPixel, this.filtraggioFNDHandler.getNumeroAdultiNumberPicker(), this.filtraggioFNDHandler.getNumeroBambiniNumberPicker());
        LinearLayout dataOraFNDLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioFNDHandler.getDataOraDialog(), "Seleziona", "Data & ora");
        LinearLayout costoFNDLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioFNDHandler.getPriceMultiSlider(), "Costo (medio)");
        LinearLayout votoRecensioniMedioFNDLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioFNDHandler.getRatingMultiSlider(), "Voto recensioni medio");
        LinearLayout distanzaFNDLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioFNDHandler.getDistanceMultiSlider(), "Distanza");
        LinearLayout posizioneFNDLinearLayout = componentiFactory.creaLayoutTipoDue(widthInPixel,this.filtraggioFNDHandler.getPositionDialog(), this.filtraggioFNDHandler.getDistanceMultiSlider(), (AlternativeRadioButton) this.filtraggioFNDHandler.getActualPositionCompoundButton(), (AlternativeRadioButton) this.filtraggioFNDHandler.getPositionCompoundButton(), "Inserisci");
        LinearLayout numeroMassimoFNDLinearLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioFNDHandler.getNumberMultiSlider(), "Numero massimo");
        LinearLayout ordinaFNDLinearLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioFNDHandler.getSortSpinner(), (CheckBox) this.filtraggioFNDHandler.getReverseSortCompoundButton());
        LinearLayout linearLayoutCodaFND = new LinearLayout(this);
        linearLayoutCodaFND.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, tipologieFNDLayout, serviziFNDLayout, numeroTuristiFNDLayout, dataOraFNDLayout, costoFNDLayout, votoRecensioniMedioFNDLayout, distanzaFNDLayout, posizioneFNDLinearLayout, numeroMassimoFNDLinearLayout, ordinaFNDLinearLayout, linearLayoutCodaFND);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaFiltriFND.setId(PG_FND_FILTRI);
        categoriePrimoSet.add(paginaFiltriFND);

        //settaggio view pager categorie secondo set di pagine

        Vector<View> categorieSecondoSet = new Vector<>();

        //creazione e aggiunta pagina descrittori alberghi
        Button indietroButtonAlberghi = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonAlberghi, "Indietro");
        Button fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        Button fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaAlberghiDescrittori = LinearLayouts.buildLayout(this, indietroButtonAlberghi, fakeButtonTwo, fakeButtonThree, minus);
        paginaAlberghiDescrittori.setId(PG_ALBERGHI_DESCR);
        categorieSecondoSet.add(paginaAlberghiDescrittori);

        //creazione e aggiunta pagina descrittori ristoranti
        Button indietroButtonRistoranti = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonRistoranti, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaRistorantiDescrittori = LinearLayouts.buildLayout(this, indietroButtonRistoranti, fakeButtonTwo, fakeButtonThree, minus);
        paginaRistorantiDescrittori.setId(PG_RISTORANTI_DESCR);
        categorieSecondoSet.add(paginaRistorantiDescrittori);

        //creazione e aggiunta pagina descrittori attrazioni
        Button indietroButtonAttrazioni = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonAttrazioni, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaAttrazioniDescrittori = LinearLayouts.buildLayout(this, indietroButtonAttrazioni, fakeButtonTwo, fakeButtonThree, minus);
        paginaAttrazioniDescrittori.setId(PG_ATTRAZIONI_DESCR);
        categorieSecondoSet.add(paginaAttrazioniDescrittori);

        //creazione e aggiunta pagina descrittori fnd
        Button indietroButtonFND = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonFND, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaFNDDescrittori = LinearLayouts.buildLayout(this, indietroButtonFND, fakeButtonTwo, fakeButtonThree, minus);
        paginaFNDDescrittori.setId(PG_FND_DESCR);
        categorieSecondoSet.add(paginaFNDDescrittori);

        //settaggio view pager struttura primo set di pagine

        Vector<View> strutturaPrimoSet = new Vector<>();

        //creazione e aggiunta pagina fake

        LinearLayout fakePageOne = new LinearLayout(this);
        fakePageOne.setId(FAKE_PG);
        strutturaPrimoSet.add(fakePageOne);

        //creazione e aggiunta pagina struttura
        Button toCategorieButtonStruttura = new Button(this);
        componentiFactory.applicaButtonStyle(toCategorieButtonStruttura, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaStruttura = LinearLayouts.buildScrollableLayout(this, toCategorieButtonStruttura, fakeButtonTwo, fakeButtonThree, minus);
        paginaStruttura.setId(PG_STRUTTURA);
        strutturaPrimoSet.add(paginaStruttura);

        //creazione e aggiunta pagina filtri recensioni
        Button toCategorieButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(toCategorieButtonRecensioni, "Indietro");
        Button resettaButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonRecensioni, "Resetta");
        Button filtraButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonRecensioni, "Filtra");
        LinearLayout paginaRecensioniFiltri = LinearLayouts.buildLayout(this, toCategorieButtonRecensioni, resettaButtonRecensioni, filtraButtonRecensioni, minus);
        linearLayout = (LinearLayout) paginaRecensioniFiltri.getChildAt(0);
        LinearLayout votiRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getVotiDialog(), "Seleziona", "Voti");
        LinearLayout daDataRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getDaDataDialog(), "Seleziona", "Da data");
        LinearLayout aDataRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getADataDialog(), "Seleziona", "A data");
        LinearLayout numeroMassimoRecensioniLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRecensioniHandler.getNumeroMassimoMultiSlider(), "Numero massimo");
        LinearLayout ordinaRecensioniLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioRecensioniHandler.getOrdinaSpinner(), this.filtraggioRecensioniHandler.getDecrescenteCheckBox());
        LinearLayout linearLayoutCodaRecensioni = new LinearLayout(this);
        linearLayoutCodaRecensioni.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, votiRecensioniLayout, daDataRecensioniLayout, aDataRecensioniLayout, numeroMassimoRecensioniLayout, ordinaRecensioniLayout, linearLayoutCodaRecensioni);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaRecensioniFiltri.setId(PG_RECENSIONI_FILTRI);
        strutturaPrimoSet.add(paginaRecensioniFiltri);

        //creazione e aggiunta pagina filtri vicine
        Button toCategorieButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(toCategorieButtonVicine, "Indietro");
        Button resettaButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonVicine, "Resetta");
        Button filtraButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonVicine, "Filtra");
        LinearLayout paginaVicineFiltri = LinearLayouts.buildLayout(this, toCategorieButtonVicine, resettaButtonVicine, filtraButtonVicine, minus);
        linearLayout = (LinearLayout) paginaVicineFiltri.getChildAt(0);
        TextView categorieVicineTextView = new TextView(this);
        categorieVicineTextView.setText("\nCategorie");
        categorieVicineTextView.setTextSize(18);
        categorieVicineTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        categorieVicineTextView.setTextColor(Color.BLACK);
        categorieVicineTextView.setBackgroundColor(Color.LTGRAY);

        LinearLayout categorieVicineLayout = new LinearLayout(this);
        categorieVicineLayout.setOrientation(LinearLayout.VERTICAL);
        categorieVicineLayout.addView(categorieVicineTextView);
        this.filtraggioStruttureVicineHandler.getAlberghiCheckBox().setTextSize(18);
        categorieVicineLayout.addView(this.filtraggioStruttureVicineHandler.getAlberghiCheckBox());
        this.filtraggioStruttureVicineHandler.getRistorantiCheckBox().setTextSize(18);
        categorieVicineLayout.addView(this.filtraggioStruttureVicineHandler.getRistorantiCheckBox());
        this.filtraggioStruttureVicineHandler.getAttrazioniCheckBox().setTextSize(18);
        categorieVicineLayout.addView(this.filtraggioStruttureVicineHandler.getAttrazioniCheckBox());
        this.filtraggioStruttureVicineHandler.getFndCheckBox().setTextSize(18);
        categorieVicineLayout.addView(this.filtraggioStruttureVicineHandler.getFndCheckBox());
        LinearLayout distanzaVicineLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioStruttureVicineHandler.getDistanceMultiSlider(), "Distanza");
        LinearLayout numeroMassimoVicineLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioStruttureVicineHandler.getNumberMultiSlider(), "Numero massimo (per categoria)");
        LinearLayout linearLayoutCodaVicine = new LinearLayout(this);
        linearLayoutCodaVicine.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, categorieVicineLayout, distanzaVicineLayout, numeroMassimoVicineLayout, linearLayoutCodaVicine);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaVicineFiltri.setId(PG_VICINE_FILTRI);
        strutturaPrimoSet.add(paginaVicineFiltri);

        //settaggio view pager struttura secondo set di pagine

        Vector<View> strutturaSecondoSet = new Vector<>();

        //aggiunta pagina fake e aggiunta pagina struttura

        strutturaSecondoSet.add(fakePageOne);
        strutturaSecondoSet.add(paginaStruttura);

        // creazione e aggiunta pagina descrittori recensioni

        Button indietroButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonRecensioni, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaRecensioniDescrittori = LinearLayouts.buildLayout(this, indietroButtonRecensioni, fakeButtonTwo, fakeButtonThree, minus);
        paginaRecensioniDescrittori.setId(PG_RECENSIONI_DESCR);
        strutturaSecondoSet.add(paginaRecensioniDescrittori);

        //creazione e aggiunta pagina descrittori vicine

        Button indietroButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonVicine, "Indietro");
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        fakeButtonThree = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonThree);
        LinearLayout paginaVicineDescrittori = LinearLayouts.buildLayout(this, indietroButtonVicine, fakeButtonTwo, fakeButtonThree, minus);
        paginaVicineDescrittori.setId(PG_VICINE_DESCR);
        strutturaSecondoSet.add(paginaVicineDescrittori);

        //creazione e settaggio view pager con primo set categorie

        SwipeDisabledViewPager viewPager = (SwipeDisabledViewPager) componentiFactory.buildViewGroup();
        viewPager.setOffscreenPageLimit(3);
        SimpleViewPagerAdapter simpleViewPagerAdapter = new SimpleViewPagerAdapter(categoriePrimoSet);
        viewPager.setAdapter(simpleViewPagerAdapter);

        TabLayout tabLayout = new TabLayout(this);
        tabLayout.setLayoutParams(layoutParams);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(CVComponentiFactory.COLORE_PRINCIPALE, CVComponentiFactory.COLORE_PRINCIPALE);
        Tab tab = tabLayout.getTabAt(0);
        tab.setText("Alberghi");
        tab = tabLayout.getTabAt(1);
        tab.setText("Ristoranti");
        tab = tabLayout.getTabAt(2);
        tab.setText("Attrazioni");
        tab = tabLayout.getTabAt(3);
        tab.setText("Food&Drink");

        SlidingUpPanelLayout slidingUpPanelLayout = this.getSlidingUpPanelLayout();

        // set OnTabSelectedListener al tab layout setOnScrollChangeListener alla scroll view della pagina struttura

        ScrollView scrollView = (ScrollView) paginaStruttura.getChildAt(0);

        float anchorPoint = ((AbstractStrutturaView.ALTEZZA_ANCHOR) / ((float) displayMetrics.heightPixels));
        SlidingUpPanelLayoutAnchorer slidingUpPanelLayoutAnchorer = new SlidingUpPanelLayoutAnchorer(slidingUpPanelLayout, anchorPoint);
        SlidingUpPanelLayoutExpander slidingUpPanelLayoutExpander = new SlidingUpPanelLayoutExpander(slidingUpPanelLayout);
        onscrollchangelisteners.SlidingUpPanelLayoutScrollableViewSetter slidingUpPanelLayoutScrollableViewSetter = new SlidingUpPanelLayoutScrollableViewSetter(slidingUpPanelLayout);
        OnClickExecuter onClickExecuter = new OnClickExecuter(null, "Categorie");
        ontabselectedlisteners.ScrollViewFocusUpExecuter scrollViewFocusUpExecuter = new ontabselectedlisteners.ScrollViewFocusUpExecuter(scrollView);
        MultipleOnTabSelectedListener multipleOnTabSelectedListener = new MultipleOnTabSelectedListener(new Vector<>());
        multipleOnTabSelectedListener.addOnTabSelectedListeners(slidingUpPanelLayoutExpander, scrollViewFocusUpExecuter, onClickExecuter);
        tabLayout.addOnTabSelectedListener(multipleOnTabSelectedListener);

        ScrollViewFocusUpExecuter focusUpOnClickListener = new ScrollViewFocusUpExecuter(scrollView);
        scrollView.setOnScrollChangeListener(slidingUpPanelLayoutScrollableViewSetter);

        // creazione e settaggio swapper per categorie

        ViewPagerCurrentPageSwapper categorieCurrentPageSwapper = new ViewPagerCurrentPageSwapper(viewPager, categoriePrimoSet, categorieSecondoSet);
        categorieCurrentPageSwapper.setTabLayout(tabLayout);
        Vector<String> categorieTabTexts = new Vector<>();
        categorieTabTexts.add("Alberghi");
        categorieTabTexts.add("Ristoranti");
        categorieTabTexts.add("Attrazioni");
        categorieTabTexts.add("Food&Drink");
        categorieCurrentPageSwapper.setTabTextsList(categorieTabTexts);

        // set OnClickListener a filtra button alberghi, ristoranti, attrazioni, fnd


        FiltraggioAlberghiHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterAlberghi = this.filtraggioAlberghiHandler.new InnerAsyncTaskExecuter();
        VisualizzatoreStruttureCategoria visualizzatoreStruttureCategoria = new VisualizzatoreStruttureCategoria(categorieCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioAlberghiHandler.getPositionableOperations();
        positionableTransformer.addObserver(visualizzatoreStruttureCategoria);
        filtraButtonAlberghi.setOnClickListener(innerAsyncTaskExecuterAlberghi);

        FiltraggioRistorantiHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterRistoranti = this.filtraggioRistorantiHandler.new InnerAsyncTaskExecuter();
        visualizzatoreStruttureCategoria = new VisualizzatoreStruttureCategoria(categorieCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioRistorantiHandler.getPositionableOperations();
        positionableTransformer.addObserver(visualizzatoreStruttureCategoria);
        filtraButtonRistoranti.setOnClickListener(innerAsyncTaskExecuterRistoranti);

        FiltraggioAttrazioniHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterAttrazioni = this.filtraggioAttrazioniHandler.new InnerAsyncTaskExecuter();
        visualizzatoreStruttureCategoria = new VisualizzatoreStruttureCategoria( categorieCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioAttrazioniHandler.getPositionableOperations();
        positionableTransformer.addObserver(visualizzatoreStruttureCategoria);
        filtraButtonAttrazioni.setOnClickListener(innerAsyncTaskExecuterAttrazioni);

        FiltraggioFNDHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterFND = this.filtraggioFNDHandler.new InnerAsyncTaskExecuter();
        visualizzatoreStruttureCategoria = new VisualizzatoreStruttureCategoria(categorieCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioFNDHandler.getPositionableOperations();
        positionableTransformer.addObserver(visualizzatoreStruttureCategoria);
        filtraButtonFND.setOnClickListener(innerAsyncTaskExecuterFND);

        AutenticazioneUtenteHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterAutenticazione = this.autenticazioneUtenteHandler.new InnerAsyncTaskExecuter();
        FullscreenLoginDialog fullscreenLoginDialog = this.autenticazioneUtenteHandler.getLoginDialog();
        Button button = fullscreenLoginDialog.getRightButton();
        button.setOnClickListener(innerAsyncTaskExecuterAutenticazione);


        MultipleOnClickListener multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        ScritturaRecensioneHandler.LastClickedStrutturaViewSetter lastClickedStrutturaViewSetterScrRec = this.scritturaRecensioneHandler.new LastClickedStrutturaViewSetter();
        ScritturaRecensioneHandler.RecensioneDialogShowExecuter recensioneDialogShowExecuter = this.scritturaRecensioneHandler.new RecensioneDialogShowExecuter();
        multipleOnClickListener.addOnClickListeners(lastClickedStrutturaViewSetterScrRec, recensioneDialogShowExecuter);
        componentiFactory.setScritturaRecensioneStarter(multipleOnClickListener);
        FullscreenReviewDialog fullscreenReviewDialog = this.scritturaRecensioneHandler.getFullscreenReviewDialog();
        ScritturaRecensioneHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuter = this.scritturaRecensioneHandler.new InnerAsyncTaskExecuter();
        button = fullscreenReviewDialog.getRightButton();
        button.setOnClickListener(innerAsyncTaskExecuter);

        VisualizzazioneProfiloUtenteHandler.RecensioneViewSetter recensioneViewSetter = this.visualizzazioneProfiloUtenteHandler.new RecensioneViewSetter();
        VisualizzazioneProfiloUtenteHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterVisProfiloUtente = this.visualizzazioneProfiloUtenteHandler.new InnerAsyncTaskExecuter();
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(recensioneViewSetter, innerAsyncTaskExecuterVisProfiloUtente);
        componentiFactory.setVisualizzaProfiloUtenteStarter(multipleOnClickListener);

        // set OnClickListener a indietro button alberghi, ristoranti, attrazioni, fnd

        indietroButtonAlberghi.setOnClickListener(categorieCurrentPageSwapper);
        indietroButtonRistoranti.setOnClickListener(categorieCurrentPageSwapper);
        indietroButtonAttrazioni.setOnClickListener(categorieCurrentPageSwapper);
        indietroButtonFND.setOnClickListener(categorieCurrentPageSwapper);

        // creazione e settaggio swapper per categorie

        ViewPagerCurrentPageSwapper strutturaCurrentPageSwapper = new ViewPagerCurrentPageSwapper(viewPager, strutturaPrimoSet, strutturaSecondoSet);
        strutturaCurrentPageSwapper.setTabLayout(tabLayout);
        Vector<String> strutturaTabTexts = new Vector<>();
        strutturaTabTexts.add("Categorie");
        strutturaTabTexts.add("Struttura");
        strutturaTabTexts.add("Recensioni");
        strutturaTabTexts.add("Vicine");
        strutturaCurrentPageSwapper.setTabTextsList(strutturaTabTexts);

        FiltraggioRecensioniHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterRecensioni = this.filtraggioRecensioniHandler.new InnerAsyncTaskExecuter();
        VisualizzatoreRecensioni visualizzatoreRecensioni = new VisualizzatoreRecensioni(strutturaCurrentPageSwapper);
        recensioniObservable = (RecensioniObservable) this.filtraggioRecensioniHandler.getObjectsModelTransformable();
        recensioniObservable.addObserver(visualizzatoreRecensioni);
        filtraButtonRecensioni.setOnClickListener(innerAsyncTaskExecuterRecensioni);

        FiltraggioStruttureVicineHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterVicine = this.filtraggioStruttureVicineHandler.new InnerAsyncTaskExecuter();
        VisualizzatoreStruttureVicine visualizzatoreStruttureVicine = new VisualizzatoreStruttureVicine(strutturaCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioStruttureVicineHandler.getPositionableOperations();
        positionableTransformer.addObserver(visualizzatoreStruttureVicine);
        filtraButtonVicine.setOnClickListener(innerAsyncTaskExecuterVicine);

        indietroButtonRecensioni.setOnClickListener(strutturaCurrentPageSwapper);
        indietroButtonVicine.setOnClickListener(strutturaCurrentPageSwapper);

        // creazione e assegnazione OnClickListeners per il MultipleOnClickListener che gestisce il passaggio dalle pagine categoria alle pagine struttura

        ViewPagerPageSetSwitcher toStrutturaPageSwitcher = new ViewPagerPageSetSwitcher(viewPager, strutturaPrimoSet, 1);
        toStrutturaPageSwitcher.setTabLayout(tabLayout);
        toStrutturaPageSwitcher.setTabTextsList(strutturaTabTexts);
        ViewParentSaver viewParentSaver = new ViewParentSaver(2);
        ViewGroupSettedViewAdder viewGroupSettedViewAdder = viewParentSaver.getRestoreViewParentOnClickListener();
        ViewGroupViewRemover viewGroupViewRemover = new ViewGroupViewRemover(scrollView, 0);
        ViewPagerCurrentPageSwapperOnIdCondition viewPagerCurrentPageSwapperOnIdCondition = new ViewPagerCurrentPageSwapperOnIdCondition(viewPager, strutturaPrimoSet, strutturaSecondoSet, PG_RECENSIONI_DESCR, 2, 1);
        viewPagerCurrentPageSwapperOnIdCondition.setTabLayout(tabLayout);
        viewPagerCurrentPageSwapperOnIdCondition.setViewPager(viewPager);
        viewPagerCurrentPageSwapperOnIdCondition.setTabTextsList(strutturaTabTexts);
        ViewPagerCurrentPageSwapperOnIdCondition swapPagineVicineOnClickListener = new ViewPagerCurrentPageSwapperOnIdCondition(viewPager, strutturaPrimoSet, strutturaSecondoSet, PG_VICINE_DESCR, 3, 1);
        swapPagineVicineOnClickListener.setTabLayout(tabLayout);
        swapPagineVicineOnClickListener.setViewPager(viewPager);
        swapPagineVicineOnClickListener.setTabTextsList(strutturaTabTexts);
        FiltraggioRecensioniHandler.LastClickedStrutturaViewSetter lastClickedStrutturaViewSetter = this.filtraggioRecensioniHandler.new LastClickedStrutturaViewSetter();
        FiltraggioStruttureVicineHandler.LastClickedPositionableSetter lastClickedPositionableSetter = this.filtraggioStruttureVicineHandler.new LastClickedPositionableSetter();
        ViewGroupClickedViewAdder viewGroupClickedViewAdder = new ViewGroupClickedViewAdder(2, scrollView, 0);
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(lastClickedStrutturaViewSetter, lastClickedPositionableSetter, focusUpOnClickListener, viewGroupSettedViewAdder, viewParentSaver, viewGroupViewRemover, viewPagerCurrentPageSwapperOnIdCondition, swapPagineVicineOnClickListener, viewGroupClickedViewAdder, toStrutturaPageSwitcher, slidingUpPanelLayoutAnchorer);
        componentiFactory.setStrutturaListener(multipleOnClickListener);

        // creazione e assegnazione OnClickListeners per il MultipleOnClickListener che gestisce il passaggio dalle pagine struttura alle pagine categoria (this.fromStrutturaToCategoriaExecuter)


        ViewPagerPageSetSwitcher toCategoriePageSwitcher = new ViewPagerPageSetSwitcher(viewPager, categoriePrimoSet, 0);
        toCategoriePageSwitcher.setTabLayout(tabLayout);
        toCategoriePageSwitcher.setTabTextsList(categorieTabTexts);
        CategoriaViewRestorer categoriaViewRestorer = new CategoriaViewRestorer(lastClickedStrutturaViewSetter);
        MultipleOnClickListener fromStruttureToCategorieListener = new MultipleOnClickListener(new Vector<>());
        fromStruttureToCategorieListener.addOnClickListeners(viewGroupSettedViewAdder, toCategoriePageSwitcher, categoriaViewRestorer);

        // assegnazione on click listener ai button per tornare alle categorie

        toCategorieButtonStruttura.setOnClickListener(fromStruttureToCategorieListener);
        toCategorieButtonRecensioni.setOnClickListener(fromStruttureToCategorieListener);
        toCategorieButtonVicine.setOnClickListener(fromStruttureToCategorieListener);
        onClickExecuter.setOnClickListener(fromStruttureToCategorieListener);

        // set OnClickListener a resettaButtonAlberghi

        Vector<View> daResettare = componentiFactory.getDaResettare();

        positionableTransformer = (PositionableTransformer) this.filtraggioAlberghiHandler.getPositionableOperations();
        MarkersRemover markersRemover = new MarkersRemover(positionableTransformer.getMarkers());
        CompoundButtonsCheckSetter compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(this.filtraggioAlberghiHandler.getActualPositionCompoundButton(), this.filtraggioAlberghiHandler.getPositionCompoundButton());
        ResettablesResetExecuter resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioAlberghiHandler.getPositionDialog(), this.filtraggioAlberghiHandler.getPriceMultiSlider(), this.filtraggioAlberghiHandler.getNumberMultiSlider(), this.filtraggioAlberghiHandler.getDistanceMultiSlider(), this.filtraggioAlberghiHandler.getTipologieDialog(), this.filtraggioAlberghiHandler.getChoicesDialog(), this.filtraggioAlberghiHandler.getStelleDialog(), this.filtraggioAlberghiHandler.getCheckInDialog(), this.filtraggioAlberghiHandler.getCheckOutDialog(), this.filtraggioAlberghiHandler.getRatingMultiSlider(), this.filtraggioAlberghiHandler.getServiziRistorazioneDialog());
        ViewEnabler viewEnabler = new ViewEnabler(daResettare.get(0), false);
        ViewsVisibilitySetter viewsVisibilitySetter = new ViewsVisibilitySetter(new Vector<>(), false);
        viewsVisibilitySetter.addViews(daResettare.remove(0), daResettare.remove(0), this.filtraggioAlberghiHandler.getDistanceMultiSlider());
        SpinnerSelectionSetter spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioAlberghiHandler.getSortSpinner(), 0);
        NumberPickersValueSetter numberPickersValueSetter = new NumberPickersValueSetter(new Vector<>(), 0);
        numberPickersValueSetter.addNumberPickers(this.filtraggioAlberghiHandler.getNumeroAdultiNumberPicker(), this.filtraggioAlberghiHandler.getNumeroBambiniNumberPicker());
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(compoundButtonsCheckSetter, resettablesResetExecuter, viewEnabler, viewsVisibilitySetter, markersRemover, spinnerSelectionSetter, numberPickersValueSetter);
        resettaButtonAlberghi.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonRistoranti

        positionableTransformer = (PositionableTransformer) this.filtraggioRistorantiHandler.getPositionableOperations();
        markersRemover = new MarkersRemover(positionableTransformer.getMarkers());
        compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(this.filtraggioRistorantiHandler.getActualPositionCompoundButton(), this.filtraggioRistorantiHandler.getPositionCompoundButton());
        resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioRistorantiHandler.getPositionDialog(), this.filtraggioRistorantiHandler.getPriceMultiSlider(), this.filtraggioRistorantiHandler.getNumberMultiSlider(), this.filtraggioRistorantiHandler.getDistanceMultiSlider(), this.filtraggioRistorantiHandler.getRatingMultiSlider(), this.filtraggioRistorantiHandler.getChoicesDialog(), this.filtraggioRistorantiHandler.getCucinaDialog(), this.filtraggioRistorantiHandler.getDataOraDialog());
        viewEnabler = new ViewEnabler(daResettare.get(0), false);
        viewsVisibilitySetter = new ViewsVisibilitySetter(new Vector<>(), false);
        viewsVisibilitySetter.addViews(daResettare.remove(0), daResettare.remove(0), this.filtraggioRistorantiHandler.getDistanceMultiSlider());
        spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioRistorantiHandler.getSortSpinner(), 0);
        numberPickersValueSetter = new NumberPickersValueSetter(new Vector<>(), 0);
        numberPickersValueSetter.addNumberPickers(this.filtraggioRistorantiHandler.getNumeroAdultiNumberPicker(), this.filtraggioRistorantiHandler.getNumeroBambiniNumberPicker());
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(compoundButtonsCheckSetter, resettablesResetExecuter, viewEnabler, viewsVisibilitySetter, markersRemover, spinnerSelectionSetter, numberPickersValueSetter);
        resettaButtonRistoranti.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonAttrazioni

        positionableTransformer = (PositionableTransformer) this.filtraggioAttrazioniHandler.getPositionableOperations();
        markersRemover = new MarkersRemover(positionableTransformer.getMarkers());
        compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(this.filtraggioAttrazioniHandler.getActualPositionCompoundButton(), this.filtraggioAttrazioniHandler.getPositionCompoundButton());
        resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioAttrazioniHandler.getPositionDialog(), this.filtraggioAttrazioniHandler.getPriceMultiSlider(), this.filtraggioAttrazioniHandler.getNumberMultiSlider(), this.filtraggioAttrazioniHandler.getDistanceMultiSlider(), this.filtraggioAttrazioniHandler.getRatingMultiSlider(), this.filtraggioAttrazioniHandler.getChoicesDialog(), this.filtraggioAttrazioniHandler.getGuidaDialog(), this.filtraggioAttrazioniHandler.getDataOraDialog(), this.filtraggioAttrazioniHandler.getTipologieDialog());
        viewEnabler = new ViewEnabler(daResettare.get(0), false);
        viewsVisibilitySetter = new ViewsVisibilitySetter(new Vector<>(), false);
        viewsVisibilitySetter.addViews(daResettare.remove(0), daResettare.remove(0), this.filtraggioAttrazioniHandler.getDistanceMultiSlider());
        spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioAttrazioniHandler.getSortSpinner(), 0);
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(compoundButtonsCheckSetter, resettablesResetExecuter, viewEnabler, viewsVisibilitySetter, markersRemover, spinnerSelectionSetter);
        resettaButtonAttrazioni.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonFND

        positionableTransformer = (PositionableTransformer) this.filtraggioFNDHandler.getPositionableOperations();
        markersRemover = new MarkersRemover(positionableTransformer.getMarkers());
        compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(this.filtraggioFNDHandler.getActualPositionCompoundButton(), this.filtraggioFNDHandler.getPositionCompoundButton());
        resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioFNDHandler.getPositionDialog(), this.filtraggioFNDHandler.getPriceMultiSlider(), this.filtraggioFNDHandler.getNumberMultiSlider(), this.filtraggioFNDHandler.getDistanceMultiSlider(), this.filtraggioFNDHandler.getRatingMultiSlider(), this.filtraggioFNDHandler.getChoicesDialog(), this.filtraggioFNDHandler.getTipologieDialog(), this.filtraggioFNDHandler.getDataOraDialog());
        viewEnabler = new ViewEnabler(daResettare.get(0), false);
        viewsVisibilitySetter = new ViewsVisibilitySetter(new Vector<>(), false);
        viewsVisibilitySetter.addViews(daResettare.remove(0), daResettare.remove(0), this.filtraggioFNDHandler.getDistanceMultiSlider());
        spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioFNDHandler.getSortSpinner(), 0);
        numberPickersValueSetter = new NumberPickersValueSetter(new Vector<>(), 0);
        numberPickersValueSetter.addNumberPickers(this.filtraggioFNDHandler.getNumeroAdultiNumberPicker(), this.filtraggioFNDHandler.getNumeroBambiniNumberPicker());
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(compoundButtonsCheckSetter, resettablesResetExecuter, viewEnabler, viewsVisibilitySetter, markersRemover, spinnerSelectionSetter, numberPickersValueSetter);
        resettaButtonFND.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonRecensioni

        resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioRecensioniHandler.getVotiDialog(), this.filtraggioRecensioniHandler.getDaDataDialog(), this.filtraggioRecensioniHandler.getADataDialog(), this.filtraggioRecensioniHandler.getNumeroMassimoMultiSlider());
        spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioRecensioniHandler.getOrdinaSpinner(), 0);
        CompoundButtonCheckSetter compoundButtonCheckSetter = new CompoundButtonCheckSetter(this.filtraggioRecensioniHandler.getDecrescenteCheckBox(), false);
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(resettablesResetExecuter, spinnerSelectionSetter, compoundButtonCheckSetter);
        resettaButtonRecensioni.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonVicine

        compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
        compoundButtonsCheckSetter.addCompoundButtons(this.filtraggioStruttureVicineHandler.getAlberghiCheckBox(), this.filtraggioStruttureVicineHandler.getRistorantiCheckBox(), this.filtraggioStruttureVicineHandler.getAttrazioniCheckBox(), this.filtraggioStruttureVicineHandler.getFndCheckBox());
        resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioStruttureVicineHandler.getNumberMultiSlider(), this.filtraggioStruttureVicineHandler.getDistanceMultiSlider());
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(resettablesResetExecuter, compoundButtonsCheckSetter);
        resettaButtonVicine.setOnClickListener(multipleOnClickListener);

        // settaggio pannello "slidibile"

        LinearLayout panelLayout = this.getPanelLayout();
        panelLayout.addView(tabLayout);
        panelLayout.addView(viewPager);

        this.cleanMapStyleSetter.addObserver(componentiFactory);

        this.autenticazioneUtenteHandler.setLastLogState();

    }

    public SwipeDisabledViewPager getViewPager() {
        LinearLayout panelLayout = this.getPanelLayout();
        SwipeDisabledViewPager viewPager = (SwipeDisabledViewPager) panelLayout.getChildAt(1);
        return viewPager;
    }

    public ListView getAlberghiListView() {
        ViewPager viewPager = this.getViewPager();
        LinearLayout page = (LinearLayout) viewPager.getChildAt(0);
        LinearLayout simpleLayout = (LinearLayout) page.getChildAt(0);
        ListView categoriaListView = (ListView) simpleLayout.getChildAt(0);
        return categoriaListView;
    }

    public ListView getRistorantiListView() {
        ViewPager viewPager = this.getViewPager();
        LinearLayout page = (LinearLayout) viewPager.getChildAt(1);
        LinearLayout simpleLayout = (LinearLayout) page.getChildAt(0);
        ListView categoriaListView = (ListView) simpleLayout.getChildAt(0);
        return categoriaListView;
    }

    public ListView getAttrazioniListView() {
        ViewPager viewPager = this.getViewPager();
        LinearLayout page = (LinearLayout) viewPager.getChildAt(2);
        LinearLayout simpleLayout = (LinearLayout) page.getChildAt(0);
        ListView categoriaListView = (ListView) simpleLayout.getChildAt(0);
        return categoriaListView;
    }

    public ListView getFNDListView() {
        ViewPager viewPager = this.getViewPager();
        LinearLayout page = (LinearLayout) viewPager.getChildAt(3);
        LinearLayout simpleLayout = (LinearLayout) page.getChildAt(0);
        ListView categoriaListView = (ListView) simpleLayout.getChildAt(0);
        return categoriaListView;
    }

    protected void adatta(ListView listView) {
        SlidingUpPanelLayout slidingUpPanelLayout = this.getSlidingUpPanelLayout();
        onscrolllisteners.SlidingUpPanelLayoutScrollableViewSetter slidingUpPanelLayoutScrollableViewSetter = new onscrolllisteners.SlidingUpPanelLayoutScrollableViewSetter(slidingUpPanelLayout);
        listView.setOnScrollListener(slidingUpPanelLayoutScrollableViewSetter);
    }

    private MappaActivity getOuter() {
        return this;
    }

    protected class VisualizzatoreStruttureCategoria implements Observer {

        private ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper;

        public VisualizzatoreStruttureCategoria(ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper) {
            this.viewPagerCurrentPageSwapper = viewPagerCurrentPageSwapper;
        }

        @Override
        public void update(Observable observable, Object object) {
            PositionableTransformer positionableTransformer = (PositionableTransformer) observable;
            LinkedList<Positionable> positionables = (LinkedList) positionableTransformer.getResults();
            if (positionables.size() > 0) {
                MappaActivity mappaActivity = getOuter();
                ListView listView = new ListView(mappaActivity);
                int id = 0;
                do {
                    Positionable positionable = positionables.removeFirst();
                    AbstractStrutturaView strutturaView = (AbstractStrutturaView) positionable;
                    strutturaView.setId(id);
                    LinearLayout container = new LinearLayout(mappaActivity);
                    container.setOrientation(LinearLayout.VERTICAL);
                    container.addView(strutturaView);
                    listView.addHeaderView(container);
                    id++;
                } while (positionables.size() > 0);
                LinearLayout linearLayout = (LinearLayout) this.viewPagerCurrentPageSwapper.getNextCurrentPage();
                linearLayout = (LinearLayout) linearLayout.getChildAt(0);
                if (linearLayout.getChildCount() == 1) {
                    linearLayout.removeViewAt(0);
                }
                AdapterUtility.adapt(listView, mappaActivity);
                adatta(listView);
                linearLayout.addView(listView);
                this.viewPagerCurrentPageSwapper.onClick(null);
            }
        }

    }

    protected class VisualizzatoreRecensioni implements Observer {

        private ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper;

        public VisualizzatoreRecensioni(ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper) {
            this.viewPagerCurrentPageSwapper = viewPagerCurrentPageSwapper;
        }

        @Override
        public void update(Observable observable, Object object) {
            RecensioniObservable recensioniObservable = (RecensioniObservable) observable;
            LinkedList<RecensioneView> recensioneViews = (LinkedList) recensioniObservable.getRecensioneViews();
            if (recensioneViews.size() > 0) {
                MappaActivity mappaActivity = getOuter();
                ListView listView = new ListView(mappaActivity);
                do {
                    RecensioneView recensioneView = recensioneViews.removeFirst();
                    LinearLayout container = new LinearLayout(mappaActivity);
                    container.setOrientation(LinearLayout.VERTICAL);
                    container.addView(recensioneView);
                    listView.addHeaderView(container);
                } while (recensioneViews.size() > 0);
                LinearLayout linearLayout = (LinearLayout) this.viewPagerCurrentPageSwapper.getNextCurrentPage();
                linearLayout = (LinearLayout) linearLayout.getChildAt(0);
                if (linearLayout.getChildCount() == 1) {
                    linearLayout.removeViewAt(0);
                }
                AdapterUtility.adapt(listView, mappaActivity);
                adatta(listView);
                linearLayout.addView(listView);
                this.viewPagerCurrentPageSwapper.onClick(null);
            }
        }
    }

    protected class VisualizzatoreStruttureVicine implements Observer {

        private ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper;

        public VisualizzatoreStruttureVicine(ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper) {
            this.viewPagerCurrentPageSwapper = viewPagerCurrentPageSwapper;
        }

        @Override
        public void update(Observable observable, Object object) {
            PositionableTransformer positionableTransformer = (PositionableTransformer) observable;
            LinkedList<Positionable> positionables = (LinkedList) positionableTransformer.getResults();
            positionableTransformer.resetMarkers();
            if (positionables.size() > 0) {
                MappaActivity mappaActivity = getOuter();
                ListView listView = new ListView(mappaActivity);
                do {
                    Positionable positionable = positionables.removeFirst();
                    AbstractStrutturaView strutturaView = (AbstractStrutturaView) positionable;
                    strutturaView.removeViewAt(2);
                    LinearLayout container = new LinearLayout(mappaActivity);
                    container.setOrientation(LinearLayout.VERTICAL);
                    container.addView(strutturaView);
                    listView.addHeaderView(container);
                } while (positionables.size() > 0);
                LinearLayout linearLayout = (LinearLayout) this.viewPagerCurrentPageSwapper.getNextCurrentPage();
                linearLayout = (LinearLayout) linearLayout.getChildAt(0);
                if (linearLayout.getChildCount() == 1) {
                    linearLayout.removeViewAt(0);
                }
                AdapterUtility.adapt(listView, mappaActivity);
                adatta(listView);
                linearLayout.addView(listView);
                this.viewPagerCurrentPageSwapper.onClick(null);
            }
        }
    }


    protected class CategoriaViewRestorer implements View.OnClickListener {

        private LastClickedPositionableSaver lastClickedPositionableSaver;

        public CategoriaViewRestorer(LastClickedPositionableSaver lastClickedPositionableSaver) {
            this.lastClickedPositionableSaver = lastClickedPositionableSaver;
        }

        @Override
        public void onClick(View view) {
            ViewPager viewPager = getViewPager();
            AbstractStrutturaView ultimaStrutturaVisitata = (AbstractStrutturaView) this.lastClickedPositionableSaver.getPositionable();
            ListView categoriaListView = null;
            if (ultimaStrutturaVisitata instanceof AlbergoView) {
                viewPager.setCurrentItem(0);
                categoriaListView = getAlberghiListView();
            } else {
                if (ultimaStrutturaVisitata instanceof RistoranteView) {
                    viewPager.setCurrentItem(1);
                    categoriaListView = getRistorantiListView();
                } else {
                    if (ultimaStrutturaVisitata instanceof AttrazioneView) {
                        viewPager.setCurrentItem(2);
                        categoriaListView = getAttrazioniListView();
                    } else {
                        if (ultimaStrutturaVisitata instanceof FNDView) {
                            viewPager.setCurrentItem(3);
                            categoriaListView = getFNDListView();
                        }
                    }
                }
            }
            int id = ultimaStrutturaVisitata.getId();
            AdapterUtility.adapt(categoriaListView, getOuter());
            categoriaListView.setSelection(id);
        }

    }
    
}

