package cvapp.handlers;

import android.app.Activity;

import java.util.Observable;

import handlers.DisableUserHandler;
import cvapp.components.CVComponentiFactory;

public class DisabilitazioneUtenteHandler extends DisableUserHandler {

    public DisabilitazioneUtenteHandler(Activity activity, CVComponentiFactory componentiFactory, AutenticazioneUtenteHandler autenticazioneUtenteHandler, Observable observable) {
        super(activity, componentiFactory, autenticazioneUtenteHandler, observable);
        this.setNoAuthenticationMessage("Non risulta alcun utente autenticato.\nSe vuoi, puoi autenticarti.\n");
        this.setDisableMessage("Non sei piu un utente autenticato e non puoi scrivere alcuna recensione.\n");
    }

}
