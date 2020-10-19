package cvapp.handlers;

import android.app.Activity;

import android.app.AlertDialog;
import android.widget.CheckBox;

import java.util.AbstractList;
import java.util.LinkedList;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractPositionableNearnessHandler;
import positionablecomponents.PositionableTransformer;
import cvapp.components.CVComponentiFactory;
import cvapp.views.AbstractStrutturaView;
import graphiccomponents.DoubleThumbMultiSlider;
import utilities.NetworkUtility;
import models.ObjectsModel;

public class FiltraggioStruttureVicineHandler extends AbstractPositionableNearnessHandler {

    private final static String SEPARATORE = "XXXX";
    private CheckBox alberghiCheckBox;
    private CheckBox ristorantiCheckBox;
    private CheckBox attrazioniCheckBox;
    private CheckBox fndCheckBox;
    private DoubleThumbMultiSlider distanceMultiSlider;
    private DoubleThumbMultiSlider numberMultiSlider;

    public FiltraggioStruttureVicineHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, PositionableTransformer positionableTransformer) {
        super(activity, (AbstractQueriesInterface) interfacciaQuery, componentiFactory, positionableTransformer, positionableTransformer,0, positionableTransformer);
        this.alberghiCheckBox = new CheckBox(activity);
        this.alberghiCheckBox.setText("Alberghi");
        this.ristorantiCheckBox = new CheckBox(activity);
        this.ristorantiCheckBox.setText("Ristoranti");
        this.attrazioniCheckBox = new CheckBox(activity);
        this.attrazioniCheckBox.setText("Attrazioni");
        this.fndCheckBox = new CheckBox(activity);
        this.fndCheckBox.setText("Food&Drink");
        this.distanceMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.DISTANZA);
        this.numberMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.NUMERO_MASSIMO);
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setNoResultMessage("Il filtraggio non ha prodotto alcun risultato.\nNon esiste alcuna struttura appartenente a questa categoria associata ai valori che hai inserito o selezionato.\n");
    }

    public void setDistanceMultiSlider(DoubleThumbMultiSlider distanceMultiSlider) {
        this.distanceMultiSlider = distanceMultiSlider;
    }

    public DoubleThumbMultiSlider getDistanceMultiSlider() {
        return this.distanceMultiSlider;
    }

    public void setNumberMultiSlider(DoubleThumbMultiSlider numberMultiSlider) {
        this.numberMultiSlider = numberMultiSlider;
    }

    public DoubleThumbMultiSlider getNumberMultiSlider() {
        return this.numberMultiSlider;
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
                if (!this.alberghiCheckBox.isChecked() && !this.ristorantiCheckBox.isChecked() && !this.fndCheckBox.isChecked() && !this.attrazioniCheckBox.isChecked()) {
                    alertDialog.setMessage("\nNon hai spuntato alcuna checkbox!");
                    return false;
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
        }
        return false;
    }

    @Override
    public int getRadius() {
        return this.distanceMultiSlider.getSelectedMaxValue();
    }

    @Override
    protected boolean executeInBackground() {
        String richiesta = this.buildRequest();
        String[] splitted = richiesta.split(FiltraggioStruttureVicineHandler.SEPARATORE);
        AbstractQueriesInterface queriesInterface = this.getQueriesInterface();
        this.setObjectsModels(new LinkedList<>());
        AbstractList<ObjectsModel> objectsModels = this.getObjectsModels();
        for (int index = 0; index < splitted.length; index++) {
            AbstractList<ObjectsModel> objectsModelsIth = queriesInterface.select(splitted[index]);
            objectsModels.addAll(objectsModelsIth);
        }
        if (this.getObjectsModels().size() == 0) {
            AlertDialog alertDialog = this.getMessageDialog();
            alertDialog.setMessage(this.getNoResultMessage());
            return false;
        }
        return true;
    }

    public CheckBox getAlberghiCheckBox() {
        return this.alberghiCheckBox;
    }

    public CheckBox getRistorantiCheckBox() {
        return this.ristorantiCheckBox;
    }

    public CheckBox getFndCheckBox() {
        return this.fndCheckBox;
    }

    public CheckBox getAttrazioniCheckBox() {
        return this.attrazioniCheckBox;
    }


    @Override
    protected String buildRequest() {
        return this.costruisciSelectVicineAlberghi() + FiltraggioStruttureVicineHandler.SEPARATORE + this.costruisciSelectVicineRistoranti() + FiltraggioStruttureVicineHandler.SEPARATORE + this.costruisciSelectVicineAttrazioni() + FiltraggioStruttureVicineHandler.SEPARATORE + this.costruisciSelectVicineFND();
    }


    protected String costruisciSelectVicineAlberghi() {
        if (this.alberghiCheckBox.isChecked()) {
            AbstractStrutturaView strutturaView = (AbstractStrutturaView) this.getPositionable();
            int radius = this.distanceMultiSlider.getSelectedMaxValue();
            int limit = this.numberMultiSlider.getSelectedMaxValue();
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            return interfacciaQuery.getSelectVicineAlberghi(strutturaView, radius, limit);
        }
        return "";
    }

    protected String costruisciSelectVicineRistoranti() {
        if (this.ristorantiCheckBox.isChecked()) {
            AbstractStrutturaView strutturaView = (AbstractStrutturaView) this.getPositionable();
            int radius = this.distanceMultiSlider.getSelectedMaxValue();
            int limit = this.numberMultiSlider.getSelectedMaxValue();
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            return interfacciaQuery.getSelectVicineRistoranti(strutturaView, radius, limit);
        }
        return "";
    }

    protected String costruisciSelectVicineAttrazioni() {
        if (this.attrazioniCheckBox.isChecked()) {
            AbstractStrutturaView strutturaView = (AbstractStrutturaView) this.getPositionable();
            int radius = this.distanceMultiSlider.getSelectedMaxValue();
            int limit = this.numberMultiSlider.getSelectedMaxValue();
            InterfacciaQuery interfacciaBackEndStub = (InterfacciaQuery) this.getQueriesInterface();
            return interfacciaBackEndStub.getSelectVicineAttrazioni(strutturaView, radius, limit);
        }
        return "";
    }

    protected String costruisciSelectVicineFND() {
        if (this.fndCheckBox.isChecked()) {
            AbstractStrutturaView strutturaView = (AbstractStrutturaView) this.getPositionable();
            int radius = this.distanceMultiSlider.getSelectedMaxValue();
            int limit = this.numberMultiSlider.getSelectedMaxValue();
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            return interfacciaQuery.getSelectVicineFND(strutturaView, radius, limit);
        }
        return "";
    }


}
