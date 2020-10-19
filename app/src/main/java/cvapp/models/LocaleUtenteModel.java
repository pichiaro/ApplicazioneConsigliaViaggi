package cvapp.models;

import models.AbstractUserPublicModel;

public class LocaleUtenteModel extends AbstractUserPublicModel {

    @Override
    public String getNationality() {
        return (String) this.getValue(0);
    }

    @Override
    public String getGender() {
        String sesso = (String) this.getValue(1);
        if (sesso.compareTo("m") == 0) {
            sesso = "uomo";
        } else {
            if (sesso.compareTo("f") == 0) {
                sesso = "donna";
            } else {
                if (sesso.compareTo("x") == 0) {
                    sesso = "no gender";
                }
            }
        }
        return sesso;
    }

    @Override
    public String getBornDate() {
        return (String) this.getValue(2);
    }

    @Override
    public String getRegistrationDate() {
        return (String) this.getValue(3);
    }

    @Override
    public String getUser() {
        return (String) this.getValue(4);
    }

    @Override
    public boolean isNationalityVisible() {
        int flag = (int) this.getValue(5);
        if (flag == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAgeVisible() {
        int flag = (int) this.getValue(6);
        if (flag == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isGenderVisible() {
        int flag = (int) this.getValue(7);
        if (flag == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int getAssociatedsCount() {
        return (int) this.getValue(8);
    }

}
