package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.PointF;
import android.widget.NumberPicker;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractPositionablesHandler;
import positionablecomponents.PositionableTransformer;
import cvapp.components.CVComponentiFactory;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenChoicesDialog;
import utilities.NetworkUtility;
import utilities.PositionUtility;

public class FiltraggioAlberghiHandler extends AbstractPositionablesHandler {

    private FullscreenChoicesDialog tipologieDialog;
    private FullscreenChoicesDialog serviziRistorazioneDialog;
    private FullscreenChoicesDialog stelleDialog;
    private FullscreenCalendarDialog checkInDialog;
    private FullscreenCalendarDialog checkOutDialog;
    private NumberPicker numeroAdultiNumberPicker;
    private NumberPicker numeroBambiniNumberPicker;

    public FiltraggioAlberghiHandler(Activity activity, PositionUtility positionUtility, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, PositionableTransformer positionableTransformer) {
        super(activity, positionUtility, (AbstractQueriesInterface) interfacciaQuery, componentiFactory, positionableTransformer, positionableTransformer, 0, positionableTransformer);
        this.positionDialog = componentiFactory.buildFullscreenInsertDialog(CVComponentiFactory.POSIZIONE_ALBERGHI);
        this.tipologieDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.TIPOLOGIE_ALBERGHI);
        this.choicesDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.SERVIZI_ALBERGHI);
        this.serviziRistorazioneDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.SERVIZI_RISTORAZIONE_ALBERGHI);
        this.stelleDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.STELLE_ALBERGHI);
        this.checkInDialog = componentiFactory.buildFullscreenCalendarDialog(CVComponentiFactory.CHECKIN_ALBERGHI);
        this.checkOutDialog = componentiFactory.buildFullscreenCalendarDialog(CVComponentiFactory.CHECKOUT_ALBERGHI);
        this.priceMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.COSTO_ALBERGHI);
        this.distanceMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.DISTANZA);
        this.numberMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.NUMERO_MASSIMO);
        this.ratingMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.VOTO_RECENSIONI_MEDIO);
        this.actualPositionCompoundButton = componentiFactory.buildCompoundButton(CVComponentiFactory.MIA_POSIZIONE);
        this.positionCompoundButton = componentiFactory.buildCompoundButton(CVComponentiFactory.DA_POSIZIONE);
        this.reverseSortCompoundButton = componentiFactory.buildCompoundButton();
        this.sortSpinner = componentiFactory.buildSpinner(CVComponentiFactory.ORDINA_ALBERGHI, this.reverseSortCompoundButton);
        this.numeroAdultiNumberPicker = componentiFactory.buildNumberPicker();
        this.numeroBambiniNumberPicker = componentiFactory.buildNumberPicker();
        this.setNoResultMessage("Il filtraggio non ha prodotto alcun risultato.\nNon esiste alcuna struttura appartenente a questa categoria associata ai valori che hai inserito o selezionato.\n");
        this.setNoPositionMessage("Hai spuntato \"Dalla posizione\" ma non hai inserito alcuna posizione.\n");
        this.setPositionDisabledMessage("Hai spuntato \"Dalla mia posizione\" o \"Dalla posizione\" ma la funzione Posizione non risulta attiva.\nEntrambe le funzionalità necessitano della funzione Posizione attiva.\n");
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setDistanceSortNotPossibleMessage("Hai selezionato l' ordinamento per distanza ma non hai spuntato \"Dalla mia posizione\" oppure \"Dalla posizione\"!\n");
        this.setSelectionAttribute("Distanza");
    }

    public FullscreenChoicesDialog getServiziRistorazioneDialog() {
        return this.serviziRistorazioneDialog;
    }

    public FullscreenChoicesDialog getTipologieDialog() {
        return this.tipologieDialog;
    }

    public FullscreenChoicesDialog getStelleDialog() {
        return this.stelleDialog;
    }

    public FullscreenCalendarDialog getCheckInDialog() {
        return this.checkInDialog;
    }

    public FullscreenCalendarDialog getCheckOutDialog() {
        return this.checkOutDialog;
    }

    public NumberPicker getNumeroAdultiNumberPicker() {
        return this.numeroAdultiNumberPicker;
    }

    public NumberPicker getNumeroBambiniNumberPicker() {
        return this.numeroBambiniNumberPicker;
    }

    protected boolean checkPositionCondition() {
        return PositionUtility.isLocationEnabled(this.getActivity());
    }

    protected boolean checkNetworkCondition() {
        return NetworkUtility.isNetworkEnabled(this.getActivity());
    }

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (super.checkFunctionsStatus()) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                String dataArrivo = this.checkInDialog.getDateInYYYYMMDDFormat();
                String oraArrivo = this.checkInDialog.getHourWithMinutes();
                String dataPartenza = this.checkOutDialog.getDateInYYYYMMDDFormat();
                String oraPartenza = this.checkOutDialog.getHourWithMinutes();
                String messaggioErrore = "";
                if (dataArrivo.length() == 0 && dataPartenza.length() > 0) {
                    messaggioErrore = "Hai selezionato una data di partenza ma non una di arrivo!\n";
                } else {
                    if (dataArrivo.length() > 0 && dataPartenza.length() > 0) {
                        if (dataArrivo.compareTo(dataPartenza) > 0) {
                            messaggioErrore = "Hai selezionato una data di arrivo successiva alla data di partenza!\n";
                        } else {
                            if (dataArrivo.compareTo(dataPartenza) == 0) {
                                if (oraPartenza.compareTo(oraArrivo) <= 0) {
                                    messaggioErrore = "Hai selezionato un orario di arrivo successivo o uguale all' orario di partenza!\n";
                                }
                            }
                        }
                    }
                }
                PointF position = null;
                String daPosizione = this.positionDialog.getEditedText();
                if (this.positionCompoundButton.isChecked()) {
                    PositionUtility positionUtility = this.getPositionUtility();
                    position = positionUtility.getLocationLatAndLon(daPosizione);
                    if (position == null) {
                        messaggioErrore = messaggioErrore + "La posizione che hai inserito non esiste!\n";
                    }
                }
                if (messaggioErrore.length() > 0) {
                    alertDialog.setMessage(messaggioErrore);
                    return false;
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
        }
        return false;
    }

    @Override
    protected String buildRequest() {
        String[] tipologie = this.tipologieDialog.getSelectedsAsArray();
        String[] stelle = this.stelleDialog.getSelectedsAsArray();
        if (stelle != null) {
            for (int i = 0; i < stelle.length; i++) {
                if (stelle[i].compareTo("Nessuna") == 0) {
                    stelle[i] = "0";
                } else {
                    stelle[i] = stelle[i].replaceFirst(" stelle| stella", "");
                }
            }
        }
        String[] serviziRistorazione = this.serviziRistorazioneDialog.getSelectedsAsArray();
        String[] servizi = this.choicesDialog.getSelectedsAsArray();
        int numeroAdulti = this.numeroAdultiNumberPicker.getValue();
        int numeroBambini = this.numeroBambiniNumberPicker.getValue();
        String dataArrivo = this.checkInDialog.getDateInYYYYMMDDFormat();
        String oraArrivo = this.checkInDialog.getHourWithMinutes();
        String dataPartenza = this.checkOutDialog.getDateInYYYYMMDDFormat();
        String oraPartenza = this.checkOutDialog.getHourWithMinutes();
        double minCosto = this.priceMultiSlider.getSelectedMinValue();
        double maxCosto = this.priceMultiSlider.getSelectedMaxValue();
        double minVotoRecensioniMedio = this.ratingMultiSlider.getSelectedMinValue();
        double maxVotoRecensioniMedio = this.ratingMultiSlider.getSelectedMaxValue();
        double maxDistanza = this.distanceMultiSlider.getSelectedMaxValue();
        String daPosizione = this.positionDialog.getEditedText();
        int numMax = this.numberMultiSlider.getSelectedMaxValue();
        String attributo = (String) this.sortSpinner.getSelectedItem();
        InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
        PointF position = null;
        if (this.actualPositionCompoundButton.isChecked()) {
            PositionUtility positionUtility = this.getPositionUtility();
            position = positionUtility.getActualLocation();
            PositionableTransformer positionableTransformer = (PositionableTransformer) this.getPositionableOperations();
            positionableTransformer.setPosition(position);
        } else {
            if (this.positionCompoundButton.isChecked()) {
                PositionUtility positionUtility = this.getPositionUtility();
                position = positionUtility.getLocationLatAndLon(daPosizione);
                PositionableTransformer positionableTransformer = (PositionableTransformer) this.getPositionableOperations();
                positionableTransformer.setPosition(position);
            }
        }
        String selectAlberghi = interfacciaQuery.getSelectAlberghi(minVotoRecensioniMedio, maxVotoRecensioniMedio, dataArrivo, oraArrivo, dataPartenza, oraPartenza, numeroAdulti, numeroBambini, minCosto, maxCosto, tipologie, stelle, servizi, serviziRistorazione, position, maxDistanza, attributo, this.reverseSortCompoundButton.isChecked(), numMax);
        return selectAlberghi;
    }

}
