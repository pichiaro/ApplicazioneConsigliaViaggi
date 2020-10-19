package cvapp.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Observable;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;
import cvapp.components.InterfacciaQuery;
import handlers.AbstractResultsHandler;
import cvapp.observables.AbstractProfiloUtenteTransformer;
import cvapp.views.RecensioneView;
import cvapp.views.UtenteView;
import utilities.NetworkUtility;

public class VisualizzazioneProfiloUtenteHandler extends AbstractResultsHandler {

    private Dialog profiloUtenteDialog;
    private RecensioneView recensioneView;

    public VisualizzazioneProfiloUtenteHandler(Activity activity, InterfacciaQuery interfacciaQuery, AbstractComponentsFactory componentsFactory, Observable observable, AbstractProfiloUtenteTransformer profiloUtenteTransformer) {
        super(activity, (AbstractQueriesInterface) interfacciaQuery, componentsFactory, observable, profiloUtenteTransformer, 0);
        this.profiloUtenteDialog = componentsFactory.buildDialog();
        this.setNetworkDisabledMessage("Almeno una tra le funzioni WiFi e Mobile deve essere attiva.\nL' applicazione necessita di una connessione ad Internet.\n");
    }

    public void setRecensioneView(RecensioneView recensioneView) {
        this.recensioneView = recensioneView;
    }

    public RecensioneView getRecensioneView() {
        return this.recensioneView;
    }

    public Dialog getProfiloUtenteDialog() {
        return this.profiloUtenteDialog;
    }

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (NetworkUtility.isNetworkEnabled(this.getActivity())) {
            InterfacciaQuery interfacciaQuery = (InterfacciaQuery) this.getQueriesInterface();
            if (!interfacciaQuery.esisteConnessioneSingleton()) {
                alertDialog.setMessage("Connessione al database assente, riprova succesivamente.\n");
                return false;
            }
            return true;
        } else {
            alertDialog.setMessage(this.getNetworkDisabledMessage());
            return false;
        }
    }

    @Override
    protected String buildRequest() {
        String nickname = this.recensioneView.getNickname();
        InterfacciaQuery interfacciaBackEndStub = (InterfacciaQuery) this.getQueriesInterface();
        return interfacciaBackEndStub.getSelectProfiloUtente(nickname);
    }

    public void mostraProfiloUtente() {
        AbstractProfiloUtenteTransformer profiloUtenteTransformer = (AbstractProfiloUtenteTransformer) this.getObservable();
        UtenteView utenteView = profiloUtenteTransformer.getUtenteView();
        Bitmap bitmap = this.recensioneView.getImmagineProfilo();
        utenteView.setImmagineProfilo(bitmap);
        this.profiloUtenteDialog.show();
        this.profiloUtenteDialog.setContentView(utenteView);
    }

    public class RecensioneViewSetter implements OnClickListener {

        @Override
        public void onClick(View view) {
            while (!(view instanceof RecensioneView)) {
                view = (View) view.getParent();
            }
            if (view != null) {
                setRecensioneView((RecensioneView) view);
            }
        }

    }
}
