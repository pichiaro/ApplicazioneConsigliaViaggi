package models;

import android.graphics.Bitmap;

import java.util.AbstractList;

public abstract class AbstractStructureModel extends ObjectsModel {

    public AbstractStructureModel(AbstractStructureModel objectsModel) {
        super(objectsModel);
    }

    public AbstractStructureModel() {
    }

    public abstract AbstractList<Bitmap> getBitmaps();

    public abstract String getID();

    public abstract String getName();

    public abstract String getAddress();

    public abstract String getCity();

    public abstract String getCountry();

    public abstract float getLatitude();

    public abstract float getLongitude();

    public abstract float getRating();

}
