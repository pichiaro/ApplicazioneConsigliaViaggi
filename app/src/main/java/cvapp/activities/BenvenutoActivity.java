package cvapp.activities;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Observable;
import java.util.Observer;


import activities.StartActivityOnNetworkEnabledExecuter;
import cvapp.components.InterfacciaQuery;
import cvapp.handlers.AutenticazioneUtenteHandler;
import cvapp.components.CVComponentiFactory;
import cvapp.handlers.DisabilitazioneUtenteHandler;
import cvapp.components.InterfacciaBackEndStub;
import cvapp.R;
import cvapp.handlers.RecuperoHandler;
import cvapp.views.RegistrazioneDialog;
import cvapp.handlers.RegistrazioneHandler;
import cvapp.handlers.RegistrazioneSender;

import utilities.NetworkUtility;


public class BenvenutoActivity extends AppCompatActivity {

    private LinearLayout subLoginLayout;
    private LinearLayout subLogoutLayout;
    private ScrollView contentView;
    private AutenticazioneUtenteHandler autenticazioneUtenteHandler;
    private DisabilitazioneUtenteHandler disabilitazioneUtenteHandler;
    private ProgressDialog progressDialog;
    private RegistrazioneHandler registrazioneHandler;
    private RecuperoHandler recuperoHandler;
    private int verde = Color.rgb(18,155,162);
    private final static int LARGHEZZA_BUTTON = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.30);


    @Override
    protected void onCreate(Bundle stateBundle) {
        super.onCreate(stateBundle);
        Builder builder = new Builder(this);

        if (!NetworkUtility.isNetworkEnabled(this)) {
            builder.setTitle("Errore");
            builder.setMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\nChiudi e riavvia l' applicazione.\n");
            builder.setCancelable(false);
            builder.setPositiveButton("",null);
            builder.create().show();
        }

        CVComponentiFactory cvComponentiFactory = new CVComponentiFactory(this);
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        InterfacciaQuery interfacciaQuery = new InterfacciaBackEndStub(this, "backendconfig.properties");
        this.autenticazioneUtenteHandler = new AutenticazioneUtenteHandler(this, interfacciaQuery, cvComponentiFactory, null);
        this.disabilitazioneUtenteHandler = new DisabilitazioneUtenteHandler(this, cvComponentiFactory, this.autenticazioneUtenteHandler, null);

        this.contentView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.contentView.addView(linearLayout);

        this.progressDialog = (ProgressDialog) cvComponentiFactory.buildWaitingDialog();
        StartActivityOnNetworkEnabledExecuter startActivityOnConditionExecuter = new StartActivityOnNetworkEnabledExecuter(this.progressDialog, this, RicercaActivity.class, null, cvComponentiFactory);
        startActivityOnConditionExecuter.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.progressDialog.setMessage("Dammi qualche attimo, sto caricando la mappa...");

        ActionBar actionBar = this.getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(CVComponentiFactory.COLORE_PRINCIPALE);
        actionBar.setTitle("Consiglia Viaggi 2019 CV'19");
        actionBar.setBackgroundDrawable(colorDrawable);

        AutenticazioneUtenteHandler.InnerAsyncTaskExecuter autenticazioneInnerAsyncTaskExecuter = this.autenticazioneUtenteHandler.new InnerAsyncTaskExecuter();
        AutenticazioneUtenteHandler.LoginDialogShowExecuter loginDialogShowExecuter = this.autenticazioneUtenteHandler.new LoginDialogShowExecuter();
        Button button = this.autenticazioneUtenteHandler.getLoginDialog().getRightButton();
        button.setOnClickListener(autenticazioneInnerAsyncTaskExecuter);
        VisualizzatoreLoginObserver visualizzatoreLoginObserver = new VisualizzatoreLoginObserver();
        VisualizzatoreLoginObservable visualizzatoreLoginObservable = new VisualizzatoreLoginObservable();
        visualizzatoreLoginObservable.addObserver(visualizzatoreLoginObserver);
        this.autenticazioneUtenteHandler.setObservable(visualizzatoreLoginObservable);

        DisabilitazioneUtenteHandler.DisableUserExecuter disabilitaUtenteExecuter = this.disabilitazioneUtenteHandler.new DisableUserExecuter();
        VisualizzatoreLogoutObserver visualizzatoreLogoutObserver = new VisualizzatoreLogoutObserver();
        VisualizzatoreLogoutObservable visualizzatoreLogoutObservable = new VisualizzatoreLogoutObservable();
        visualizzatoreLogoutObservable.addObserver(visualizzatoreLogoutObserver);
        this.disabilitazioneUtenteHandler.setObservable(visualizzatoreLogoutObservable);

        this.registrazioneHandler = new RegistrazioneHandler(this, interfacciaQuery, cvComponentiFactory);
        RegistrazioneHandler.RegistrazioneDialogShowExecuter registrazioneDialogShowExecuter = this.registrazioneHandler.new RegistrazioneDialogShowExecuter();
        RegistrazioneHandler.InnerAsyncTaskExecuter raccoglitoreDatiEsecutore = this.registrazioneHandler.new InnerAsyncTaskExecuter();
        RegistrazioneDialog registrazioneDialog = this.registrazioneHandler.getRegistrazioneDialog();
        button = registrazioneDialog.getRightButton();
        button.setOnClickListener(raccoglitoreDatiEsecutore);

        RegistrazioneSender registrazioneSender = new RegistrazioneSender(this, this.registrazioneHandler,cvComponentiFactory);
        RegistrazioneSender.InnerAsyncTaskExecuter senderEsecutore = registrazioneSender.new InnerAsyncTaskExecuter();
        button = this.registrazioneHandler.getCodiceDialog().getRightButton();
        button.setOnClickListener(senderEsecutore);

        this.recuperoHandler = new RecuperoHandler(this, interfacciaQuery, cvComponentiFactory);
        RecuperoHandler.UserIDDialogShowExecuter userIDDialogShowExecuter = this.recuperoHandler.new UserIDDialogShowExecuter();
        RecuperoHandler.InnerAsyncTaskExecuter innerAsyncTaskExecuter = this.recuperoHandler.new InnerAsyncTaskExecuter();
        button = this.recuperoHandler.getUserIdDialog().getRightButton();
        button.setOnClickListener(innerAsyncTaskExecuter);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.travel);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        LayoutParams layoutParams = new LayoutParams(width, (int) (height * 0.35));
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ScaleType.FIT_START);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(bitmap);
        linearLayout.addView(imageView);

        this.subLogoutLayout = new LinearLayout(this);
        this.subLogoutLayout.setOrientation(LinearLayout.VERTICAL);

        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        layoutParams = new LayoutParams (BenvenutoActivity.LARGHEZZA_BUTTON * 3,(int) (height * 0.075));
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.rightMargin = BenvenutoActivity.LARGHEZZA_BUTTON / 2;
        layoutParams.leftMargin = BenvenutoActivity.LARGHEZZA_BUTTON / 2;
        layoutParams.topMargin = (int)(height * 0.025);
        button.setText("Esci");
        button.setTextSize(20);
        button.setAllCaps(false);
        button.setOnClickListener(disabilitaUtenteExecuter);
        drawable = resources.getDrawable(R.drawable.exit, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setLayoutParams(layoutParams);
        this.subLogoutLayout.addView(button);

        this.subLoginLayout = new LinearLayout(this);
        this.subLoginLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LayoutParams (width,(int) (height * 0.250)));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        button.setText("Cerca");
        button.setTextSize(20);
        button.setAllCaps(false);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setOnClickListener(startActivityOnConditionExecuter);
        drawable = resources.getDrawable(R.drawable.search, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        startActivityOnConditionExecuter = new StartActivityOnNetworkEnabledExecuter(this.progressDialog, this, MappaActivity.class, null, cvComponentiFactory);
        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        button.setText("Mappa");
        button.setTextSize(20);
        button.setAllCaps(false);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setOnClickListener(startActivityOnConditionExecuter);
        drawable = resources.getDrawable(R.drawable.onmap, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);

        ((LinearLayout)(this.contentView.getChildAt(0))).addView(linearLayout);

        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        button.setText("Login");
        button.setTextSize(20);
        button.setAllCaps(false);
        button.setOnClickListener(loginDialogShowExecuter);
        drawable = resources.getDrawable(R.drawable.user, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setLayoutParams(layoutParams);
        this.subLoginLayout.addView(button);

        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        button.setText("Registrati");
        button.setTextSize(18);
        button.setAllCaps(false);
        button.setOnClickListener(registrazioneDialogShowExecuter);
        drawable = resources.getDrawable(R.drawable.registration, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setLayoutParams(layoutParams);
        this.subLoginLayout.addView(button);

        button = new Button (this);
        button.setBackgroundResource(R.drawable.border);
        button.setText("Recupera");
        button.setTextSize(18);
        button.setAllCaps(false);
        button.setOnClickListener(userIDDialogShowExecuter);
        drawable = resources.getDrawable(R.drawable.credentials, null);
        drawable.setBounds(-10, 0, 75, 60);
        button.setCompoundDrawables(null, null, drawable, null);
        button.setTextColor(CVComponentiFactory.COLORE_PRINCIPALE);
        button.setLayoutParams(layoutParams);
        this.subLoginLayout.addView(button);
        ((LinearLayout)(this.contentView.getChildAt(0))).addView(this.subLoginLayout);
        this.setContentView(this.contentView);
        this.autenticazioneUtenteHandler.executeAutoLog();
        this.contentView.setBackgroundColor(verde);
        this.subLoginLayout.setBackgroundColor(verde);
        this.subLogoutLayout.setBackgroundColor(verde);
        
    }

    @Override
    public void onStop() {
        super.onStop();
        this.progressDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.autenticazioneUtenteHandler.setLastLogState();
        ((LinearLayout)(this.contentView.getChildAt(0))).removeView(this.subLoginLayout);
        ((LinearLayout)(this.contentView.getChildAt(0))).removeView(this.subLogoutLayout);
        if (this.autenticazioneUtenteHandler.isLogged()) {
            ((LinearLayout)(this.contentView.getChildAt(0))).addView(this.subLogoutLayout);
        }
        else {
            ((LinearLayout)(this.contentView.getChildAt(0))).addView(this.subLoginLayout);
        }
    }

    protected class VisualizzatoreLoginObserver implements Observer {

        @Override
        public void update(Observable observable, Object object) {
            if (autenticazioneUtenteHandler.isLogged()) {
                ((LinearLayout)(contentView.getChildAt(0))).removeView(subLoginLayout);
                ((LinearLayout)(contentView.getChildAt(0))).addView(subLogoutLayout);
            }
        }

    }

    protected class VisualizzatoreLoginObservable extends Observable {

        @Override
        public void notifyObservers() {
            this.setChanged();
            super.notifyObservers();
        }

    }

    protected class VisualizzatoreLogoutObserver implements Observer {

        @Override
        public void update(Observable observable, Object object) {
            ((LinearLayout)(contentView.getChildAt(0))).removeView(subLogoutLayout);
            ((LinearLayout)(contentView.getChildAt(0))).addView(subLoginLayout);
        }

    }

    protected class VisualizzatoreLogoutObservable extends Observable {

        @Override
        public void notifyObservers() {
            this.setChanged();
            super.notifyObservers();
        }

    }

    @Override
    public void onBackPressed() {

    }

}





