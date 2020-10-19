package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;

import javax.mail.Authenticator;

import asynctasks.AckSender;
import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractAsyncHandler;
import authenticators.UserAuthenticator;
import cvapp.components.CVComponentiFactory;
import graphiccomponents.FullscreenInsertDialog;
import models.ObjectsModel;
import utilities.NetworkUtility;
import utilities.StringsUtility;

public class RecuperoHandler extends AbstractAsyncHandler {

    private FullscreenInsertDialog userIdDialog;
    private CVComponentiFactory componentiFactory;
    private InterfacciaQuery interfacciaQuery;
    private AlertDialog messageDialog;
    private Activity activity;
    private AckSender ackSender;
    private String password;
    private ProgressDialog progressDialog;
    private Authenticator authenticator;
    private String userIdNotValidMessage;
    private String networkDisabledMessage;
    private String successMessage;

    public RecuperoHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory) {
        this.interfacciaQuery = interfacciaQuery;
        this.componentiFactory = componentiFactory;
        this.userIdDialog = componentiFactory.buildFullscreenInsertDialog(CVComponentiFactory.RECUPERO_PASSWORD);
        this.messageDialog = componentiFactory.buildAlertDialog();
        this.activity = activity;
        this.progressDialog = (ProgressDialog) this.componentiFactory.buildWaitingDialog();
        this.progressDialog.setMessage("Attendi qualche attimo, sto inviando la tua password alla tua e-mail...");
        this.authenticator = componentiFactory.buildAuthenticator();
        this.userIdNotValidMessage = "Lo user ID che hai inserito non è valido, non appartiene ad alcun utente registrato!\n";
        this.networkDisabledMessage = "Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n";
        this.successMessage = "La password è stata inviata con successo alla tua e-mail.\n";
    }


    public void setUserIdDialog(FullscreenInsertDialog userIdDialog) {
        this.userIdDialog = userIdDialog;
    }

    public FullscreenInsertDialog getUserIdDialog() {
        return this.userIdDialog;
    }

    public void setComponentiFactory(CVComponentiFactory componentiFactory) {
        this.componentiFactory = componentiFactory;
    }

    public CVComponentiFactory getComponentiFactory() {
        return this.componentiFactory;
    }

    public void setInterfacciaQuery(InterfacciaQuery interfacciaQuery) {
        this.interfacciaQuery = interfacciaQuery;
    }

    public InterfacciaQuery getInterfacciaQuery() {
        return this.interfacciaQuery;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setAckSender(AckSender ackSender) {
        this.ackSender = ackSender;
    }

    public AckSender getAckSender() {
        return this.ackSender;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void mostraUserIDDialog() {
        if (NetworkUtility.isNetworkEnabled(this.activity)) {
            if (this.interfacciaQuery.esisteConnessioneSingleton()) {
                this.userIdDialog.reset();
                this.userIdDialog.show();
            }
            else {
                this.messageDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
                this.messageDialog.show();
            }
        } else {
            this.messageDialog.setMessage(this.networkDisabledMessage);
            this.messageDialog.show();
        }
    }

    @Override
    protected boolean checkFunctionsStatus() {
        if (NetworkUtility.isNetworkEnabled(this.activity)) {
            if (this.interfacciaQuery.esisteConnessioneSingleton()) {
                String userId = this.userIdDialog.getEditedText();
                if (userId.length() == 0) {
                    this.messageDialog.setMessage("Non hai inserito alcuno user ID!\n");
                    return false;
                } else {
                    if (userId.contains(" ")) {
                        this.messageDialog.setMessage("Lo user ID contiene spazi!\n");
                        return false;
                    } else {
                        if (!StringsUtility.isEmailAddress(userId)) {
                            this.messageDialog.setMessage("Lo user ID non rappresenta una e-mail!\n");
                            return false;
                        }
                    }
                }
                String userIdSelect = ((AbstractQueriesInterface) this.interfacciaQuery).getExistUserIDSelect(userId, true);
                AbstractList<ObjectsModel> objectsModels = ((AbstractQueriesInterface) this.interfacciaQuery).select(userIdSelect);
                if (objectsModels.size() != 1) {
                    this.messageDialog.setMessage(this.userIdNotValidMessage);
                    return false;
                }
                return true;
            }
            this.messageDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
            return false;
        }
        this.messageDialog.setMessage(this.networkDisabledMessage);
        return false;
    }

    @Override
    protected boolean executeBefore() {
        this.ackSender = null;
        this.password = null;
        this.progressDialog.show();
        return true;
    }


    @Override
    protected boolean executeInBackground() {
        if (this.ackSender == null) {
            if (this.password == null) {
                String userId = this.userIdDialog.getEditedText();
                AbstractQueriesInterface queriesInterface = (AbstractQueriesInterface) this.interfacciaQuery;
                String passwordSelect = queriesInterface.getPasswordSelect(userId, true);
                AbstractList<ObjectsModel> objectsModels = queriesInterface.select(passwordSelect);
                ObjectsModel objectsModel = objectsModels.get(0);
                this.password = (String) objectsModel.getValue(0);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean executeAfter() {
        this.progressDialog.dismiss();
        if (this.ackSender == null) {
            if (this.password != null) {
                String userId = this.userIdDialog.getEditedText();
                this.ackSender = new AckSender(this.progressDialog, this.userIdDialog, this.messageDialog, this.successMessage, "L' e-mail che hai fornito non esiste!.\n", userId, "Recupero password CV'19", this.password, (UserAuthenticator) this.authenticator);
                this.ackSender.execute();
                return true;
            }
        }
        this.messageDialog.show();
        return false;
    }

    public class UserIDDialogShowExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            mostraUserIDDialog();
        }

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
