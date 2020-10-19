package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.PointF;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractPositionablesHandler;
import positionablecomponents.PositionableTransformer;
import cvapp.components.CVComponentiFactory;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenChoicesDialog;
import utilities.NetworkUtility;
import utilities.PositionUtility;

public class FiltraggioAttrazioniHandler extends AbstractPositionablesHandler {

    private FullscreenChoicesDialog tipologieDialog;
    private FullscreenChoicesDialog guidaDialog;
    private FullscreenCalendarDialog dataOraDialog;

    public FiltraggioAttrazioniHandler(Activity activity, PositionUtility positionUtility, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, PositionableTransformer positionableTransformer) {
        super(activity, positionUtility, (AbstractQueriesInterface) interfacciaQuery, componentiFactory, positionableTransformer, positionableTransformer, 0, positionableTransformer);
        this.positionDialog = componentiFactory.buildFullscreenInsertDialog(CVComponentiFactory.POSIZIONE_ATTRAZIONI);
        this.tipologieDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.TIPOLOGIE_ATTRAZIONI);
        this.choicesDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.SERVIZI_ATTRAZIONI);
        this.guidaDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.GUIDE_ATTRAZIONI);
        this.dataOraDialog = componentiFactory.buildFullscreenCalendarDialog(CVComponentiFactory.DATAORA_ATTRAZIONI);
        this.priceMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.COSTO_ATTRAZIONI);
        this.distanceMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.DISTANZA);
        this.numberMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.NUMERO_MASSIMO);
        this.ratingMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.VOTO_RECENSIONI_MEDIO);
        this.actualPositionCompoundButton = componentiFactory.buildCompoundButton(CVComponentiFactory.MIA_POSIZIONE);
        this.positionCompoundButton = componentiFactory.buildCompoundButton(CVComponentiFactory.DA_POSIZIONE);
        this.reverseSortCompoundButton = componentiFactory.buildCompoundButton();
        this.sortSpinner = componentiFactory.buildSpinner(CVComponentiFactory.ORDINA_ATTRAZIONI, this.reverseSortCompoundButton);
        this.setNoResultMessage("Il filtraggio non ha prodotto alcun risultato.\nNon esiste alcuna struttura appartenente a questa categoria associata ai valori che hai inserito o selezionato.\n");
        this.setNoPositionMessage("Hai spuntato \"Dalla posizione\" ma non hai inserito alcuna posizione.\n");
        this.setPositionDisabledMessage("Hai spuntato \"Dalla mia posizione\" o \"Dalla posizione\" ma la funzione Posizione non risulta attiva.\nEntrambe le funzionalità necessitano della funzione Posizione attiva.\n");
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setDistanceSortNotPossibleMessage("Hai selezionato l' ordinamento per distanza ma non hai spuntato \"Dalla mia posizione\" oppure \"Dalla posizione\"!\n");
        this.setSelectionAttribute("Distanza");
    }

    public FullscreenChoicesDialog getGuidaDialog() {
        return this.guidaDialog;
    }

    public FullscreenChoicesDialog getTipologieDialog() {
        return this.tipologieDialog;
    }

    public FullscreenCalendarDialog getDataOraDialog() {
        return this.dataOraDialog;
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
                String daPosizione = this.positionDialog.getEditedText();
                PointF position = null;
                if (this.positionCompoundButton.isChecked()) {
                    PositionUtility positionUtility = this.getPositionUtility();
                    position = positionUtility.getLocationLatAndLon(daPosizione);
                    if (position == null) {
                        alertDialog.setMessage("La posizione che hai inserito non esiste!\n");
                        return false;
                    }
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
        }
        return false;
    }

    @Override
    protected String buildRequest() {
        double minCosto = this.priceMultiSlider.getSelectedMinValue();
        double maxCosto = this.priceMultiSlider.getSelectedMaxValue();
        double minVotoRecensioniMedio = this.ratingMultiSlider.getSelectedMinValue();
        double maxVotoRecensioniMedio = this.ratingMultiSlider.getSelectedMaxValue();
        String daPosizione = this.positionDialog.getEditedText();
        String data = this.dataOraDialog.getDateInYYYYMMDDFormat();
        String ora = this.dataOraDialog.getHourWithMinutes();
        String tipologie[] = this.tipologieDialog.getSelectedsAsArray();
        String tipologieGuida[] = this.guidaDialog.getSelectedsAsArray();
        String servizi[] = this.choicesDialog.getSelectedsAsArray();
        double maxDistanza = this.distanceMultiSlider.getSelectedMaxValue();
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
        String selectAttrazioni = interfacciaQuery.getSelectAttrazioni(data, ora, minCosto, maxCosto, minVotoRecensioniMedio, maxVotoRecensioniMedio, tipologie, servizi, tipologieGuida, position, maxDistanza, attributo, this.reverseSortCompoundButton.isChecked(), numMax);
        return selectAttrazioni;
    }

}
