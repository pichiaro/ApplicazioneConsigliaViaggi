package cvapp.observables;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;

import cvapp.components.CVComponentiFactory;
import cvapp.views.RecensioneView;
import models.ObjectsModel;

public class RecensioniObservable extends AbstractRecensioniTransformer {

    public RecensioniObservable(CVComponentiFactory componentsFactory) {
        super(componentsFactory);
        this.setRecensioneViews(new LinkedList<>());
    }

    @Override
    public void transform(AbstractList<ObjectsModel> objectsModels, int option) {
        AbstractList<RecensioneView> recensioneViews = this.getRecensioneViews();
        recensioneViews.removeAll(recensioneViews);
        if (objectsModels.size() > 0) {
            CVComponentiFactory componentiFactory = this.getComponentiFactory();
            Iterator<ObjectsModel> iterator = objectsModels.iterator();
            while (iterator.hasNext()) {
                ObjectsModel objectsModel = iterator.next();
                RecensioneView recensioneView = (RecensioneView) componentiFactory.buildView(objectsModel);
                recensioneViews.add(recensioneView);
            }
            this.setChanged();
        }
    }

}
