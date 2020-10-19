package cvapp.models;

import java.util.AbstractList;

import models.AbstractStructureModel;

public abstract class AbstractAlbergoModel extends AbstractStructureModel {

    abstract public String getTipologia();

    abstract public AbstractList<String> getServiziAndServiziRistorazione();

    abstract public int getStelle();

    abstract public float getCosto();
    
}