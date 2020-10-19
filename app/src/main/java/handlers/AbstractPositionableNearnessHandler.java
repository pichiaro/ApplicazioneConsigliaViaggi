package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.PointF;
import android.view.View;

import java.util.AbstractList;
import java.util.Observable;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;
import positionablecomponents.LastClickedPositionableSaver;
import positionablecomponents.Positionable;
import positionablecomponents.PositionableOperations;
import models.ObjectsModelTransformable;

public abstract class AbstractPositionableNearnessHandler extends AbstractResultsHandler {

    private boolean isSortedReversed;
    private PositionableOperations positionableOperations;
    private Positionable positionable;

    public AbstractPositionableNearnessHandler(Activity activity, AbstractQueriesInterface queriesInterface, AbstractComponentsFactory componentsFactory, Observable observable, ObjectsModelTransformable objectsModelTransformable, int transformationOption, PositionableOperations positionableOperations) {
        super(activity, queriesInterface, componentsFactory, observable, objectsModelTransformable, transformationOption);
        this.positionableOperations = positionableOperations;
    }

    public void setSortedReversed(boolean sortedReversed) {
        isSortedReversed = sortedReversed;
    }

    public boolean isSortedReversed() {
        return this.isSortedReversed;
    }

    public void setPositionable(Positionable positionable) {
        this.positionable = positionable;
    }

    public Positionable getPositionable() {
        return this.positionable;
    }

    public void setPositionableOperations(PositionableOperations positionableOperations) {
        this.positionableOperations = positionableOperations;
    }

    public PositionableOperations getPositionableOperations() {
        return this.positionableOperations;
    }

    protected abstract boolean checkNetworkCondition();

    @Override
    protected boolean checkFunctionsStatus() {
        if (this.checkNetworkCondition()) {
            if (this.positionable == null) {
                return false;
            }
            return true;
        }
        AlertDialog alertDialog = this.getMessageDialog();
        alertDialog.setMessage(this.getNetworkDisabledMessage());
        return false;
    }

    public abstract int getRadius();

    @Override
    protected boolean executeAfter() {
        if (this.positionableOperations != null) {
            this.positionableOperations.resetMarkers();
            if (super.executeAfter()) {
                if (this.positionable != null) {
                    AbstractList<Positionable> positionables = this.positionableOperations.getResults();
                    if (positionables.size() > 0) {
                        float latitude = this.positionable.getLatitude();
                        float longitude = this.positionable.getLongitude();
                        PointF position = new PointF(latitude, longitude);
                        this.positionableOperations.setPosition(position);
                        this.positionableOperations.sortResultsByDistance(this.isSortedReversed);
                        String label = this.positionable.getName();
                        int radius = this.getRadius();
                        this.positionableOperations.applyLowerOrEqualDistanceFrom(label, radius);
                        this.positionableOperations.zoom(radius);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public class LastClickedPositionableSetter extends LastClickedPositionableSaver {

        @Override
        public void onClick(View view) {
            super.onClick(view);
            Positionable positionable = this.getPositionable();
            setPositionable(positionable);
        }

    }

}
