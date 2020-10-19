package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractInsertsHandler;
import positionablecomponents.LastClickedPositionableSaver;
import positionablecomponents.Positionable;
import cvapp.components.CVComponentiFactory;
import cvapp.views.AbstractStrutturaView;
import graphiccomponents.FullscreenReviewDialog;
import utilities.NetworkUtility;
import utilities.DatesUtility;

public class ScritturaRecensioneHandler extends AbstractInsertsHandler {

    public final static int LUNGHEZZA_MINIMA_TITOLO = 10;
    public final static int LUNGHEZZA_MINIMA_TESTO = 25;
    public final static int LUNGHEZZA_MASSIMA_TESTO = 500;
    public final static String DATA_MINIMA_RECENSIONE = "20190101";

    private FullscreenReviewDialog fullscreenReviewDialog;
    private AutenticazioneUtenteHandler autenticazioneUtenteHandler;
    private AbstractStrutturaView strutturaView;

    public ScritturaRecensioneHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, AutenticazioneUtenteHandler autenticazioneUtenteHandler) {
        super(activity, (AbstractQueriesInterface) interfacciaQuery, componentiFactory);
        this.autenticazioneUtenteHandler = autenticazioneUtenteHandler;
        this.fullscreenReviewDialog = componentiFactory.buildFullscreenReviewDialog();
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setNoResultMessage("Inserimento fallito.\nHai inserito una recensione troppo vicina temporalmente ad un' altra!");
        this.setSuccessMessage("La tua recensione è stata inviata correttamente.\nGrazie!\n");
    }

    public void setStrutturaView(AbstractStrutturaView strutturaView) {
        this.strutturaView = strutturaView;
    }

    public AbstractStrutturaView getStrutturaView() {
        return this.strutturaView;
    }

    public void setFullscreenReviewDialog(FullscreenReviewDialog fullscreenReviewDialog) {
        this.fullscreenReviewDialog = fullscreenReviewDialog;
    }

    public FullscreenReviewDialog getFullscreenReviewDialog() {
        return this.fullscreenReviewDialog;
    }

    public void setAutenticazioneUtenteHandler(AutenticazioneUtenteHandler autenticazioneUtenteHandler) {
        this.autenticazioneUtenteHandler = autenticazioneUtenteHandler;
    }

    public AutenticazioneUtenteHandler getAutenticazioneUtenteHandler() {
        return autenticazioneUtenteHandler;
    }

    public void mostraRecensioneDialog() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (NetworkUtility.isNetworkEnabled(this.getActivity())) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                if (!this.autenticazioneUtenteHandler.isLogged()) {
                    alertDialog.setMessage("La scrittura delle recensioni è possibile solo per gli utenti autenticati.\nEffettua il Login.\n");
                    alertDialog.show();
                } else {
                    this.fullscreenReviewDialog.reset();
                    this.fullscreenReviewDialog.show();
                }
            }
            else {
                alertDialog.setMessage("Connessione al database assente, riprova successivamente!\n");
                alertDialog.show();
            }
        } else {
            alertDialog.setMessage(this.getNetworkDisabledMessage());
            alertDialog.show();
        }
    }

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (NetworkUtility.isNetworkEnabled(this.getActivity())) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                if (!this.autenticazioneUtenteHandler.isLogged()) {
                    alertDialog.setMessage("La scrittura delle recensioni è possibile solo per gli utenti autenticati.\nEffettua il Login.\n");
                    return false;
                }
                String messaggioErrore = "";
                String titolo = this.fullscreenReviewDialog.getReviewTitle();
                if (titolo.length() == 0) {
                    messaggioErrore = "Non hai inserito alcun titolo!\n";
                } else {
                    if (titolo.length() < LUNGHEZZA_MINIMA_TITOLO) {
                        messaggioErrore = messaggioErrore + "Il titolo è troppo breve!\n";
                    }
                }
                String data = this.fullscreenReviewDialog.getReviewDate();
                if (data.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai selezionato alcuna data!\n";
                } else {
                    String localData = DatesUtility.getLocalYYYYMMDDDate(false);
                    if (data.compareTo(localData) > 0) {
                        messaggioErrore = messaggioErrore + "Hai selezionato una data successiva alla data odierna!\n";
                    } else {
                        if (data.compareTo(DATA_MINIMA_RECENSIONE) < 0) {
                            messaggioErrore = messaggioErrore + "Hai selezionato una data non valida!\n";
                        }
                    }
                }
                float voto = this.fullscreenReviewDialog.getReviewRating();
                if (voto == 0) {
                    messaggioErrore = messaggioErrore + "Non hai dato alcun voto!\n";
                }
                String testo = fullscreenReviewDialog.getReviewText();
                if (testo.length() == 0) {
                    messaggioErrore = messaggioErrore + "Non hai inserito alcun testo!\n";
                } else {
                    if (testo.length() < LUNGHEZZA_MINIMA_TESTO) {
                        messaggioErrore = messaggioErrore + "Il testo è troppo breve!\n";
                    } else {
                        if (testo.length() > LUNGHEZZA_MASSIMA_TESTO) {
                            messaggioErrore = messaggioErrore + "Il testo è troppo lungo!\n";
                        }
                    }
                }
                if (messaggioErrore.length() > 0) {
                    alertDialog.setMessage(messaggioErrore);
                    return false;
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente, riprova successivamente!\n");
            return false;
        }
        alertDialog.setMessage(this.getNetworkDisabledMessage());
        return false;
    }

    @Override
    protected boolean executeInBackground() {
        if (super.executeInBackground()) {
            String nicknameAutenticato = this.autenticazioneUtenteHandler.getUsername();
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            String updateRecensioni = interfacciaQuery.getUpdateRecensioniCommand(nicknameAutenticato);
            boolean updateOk = ((AbstractQueriesInterface)interfacciaQuery).executeCommand(updateRecensioni);
            if (!updateOk) {
                AlertDialog alertDialog = this.getMessageDialog();
                alertDialog.setMessage("Mi dispiace, ho riscontrato un errore nel database, riprova successivamente.\n");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected String getTablename() {
        AbstractQueriesInterface queriesInterface = (AbstractQueriesInterface) this.getQueriesInterface();
        return queriesInterface.getTableName(this.strutturaView);
    }

    @Override
    protected AbstractList<Object> buildValues() {
        FullscreenReviewDialog fullscreenReviewDialog = this.fullscreenReviewDialog;
        String titolo = fullscreenReviewDialog.getReviewTitle();
        String data = fullscreenReviewDialog.getReviewDate();
        float voto = fullscreenReviewDialog.getReviewRating();
        String testo = fullscreenReviewDialog.getReviewText();
        String strutturaId = this.strutturaView.getPositionID();
        String nicknameAutenticato = this.autenticazioneUtenteHandler.getUsername();
        InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
        return interfacciaQuery.prepareRecensioniValues(strutturaId, nicknameAutenticato, data, titolo, voto, testo);
    }

    public class LastClickedStrutturaViewSetter extends LastClickedPositionableSaver {

        @Override
        public void onClick(View view) {
            super.onClick(view);
            Positionable positionable = this.getPositionable();
            setStrutturaView((AbstractStrutturaView) positionable);
        }

    }

    public class RecensioneDialogShowExecuter implements OnClickListener {

        @Override
        public void onClick(View view) {
            mostraRecensioneDialog();
        }

    }

}
