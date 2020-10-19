package cvapp.activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import components.AbstractQueriesInterface;
import cvapp.adapters.SuggerimentiStrutturaSpecificaAdapter;
import cvapp.components.InterfacciaQuery;
import cvapp.handlers.AutenticazioneUtenteHandler;
import cvapp.components.CVComponentiFactory;
import cvapp.handlers.DisabilitazioneUtenteHandler;
import cvapp.handlers.FiltraggioRecensioniHandler;
import cvapp.handlers.FiltraggioStrutturaSpecificaHandler;
import cvapp.handlers.FiltraggioStruttureVicineHandler;
import cvapp.components.InterfacciaBackEndStub;
import cvapp.observables.ProfiloUtenteObservable;
import cvapp.R;
import cvapp.observables.RecensioniObservable;
import cvapp.handlers.ScritturaRecensioneHandler;
import cvapp.handlers.VisualizzazioneProfiloUtenteHandler;
import ontabselectedlisteners.SlidingUpPanelLayoutExpander;
import utilities.AdapterUtility;
import adapters.SimpleViewPagerAdapter;
import positionablecomponents.Positionable;
import positionablecomponents.PositionableTransformer;
import cvapp.views.RecensioneView;
import cvapp.views.AbstractStrutturaView;
import activities.BackgroundMapViewActivity;
import onmapreadycallbacks.CleanMapStyleSetter;
import graphiccomponents.FullscreenInsertDialog;
import graphiccomponents.FullscreenLoginDialog;
import graphiccomponents.FullscreenReviewDialog;
import graphiccomponents.LinearLayouts;
import graphiccomponents.ResettablesResetExecuter;
import graphiccomponents.SwipeDisabledViewPager;
import onclicklisteners.CompoundButtonCheckSetter;
import onclicklisteners.CompoundButtonsCheckSetter;
import onclicklisteners.MultipleOnClickListener;
import onclicklisteners.ScrollViewFocusUpExecuter;
import onclicklisteners.SlidingUpPanelLayoutAnchorer;
import onclicklisteners.SpinnerSelectionSetter;
import onclicklisteners.viewpagersetmodifiers.ViewPagerCurrentPageSwapper;
import onclicklisteners.viewpagersetmodifiers.ViewPagerCurrentPageSwapperOnIdCondition;
import onscrollchangelisteners.SlidingUpPanelLayoutScrollableViewSetter;
import ontabselectedlisteners.MultipleOnTabSelectedListener;
import ontabselectedlisteners.OnClickExecuter;
import utilities.PositionUtility;

public class RicercaActivity extends BackgroundMapViewActivity {

