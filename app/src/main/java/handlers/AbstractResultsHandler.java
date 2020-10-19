package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;
import java.util.Observable;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;
import models.ObjectsModel;
import models.ObjectsModelTransformable;

public abstract class AbstractResultsHandler extends AbstractAsyncHandler {
    private Dialog waitingDialog;
    private AlertDialog messageDialog;
    private Activity activity;
    private String noResultMessage;
    private String networkDisabledMessage;
    private AbstractQueriesInterface queriesInterface;
    private ObjectsModelTransformable objectsModelTransformable;
    private int transformationOption;
    private Observable observable;
    private AbstractList<ObjectsModel> objectsModels;

    public AbstractResultsHandler(Activity activity, AbstractQueriesInterface queriesInterface, AbstractComponentsFactory componentsFactory, Observable observable, ObjectsModelTransformable objectsModelTransformable, int transformationOption) {
        this.observable = observable;
        this.activity = activity;
        this.queriesInterface = queriesInterface;
        this.objectsModelTransformable = objectsModelTransformable;
        this.waitingDialog = componentsFactory.buildWaitingDialog();
        this.messageDialog = componentsFactory.buildAlertDialog();
        this.transformationOption = transformationOption;
    }

    protected void setObjectsModels(AbstractList<ObjectsModel> objectsModels) {
        this.objectsModels = objectsModels;
    }

    protected AbstractList<ObjectsModel> getObjectsModels() {
        return this.objectsModels;
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

    public AbstractQueriesInterface getQueriesInterface() {
        return this.queriesInterface;
    }

    public void setObjectsModelTransformable(ObjectsModelTransformable objectsModelTransformable) {
        this.objectsModelTransformable = objectsModelTransformable;
    }

    public ObjectsModelTransformable getObjectsModelTransformable() {
        return this.objectsModelTransformable;
    }

    public void setTransformationOption(int transformationOption) {
        this.transformationOption = transformationOption;
    }

    public int getTransformationOption() {
        return this.transformationOption;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public Observable getObservable() {
        return observable;
    }

    protected abstract String buildRequest();

    @Override
    protected boolean executeBefore() {
        if (this.waitingDialog != null) {
            this.waitingDialog.show();
        }
        return true;
    }

    @Override
    protected boolean executeInBackground() {
        String query = this.buildRequest();
        if (this.queriesInterface != null) {
            this.objectsModels = this.queriesInterface.select(query);
            if (this.objectsModels.size() > 0) {
                return true;
            }
            if (this.messageDialog != null) {
                this.messageDialog.setMessage(this.noResultMessage);
            }
        }
        return false;
    }

    @Override
    protected boolean executeAfter() {
        if (this.objectsModelTransformable != null) {
            if (this.objectsModels.size() > 0) {
                this.objectsModelTransformable.transform(this.objectsModels, this.transformationOption);
                return true;
            }
            if (this.messageDialog != null) {
                this.messageDialog.show();
            }
        }
        return false;
    }

    public class InnerAsyncTaskExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkFunctionsStatus()) {
                InnerAsyncTask innerAsyncTask = new InnerAsyncTask() {

                    @Override
                    protected void onPostExecute(Void nothing) {
                        executeAfter();
                        if (objectsModels.size() > 0) {
                            if (observable != null) {
                                observable.notifyObservers();
                            }
                        }
                        if (waitingDialog != null) {
                            waitingDialog.dismiss();
                        }
                    }

                };
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
