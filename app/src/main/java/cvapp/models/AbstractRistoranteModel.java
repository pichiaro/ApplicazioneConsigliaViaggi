package cvapp.models;

import java.util.AbstractList;

import models.AbstractStructureModel;

public abstract class AbstractRistoranteModel extends AbstractStructureModel {

    abstract public AbstractList<String> getServiziAndCucina();

    abstract public float getCosto();

    abstract public String getGiornoChiusura();

    abstract public String getOraChiusura();

    abstract public String getOraApertura();

}
