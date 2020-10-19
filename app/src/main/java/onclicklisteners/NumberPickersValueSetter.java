package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;

import java.util.AbstractList;
import java.util.Iterator;

public class NumberPickersValueSetter implements  OnClickListener {

    private AbstractList<NumberPicker> numberPickers;
    private int value;

    public NumberPickersValueSetter(AbstractList<NumberPicker> numberPickers, int value) {
        this.numberPickers = numberPickers;
        this.value = value;
    }

    public void setNumberPickers(AbstractList<NumberPicker> numberPickers) {
        this.numberPickers = numberPickers;
    }

    public AbstractList<NumberPicker> getNumberPickers() {
        return this.numberPickers;
    }

    public void addNumberPickers(NumberPicker...numberPickers) {
        if (this.numberPickers != null) {
            if (numberPickers != null) {
                for (int index = 0; index < numberPickers.length; index++) {
                    if (numberPickers[index] != null) {
                        this.numberPickers.add(numberPickers[index]);
                    }
                }
            }
        }
    }

    public NumberPicker getNumberPicker(int index) {
        NumberPicker numberPicker = null;
        if (this.numberPickers != null) {
            if (index >= 0) {
                if (index < this.numberPickers.size()) {
                    numberPicker = this.numberPickers.get(index);
                }
            }
        }
        return numberPicker;
    }

    public NumberPicker removeNumberPicker(int index) {
        NumberPicker numberPicker = null;
        if (this.numberPickers != null) {
            if (index >= 0) {
                if (index < this.numberPickers.size()) {
                    numberPicker = this.numberPickers.remove(index);
                }
            }
        }
        return numberPicker;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public void onClick(View view) {
        if (this.numberPickers != null) {
            Iterator<NumberPicker> iterator = this.numberPickers.iterator();
            while (iterator.hasNext()) {
                NumberPicker next = iterator.next();
                if (next != null) {
                    next.setValue(this.value);
                }
            }
        }
    }
}
