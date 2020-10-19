package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Spinner;

import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractResultsHandler;
import positionablecomponents.LastClickedPositionableSaver;
import positionablecomponents.Positionable;
import cvapp.components.CVComponentiFactory;
import cvapp.observables.AbstractRecensioniTransformer;
import cvapp.views.AbstractStrutturaView;
import graphiccomponents.AlternativeCheckBox;
import graphiccomponents.DoubleThumbMultiSlider;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenChoicesDialog;
import utilities.NetworkUtility;

public class FiltraggioRecensioniHandler extends AbstractResultsHandler {

    private FullscreenChoicesDialog votiDialog;
    private FullscreenCalendarDialog daDataDialog;
    private FullscreenCalendarDialog aDataDialog;
    private Spinner ordinaSpinner;
    private DoubleThumbMultiSlider numeroMassimoMultiSlider;
    private AlternativeCheckBox decrescenteCheckBox;
    private AbstractStrutturaView strutturaView;


    public FiltraggioRecensioniHandler(Activity activity, InterfacciaQuery interfacciaQuery, CVComponentiFactory componentiFactory, AbstractRecensioniTransformer recensioniTransformer) {
        super(activity, (AbstractQueriesInterface) interfacciaQuery, componentiFactory, recensioniTransformer, recensioniTransformer, 0);
        this.votiDialog = componentiFactory.buildFullscreenChoicesDialog(CVComponentiFactory.VOTI_RECENSIONI);
        this.aDataDialog = componentiFactory.buildFullscreenCalendarDialog(CVComponentiFactory.ADATA_RECENSIONI);
        this.daDataDialog = componentiFactory.buildFullscreenCalendarDialog(CVComponentiFactory.DADATA_RECENSIONI);
        this.decrescenteCheckBox = (AlternativeCheckBox) componentiFactory.buildCompoundButton();
        this.ordinaSpinner = componentiFactory.buildSpinner(CVComponentiFactory.ORDINA_RECENSIONI, this.decrescenteCheckBox);
        this.numeroMassimoMultiSlider = componentiFactory.buildDoubleThumbMultiSlider(CVComponentiFactory.NUMERO_MASSIMO);
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
        this.setNoResultMessage("Il filtraggio non ha prodotto alcun risultato.\nNon esiste alcuna recensione di questa struttura associata ai valori che hai inserito o selezionato.\n");
    }

    public void setStrutturaView(AbstractStrutturaView strutturaView) {
        this.strutturaView = strutturaView;
    }

    public AbstractStrutturaView getStrutturaView() {
        return this.strutturaView;
    }

    public void setADataDialog(FullscreenCalendarDialog aDataDialog) {
        this.aDataDialog = aDataDialog;
    }

    public FullscreenCalendarDialog getADataDialog() {
        return this.aDataDialog;
    }

    public void setaDaDataDialog(FullscreenCalendarDialog daDataDialog) {
        this.daDataDialog = daDataDialog;
    }

    public FullscreenCalendarDialog getDaDataDialog() {
        return this.daDataDialog;
    }

    public void setVotiDialog(FullscreenChoicesDialog votiDialog) {
        this.votiDialog = votiDialog;
    }

    public FullscreenChoicesDialog getVotiDialog() {
        return this.votiDialog;
    }

    public void setNumeroMassimoMultiSlider(DoubleThumbMultiSlider numeroMassimoMultiSlider) {
        this.numeroMassimoMultiSlider = numeroMassimoMultiSlider;
    }

    public DoubleThumbMultiSlider getNumeroMassimoMultiSlider() {
        return this.numeroMassimoMultiSlider;
    }

    public void setDecrescenteCheckBox(AlternativeCheckBox decrescenteCheckBox) {
        this.decrescenteCheckBox = decrescenteCheckBox;
    }

    public AlternativeCheckBox getDecrescenteCheckBox() {
        return this.decrescenteCheckBox;
    }

    public void setOrdinaSpinner(Spinner ordinaSpinner) {
        this.ordinaSpinner = ordinaSpinner;
    }

    public Spinner getOrdinaSpinner() {
        return this.ordinaSpinner;
    }

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (NetworkUtility.isNetworkEnabled(this.getActivity())) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (interfacciaQuery.esisteConnessioneSingleton()) {
                String daData = this.daDataDialog.getDateInYYYYMMDDFormat();
                String aData = this.aDataDialog.getDateInYYYYMMDDFormat();
                if (daData.length() == 0 && aData.length() > 0) {
                    alertDialog.setMessage("Hai inserito una data finale ma nessuna data iniziale!\n");
                    return false;
                } else {
                    if (aData.length() > 0 && daData.compareTo(aData) > 0) {
                        alertDialog.setMessage("La data iniziale è successiva a quella finale!\n");
                        return false;
                    }
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
    protected String buildRequest() {
        String[] voti = this.votiDialog.getSelectedsAsArray();
        int numMax = this.numeroMassimoMultiSlider.getSelectedMaxValue();
        String daData = this.daDataDialog.getDateInYYYYMMDDFormat();
        String aData = this.aDataDialog.getDateInYYYYMMDDFormat();
        String attributo = (String) this.ordinaSpinner.getSelectedItem();
        InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
        String selectRecensioni = interfacciaQuery.getSelectRecensioni(this.strutturaView, voti, daData, aData, attributo, this.decrescenteCheckBox.isChecked(), numMax);
        return selectRecensioni;
    }

    public class LastClickedStrutturaViewSetter extends LastClickedPositionableSaver {

        @Override
        public void onClick(View view) {
            super.onClick(view);
            Positionable positionable = this.getPositionable();
            setStrutturaView((AbstractStrutturaView) positionable);
        }

    }

}
