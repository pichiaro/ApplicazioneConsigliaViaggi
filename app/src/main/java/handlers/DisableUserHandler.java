package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Observable;

import components.AbstractComponentsFactory;

public class DisableUserHandler {

    private Activity activity;
    private AlertDialog messageDialog;
    private AbstractAuthenticationHandler authenticationHandler;
    private String noAuthenticationMessage;
    private String disableMessage;
    private Observable observable;

    public DisableUserHandler(Activity activity, AbstractComponentsFactory componentsFactory, AbstractAuthenticationHandler authenticationHandler, Observable observable) {
        this.activity = activity;
        this.authenticationHandler = authenticationHandler;
        this.messageDialog = componentsFactory.buildAlertDialog();
        this.observable = observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public Observable getObservable() {
        return this.observable;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setAuthenticationHandler(AbstractAuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    public AbstractAuthenticationHandler getAuthenticationHandler() {
        return this.authenticationHandler;
    }

    public void setDisableMessage(String disableMessage) {
        this.disableMessage = disableMessage;
    }

    public String getDisableMessage() {
        return this.disableMessage;
    }

    public void setNoAuthenticationMessage(String noAuthenticationMessage) {
        this.noAuthenticationMessage = noAuthenticationMessage;
    }

    public String getNoAuthenticationMessage() {
        return this.noAuthenticationMessage;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void disableUser() {
        if (!this.authenticationHandler.isLogged()) {
            this.messageDialog.setMessage(this.getNoAuthenticationMessage());
            this.messageDialog.show();
        } else {
            this.authenticationHandler.disableUser();
            this.messageDialog.setMessage(this.getDisableMessage());
            this.messageDialog.show();
        }
    }

    public class DisableUserExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            disableUser();
            observable.notifyObservers();
        }

    }

}
