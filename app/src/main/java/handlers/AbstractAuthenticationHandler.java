package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import java.util.AbstractList;
import java.util.Observable;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;
import database.AbstractUserCache;
import graphiccomponents.AlternativeRadioButton;
import graphiccomponents.FullscreenLoginDialog;
import models.ObjectsModel;

public abstract class AbstractAuthenticationHandler extends AbstractAsyncHandler {

    private String username;
    private AlertDialog messageDialog;
    private Dialog waitingDialog;
    private FullscreenLoginDialog loginDialog;
    private Activity activity;
    private AbstractQueriesInterface queriesInterface;
    private ObjectsModel objectsModel;
    private AbstractComponentsFactory componentsFactory;
    private AbstractUserCache userCache;
    private boolean isLogged;
    private Observable observable;

    private String networkDisabledmessage;
    private String authenticationExistsMessage;
    private String successMessage;
    private String nicknameAttribute;
    private String noResultMessage;
    private String idAttribute;
    private String passwordAttribute;


    public AbstractAuthenticationHandler(Activity activity, AbstractQueriesInterface queriesInterface, AbstractComponentsFactory componentiFactory, Observable observable) {
        this.activity = activity;
        this.queriesInterface = queriesInterface;
        this.loginDialog = componentiFactory.buildFullscreenLoginDialog();
        this.messageDialog = componentiFactory.buildAlertDialog();
        this.waitingDialog = componentiFactory.buildWaitingDialog();
        this.componentsFactory = componentiFactory;
        this.userCache = componentiFactory.buildUserCache();
        this.observable = observable;
    }

    public void setNoResultMessage(String noResultMessage) {
        this.noResultMessage = noResultMessage;
    }

    public String getNoResultMessage() {
        return this.noResultMessage;
    }

    public void setNicknameAttribute(String nicknameAttribute) {
        this.nicknameAttribute = nicknameAttribute;
    }

    public String getNicknameAttribute() {
        return this.nicknameAttribute;
    }

