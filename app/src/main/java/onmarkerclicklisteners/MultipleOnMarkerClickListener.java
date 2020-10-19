package onmarkerclicklisteners;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

import java.util.AbstractList;
import java.util.Iterator;

public class MultipleOnMarkerClickListener implements OnMarkerClickListener {

    private AbstractList<OnMarkerClickListener> onMarkerClickListeners;

    public MultipleOnMarkerClickListener(AbstractList<OnMarkerClickListener> onMarkerClickListeners) {
        this.onMarkerClickListeners = onMarkerClickListeners;
    }

    public void setOnMarkerClickListeners(AbstractList<OnMarkerClickListener> onMarkerClickListenerList) {
        this.onMarkerClickListeners = onMarkerClickListenerList;
    }

    public AbstractList<OnMarkerClickListener> getOnMarkerClickListeners() {
        return this.onMarkerClickListeners;
    }

    public void addOnMarkerClickListeners(OnMarkerClickListener...onMarkerClickListeners) {
        if (this.onMarkerClickListeners != null) {
            if (onMarkerClickListeners != null) {
                for (int index = 0; index < onMarkerClickListeners.length; index++) {
                    if (onMarkerClickListeners[index] != null) {
                        if (onMarkerClickListeners[index] != null) {
                            this.onMarkerClickListeners.add(onMarkerClickListeners[index]);
                        }
                    }
                }
            }
        }
    }

    public OnMarkerClickListener getOnMarkerClickListener(int index) {
        OnMarkerClickListener onMarkerClickListener = null;
        if (this.onMarkerClickListeners != null) {
            if (index >= 0) {
                if (index < this.onMarkerClickListeners.size()) {
                    onMarkerClickListener = this.onMarkerClickListeners.get(index);
                }
            }
        }
        return onMarkerClickListener;
    }

    public OnMarkerClickListener removeOnMarkerClickListener(int index) {
        OnMarkerClickListener onMarkerClickListener = null;
        if (this.onMarkerClickListeners != null) {
            if (index >= 0) {
                if (index < this.onMarkerClickListeners.size()) {
                    onMarkerClickListener = this.onMarkerClickListeners.remove(index);
                }
            }
        }
        return onMarkerClickListener;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.onMarkerClickListeners != null) {
            Iterator<OnMarkerClickListener> iterator = this.onMarkerClickListeners.iterator();
            while (iterator.hasNext()) {
                OnMarkerClickListener next = iterator.next();
                if (next != null) {
                    next.onMarkerClick(marker);
                }
            }
        }
        return true;
    }

}
