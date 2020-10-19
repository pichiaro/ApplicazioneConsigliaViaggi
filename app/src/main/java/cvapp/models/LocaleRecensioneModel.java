package cvapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import models.AbstractReviewModel;

public class LocaleRecensioneModel extends AbstractReviewModel {

    @Override
    public Bitmap getImage() {
        try {
            Object images = this.getValue(0);
            Serializable serializable = (Serializable) images;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
            byte[] data = byteArrayOutputStream.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        }
        catch(Exception exception) {
            return (Bitmap) this.getValue(0);
        }
    }

    @Override
    public String getUser() {
        return (String) this.getValue(1);
    }

    @Override
    public String getDate() {
        return (String) this.getValue(2);
    }

    @Override
    public String getTitle() {
        return (String) this.getValue(3);
    }

    @Override
    public float getRating() {
        return Float.parseFloat(String.valueOf(this.getValue(4)));
    }

    @Override
    public String getText() {
        return (String) this.getValue(5);
    }

}