    public void setIDAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }

    public String getIDAttribute() {
        return this.idAttribute;
    }

    public void setPasswordAttribute(String passwordAttribute) {
        this.passwordAttribute = passwordAttribute;
    }

    public String getPasswordAttribute() {
        return this.passwordAttribute;
    }

    public void setNetworkDisabledmessage(String networkDisabledmessage) {
        this.networkDisabledmessage = networkDisabledmessage;
    }

    public String getNetworkDisabledmessage() {
        return this.networkDisabledmessage;
    }

    public void setAuthenticationExistsMessage(String authenticationExistsMessage) {
        this.authenticationExistsMessage = authenticationExistsMessage;
    }

    public String getAuthenticationExistsMessage() {
        return this.authenticationExistsMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return this.successMessage;
    }

    public String getUsername() {
        return this.username;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public void setWaitingDialog(Dialog waitingDialog) {
        this.waitingDialog = waitingDialog;
    }

    public Dialog getWaitingDialog() {
        return this.waitingDialog;
    }

    public void setLoginDialog(FullscreenLoginDialog loginDialog) {
        this.loginDialog = loginDialog;
    }

    public FullscreenLoginDialog getLoginDialog() {
        return this.loginDialog;
    }

    public void setComponentsFactory(AbstractComponentsFactory componentsFactory) {
        this.componentsFactory = componentsFactory;
    }

    public AbstractComponentsFactory getComponentsFactory() {
        return this.componentsFactory;
    }

    public void setQueriesInterface(AbstractQueriesInterface queriesInterface) {
        this.queriesInterface = queriesInterface;
    }

    public AbstractQueriesInterface getQueriesInterface() {
        return this.queriesInterface;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public Observable getObservable() {
        return this.observable;
    }

    public boolean isLogged() {
        return this.isLogged;
    }

    public void disableUser() {
        if (this.isLogged) {
            ObjectsModel objectsModel = this.userCache.getUser();
            String userId = (String) objectsModel.getValue(AbstractUserCache.USER_ID);
            String password = (String) objectsModel.getValue(AbstractUserCache.PASSWORD);
            int mode = (int) objectsModel.getValue(AbstractUserCache.MODE);
            this.userCache.insertUser(userId, this.username, password, mode, AbstractUserCache.DISCONNECTED);
            this.username = "";
            this.isLogged = false;
            this.objectsModel = null;
            if (this.loginDialog != null) {
                this.loginDialog.hidePassword();
            }
            this.showFailureOnActivity();
        }
    }

    public void showLoginDialog() {
        if (this.checkNetworkCondition()) {
            if (this.isLogged) {
                this.messageDialog.setMessage(this.authenticationExistsMessage);
                this.messageDialog.show();
            } else {
                this.loginDialog.show();
            }
        } else {
            this.messageDialog.setMessage(this.networkDisabledmessage);
            this.messageDialog.show();
        }
    }

    protected abstract boolean checkNetworkCondition();

    @Override
    protected boolean checkFunctionsStatus() {
        if (this.checkNetworkCondition()) {
            if (this.isLogged) {
                if (this.messageDialog != null) {
                    this.messageDialog.setMessage(this.authenticationExistsMessage);
                }
                return false;
            }
            return true;
        }
        if (this.messageDialog != null) {
            this.messageDialog.setMessage(this.networkDisabledmessage);
        }
        return false;
    }

    abstract protected void showSuccessOnActivity();

    abstract protected void showFailureOnActivity();

    abstract protected String buildRequest();

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
            String query = this.buildRequest();
            AbstractList<ObjectsModel> objectsModels = this.queriesInterface.select(query);
            if (objectsModels.size() != 1) {
                if (this.messageDialog != null) {
                    this.messageDialog.setMessage(this.noResultMessage);
                }
                this.objectsModel = null;
                return false;
            }
            if (this.messageDialog != null) {
                this.messageDialog.setMessage(this.successMessage);
            }
            this.objectsModel = objectsModels.get(0);
            return true;
        }
        return false;
    }

    @Override
    protected boolean executeAfter() {
        if (this.userCache != null) {
            if (this.loginDialog != null) {
                if (this.objectsModel != null) {
                    this.username = (String) this.objectsModel.getValue(this.nicknameAttribute);
                    String userId = (String) this.objectsModel.getValue(this.idAttribute);
                    String password = (String) this.objectsModel.getValue(this.passwordAttribute);
                    if (this.loginDialog.isPasswordRemembered()) {
                        this.userCache.insertUser(userId, this.username, password, AbstractUserCache.ONLY_PASSWORD_MODE, AbstractUserCache.CONNECTED);
                    } else {
                        if (this.loginDialog.areCredentialsRemembered()) {
                            this.userCache.insertUser(userId, this.username, password, AbstractUserCache.CREDENTIALS_MODE, AbstractUserCache.CONNECTED);
                        } else {
                            if (this.loginDialog.isLoginAutomatic()) {
                                this.userCache.insertUser(userId, this.username, password, AbstractUserCache.AUTO, AbstractUserCache.CONNECTED);
                            } else {
                                this.userCache.insertUser(userId, this.username, password, AbstractUserCache.NO_MODE, AbstractUserCache.CONNECTED);
                            }
                        }
                    }
                    this.loginDialog.setReadInputDefaultText(userId + "\n" + password);
                    this.loginDialog.dismiss();
                    this.isLogged = true;
                    if (this.messageDialog != null) {
                        this.messageDialog.show();
                    }
                    this.showSuccessOnActivity();
                    return true;
                }
            }
        }
        if (this.messageDialog != null) {
            this.messageDialog.show();
        }
        return false;
    }

    public void executeAutoLog() {
        if (this.userCache != null) {
            if (this.loginDialog != null) {
                if (!this.isLogged) {
                    ObjectsModel objectsModel = this.userCache.getUser();
                    String username = (String) objectsModel.getValue(AbstractUserCache.USERNAME);
                    String userId = (String) objectsModel.getValue(AbstractUserCache.USER_ID);
                    String password = (String) objectsModel.getValue(AbstractUserCache.PASSWORD);
                    if (username != null && password != null && userId != null) {
                        if (username.length() > 0 && password.length() > 0 && userId.length() > 0) {
                            int mode = (int) objectsModel.getValue(AbstractUserCache.MODE);
                            switch (mode) {
                                case AbstractUserCache.AUTO:
                                    this.isLogged = true;
                                    this.username = username;
                                    this.loginDialog.hidePassword();
                                    EditText editText = this.loginDialog.getPasswordEditText();
                                    editText.setText("");
                                    editText = this.loginDialog.getUserNameEditText();
                                    editText.setText("");
                                    AlternativeRadioButton alternativeRadioButton = this.loginDialog.getMakeLoginAutomaticRadioButton();
                                    alternativeRadioButton.setChecked(true);
                                    alternativeRadioButton = this.loginDialog.getRememberPasswordRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberCredentialsRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    this.userCache.insertUser(userId, this.username, password, AbstractUserCache.AUTO, AbstractUserCache.CONNECTED);
                                    this.showSuccessOnActivity();
                                    break;
                                case AbstractUserCache.ONLY_PASSWORD_MODE:
                                    editText = this.loginDialog.getPasswordEditText();
                                    editText.setText(password);
                                    editText = this.loginDialog.getUserNameEditText();
                                    editText.setText("");
                                    this.loginDialog.hidePassword();
                                    alternativeRadioButton = this.loginDialog.getMakeLoginAutomaticRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberPasswordRadioButton();
                                    alternativeRadioButton.setChecked(true);
                                    alternativeRadioButton = this.loginDialog.getRememberCredentialsRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    break;
                                case AbstractUserCache.CREDENTIALS_MODE:
                                    editText = this.loginDialog.getPasswordEditText();
                                    editText.setText(password);
                                    editText = this.loginDialog.getUserNameEditText();
                                    editText.setText(userId);
                                    this.loginDialog.hidePassword();
                                    alternativeRadioButton = this.loginDialog.getMakeLoginAutomaticRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberPasswordRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberCredentialsRadioButton();
                                    alternativeRadioButton.setChecked(true);
                                    break;
                                case AbstractUserCache.NO_MODE:
                                    editText = this.loginDialog.getPasswordEditText();
                                    editText.setText("");
                                    editText = this.loginDialog.getUserNameEditText();
                                    editText.setText("");
                                    this.loginDialog.hidePassword();
                                    alternativeRadioButton = this.loginDialog.getMakeLoginAutomaticRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberPasswordRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    alternativeRadioButton = this.loginDialog.getRememberCredentialsRadioButton();
                                    alternativeRadioButton.setChecked(false);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setLastLogState() {
        if (this.userCache != null) {
            if (!this.isLogged) {
                ObjectsModel objectsModel = this.userCache.getUser();
                int connection = (int) objectsModel.getValue(AbstractUserCache.CONN);
                if (connection == AbstractUserCache.CONNECTED) {
                    this.isLogged = true;
                    this.username = (String) objectsModel.getValue(AbstractUserCache.USERNAME);
                    this.showSuccessOnActivity();
                } else {
                    if (connection == AbstractUserCache.DISCONNECTED) {
                        this.username = "";
                        this.showFailureOnActivity();
                    }
                }
            }
        }
    }

    public class InnerAsyncTaskExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkFunctionsStatus()) {
                InnerAsyncTask innerAsyncTask = new InnerAsyncTask() {

                    @Override
                    protected void onPostExecute(Void nothing) {
                        executeAfter();
                        if (isLogged()) {
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
            } else {
                if (messageDialog != null) {
                    messageDialog.show();
                }
            }
        }

    }

    public class LoginDialogShowExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            showLoginDialog();
        }

    }

}
