package testduemetodi;

import android.app.Activity;
import android.graphics.PointF;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;

import components.AbstractComponentsFactory;
import cvapp.components.CVComponentiFactory;
import cvapp.components.InterfacciaBackEndStub;
import cvapp.handlers.FiltraggioRistorantiHandler;
import cvapp.views.RistoranteView;
import graphiccomponents.DoubleThumbMultiSlider;
import models.ObjectsModel;
import positionablecomponents.Positionable;
import positionablecomponents.PositionableTransformer;
import utilities.PositionUtility;

/* Test del metodo executeInBackground() di FiltraggioRistorantiHandler -
   scenario: filtraggio entro una distanza massima dalla posizione attuale
   + ordinamento decrescente dei risultati in base (verifica crescenza perchè
   vengono prelevati con pop(head) e inseriti in listview con push(head))
   alla loro distanza dalla posizione attuale
   + numero massimo risultati minore o uguale a maxnum
*/

public class RistorantiViciniPosizioneAttualeTestDriver extends TestCase {

    private FiltraggioRistorantiHandlerUnit filtraggioRistorantiHandlerUnit;
    private final int fattoreTolleranza = 100;
    private final int distanzaTest = 2500;
    private final float latitudineNapoli = (float) 40.51;
    private final float longitudineNapoli = (float) 14.16;
    private final int maxNum = 50;

    @Override
    @Before
    public void setUp() {
        try {
            super.setUp();
            assertTrue("Valore tolleranza errato", this.fattoreTolleranza > 0 && this.fattoreTolleranza <= 100);
            assertTrue("Maxnum negativo" , this.maxNum > 0);
            AppCompatActivity activity = new AppCompatActivity();
            InterfacciaBackEndStub interfacciaQueryStub = new InterfacciaBackEndStub(activity, "backendconfig.properties");
            interfacciaQueryStub.initialize();
            CVComponentiFactory cvComponentiFactory = new CVComponentiFactory(activity);
            PositionableTransformerStub positionableTransformer = new PositionableTransformerStub(cvComponentiFactory);
            PositionUtilityStub positionUtilityStub = new PositionUtilityStub();
            this.filtraggioRistorantiHandlerUnit = new FiltraggioRistorantiHandlerUnit(activity, positionUtilityStub, interfacciaQueryStub, cvComponentiFactory, positionableTransformer);
            Spinner spinner = filtraggioRistorantiHandlerUnit.getSortSpinner();
            spinner.setSelection(8);
            DoubleThumbMultiSlider distanceMultiSlider = filtraggioRistorantiHandlerUnit.getDistanceMultiSlider();
            distanceMultiSlider.setMax(this.distanzaTest);
            distanceMultiSlider.setRight(this.distanzaTest);
            DoubleThumbMultiSlider numberMultiSlider = this.filtraggioRistorantiHandlerUnit.getNumberMultiSlider();
            numberMultiSlider.setMax(this.maxNum);
            numberMultiSlider.setRight(this.maxNum);
            CompoundButton compoundButton = filtraggioRistorantiHandlerUnit.getActualPositionCompoundButton();
            compoundButton.setChecked(false);
        } catch (Exception exception) {
            Log.d("", "Exception information : " + exception.getMessage());
        }
    }

    @Override
    @After
    public void tearDown() {
        try {
            super.tearDown();
            assertTrue("Valore tolleranza errato", this.fattoreTolleranza > 0 && this.fattoreTolleranza <= 100);
            assertTrue("Maxnum negativo" , this.maxNum > 0);
            PositionableTransformerStub positionableTransformerStub = (PositionableTransformerStub) this.filtraggioRistorantiHandlerUnit.getPositionableOperations();
            PositionUtilityStub positionUtilityStub = (PositionUtilityStub) this.filtraggioRistorantiHandlerUnit.getPositionUtility();
            PointF pointOne = positionableTransformerStub.getPosition();
            PointF pointTwo = positionUtilityStub.getActualLocation();
            assertTrue("Punti di posizione attuale diversi", pointOne.x == pointTwo.x && pointOne.y == pointTwo.y);
            boolean afterFlag = this.filtraggioRistorantiHandlerUnit.executeAfter();
            int distanzaPrecedente = 0;
            if (afterFlag) {
                PositionableTransformer positionableTransformer = (PositionableTransformer) this.filtraggioRistorantiHandlerUnit.getObservable();
                AbstractList<Positionable> positionables = positionableTransformer.getResults();
                Iterator<Positionable> iterator = positionables.iterator();
                while (iterator.hasNext()) {
                    Positionable positionable = iterator.next();
                    RistoranteView ristoranteView = (RistoranteView) positionable;
                    TextView textView = (TextView) ristoranteView.getChildAt(5);
                    String distance = (String) textView.getText();
                    String[] splitted = distance.split("m");
                    distance = splitted[0].replace("   ", "");
                    int metri = Integer.parseInt(distance);
                    assertTrue("Distanza errata (attuale < precedente)", metri >= distanzaPrecedente);
                    distanzaPrecedente = metri;
                    double tolleranza = (((double) this.distanzaTest) + (((double) this.distanzaTest) / this.fattoreTolleranza));
                    assertTrue("Distanza errata (attuale > inserita)", metri <= tolleranza);
                }
            } else {
                Log.d("", "executeAfter() return false");
            }
        } catch (Exception exception) {
            Log.d("", "Exception information : " + exception.getMessage());
        }
    }

