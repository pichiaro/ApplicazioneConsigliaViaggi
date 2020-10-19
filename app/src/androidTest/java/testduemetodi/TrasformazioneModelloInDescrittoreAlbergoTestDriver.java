package testduemetodi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractList;
import java.util.Vector;

import cvapp.components.CVComponentiFactory;
import cvapp.models.AbstractAlbergoModel;
import cvapp.views.AlbergoView;
import models.ObjectsModel;
import positionablecomponents.Positionable;

/*
   Test del metodo buildPositionable() della Factory.
   Lo scopo del test è garantire la correttezza della
   trasformazione da modello a descrittore (view).
   Il descrittore deve essere associato correttamente al modello,
   ovvero le associazioni tra le subview e gli  attributi del
   modello devono essere corrette.
   Lo scenario descrive la situazione d' esecuzione
   con un parametro di tipo "modello d' albergo"
 */

public class TrasformazioneModelloInDescrittoreAlbergoTestDriver extends TestCase {

    private CVComponentiFactoryStub cvComponentiFactory;
    private final String tipologiaTest = "Type";
    private final Vector<String> serviziAndServiziRistorazioneTest = new Vector();
    {
        this.serviziAndServiziRistorazioneTest.add("SERVIZIO1");
        this.serviziAndServiziRistorazioneTest.add("SERVIZIO2");
        this.serviziAndServiziRistorazioneTest.add("SERVIZIO3");
    }
    private final int stelleTest = 5;
    private final float costoCamereTest = (float) 100.01;
    private final Vector<Bitmap> bitmapsTest = new Vector();
    private final String idTest = "Id";
    private final String nameTest = "Name";
    private final String addressTest = "Address";
    private final String cityTest = "City";
    private final String countryTest = "Country";
    private final float latitudeTest = (float) 40.51;
    private final float longitudeTest = (float) 14.16;
    private final float ratingTest = (float) 2.50;

    @Override
    @Before
    public void setUp() {
        try {
            super.setUp();
            AppCompatActivity activity = new AppCompatActivity();
            this.cvComponentiFactory = new CVComponentiFactoryStub(activity);
        } catch (Exception exception) {
            Log.d("", "Exception information : " + exception.getMessage());
        }
    }

    @Override
    @After
    public void tearDown() {
        try {
            super.tearDown();
            Positionable positionable = this.cvComponentiFactory.getLastPositionable();
            assertTrue("Classe descrittore non corretta", positionable instanceof AlbergoView);
            AlbergoView albergoView = (AlbergoView) positionable;
            String nomeView = albergoView.getName().replace("   ","");
            TextView fullAddressView = (TextView) albergoView.getChildAt(3);
            String fullAddress = ((String) fullAddressView.getText()).replace("   ","");
            TextView tipoView = (TextView) albergoView.getChildAt(4);
            String tipologia = ((String) tipoView.getText()).replace("   ","");
            TextView stelleView = (TextView) albergoView.getChildAt(5);
            String stelleStr = ((String) stelleView.getText()).replace("   ","");
            int stelle = Integer.parseInt(stelleStr);
            TextView costoView = (TextView) albergoView.getChildAt(6);
            String costo = ((String) costoView.getText()).replace("   | (minimo)","");
            TextView serviziView = (TextView) albergoView.getChildAt(7);
            String servizi = ((String) serviziView.getText()).replaceAll("\n  -  "," ");
            servizi = servizi.replaceAll("\n","");
            RatingBar ratingBar = (RatingBar) albergoView.getChildAt(8);
            float rating = ratingBar.getRating();
            assertEquals("Nome non corretto", nomeView,this.nameTest);
            assertEquals("Indirizzo / città / paese non corretto", fullAddress,this.addressTest + ", " + this.cityTest + ", " + this.countryTest);
            assertEquals("Tipo non corretto", tipologia, this.tipologiaTest);
            assertEquals("Stelle non corretto", stelle, this.stelleTest);
            assertEquals("Costo non corretto", costo, this.costoCamereTest);
            assertEquals("Servizi / Servizi rist. non corretti", servizi, this.serviziAndServiziRistorazioneTest.get(0) + " " + this.serviziAndServiziRistorazioneTest.get(1) + " " + this.serviziAndServiziRistorazioneTest.get(2));
            assertEquals("Voto non corretto",rating,this.ratingTest);
            String id = albergoView.getPositionID();
            float lat = albergoView.getLatitude();
            float lon = albergoView.getLongitude();
            assertEquals("ID non corretto", this.idTest, id);
            assertTrue("Coordinate non corrette", lat == this.latitudeTest && lon == this.longitudeTest);
        } catch (Exception exception) {
            Log.d("", "Exception information : " + exception.getMessage());
        }
    }

    @Test
    public void eseguiTest() {
        TestAlbergoModel albergoModel = new TestAlbergoModel();
        Positionable positionable = this.cvComponentiFactory.buildPositionable(albergoModel);
        assertTrue("Classe descrittore non corretta", positionable instanceof AlbergoView);
    }

    // Stub : Un modello appositamente creato per il test

    public class TestAlbergoModel extends AbstractAlbergoModel {

        public String getTipologia() {
            return tipologiaTest;
        }

        public AbstractList<String> getServiziAndServiziRistorazione() {
            return serviziAndServiziRistorazioneTest;
        }

        public int getStelle() {
            return stelleTest;
        }

        public float getCosto() {
            return costoCamereTest;
        }

        public AbstractList<Bitmap> getBitmaps() {
            return bitmapsTest;
        }

        public String getID() {
            return idTest;
        }

        public String getName() {
            return nameTest;
        }

        public String getAddress() {
            return addressTest;
        }

        public String getCity() {
            return cityTest;
        }

        public String getCountry() {
            return countryTest;
        }

        public float getLatitude() {
            return latitudeTest;
        }

        public float getLongitude() {
            return longitudeTest;
        }

        public float getRating() {
            return ratingTest;
        }

    }

    /* Unit: differisce da CVComponentiFactory solamente dal fatto che
       l' ultimo descrittore costruito viene memorizzato.
     */

    public class CVComponentiFactoryStub extends CVComponentiFactory {

        public CVComponentiFactoryStub(Activity activity) {
            super(activity);
        }

        private Positionable lastPositionable;

        @Override
        public Positionable buildPositionable(ObjectsModel model) {
            this.lastPositionable = super.buildPositionable(model);
            return this.lastPositionable;
        }

        public Positionable getLastPositionable() {
            return this.lastPositionable;
        }

    }

}
