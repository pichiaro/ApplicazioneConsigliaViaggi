package handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.AbstractList;
import java.util.Observable;

import components.AbstractComponentsFactory;
import components.AbstractQueriesInterface;
import positionablecomponents.Positionable;
import positionablecomponents.PositionableOperations;
import graphiccomponents.DoubleThumbMultiSlider;
import graphiccomponents.FullscreenChoicesDialog;
import graphiccomponents.FullscreenInsertDialog;
import models.ObjectsModelTransformable;
import utilities.PositionUtility;

public abstract class AbstractPositionablesHandler extends AbstractResultsHandler {

    protected FullscreenInsertDialog positionDialog;
    protected FullscreenChoicesDialog choicesDialog;
    protected DoubleThumbMultiSlider priceMultiSlider;
    protected DoubleThumbMultiSlider distanceMultiSlider;
    protected DoubleThumbMultiSlider ratingMultiSlider;
    protected DoubleThumbMultiSlider numberMultiSlider;
    protected CompoundButton actualPositionCompoundButton;
    protected CompoundButton positionCompoundButton;
    protected Spinner sortSpinner;
    protected CompoundButton reverseSortCompoundButton;
    private PositionableOperations positionableOperations;
    private PositionUtility positionUtility;
    private String noPositionMessage;
    private String positionDisabledMessage;
    private String distanceSortNotPossibleMessage;
    private String selectionAttribute;

    public AbstractPositionablesHandler(Activity activity, PositionUtility positionUtiliy, AbstractQueriesInterface queriesInterface, AbstractComponentsFactory componentsFactory, Observable observable, ObjectsModelTransformable objectsModelTransformable, int transformationOption, PositionableOperations positionableOperations) {
        super(activity, queriesInterface, componentsFactory, observable, objectsModelTransformable, transformationOption);
        this.positionableOperations = positionableOperations;
        this.positionUtility = positionUtiliy;
    }

    public void setNoPositionMessage(String noPositionMessage) {
        this.noPositionMessage = noPositionMessage;
    }

    public String getNoPositionMessage() {
        return this.noPositionMessage;
    }

    public void setPositionDisabledMessage(String positionDisabledMessage) {
        this.positionDisabledMessage = positionDisabledMessage;
    }

    public String getPositionDisabledMessage() {
        return this.positionDisabledMessage;
    }

    public void setDistanceSortNotPossibleMessage(String distanceSortNotPossibleMessage) {
        this.distanceSortNotPossibleMessage = distanceSortNotPossibleMessage;
    }

    public String getDistanceSortNotPossibleMessage() {
        return this.distanceSortNotPossibleMessage;
    }

    public void setSelectionAttribute(String selectionAttribute) {
        this.selectionAttribute = selectionAttribute;
    }

    public String getSelectionAttribute() {
        return this.selectionAttribute;
    }

    public void setPositionCompoundButton(CompoundButton positionCompoundButton) {
        this.positionCompoundButton = positionCompoundButton;
    }

    public CompoundButton getPositionCompoundButton() {
        return this.positionCompoundButton;
    }

    public void setReverseSortCompoundButton(CompoundButton reverseSortCompoundButton) {
        this.reverseSortCompoundButton = reverseSortCompoundButton;
    }

    public CompoundButton getReverseSortCompoundButton() {
        return this.reverseSortCompoundButton;
    }

    public void setPriceMultiSlider(DoubleThumbMultiSlider priceMultiSlider) {
        this.priceMultiSlider = priceMultiSlider;
    }

    public DoubleThumbMultiSlider getPriceMultiSlider() {
        return this.priceMultiSlider;
    }

    public void setDistanceMultiSlider(DoubleThumbMultiSlider distanceMultiSlider) {
        this.distanceMultiSlider = distanceMultiSlider;
    }

    public DoubleThumbMultiSlider getDistanceMultiSlider() {
        return this.distanceMultiSlider;
    }

    public void setNumberMultiSlider(DoubleThumbMultiSlider numberMultiSlider) {
        this.numberMultiSlider = numberMultiSlider;
    }

