package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractAuthenticationHandler;
import cvapp.components.CVComponentiFactory;
import graphiccomponents.FullscreenLoginDialog;
import utilities.NetworkUtility;

public class AutenticazioneUtenteHandler extends AbstractAuthenticationHandler {

    public AutenticazioneUtenteHandler(Activity activity, InterfacciaQuery interfacciaBackEnd, CVComponentiFactory componentsFactory, Observable observable) {
        super(activity, (AbstractQueriesInterface) interfacciaBackEnd, componentsFactory, observable);
        this.setNetworkDisabledmessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setAuthenticationExistsMessage("Al momento risulta già un' autenticazione attiva.\nL' utente autenticato deve effettuare il Logout.\n");
        this.setSuccessMessage("Hai effettuato correttamente il Login.\nAddesso, se lo desideri, puoi scrivere le tue recensioni!\n");
        this.setNoResultMessage("Login non riuscito.\nHai inserito credenziali non valide!\n");
        this.setIDAttribute(((AbstractQueriesInterface)interfacciaBackEnd).getUserIDAttribute());
        this.setNicknameAttribute(((AbstractQueriesInterface)interfacciaBackEnd).getUserNicknameAttribute());
        this.setPasswordAttribute(((AbstractQueriesInterface)interfacciaBackEnd).getUserPasswordAttribute());
    }

    @Override
    protected boolean checkNetworkCondition() {
        return NetworkUtility.isNetworkEnabled(this.getActivity());
    }

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (super.checkFunctionsStatus()) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                FullscreenLoginDialog fullscreenLoginDialog = this.getLoginDialog();
                String username = fullscreenLoginDialog.getUsername();
                String password = fullscreenLoginDialog.getPassword();
                String messaggioErrore = "";
                if (username.length() == 0) {
                    messaggioErrore = "Non hai inserito alcuno username!\n";
                } else {
                    if (username.contains(" ")) {
                        messaggioErrore = "Lo username contiene spazi!\n";
                    }
                }
                if (password.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcuna password!\n";
                } else {
                    if (password.contains(" ")) {
                        messaggioErrore = "La password contiene spazi!\n";
                    }
                }
                if (messaggioErrore.length() > 0) {
                    alertDialog.setMessage(messaggioErrore);
                    return false;
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
        }
        return false;
    }

    @Override
    protected String buildRequest() {
        FullscreenLoginDialog fullscreenLoginDialog = this.getLoginDialog();
        String username = fullscreenLoginDialog.getUsername();
        String password = fullscreenLoginDialog.getPassword();
        InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
        String selectUtente = interfacciaQuery.getSelectUtente(username, password);
        return selectUtente;
    }

    @Override
    protected void showSuccessOnActivity() {
        Activity activity = this.getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            CVComponentiFactory componentiFactory = (CVComponentiFactory) this.getComponentsFactory();
            String subtitle = componentiFactory.buildBarTitle(this.getUsername());
            actionBar.setSubtitle(subtitle);
        }
    }

    @Override
    protected void showFailureOnActivity() {
        Activity activity = this.getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            actionBar.setSubtitle("");
        }
    }

}
