package cvapp.observables;

import java.util.AbstractList;

import cvapp.components.CVComponentiFactory;
import cvapp.views.UtenteView;
import models.ObjectsModel;

public class ProfiloUtenteObservable extends AbstractProfiloUtenteTransformer {

    public ProfiloUtenteObservable(CVComponentiFactory componentiFactory) {
        super(componentiFactory);
    }

    @Override
    public void transform(AbstractList<ObjectsModel> objectsModels, int option) {
        if (option >= 0) {
            if (option < objectsModels.size()) {
                ObjectsModel objectsModel = objectsModels.get(option);
                CVComponentiFactory componentiFactory = this.getComponentiFactory();
                UtenteView utenteView = (UtenteView) componentiFactory.buildView(objectsModel);
                this.setUtenteView(utenteView);
                this.setChanged();
            }
        }
    }

}
