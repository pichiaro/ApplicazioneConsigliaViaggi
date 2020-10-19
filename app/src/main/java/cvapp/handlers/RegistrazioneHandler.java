package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;

import java.util.AbstractList;
import java.util.Vector;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractAsyncHandler;
import authenticators.UserAuthenticator;
import cvapp.components.CVComponentiFactory;
import cvapp.components.CodiceSender;
import cvapp.views.RegistrazioneDialog;
import graphiccomponents.AlternativeCheckBox;
import graphiccomponents.AlternativeRadioButton;
import graphiccomponents.FullscreenInsertDialog;
import models.ObjectsModel;
import utilities.DatesUtility;
import utilities.NetworkUtility;
import utilities.StringsUtility;

public class RegistrazioneHandler extends AbstractAsyncHandler  {

    public final static String DATA_NASCITA_MINIMA = "20060101";
    public final static int NICKNAME_LUNGHEZZA_MINIMA = 5;

    private RegistrazioneDialog registrazioneDialog;
    private CVComponentiFactory componentiFactory;
    private InterfacciaQuery interfacciaQuery;
    private AlertDialog messageDialog;
    private Activity activity;
    private CodiceSender codiceSender;
    private AbstractList<Object> values;
    private ProgressDialog progressDialog;
    private FullscreenInsertDialog codiceDialog;
    private UserAuthenticator userAuthenticator;
    private String networkDisabledMessage;

