package cvapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Vector;


public class LocaleFNDModel extends AbstractFNDModel {

    @Override
    public AbstractList<Bitmap> getBitmaps() {
        Vector<Bitmap> vector = new Vector<>();
        try {
            Object images = this.getValue(0);
            Serializable[] serializables = (Serializable[]) images;
            for (int index = 0; index < serializables.length; index++) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(serializables[index]);
                objectOutputStream.flush();
                byte[] data = byteArrayOutputStream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                vector.add(bitmap);
            }
        }
        catch(Exception exception) {
            vector.add((Bitmap) this.getValue(1));
            vector.add((Bitmap) this.getValue(0));
        }
        return vector;
    }

    @Override
    public String getID() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(1);
        }
        return String.valueOf(this.getValue(2));
    }

    @Override
    public String getName() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(2);
        }
        return (String) this.getValue(3);
    }

    @Override
    public String getAddress() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(3);
        }
        return (String) this.getValue(4);
    }

    @Override
    public String getCity() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(4);
        }
        return (String) this.getValue(5);
    }

    @Override
    public String getCountry() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(5);
        }
        return ((String) this.getValue(6));
    }

    @Override
    public String getTipologia() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(6);
        }
        return ((String) this.getValue(7));
    }

    @Override
    public float getCosto() {
        if (this.getValue(0) instanceof Serializable[]) {
            return Float.parseFloat(String.valueOf(this.getValue(7)));
        }
        return Float.parseFloat(String.valueOf(this.getValue(8)));
    }

    @Override
    public String getGiornoChiusura() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(8);
        }
        return ((String) this.getValue(9));
    }

    @Override
    public String getOraApertura() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(9);
        }
        return ((String) this.getValue(10));
    }

    @Override
    public String getOraChiusura() {
        if (this.getValue(0) instanceof Serializable[]) {
            return (String) this.getValue(10);
        }
        return ((String) this.getValue(11));
    }

    @Override
    public AbstractList<String> getServizi() {
        String string = "";
        if (this.getValue(0) instanceof Serializable[]) {
            string = (String) this.getValue(11);
        }
        else {
            string = (String) this.getValue(12);
        }
        String[] split = string.split("\n");
        Vector<String> vector = new Vector<>();
        for (int index = 0; index < split.length; index++) {
            vector.add(split[index]);
        }
        return vector;
    }

    @Override
    public float getRating() {
        if (this.getValue(0) instanceof Serializable[]) {
            return Float.parseFloat(String.valueOf(this.getValue(12)));
        }
        return Float.parseFloat(String.valueOf(this.getValue(13)));
    }

    @Override
    public float getLatitude() {
        if (this.getValue(0) instanceof Serializable[]) {
            return Float.parseFloat(String.valueOf(this.getValue(13)));
        }
        return Float.parseFloat(String.valueOf(this.getValue(14)));
    }

    @Override
    public float getLongitude() {
        if (this.getValue(0) instanceof Serializable[]) {
            return Float.parseFloat(String.valueOf(this.getValue(14)));
        }
        return Float.parseFloat(String.valueOf(this.getValue(15)));
    }

}