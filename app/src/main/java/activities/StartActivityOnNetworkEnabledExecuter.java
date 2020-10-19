package activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import components.AbstractComponentsFactory;
import utilities.NetworkUtility;

public class StartActivityOnNetworkEnabledExecuter extends AbstractStartActivityOnConditionExecuter {

    private AlertDialog messageDialog;
    private String networkDisabledMessage;

    public StartActivityOnNetworkEnabledExecuter(Dialog dialog, Activity fromActivity, Class<? extends Activity> toActivityClass, Bundle bundle, AbstractComponentsFactory componentsFactory) {
        super(dialog, fromActivity, toActivityClass, bundle);
        if (componentsFactory != null) {
            this.messageDialog = componentsFactory.buildAlertDialog();
        }
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void setNetworkDisabledMessage(String networkDisabledMessage) {
        this.networkDisabledMessage = networkDisabledMessage;
    }

    public String getNetworkDisabledMessage() {
        return this.networkDisabledMessage;
    }

    @Override
    protected boolean checkCondition() {
        if (NetworkUtility.isNetworkEnabled(this.getFromActivity())) {
            return true;
        }
        if (this.messageDialog != null) {
            this.messageDialog.setMessage(this.networkDisabledMessage);
            this.messageDialog.show();
        }
        return false;
    }

}