    public RegistrazioneHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory) {
        this.interfacciaQuery = interfacciaQuery;
        this.componentiFactory = componentiFactory;
        this.registrazioneDialog = (RegistrazioneDialog) componentiFactory.buildFullscreenSigninDialog();
        this.messageDialog = componentiFactory.buildAlertDialog();
        this.activity = activity;
        this.progressDialog = (ProgressDialog) this.componentiFactory.buildWaitingDialog();
        this.progressDialog.setMessage("Attendi qualche attimo, sto inviando il codice di verifica alla tua e-mail...");
        this.codiceDialog = componentiFactory.buildFullscreenInsertDialog(CVComponentiFactory.CODICE_VERIFICA);
        this.userAuthenticator = (UserAuthenticator) componentiFactory.buildAuthenticator();
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
    }


    public void setNetworkDisabledMessage(String networkDisabledMessage) {
        this.networkDisabledMessage = networkDisabledMessage;
    }

    public String getNetworkDisabledMessage() {
        return this.networkDisabledMessage;
    }

    public void setRegistrazioneDialog(RegistrazioneDialog registrazioneDialog) {
        this.registrazioneDialog = registrazioneDialog;
    }

    public RegistrazioneDialog getRegistrazioneDialog() {
        return this.registrazioneDialog;
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

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public ProgressDialog getProgressDialog() {
        return this.progressDialog;
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

    public void setCodiceSender(CodiceSender codiceSender) {
        this.codiceSender = codiceSender;
    }

    public CodiceSender getCodiceSender() {
        return this.codiceSender;
    }

    public void setMessageDialog(AlertDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    public AlertDialog getMessageDialog() {
        return this.messageDialog;
    }

    public AbstractList<Object> getValuesFormaInviabile() {
        return this.interfacciaQuery.prepareUtentiValues((String) values.get(0), (String) values.get(1), (String) values.get(2), (String) values.get(3), (String) values.get(4), (String) values.get(5), (String) values.get(6), (String) values.get(7), (String) values.get(8), (boolean) values.get(9), (boolean) values.get(10), (boolean) values.get(11));
    }

    protected void setValues(AbstractList<Object> values) {
        this.values = values;
    }

    public void setCodiceDialog(FullscreenInsertDialog codiceDialog) {
        this.codiceDialog = codiceDialog;
    }

    public FullscreenInsertDialog getCodiceDialog() {
        return this.codiceDialog;
    }

    public void mostraRegistrazioneDialog() {
        if (NetworkUtility.isNetworkEnabled(this.activity)) {
            if (this.interfacciaQuery.esisteConnessioneSingleton()) {
                this.registrazioneDialog.reset();
                this.registrazioneDialog.show();
            } else {
                this.messageDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
                this.messageDialog.show();
            }
        } else {
            this.messageDialog.setMessage(this.networkDisabledMessage);
            this.messageDialog.show();
        }
    }

    public void chiudiRegistrazioneDialog() {
        this.registrazioneDialog.dismiss();
    }

    @Override
    protected boolean checkFunctionsStatus() {
        if (NetworkUtility.isNetworkEnabled(this.activity)) {
            if (this.interfacciaQuery.esisteConnessioneSingleton()) {
                String messaggioErrore = "";
                String nome = this.registrazioneDialog.getName();
                if (nome.length() == 0) {
                    messaggioErrore = "Non hai inserito alcun nome!\n";
                } else {
                    if (nome.length() == 1) {
                        messaggioErrore = "Il nome è troppo breve!\n";
                    }
                }
                String cognome = this.registrazioneDialog.getSurname();
                if (cognome.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcun cognome!\n";
                } else {
                    if (cognome.length() == 1) {
                        messaggioErrore = messaggioErrore + "Il cognome è troppo breve!\n";
                    }
                }
                String data = this.registrazioneDialog.getBornDate();
                if (data.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai selezionato alcuna data!\n";
                } else {
                    String localData = DatesUtility.getLocalYYYYMMDDDate(false);
                    if (data.compareTo(localData) > 0) {
                        messaggioErrore = messaggioErrore + "Hai selezionato una data successiva alla data odierna!\n";
                    } else {
                        if (data.compareTo(DATA_NASCITA_MINIMA) > 0) {
                            messaggioErrore = messaggioErrore + "Hai selezionato una data non valida!\n";
                        }
                    }
                }
                String paese = this.registrazioneDialog.getNationality();
                if (paese.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcuna nazionalità!\n";
                } else {
                    AutoCompleteTextView autoCompleteTextView = this.registrazioneDialog.getNationalityEditText();
                    ListAdapter listAdapter = autoCompleteTextView.getAdapter();
                    int count = listAdapter.getCount();
                    int index;
                    for (index = 0; index < count; index++) {
                        String string = (String) listAdapter.getItem(index);
                        if (string.compareTo(paese) == 0) {
                            break;
                        }
                    }
                    if (index == count) {
                        messaggioErrore = messaggioErrore + "La nazionalità che hai inserito non esiste!\n";
                    }
                }
                String userId = this.registrazioneDialog.getUserID();
                if (userId.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcuno user ID!\n";
                } else {
                    if (userId.contains(" ")) {
                        messaggioErrore = messaggioErrore + "Lo user ID contiene spazi!\n";
                    } else {
                        if (!StringsUtility.isEmailAddress(userId)) {
                            messaggioErrore = messaggioErrore + "Lo user ID non rappresenta una e-mail!\n";
                        }
                    }
                }
                String password = this.registrazioneDialog.getPassword();
                if (password.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcuna password!\n";
                } else {
                    if (password.contains(" ")) {
                        messaggioErrore = messaggioErrore + "La password contiene spazi!\n";
                    }
                }
                String confirmedPassword = this.registrazioneDialog.getConfirmPassword();
                if (confirmedPassword.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcuna password di conferma!\n";
                } else {
                    if (password.compareTo(confirmedPassword) != 0) {
                        messaggioErrore = messaggioErrore + "La password e la password di conferma non coincidono!\n";
                    }
                }
                String nickname = "";
                AlternativeRadioButton alternativeRadioButton = this.registrazioneDialog.getNomeCognomeNicknameRadioButton();
                if (!alternativeRadioButton.isChecked()) {
                    alternativeRadioButton = this.registrazioneDialog.getInserisciNicknameRadioButton();
                    if (!alternativeRadioButton.isChecked()) {
                        messaggioErrore = messaggioErrore + "Non hai selezionato alcuna opzione per il nickname!\n";
                    } else {
                        nickname = this.registrazioneDialog.getNickname();
                        if (nickname.length() == 0) {
                            messaggioErrore = messaggioErrore + "Non hai inserito alcun nickname!\n";
                        } else {
                            if (nickname.length() < NICKNAME_LUNGHEZZA_MINIMA) {
                                messaggioErrore = messaggioErrore + "Il nickname è troppo breve!\n";
                            }
                        }
                    }
                }
                alternativeRadioButton = this.registrazioneDialog.getNoGenderRadioButton();
                if (!alternativeRadioButton.isChecked()) {
                    alternativeRadioButton = this.registrazioneDialog.getMaleRadioButton();
                    if (!alternativeRadioButton.isChecked()) {
                        alternativeRadioButton = this.registrazioneDialog.getFemaleRadioButton();
                        if (!alternativeRadioButton.isChecked()) {
                            messaggioErrore = messaggioErrore + "Non hai selezionato alcuna impostazione di genere!\n";
                        }
                    }
                }
                if (messaggioErrore.length() > 0) {
                    this.messageDialog.setMessage(messaggioErrore);
                    return false;
                }
                String nickSelect = ((AbstractQueriesInterface) this.interfacciaQuery).getExistNicknameSelect(nickname);
                AbstractList<ObjectsModel> objectsModels = ((AbstractQueriesInterface) this.interfacciaQuery).select(nickSelect);
                if (objectsModels.size() != 0) {
                    messaggioErrore = "Il nickname che hai scelto non è valido, appartiene ad un utente registrato!\n";
                }
                String userIdSelect = ((AbstractQueriesInterface) this.interfacciaQuery).getExistUserIDSelect(userId, true);
                objectsModels = ((AbstractQueriesInterface) this.interfacciaQuery).select(userIdSelect);
                if (objectsModels.size() != 0) {
                    messaggioErrore = messaggioErrore + "Lo user ID che hai inserito non è valido, appartiene ad un utente registrato!\n";
                }
                if (messaggioErrore.length() > 0) {
                    this.messageDialog.setMessage(messaggioErrore);
                    this.values = null;
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
        this.codiceSender = null;
        this.values = null;
        this.progressDialog.show();
        return true;
    }

    @Override
    protected boolean executeInBackground() {
        if (this.codiceSender == null) {
            if (this.values == null) {
                String nome = this.registrazioneDialog.getName();
                String cognome = this.registrazioneDialog.getSurname();
                String data = this.registrazioneDialog.getBornDate();
                String paese = this.registrazioneDialog.getNationality();
                String userId = this.registrazioneDialog.getUserID();
                String password = this.registrazioneDialog.getPassword();
                String nickname = "";
                AlternativeRadioButton alternativeRadioButton = this.registrazioneDialog.getNomeCognomeNicknameRadioButton();
                if (!alternativeRadioButton.isChecked()) {
                    alternativeRadioButton = this.registrazioneDialog.getInserisciNicknameRadioButton();
                    if (alternativeRadioButton.isChecked()) {
                        nickname = this.registrazioneDialog.getNickname();
                    }
                } else {
                    nickname = nome + " " + cognome;
                }
                String gender = "";
                alternativeRadioButton = this.registrazioneDialog.getNoGenderRadioButton();
                if (alternativeRadioButton.isChecked()) {
                    gender = String.valueOf(alternativeRadioButton.getText());
                } else {
                    alternativeRadioButton = this.registrazioneDialog.getMaleRadioButton();
                    if (alternativeRadioButton.isChecked()) {
                        gender = String.valueOf(alternativeRadioButton.getText());
                    } else {
                        alternativeRadioButton = this.registrazioneDialog.getFemaleRadioButton();
                        if (alternativeRadioButton.isChecked()) {
                            gender = String.valueOf(alternativeRadioButton.getText());
                        }
                    }
                }
                AlternativeCheckBox alternativeCheckBox = this.registrazioneDialog.getAgeCheckBox();
                boolean etaPrivacy = alternativeCheckBox.isChecked();
                alternativeCheckBox = this.registrazioneDialog.getGenderCheckBox();
                boolean genderPrivacy = alternativeCheckBox.isChecked();
                alternativeCheckBox = this.registrazioneDialog.getNationalityCheckBox();
                boolean nazPrivacy = alternativeCheckBox.isChecked();
                this.values = new Vector<>();
                this.values.add(nome);
                this.values.add(cognome);
                this.values.add(paese);
                this.values.add(gender);
                this.values.add(data);
                String localData = DatesUtility.getLocalYYYYMMDDDate(false);
                this.values.add(localData);
                this.values.add(userId);
                this.values.add(nickname);
                this.values.add(password);
                this.values.add(etaPrivacy);
                this.values.add(genderPrivacy);
                this.values.add(nazPrivacy);
                this.codiceDialog.reset();
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean executeAfter() {
        this.progressDialog.dismiss();
        if (this.codiceSender == null) {
            if (this.values != null) {
                this.codiceSender = new CodiceSender(this.progressDialog, this.messageDialog, this.codiceDialog, (String) this.values.get(6), this.userAuthenticator);
                this.codiceSender.execute();
                return true;
            }
        }
        this.messageDialog.show();
        return false;
    }

    public class RegistrazioneDialogShowExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            mostraRegistrazioneDialog();
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
