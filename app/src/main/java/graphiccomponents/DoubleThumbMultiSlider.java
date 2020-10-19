package graphiccomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import io.apptik.widget.MultiSlider;

public class DoubleThumbMultiSlider extends MultiSlider implements Resettable {

    public final static int LEFT_THUMB = 0;
    public final static int RIGHT_THUMB = 1;
    private boolean hasLeftThumb;
    private TextView minValueTextView;
    private TextView maxValueTextView;
    private String unitOfMeasure;
    public int increment = 1;

    public DoubleThumbMultiSlider(Context context, AttributeSet attributeSet, String unitOfMeasure, int increment) {
        super(context, attributeSet);
        this.minValueTextView = new TextView(context);
        this.maxValueTextView = new TextView(context);
        this.setUnitOfMeasure(unitOfMeasure);
        this.setIncrement(increment);
        this.setOnThumbValueChangeListener(new Increaser());
    }

    public DoubleThumbMultiSlider(Context context, String unitOfMeasure, int increment) {
        super(context);
        this.minValueTextView = new TextView(context);
        this.maxValueTextView = new TextView(context);
        this.setUnitOfMeasure(unitOfMeasure);
        this.setIncrement(increment);
        this.setOnThumbValueChangeListener(new Increaser());
    }

    public TextView getMinValueTextView() {
        return this.minValueTextView;
    }

    public TextView getMaxValueTextView() {
        return this.maxValueTextView;
    }

    public void setMin(int min) {
        if (this.unitOfMeasure != null) {
            this.minValueTextView.setText(min + unitOfMeasure);
        } else {
            this.minValueTextView.setText(String.valueOf(min));
        }
        super.setMin(min, true, true);
    }

    public void setMax(int max) {
        if (this.unitOfMeasure != null) {
            this.maxValueTextView.setText(max + unitOfMeasure);
        } else {
            this.maxValueTextView.setText(String.valueOf(max));
        }
        super.setMax(max, true, true);
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        int min = this.getMin();
        int max = this.getMax();
        this.setMax(max);
        this.setMin(min);
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setHasLeftThumb(boolean hasLeftThumb) {
        this.hasLeftThumb = hasLeftThumb;
        Thumb leftThumb = this.getThumb(DoubleThumbMultiSlider.LEFT_THUMB);
        if (!this.hasLeftThumb) {
            leftThumb.setEnabled(false);
            leftThumb.setInvisibleThumb(true);
        }
        else {
            leftThumb.setEnabled(true);
            leftThumb.setInvisibleThumb(false);
        }
    }

    public int getSelectedMinValue() {
        CharSequence charSequence = this.minValueTextView.getText();
        String value = charSequence.toString();
        if (this.unitOfMeasure != null) {
            value = value.replace(this.unitOfMeasure, "");
        }
        int min = Integer.parseInt(value);
        return min;
    }

    public int getSelectedMaxValue() {
        CharSequence charSequence = this.maxValueTextView.getText();
        String value = charSequence.toString();
        if (this.unitOfMeasure != null) {
            value = value.replace(this.unitOfMeasure, "");
        }
        int max = Integer.parseInt(value);
        return max;
    }

    public boolean hasLeftThumb() {
        return this.hasLeftThumb;
    }

    @Override
    public void reset() {
        Thumb leftThumb = getThumb(DoubleThumbMultiSlider.LEFT_THUMB);
        int min = this.getMin();
        leftThumb.setValue(min);
        this.setMin(min);
        Thumb rightThumb = getThumb(DoubleThumbMultiSlider.RIGHT_THUMB);
        int max = this.getMax();
        rightThumb.setValue(max);
        this.setMax(max);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.minValueTextView.setVisibility(visibility);
        this.maxValueTextView.setVisibility(visibility);
    }

    public void setIncrement(int increment) {
        if (increment != 0) {
            this.increment = increment;
        }
        else {
            this.increment = 1;
        }
    }

    public int getIncrement() {
        return this.increment;
    }

    public void resetOnThumbValueChangeListener() {
        this.setOnThumbValueChangeListener(new Increaser());
    }
    
    private class Increaser implements OnThumbValueChangeListener {

        @Override
        public void onValueChanged(MultiSlider multiSlider, Thumb thumb, int which, int value) {
            value = (value / increment) * increment;
            if (which == DoubleThumbMultiSlider.LEFT_THUMB) {
                if (unitOfMeasure != null) {
                    minValueTextView.setText(value + unitOfMeasure);
                } else {
                    minValueTextView.setText(String.valueOf(value));
                }
            } else {
                if (which == DoubleThumbMultiSlider.RIGHT_THUMB) {
                    if (unitOfMeasure != null) {
                        maxValueTextView.setText(value + unitOfMeasure);
                    } else {
                        maxValueTextView.setText(String.valueOf(value));
                    }
                }
            }
        }

    }

}
