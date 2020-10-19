package cvapp.observables;

import java.util.Observable;

import cvapp.components.CVComponentiFactory;
import cvapp.views.UtenteView;
import models.ObjectsModelTransformable;

public abstract class AbstractProfiloUtenteTransformer extends Observable implements ObjectsModelTransformable {

    private CVComponentiFactory componentiFactory;
    private UtenteView utenteView;

    public AbstractProfiloUtenteTransformer(CVComponentiFactory componentiFactory) {
        this.componentiFactory = componentiFactory;
    }

    protected void setUtenteView(UtenteView utenteView) {
        this.utenteView = utenteView;
    }

    public UtenteView getUtenteView() {
        return this.utenteView;
    }

    public void setComponentiFactory(CVComponentiFactory componentiFactory) {
        this.componentiFactory = componentiFactory;
    }

    public CVComponentiFactory getComponentiFactory() {
        return this.componentiFactory;
    }

}
