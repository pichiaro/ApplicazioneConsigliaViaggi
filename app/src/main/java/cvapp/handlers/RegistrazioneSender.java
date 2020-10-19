package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;

import cvapp.components.InterfacciaQuery;
import handlers.AbstractAsyncHandler;

import components.AbstractQueriesInterface;
import cvapp.components.CVComponentiFactory;
import cvapp.components.CodiceSender;
import graphiccomponents.FullscreenInsertDialog;
import utilities.NetworkUtility;

public class RegistrazioneSender extends AbstractAsyncHandler {

    private RegistrazioneHandler registrazioneHandler;
    private Activity activity;
    private AlertDialog messageDialog;
    private InterfacciaQuery interfacciaQuery;
    private String codeNotEqualMessage;
    private String networkDisabledMessage;
    private String errorMessage;
    private String successMessage;

    public RegistrazioneSender(Activity activity, RegistrazioneHandler registrazioneHandler, CVComponentiFactory componentiFactory) {
        this.registrazioneHandler = registrazioneHandler;
        this.interfacciaQuery = registrazioneHandler.getInterfacciaQuery();
        this.activity = activity;
        this.messageDialog = componentiFactory.buildAlertDialog();
        this.codeNotEqualMessage = "Il codice inserito non è uguale a quello che ti abbiamo inviato!\n";
        this.networkDisabledMessage = "Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n";
        this.errorMessage = "Mi dispiace, ho riscontrato un errore nel database, riprova successivamente.\n";
        this.successMessage = "Hai eseguito la registrazione con successo, complimenti!!\n";
    }

    public void setRegistrazioneHandler(RegistrazioneHandler registrazioneHandler) {
        this.registrazioneHandler = registrazioneHandler;
        this.interfacciaQuery = registrazioneHandler.getInterfacciaQuery();
    }

    public RegistrazioneHandler getRegistrazioneHandler() {
        return this.registrazioneHandler;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public InterfacciaQuery getInterfacciaQuery() {
        return this.interfacciaQuery;
    }

    @Override
    protected boolean checkFunctionsStatus() {
        if (NetworkUtility.isNetworkEnabled(this.activity)) {
            if (this.interfacciaQuery.esisteConnessioneSingleton()) {
                this.messageDialog.setMessage("Conessiona al database non disponibile, riprova successivamente!\n");
                return false;
            }
            return true;
        }
        this.messageDialog.setMessage(this.networkDisabledMessage);
        return false;
    }

    @Override
    protected boolean executeBefore() {
        ProgressDialog progressDialog = this.registrazioneHandler.getProgressDialog();
        progressDialog.show();
        return true;
    }

    @Override
    protected boolean executeInBackground() {
        CodiceSender codiceSender = this.registrazioneHandler.getCodiceSender();
        FullscreenInsertDialog fullscreenInsertDialog = codiceSender.getCodiceDialog();
        String codice = fullscreenInsertDialog.getEditedText();
        String codiceInviato = codiceSender.getCodiceInviato();
        if (codice.compareTo(codiceInviato) == 0) {
            AbstractList<Object> value = this.registrazioneHandler.getValuesFormaInviabile();
            String tableName = ((AbstractQueriesInterface)this.interfacciaQuery).getUserTableName();
            AbstractList<String> attributes = ((AbstractQueriesInterface)this.interfacciaQuery).getAttributes(tableName);
            boolean insertOk = ((AbstractQueriesInterface)this.interfacciaQuery).insert(tableName, attributes, value);
            if (!insertOk) {
                this.messageDialog.setMessage(this.errorMessage);
                return false;
            }
            this.messageDialog.setMessage(this.successMessage);
            this.registrazioneHandler.chiudiRegistrazioneDialog();
            codiceSender.chiudiCodiceDialog();
            return true;
        }
        this.messageDialog.setMessage(this.codeNotEqualMessage);
        return false;
    }

    @Override
    protected boolean executeAfter() {
        ProgressDialog progressDialog = this.registrazioneHandler.getProgressDialog();
        progressDialog.dismiss();
        this.messageDialog.show();
        return true;
    }

    public class InnerAsyncTaskExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkFunctionsStatus()) {
                InnerAsyncTask innerAsyncTask = new InnerAsyncTask();
                innerAsyncTask.execute();
            } else {
                messageDialog.show();
            }
        }
    }

}
