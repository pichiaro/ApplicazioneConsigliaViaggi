package models;

public abstract class AbstractUserPublicModel extends ObjectsModel {

    public AbstractUserPublicModel(AbstractUserPublicModel objectsModel) {
        super(objectsModel);
    }

    public AbstractUserPublicModel() {

    }

    public abstract String getNationality();

    public abstract String getGender();

    public abstract String getBornDate();

    public abstract String getRegistrationDate();

    public abstract String getUser();

    public abstract boolean isGenderVisible();

    public abstract boolean isAgeVisible();

    public abstract boolean isNationalityVisible();

    public abstract int getAssociatedsCount();
}
