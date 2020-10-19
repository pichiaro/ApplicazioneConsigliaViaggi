package cvapp.activities;

import java.util.Observable;
import java.util.Observer;

import cvapp.handlers.VisualizzazioneProfiloUtenteHandler;

public class ProfiloUtenteObserver implements Observer {

    private VisualizzazioneProfiloUtenteHandler visualizzazioneProfiloUtenteHandler;

    public ProfiloUtenteObserver(VisualizzazioneProfiloUtenteHandler visualizzazioneProfiloUtenteHandler) {
        this.visualizzazioneProfiloUtenteHandler = visualizzazioneProfiloUtenteHandler;
    }

    public void setVisualizzazioneProfiloUtenteHandler(VisualizzazioneProfiloUtenteHandler visualizzazioneProfiloUtenteHandler) {
        this.visualizzazioneProfiloUtenteHandler = visualizzazioneProfiloUtenteHandler;
    }

    public VisualizzazioneProfiloUtenteHandler getVisualizzazioneProfiloUtenteHandler() {
        return visualizzazioneProfiloUtenteHandler;
    }

    @Override
    public void update(Observable observable, Object object) {
        this.visualizzazioneProfiloUtenteHandler.mostraProfiloUtente();
    }

}