    @Test
    public void eseguiTest() {
        assertTrue("Valore tolleranza errato", this.fattoreTolleranza > 0 && this.fattoreTolleranza <= 100);
        assertTrue("Maxnum negativo" , this.maxNum > 0);
        PositionableTransformerStub positionableTransformerStub = (PositionableTransformerStub) this.filtraggioRistorantiHandlerUnit.getPositionableOperations();
        PositionUtilityStub positionUtilityStub = (PositionUtilityStub) this.filtraggioRistorantiHandlerUnit.getPositionUtility();
        PointF pointOne = positionableTransformerStub.getPosition();
        PointF pointTwo = positionUtilityStub.getActualLocation();
        assertTrue("Punti di posizione attuale diversi", pointOne.x == pointTwo.x && pointOne.y == pointTwo.y);
        boolean backgroundFlag = this.filtraggioRistorantiHandlerUnit.executeInBackground();
        AbstractList<Positionable> positionables = positionableTransformerStub.getResults();
        assertTrue("Metodo ritorna falso ma esiste almeno una struttura prelevata", (positionables.size() == 0 && !backgroundFlag) || (positionables.size() > 0 && backgroundFlag));
        if (!backgroundFlag) {
            Log.d("", "executeInBackground() return false");
        }
    }

    public class FiltraggioRistorantiHandlerUnit extends FiltraggioRistorantiHandler {

       /* la classe FiltraggioRistorantiHandler definisce executeInBackground(), executeAfter() protected
          per i motivi descritti nella documentazione;
       */
       // per il test necessito di executeInBackground(), executeAfter() public
       // effettuo banali override
       // questa classe è la Unit

       public FiltraggioRistorantiHandlerUnit(Activity activity, PositionUtility positionUtility, InterfacciaBackEndStub interfacciaBackEndStub, CVComponentiFactory componentiFactory, PositionableTransformer positionableTransformer) {
           super(activity, positionUtility, interfacciaBackEndStub, componentiFactory, positionableTransformer);
       }

        @Override
        public boolean executeInBackground() {
            return super.executeInBackground();
        }

        @Override
        public boolean executeAfter() {
           return super.executeAfter();
        }


    }

    // Semplici Stubs che non necessitano delle componenti Network, Position, GoogleMap, Observable
    // ulteriore Stub : InterfacciaBackEndStub

    public class PositionUtilityStub extends PositionUtility {

        @Override
        public PointF getActualLocation() {
            PointF location = this.getActualLocation();
            location.x = latitudineNapoli;
            location.y = longitudineNapoli;
            return location;
        }

    }

    public class PositionableTransformerStub extends PositionableTransformer {

        public PositionableTransformerStub(AbstractComponentsFactory componentsFactory) {
            super(componentsFactory, 0);
        }

        @Override
        public void transform(AbstractList<ObjectsModel> objectsModels, int option) {
            LinkedList<Positionable> positionables = (LinkedList) this.getResults();
            positionables.removeAll(positionables);
            if (objectsModels.size() > 0) {
                AbstractComponentsFactory componentsFactory = this.getPositionableBuildable();
                Iterator<ObjectsModel> iterator = objectsModels.iterator();
                while (iterator.hasNext()) {
                    ObjectsModel objectsModel = iterator.next();
                    if (objectsModel != null) {
                        Positionable positionable = componentsFactory.buildPositionable(objectsModel);
                        positionables.addFirst(positionable);
                    }

                }
            }
        }

    }


}
