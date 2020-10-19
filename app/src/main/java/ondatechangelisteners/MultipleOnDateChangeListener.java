package ondatechangelisteners;

import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import java.util.AbstractList;
import java.util.Iterator;

public class MultipleOnDateChangeListener implements OnDateChangeListener{

    private AbstractList<OnDateChangeListener> onDateChangeListeners;

    public MultipleOnDateChangeListener(AbstractList<OnDateChangeListener> onDateChangeListeners) {
        this.onDateChangeListeners = onDateChangeListeners;
    }

    public void setOnDateChangeListeners(AbstractList<OnDateChangeListener> onDateChangeListeners) {
        this.onDateChangeListeners = onDateChangeListeners;
    }

    public AbstractList<OnDateChangeListener> getOnDateChangeListeners() {
        return this.onDateChangeListeners;
    }

    public void addOnDateChangeListeners(OnDateChangeListener...onDateChangeListeners) {
        if (this.onDateChangeListeners != null) {
            if (onDateChangeListeners != null) {
                for (int index = 0; index < onDateChangeListeners.length; index++) {
                    if (onDateChangeListeners[index] != null) {
                        if (onDateChangeListeners[index] != null) {
                            this.onDateChangeListeners.add(onDateChangeListeners[index]);
                        }
                    }
                }
            }
        }
    }

    public OnDateChangeListener getOnDateChangeListener(int index) {
        OnDateChangeListener onDateChangeListener = null;
        if (this.onDateChangeListeners != null) {
            if (index >= 0) {
                if (index < this.onDateChangeListeners.size()) {
                    onDateChangeListener = this.onDateChangeListeners.get(index);
                }
            }
        }
        return onDateChangeListener;
    }

    public OnDateChangeListener removeOnDateChangeListener(int index) {
        OnDateChangeListener onDateChangeListener = null;
        if (this.onDateChangeListeners != null) {
            if (index >= 0) {
                if (index < this.onDateChangeListeners.size()) {
                    onDateChangeListener = this.onDateChangeListeners.remove(index);
                }
            }
        }
        return onDateChangeListener;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        if (this.onDateChangeListeners != null) {
            Iterator<OnDateChangeListener> iterator = this.onDateChangeListeners.iterator();
            while (iterator.hasNext()) {
                OnDateChangeListener onDateChangeListener = iterator.next();
                if (onDateChangeListener != null) {
                    onDateChangeListener.onSelectedDayChange(view, year, month, dayOfMonth);
                }
            }
        }
    }

}