    private final static String MAP_VIEW_BUNDLE_KEY = "AIzaSyAWkn7A7v0NzrPhUS46kK8ohkmthMAsp6Y";
    public final static int PG_STRUTTURA = 8;
    public final static int PG_RECENSIONI_FILTRI = 9;
    public final static int PG_VICINE_FILTRI = 10;
    public final static int PG_RECENSIONI_DESCR = 11;
    public final static int PG_VICINE_DESCR = 12;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_ricerca_activity, menu);
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
            case R.id.search:
                FullscreenInsertDialog searchDialog = this.filtraggioStrutturaSpecificaHandler.getRicercaSpecificaDialog();
                AbstractQueriesInterface queriesInterface = this.filtraggioStrutturaSpecificaHandler.getQueriesInterface();
                AbstractList<String> suggerimenti = queriesInterface.getSuggestions();
                SuggerimentiStrutturaSpecificaAdapter suggerimentiStrutturaSpecificaAdapter = new SuggerimentiStrutturaSpecificaAdapter(this, android.R.layout.simple_dropdown_item_1line, suggerimenti, 20, null);
                AutoCompleteTextView autocompleteTextView = searchDialog.getAutoCompleteTextView();
                autocompleteTextView.setAdapter(suggerimentiStrutturaSpecificaAdapter);
                this.filtraggioStrutturaSpecificaHandler.mostraRicercaSpecificaDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AutenticazioneUtenteHandler autenticazioneUtenteHandler;
    private DisabilitazioneUtenteHandler disabilitazioneUtenteHandler;

    private ScritturaRecensioneHandler scritturaRecensioneHandler;
    private FiltraggioRecensioniHandler filtraggioRecensioniHandler;
    private VisualizzazioneProfiloUtenteHandler visualizzazioneProfiloUtenteHandler;
    private FiltraggioStruttureVicineHandler filtraggioStruttureVicineHandler;
    private FiltraggioStrutturaSpecificaHandler filtraggioStrutturaSpecificaHandler;

    private PositionUtility positionUtility;
    private CleanMapStyleSetter cleanMapStyleSetter;

    @Override
    protected void onCreate(Bundle stateBundle) {
        super.onCreate(stateBundle);


        this.positionUtility = new PositionUtility();
        PositionUtility.isAccessFineLocationGranted(this);
        this.positionUtility.assignLocationRequest(this);
        this.positionUtility.createGeocoder(this, Locale.ITALIAN);

        this.setMapViewBundleKey(RicercaActivity.MAP_VIEW_BUNDLE_KEY);
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
        this.filtraggioStrutturaSpecificaHandler = new FiltraggioStrutturaSpecificaHandler(this, interfacciaQuery, componentiFactory, positionableTransformer);
        this.cleanMapStyleSetter.addObserver(positionableTransformer);
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
        actionBar.setTitle("Ricerca Struttura");
        actionBar.setBackgroundDrawable(colorDrawable);

        int heightInPixel = displayMetrics.heightPixels;
        LayoutParams layoutParams = new LayoutParams(widthInPixel, (int) (heightInPixel * 0.0625));
        int minus = actionBarHeight + layoutParams.height;

        //settaggio view pager struttura primo set di pagine

        Vector<View> strutturaPrimoSet = new Vector<>();

        //creazione e aggiunta pagina struttura
        Button fakeButtonZero = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonZero);
        Button fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        Button fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        LinearLayout paginaStruttura = LinearLayouts.buildScrollableLayout(this, fakeButtonZero, fakeButtonOne, fakeButtonTwo, minus);
        paginaStruttura.setId(PG_STRUTTURA);
        strutturaPrimoSet.add(paginaStruttura);

        //creazione e aggiunta pagina filtri recensioni
        fakeButtonZero = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonZero);
        Button resettaButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonRecensioni, "Resetta");
        Button filtraButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonRecensioni, "Filtra");
        LinearLayout paginaRecensioniFiltri = LinearLayouts.buildLayout(this, fakeButtonZero, resettaButtonRecensioni, filtraButtonRecensioni, minus);
        LinearLayout linearLayout = (LinearLayout) paginaRecensioniFiltri.getChildAt(0);
        LinearLayout votiRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getVotiDialog(), "Seleziona", "Voti");
        LinearLayout daDataRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getDaDataDialog(), "Seleziona", "Da data");
        LinearLayout aDataRecensioniLayout = componentiFactory.creaLayoutTipoUno(widthInPixel, this.filtraggioRecensioniHandler.getADataDialog(), "Seleziona", "A data");
        LinearLayout numeroMassimoRecensioniLayout = componentiFactory.creaLayoutTipoTre(widthInPixel, heightInPixel, this.filtraggioRecensioniHandler.getNumeroMassimoMultiSlider(), "Numero massimo");
        LinearLayout ordinaRecensioniLayout = componentiFactory.creaLayoutTipoQuattro(widthInPixel, heightInPixel, this.filtraggioRecensioniHandler.getOrdinaSpinner(), this.filtraggioRecensioniHandler.getDecrescenteCheckBox());
        LinearLayout linearLayoutCodaRecensioni = new LinearLayout(this);
        linearLayoutCodaRecensioni.setMinimumHeight((int)(Math.ceil(heightInPixel * 0.15)));
        ListView listView = new ListView(this);
        listView.setDivider(null);
        AdapterUtility.adapt(listView, this, votiRecensioniLayout, daDataRecensioniLayout, aDataRecensioniLayout, numeroMassimoRecensioniLayout, ordinaRecensioniLayout, linearLayoutCodaRecensioni);
        this.adatta(listView);
        linearLayout.addView(listView);
        paginaRecensioniFiltri.setId(PG_RECENSIONI_FILTRI);
        strutturaPrimoSet.add(paginaRecensioniFiltri);

        //creazione e aggiunta pagina filtri vicine
        fakeButtonZero = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonZero);
        Button resettaButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(resettaButtonVicine, "Resetta");
        Button filtraButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(filtraButtonVicine, "Filtra");
        LinearLayout paginaVicineFiltri = LinearLayouts.buildLayout(this, fakeButtonZero, resettaButtonVicine, filtraButtonVicine, minus);
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

        strutturaSecondoSet.add(paginaStruttura);

        // creazione e aggiunta pagina descrittori recensioni

        Button indietroButtonRecensioni = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonRecensioni, "Indietro");
        fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        LinearLayout paginaRecensioniDescrittori = LinearLayouts.buildLayout(this, indietroButtonRecensioni, fakeButtonOne, fakeButtonTwo, minus);
        paginaRecensioniDescrittori.setId(PG_RECENSIONI_DESCR);
        strutturaSecondoSet.add(paginaRecensioniDescrittori);

        //creazione e aggiunta pagina descrittori vicine

        Button indietroButtonVicine = new Button(this);
        componentiFactory.applicaButtonStyle(indietroButtonVicine, "Indietro");
        fakeButtonOne = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonOne);
        fakeButtonTwo = new Button(this);
        componentiFactory.applicaFakeButtonStyle(fakeButtonTwo);
        LinearLayout paginaVicineDescrittori = LinearLayouts.buildLayout(this, indietroButtonVicine, fakeButtonOne, fakeButtonTwo, minus);
        paginaVicineDescrittori.setId(PG_VICINE_DESCR);
        strutturaSecondoSet.add(paginaVicineDescrittori);

        //creazione e settaggio view pager con primo set categorie

        SwipeDisabledViewPager viewPager = (SwipeDisabledViewPager) componentiFactory.buildViewGroup();
        viewPager.setOffscreenPageLimit(2);

        SimpleViewPagerAdapter simpleViewPagerAdapter = new SimpleViewPagerAdapter(strutturaPrimoSet);
        viewPager.setAdapter(simpleViewPagerAdapter);

        TabLayout tabLayout = new TabLayout(this);
        tabLayout.setLayoutParams(layoutParams);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(CVComponentiFactory.COLORE_PRINCIPALE, CVComponentiFactory.COLORE_PRINCIPALE);
        Tab tab = tabLayout.getTabAt(0);
        tab.setText("Struttura");
        tab = tabLayout.getTabAt(1);
        tab.setText("Recensioni");
        tab = tabLayout.getTabAt(2);
        tab.setText("Vicine");
        Vector<String> strutturaTabTexts = new Vector<>();
        strutturaTabTexts.add("Struttura");
        strutturaTabTexts.add("Recensioni");
        strutturaTabTexts.add("Vicine");

        SlidingUpPanelLayout slidingUpPanelLayout = this.getSlidingUpPanelLayout();

        // set OnTabSelectedListener al tab layout setOnScrollChangeListener alla scroll view della pagina struttura

        ScrollView scrollView = (ScrollView) paginaStruttura.getChildAt(0);

        float anchorPoint = ((AbstractStrutturaView.ALTEZZA_ANCHOR) / ((float) displayMetrics.heightPixels));
        SlidingUpPanelLayoutAnchorer slidingUpPanelLayoutAnchorer = new SlidingUpPanelLayoutAnchorer(slidingUpPanelLayout, anchorPoint);
        SlidingUpPanelLayoutExpander slidingUpPanelLayoutExpander = new SlidingUpPanelLayoutExpander(slidingUpPanelLayout);
        SlidingUpPanelLayoutScrollableViewSetter slidingUpPanelLayoutScrollableViewSetter = new SlidingUpPanelLayoutScrollableViewSetter(slidingUpPanelLayout);
        OnClickExecuter onClickExecuter = new OnClickExecuter(null, "Categorie");
        ontabselectedlisteners.ScrollViewFocusUpExecuter scrollViewFocusUpExecuter = new ontabselectedlisteners.ScrollViewFocusUpExecuter(scrollView);
        MultipleOnTabSelectedListener multipleOnTabSelectedListener = new MultipleOnTabSelectedListener(new Vector<>());
        multipleOnTabSelectedListener.addOnTabSelectedListeners(slidingUpPanelLayoutExpander, scrollViewFocusUpExecuter, onClickExecuter);
        tabLayout.addOnTabSelectedListener(multipleOnTabSelectedListener);

        ScrollViewFocusUpExecuter focusUpOnClickListener = new ScrollViewFocusUpExecuter(scrollView);
        scrollView.setOnScrollChangeListener(slidingUpPanelLayoutScrollableViewSetter);

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

        ViewPagerCurrentPageSwapper strutturaCurrentPageSwapper = new ViewPagerCurrentPageSwapper(viewPager, strutturaPrimoSet, strutturaSecondoSet);
        strutturaCurrentPageSwapper.setTabLayout(tabLayout);
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

        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(focusUpOnClickListener, slidingUpPanelLayoutAnchorer);
        componentiFactory.setStrutturaListener(multipleOnClickListener);

        ViewPagerCurrentPageSwapperOnIdCondition viewPagerCurrentPageSwapperOnIdCondition = new ViewPagerCurrentPageSwapperOnIdCondition(viewPager, strutturaPrimoSet, strutturaSecondoSet, PG_RECENSIONI_DESCR, 2, 0);
        viewPagerCurrentPageSwapperOnIdCondition.setTabLayout(tabLayout);
        viewPagerCurrentPageSwapperOnIdCondition.setViewPager(viewPager);
        viewPagerCurrentPageSwapperOnIdCondition.setTabTextsList(strutturaTabTexts);
        ViewPagerCurrentPageSwapperOnIdCondition swapPagineVicineOnClickListener = new ViewPagerCurrentPageSwapperOnIdCondition(viewPager, strutturaPrimoSet, strutturaSecondoSet, PG_VICINE_DESCR, 3, 0);
        swapPagineVicineOnClickListener.setTabLayout(tabLayout);
        swapPagineVicineOnClickListener.setViewPager(viewPager);
        swapPagineVicineOnClickListener.setTabTextsList(strutturaTabTexts);

        FiltraggioStrutturaSpecificaHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuterSpecifica = this.filtraggioStrutturaSpecificaHandler.new InnerAsyncTaskExecuter();
        VisualizzatoreStrutturaSpecifica visualizzatoreStrutturaSpecifica = new VisualizzatoreStrutturaSpecifica(strutturaCurrentPageSwapper);
        positionableTransformer = (PositionableTransformer) this.filtraggioStrutturaSpecificaHandler.getPositionableTransformer();
        positionableTransformer.addObserver(visualizzatoreStrutturaSpecifica);
        FullscreenInsertDialog fullscreenInsertDialog = this.filtraggioStrutturaSpecificaHandler.getRicercaSpecificaDialog();
        button = fullscreenInsertDialog.getRightButton();
        button.setOnClickListener(innerAsyncTaskExecuterSpecifica);

        // set OnClickListener a resettaButtonRecensioni

        ResettablesResetExecuter resettablesResetExecuter = new ResettablesResetExecuter(new Vector<>());
        resettablesResetExecuter.addResettables(this.filtraggioRecensioniHandler.getVotiDialog(), this.filtraggioRecensioniHandler.getDaDataDialog(), this.filtraggioRecensioniHandler.getADataDialog(), this.filtraggioRecensioniHandler.getNumeroMassimoMultiSlider());
        SpinnerSelectionSetter spinnerSelectionSetter = new SpinnerSelectionSetter(this.filtraggioRecensioniHandler.getOrdinaSpinner(), 0);
        CompoundButtonCheckSetter compoundButtonCheckSetter = new CompoundButtonCheckSetter(this.filtraggioRecensioniHandler.getDecrescenteCheckBox(), false);
        multipleOnClickListener = new MultipleOnClickListener(new Vector<>());
        multipleOnClickListener.addOnClickListeners(resettablesResetExecuter, spinnerSelectionSetter, compoundButtonCheckSetter);
        resettaButtonRecensioni.setOnClickListener(multipleOnClickListener);

        // set OnClickListener a resettaButtonVicine

        CompoundButtonsCheckSetter compoundButtonsCheckSetter = new CompoundButtonsCheckSetter(new Vector<>(), false);
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

        slidingUpPanelLayout.setEnabled(false);

        this.cleanMapStyleSetter.addObserver(componentiFactory);

        this.autenticazioneUtenteHandler.setLastLogState();

    }

    public SwipeDisabledViewPager getViewPager() {
        LinearLayout panelLayout = this.getPanelLayout();
        SwipeDisabledViewPager viewPager = (SwipeDisabledViewPager) panelLayout.getChildAt(1);
        return viewPager;
    }

    protected void adatta(ListView listView) {
        SlidingUpPanelLayout slidingUpPanelLayout = this.getSlidingUpPanelLayout();
        onscrolllisteners.SlidingUpPanelLayoutScrollableViewSetter slidingUpPanelLayoutScrollableViewSetter = new onscrolllisteners.SlidingUpPanelLayoutScrollableViewSetter(slidingUpPanelLayout);
        listView.setOnScrollListener(slidingUpPanelLayoutScrollableViewSetter);
    }

    private RicercaActivity getOuter() {
        return this;
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
                RicercaActivity mappaActivity = getOuter();
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

        protected VisualizzatoreStruttureVicine(ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper) {
            this.viewPagerCurrentPageSwapper = viewPagerCurrentPageSwapper;
        }

        @Override
        public void update(Observable observable, Object object) {
            PositionableTransformer positionableTransformer = (PositionableTransformer) observable;
            LinkedList<Positionable> positionables = (LinkedList) positionableTransformer.getResults();
            positionableTransformer.resetMarkers();
            if (positionables.size() > 0) {
                RicercaActivity mappaActivity = getOuter();
                ListView listView = new ListView(mappaActivity);
                do {
                    Positionable googleMapPositionable = positionables.removeFirst();
                    AbstractStrutturaView strutturaView = (AbstractStrutturaView) googleMapPositionable;
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

    protected class VisualizzatoreStrutturaSpecifica implements Observer {

        private ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper;

        public VisualizzatoreStrutturaSpecifica(ViewPagerCurrentPageSwapper viewPagerCurrentPageSwapper) {
            this.viewPagerCurrentPageSwapper = viewPagerCurrentPageSwapper;
        }

        @Override
        public void update(Observable observable, Object object) {

            getSlidingUpPanelLayout().setEnabled(true);
            SwipeDisabledViewPager swipeDisabledViewPager = getViewPager();
            swipeDisabledViewPager.setEnabled(true);

            PositionableTransformer positionableTransformer = (PositionableTransformer) observable;
            LinkedList<Positionable> positionables = (LinkedList) positionableTransformer.getResults();
            if (positionables.size() == 1) {

                Positionable googleMapPositionable = positionables.removeFirst();
                AbstractStrutturaView strutturaView = (AbstractStrutturaView) googleMapPositionable;
                LinearLayout container = new LinearLayout(getOuter());
                container.setOrientation(LinearLayout.VERTICAL);
                container.addView(strutturaView);

                filtraggioRecensioniHandler.setStrutturaView(strutturaView);
                filtraggioStruttureVicineHandler.setPositionable(strutturaView);

                LinearLayout linearLayout = (LinearLayout) swipeDisabledViewPager.getChildAt(1);
                if (linearLayout.getId() == RicercaActivity.PG_RECENSIONI_DESCR) {
                    swipeDisabledViewPager.setCurrentItem(1);
                    this.viewPagerCurrentPageSwapper.onClick(null);
                }

                linearLayout = (LinearLayout) swipeDisabledViewPager.getChildAt(2);
                if (linearLayout.getId() == RicercaActivity.PG_VICINE_DESCR) {
                    swipeDisabledViewPager.setCurrentItem(2);
                    this.viewPagerCurrentPageSwapper.onClick(null);
                }

                swipeDisabledViewPager.setCurrentItem(0);

                linearLayout = (LinearLayout) this.viewPagerCurrentPageSwapper.getNextCurrentPage();
                ScrollView scrollView = (ScrollView) linearLayout.getChildAt(0);
                if (scrollView.getChildCount() > 0) {
                    scrollView.removeViewAt(0);
                }
                scrollView.addView(container);
                this.viewPagerCurrentPageSwapper.onClick(null);

            }
        }
    }

}

