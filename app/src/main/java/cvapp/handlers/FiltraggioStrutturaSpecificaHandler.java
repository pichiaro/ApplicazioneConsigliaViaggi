package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;


import java.util.AbstractList;
import java.util.LinkedList;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractResultsHandler;
import positionablecomponents.PositionableTransformer;
import cvapp.components.CVComponentiFactory;
import graphiccomponents.FullscreenInsertDialog;
import models.ObjectsModel;
import utilities.NetworkUtility;

public class FiltraggioStrutturaSpecificaHandler extends AbstractResultsHandler {

    private final static String SEPARATORE = "XXXX";
    private FullscreenInsertDialog ricercaSpecificaDialog;
    private PositionableTransformer positionableTransformer;

    public FiltraggioStrutturaSpecificaHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, PositionableTransformer positionableTransformer) {
        super(activity, (AbstractQueriesInterface) interfacciaQuery, componentiFactory, positionableTransformer, positionableTransformer, 0);
        this.ricercaSpecificaDialog = componentiFactory.buildFullscreenInsertDialog(CVComponentiFactory.RICERCA_SPECIFICA);
        this.positionableTransformer = positionableTransformer;
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
    }

    public void setRicercaSpecificaDialog(FullscreenInsertDialog ricercaSpecificaDialog) {
        this.ricercaSpecificaDialog = ricercaSpecificaDialog;
    }

    public FullscreenInsertDialog getRicercaSpecificaDialog() {
        return this.ricercaSpecificaDialog;
    }

    public void setPositionableTransformer(PositionableTransformer positionableTransformer) {
        this.positionableTransformer = positionableTransformer;
    }

    public PositionableTransformer getPositionableTransformer() {
        return this.positionableTransformer;
    }

    public void mostraRicercaSpecificaDialog() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (NetworkUtility.isNetworkEnabled(this.getActivity())) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                this.ricercaSpecificaDialog.reset();
                this.ricercaSpecificaDialog.show();
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
        }
        else {
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
                String string = this.ricercaSpecificaDialog.getEditedText();
                if (string.length() == 0) {
                    alertDialog.setMessage("Non hai inserito nulla!\n");
                    return false;
                }
                return true;
            }
            alertDialog.setMessage("Connessione al database assente! Riprova successivamente.\n");
            return false;
        }
        alertDialog.setMessage(this.getNetworkDisabledMessage());
        return false;
    }

    @Override
    protected boolean executeInBackground() {
        String richiesta = this.buildRequest();
        String[] splitted = richiesta.split(FiltraggioStrutturaSpecificaHandler.SEPARATORE);
        AbstractQueriesInterface queriesInterface = this.getQueriesInterface();
        this.setObjectsModels(new LinkedList<>());
        AbstractList<ObjectsModel> objectsModels = this.getObjectsModels();
        for (int index = 0; index < splitted.length; index++) {
            AbstractList<ObjectsModel> objectsModelsIth = queriesInterface.select(splitted[index]);
            objectsModels.addAll(objectsModelsIth);
        }
        AlertDialog alertDialog = this.getMessageDialog();
        if (objectsModels.size() == 0) {
            alertDialog.setMessage("La struttura che stai cercando non esiste, riprova.\n");
            return false;
        }
        if (objectsModels.size() > 1) {
            alertDialog.setMessage("Ho riscontrato un errore nel database.\n");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executeAfter() {
        this.positionableTransformer.resetMarkers();
        if (super.executeAfter()) {
            this.ricercaSpecificaDialog.dismiss();
            return true;
        }
        return false;
    }

    @Override
    protected String buildRequest() {
        String string = this.ricercaSpecificaDialog.getEditedText();
        InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
        String selectAlbergo = interfacciaQuery.getSelectSpecificaAlbergo(string);
        String selectRistorante = interfacciaQuery.getSelectSpecificaRistorante(string);
        String selectAttrazione = interfacciaQuery.getSelectSpecificaAttrazione(string);
        String selectFND = interfacciaQuery.getSelectSpecificaFND(string);
        return selectAlbergo + FiltraggioStrutturaSpecificaHandler.SEPARATORE + FiltraggioStrutturaSpecificaHandler.SEPARATORE + selectRistorante + FiltraggioStrutturaSpecificaHandler.SEPARATORE + selectAttrazione + FiltraggioStrutturaSpecificaHandler.SEPARATORE + selectFND;
    }

}
