package cvapp.models;

import java.util.AbstractList;

import models.AbstractStructureModel;

public abstract  class AbstractFNDModel extends AbstractStructureModel {

    abstract public AbstractList<String> getServizi();

    abstract public String getTipologia();

    abstract public float getCosto();

    abstract public String getGiornoChiusura();

    abstract public String getOraChiusura();

    abstract public String getOraApertura();

}

