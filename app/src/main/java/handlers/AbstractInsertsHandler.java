package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;

public abstract class AbstractInsertsHandler extends AbstractAsyncHandler {

    private Dialog waitingDialog;
    private AlertDialog messageDialog;
    private Activity activity;
    private String noResultMessage;
    private String successMessage;
    private String networkDisabledMessage;
    private AbstractQueriesInterface queriesInterface;

    public AbstractInsertsHandler(Activity activity, AbstractQueriesInterface queriesInterface, AbstractComponentsFactory componentsFactory) {
        this.activity = activity;
        this.queriesInterface = queriesInterface;
        this.waitingDialog = componentsFactory.buildWaitingDialog();
        this.messageDialog = componentsFactory.buildAlertDialog();
    }

    public void setNetworkDisabledMessage(String networkDisabledMessage) {
        this.networkDisabledMessage = networkDisabledMessage;
    }

    public String getNetworkDisabledMessage() {
        return this.networkDisabledMessage;
    }

    public void setNoResultMessage(String noResultMessage) {
        this.noResultMessage = noResultMessage;
    }

    public String getNoResultMessage() {
        return this.noResultMessage;
    }

    public void setWaitingDialog(Dialog waitingDialog) {
        this.waitingDialog = waitingDialog;
    }

    public Dialog getWaitingDialog() {
        return this.waitingDialog;
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

    public void setQueriesInterface(AbstractQueriesInterface queriesInterface) {
        this.queriesInterface = queriesInterface;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return this.successMessage;
    }

    public AbstractQueriesInterface getQueriesInterface() {
        return this.queriesInterface;
    }

    protected abstract AbstractList<Object> buildValues();

    protected abstract String getTablename();

    @Override
    protected abstract boolean checkFunctionsStatus();

    @Override
    protected boolean executeBefore() {
        if (this.waitingDialog != null) {
            this.waitingDialog.show();
        }
        return true;
    }

    @Override
    protected boolean executeInBackground() {
        if (this.queriesInterface != null) {
            AbstractList<Object> values = this.buildValues();
            if (values.size() == 0) {
                if (this.messageDialog != null) {
                    this.messageDialog.setMessage(this.noResultMessage);
                }
                return false;
            }
            String tablename = this.getTablename();
            AbstractList<String> attributi = this.queriesInterface.getAttributes(tablename);
            boolean insertOk = this.queriesInterface.insert(tablename, attributi, values);
            if (!insertOk) {
                if (this.messageDialog != null) {
                    this.messageDialog.setMessage(this.noResultMessage);
                }
                return false;
            }
            if (this.messageDialog != null) {
                this.messageDialog.setMessage(this.successMessage);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean executeAfter() {
        if (this.waitingDialog != null) {
            this.waitingDialog.dismiss();
        }
        if (this.messageDialog != null) {
            this.messageDialog.show();
        }
        return true;
    }

    public class InnerAsyncTaskExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkFunctionsStatus()) {
                InnerAsyncTask innerAsyncTask = new InnerAsyncTask();
                innerAsyncTask.execute();
            }
            else {
                if (messageDialog != null) {
                    messageDialog.show();
                }
            }
        }

    }


}
