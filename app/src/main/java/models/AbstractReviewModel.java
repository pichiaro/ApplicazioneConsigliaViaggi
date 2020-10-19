package models;

import android.graphics.Bitmap;

public abstract class AbstractReviewModel extends ObjectsModel {

    public AbstractReviewModel(AbstractReviewModel objectsModel) {
        super(objectsModel);
    }

    public AbstractReviewModel() {

    }

    public abstract Bitmap getImage();

    public abstract String getUser();

    public abstract String getDate();

    public abstract String getTitle();

    public abstract String getText();

    public abstract float getRating();

}
