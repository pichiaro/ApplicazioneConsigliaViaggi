package asynctasks;

import android.app.AlertDialog;
import android.app.Dialog;

import authenticators.UserAuthenticator;

public class AckSender extends SMTPMailSender {

    private AlertDialog messageDialog;
    private Dialog closeIfOkDialog;
    private String errorMessage;
    private String successMessage;

    public AckSender(Dialog dialog, Dialog closeIfOkDialog, AlertDialog messageDialog, String successMessage, String errorMessage, String email, String subject, String message, UserAuthenticator userAuthenticator) {
      super(dialog, email, subject, message, userAuthenticator);
      this.messageDialog = messageDialog;
      this.closeIfOkDialog = closeIfOkDialog;
      this.successMessage = successMessage;
      this.errorMessage = errorMessage;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return this.successMessage;
    }

    public void setCloseIfOkDialog(Dialog closeIfOkDialog) {
        this.closeIfOkDialog = closeIfOkDialog;
    }

    public Dialog getCloseIfOkDialog() {
        return this.closeIfOkDialog;
    }

    @Override
    protected void onPostExecute(Void nothing) {
        super.onPostExecute(nothing);
        if (this.messageDialog != null) {
            if (!this.isOk()) {
                this.messageDialog.setMessage(this.errorMessage);
            } else {
                this.messageDialog.setMessage(this.successMessage);
                if (this.closeIfOkDialog != null) {
                    this.closeIfOkDialog.dismiss();
                }
            }
            this.messageDialog.show();
        }
    }

}