    public DoubleThumbMultiSlider getNumberMultiSlider() {
        return this.numberMultiSlider;
    }

    public void setRatingMultiSlider(DoubleThumbMultiSlider ratingMultiSlider) {
        this.ratingMultiSlider = ratingMultiSlider;
    }

    public DoubleThumbMultiSlider getRatingMultiSlider() {
        return this.ratingMultiSlider;
    }

    public void setActualPositionCompoundButton(CompoundButton actualPositionCompoundButton) {
        this.actualPositionCompoundButton = actualPositionCompoundButton;
    }

    public CompoundButton getActualPositionCompoundButton() {
        return this.actualPositionCompoundButton;
    }

    public void setSortSpinner(Spinner sortSpinner) {
        this.sortSpinner = sortSpinner;
    }

    public Spinner getSortSpinner() {
        return this.sortSpinner;
    }

    public void setPositionDialog(FullscreenInsertDialog positionDialog) {
        this.positionDialog = positionDialog;
    }

    public FullscreenInsertDialog getPositionDialog() {
        return this.positionDialog;
    }

    public void setChoicesDialog(FullscreenChoicesDialog choicesDialog) {
        this.choicesDialog = choicesDialog;
    }

    public FullscreenChoicesDialog getChoicesDialog() {
        return this.choicesDialog;
    }

    public void setPositionableOperations(PositionableOperations positionableOperations) {
        this.positionableOperations = positionableOperations;
    }

    public PositionableOperations getPositionableOperations() {
        return this.positionableOperations;
    }

    public void setPositionUtility(PositionUtility positionUtility) {
        this.positionUtility = positionUtility;
    }

    public PositionUtility getPositionUtility() {
        return this.positionUtility;
    }


    protected abstract boolean checkNetworkCondition();

    protected abstract boolean checkPositionCondition();

    @Override
    protected boolean checkFunctionsStatus() {
        AlertDialog alertDialog = this.getMessageDialog();
        if (this.checkNetworkCondition()) {
            if (this.actualPositionCompoundButton != null && this.positionCompoundButton != null) {
                if (this.actualPositionCompoundButton.isChecked() || this.positionCompoundButton.isChecked()) {
                    if (!this.checkPositionCondition()) {
                        alertDialog.setMessage(this.positionDisabledMessage);
                        return false;
                    } else {
                        if (this.positionCompoundButton != null) {
                            if (this.positionCompoundButton.isChecked()) {
                                if (this.positionDialog != null) {
                                    if (this.positionDialog.getEditedText() == null) {
                                        alertDialog.setMessage(this.noPositionMessage);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (this.sortSpinner != null) {
                        String attributo = (String) this.sortSpinner.getSelectedItem();
                        if (attributo.compareTo(this.selectionAttribute) == 0) {
                            alertDialog.setMessage(this.distanceSortNotPossibleMessage);
                            return false;
                        }
                    }
                }
            }
            return true;
        } else {
            alertDialog.setMessage(this.getNetworkDisabledMessage());
            return false;
        }
    }

    @Override
    protected boolean executeAfter() {
        if (this.positionableOperations != null) {
            this.positionableOperations.resetMarkers();
            if (super.executeAfter()) {
                if (this.positionableOperations.getPosition() != null) {
                    AbstractList<Positionable> positionables = this.positionableOperations.getResults();
                    if (positionables.size() > 0) {
                        if (this.sortSpinner != null) {
                            Object object = this.sortSpinner.getSelectedItem();
                            String attribute = object.toString();
                            if (this.reverseSortCompoundButton != null) {
                                if (attribute.compareTo(this.selectionAttribute) == 0) {
                                    this.positionableOperations.sortResultsByDistance(this.reverseSortCompoundButton.isChecked());
                                }
                            }
                        }
                        if (this.positionDialog != null) {
                            if (this.distanceMultiSlider != null) {
                                String label = this.positionDialog.getEditedText();
                                int radius = this.distanceMultiSlider.getSelectedMaxValue();
                                this.positionableOperations.applyLowerOrEqualDistanceFrom(label, radius);
                                this.positionableOperations.zoom(radius);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

}
