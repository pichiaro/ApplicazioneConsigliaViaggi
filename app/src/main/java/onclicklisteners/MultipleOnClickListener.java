package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;
import java.util.Iterator;

public class MultipleOnClickListener implements View.OnClickListener {

    private AbstractList<OnClickListener> onClickListeners;

    public MultipleOnClickListener(AbstractList<OnClickListener> onClickListeners) {
        this.onClickListeners = onClickListeners;
    }

    public void setOnClickListeners(AbstractList<OnClickListener> onClickListenerList) {
        this.onClickListeners = onClickListenerList;
    }

    public AbstractList<OnClickListener> getOnClickListeners() {
        return this.onClickListeners;
    }

    public void addOnClickListeners(OnClickListener...onClickListeners) {
        if (this.onClickListeners != null) {
            if (onClickListeners != null) {
                for (int index = 0; index < onClickListeners.length; index++) {
                    if (onClickListeners[index] != null) {
                        this.onClickListeners.add(onClickListeners[index]);
                    }
                }
            }
        }
    }

    public OnClickListener getOnClickListener(int index) {
        OnClickListener onClickListener = null;
        if (this.onClickListeners != null) {
            if (index >= 0) {
                if (index < this.onClickListeners.size()) {
                    onClickListener = this.onClickListeners.get(index);
                }
            }
        }
        return onClickListener;
    }

    public OnClickListener removeOnClickListener(int index) {
        OnClickListener onClickListener = null;
        if (this.onClickListeners != null) {
            if (index >= 0) {
                if (index < this.onClickListeners.size()) {
                    onClickListener = this.onClickListeners.remove(index);
                }
            }
        }
        return onClickListener;
    }

    @Override
    public void onClick(View view) {
        if (this.onClickListeners != null) {
            Iterator<OnClickListener> iterator = this.onClickListeners.iterator();
            while (iterator.hasNext()) {
                OnClickListener next = iterator.next();
                if (next != null) {
                    next.onClick(view);
                }
            }
        }
    }

}
