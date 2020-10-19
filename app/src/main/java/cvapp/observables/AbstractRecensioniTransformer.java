package cvapp.observables;

import java.util.AbstractList;
import java.util.Observable;

import cvapp.components.CVComponentiFactory;
import cvapp.views.RecensioneView;
import models.ObjectsModelTransformable;

public abstract class AbstractRecensioniTransformer extends Observable implements ObjectsModelTransformable {

    private CVComponentiFactory componentiFactory;
    private AbstractList<RecensioneView> recensioneViews;

    public AbstractRecensioniTransformer(CVComponentiFactory componentsFactory) {
        this.componentiFactory = componentsFactory;
    }

    public void setRecensioneViews(AbstractList<RecensioneView> recensioneViews) {
        this.recensioneViews = recensioneViews;
    }

    public AbstractList<RecensioneView> getRecensioneViews() {
        return this.recensioneViews;
    }

    public void setComponentiFactory(CVComponentiFactory componentiFactory) {
        this.componentiFactory = componentiFactory;
    }

    public CVComponentiFactory getComponentiFactory() {
        return this.componentiFactory;
    }

}
