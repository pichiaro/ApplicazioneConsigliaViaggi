package cvapp.components;

import android.app.AlertDialog;
import android.app.Dialog;

import asynctasks.SMTPMailSender;
import authenticators.UserAuthenticator;
import graphiccomponents.FullscreenInsertDialog;

public class CodiceSender extends SMTPMailSender {

    private static int CODICE = 1000;
    private AlertDialog messageDialog;
    private FullscreenInsertDialog codiceDialog;
    private final static String SUBJECT = "Codice di conferma CV'19";

    public CodiceSender(Dialog dialog, AlertDialog messageDialog, FullscreenInsertDialog codiceDialog, String email, UserAuthenticator userAuthenticator) {
      super(dialog, email, CodiceSender.SUBJECT, String.valueOf(CodiceSender.CODICE), userAuthenticator);
      this.messageDialog = messageDialog;
      this.codiceDialog = codiceDialog;
    }

    public String getCodiceInviato() {
        return String.valueOf(CodiceSender.CODICE - 1);
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void setCodiceDialog(FullscreenInsertDialog codiceDialog) {
        this.codiceDialog = codiceDialog;
    }

    public FullscreenInsertDialog getCodiceDialog() {
        return this.codiceDialog;
    }

    public void chiudiCodiceDialog() {
        if (this.codiceDialog != null) {
            this.codiceDialog.dismiss();
        }
    }

    @Override
    protected void onPostExecute(Void nothing) {
        super.onPostExecute(nothing);
        if (this.isOk()) {
            this.codiceDialog.show();
            CodiceSender.CODICE = CodiceSender.CODICE + 1;
        }
        else {
            this.messageDialog.setMessage("L' invio del codice di verifica non è riuscito, evidentemente la mail che hai fornito non è valida.\n");
            this.messageDialog.show();
        }
    }

}
