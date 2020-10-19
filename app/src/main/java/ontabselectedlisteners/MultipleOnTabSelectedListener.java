package ontabselectedlisteners;

import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import java.util.AbstractList;
import java.util.Iterator;

public class MultipleOnTabSelectedListener implements OnTabSelectedListener {

    private AbstractList<OnTabSelectedListener> onTabSelectedListeners;

    public MultipleOnTabSelectedListener(AbstractList<OnTabSelectedListener> onTabSelectedListeners) {
        this.onTabSelectedListeners = onTabSelectedListeners;
    }

    public void setOnTabSelectedListeners(AbstractList<OnTabSelectedListener> onTabSelectedListeners) {
        this.onTabSelectedListeners = onTabSelectedListeners;
    }

    public AbstractList<OnTabSelectedListener> getOnTabSelectedListeners() {
        return this.onTabSelectedListeners;
    }

    public void addOnTabSelectedListeners(OnTabSelectedListener...onTabSelectedListeners) {
        if (this.onTabSelectedListeners != null) {
            if (onTabSelectedListeners != null) {
                for (int index = 0; index < onTabSelectedListeners.length; index++) {
                    if (onTabSelectedListeners[index] != null) {
                        if (onTabSelectedListeners[index] != null) {
                            this.onTabSelectedListeners.add(onTabSelectedListeners[index]);
                        }
                    }
                }
            }
        }
    }

    public OnTabSelectedListener getOnTabSelectedListener(int index) {
        OnTabSelectedListener onTabSelectedListener = null;
        if (this.onTabSelectedListeners != null) {
            if (index >= 0) {
                if (index < this.onTabSelectedListeners.size()) {
                    onTabSelectedListener = this.onTabSelectedListeners.get(index);
                }
            }
        }
        return onTabSelectedListener;
    }

    public OnTabSelectedListener removeOnTabSelectedListener(int index) {
        OnTabSelectedListener onTabSelectedListener = null;
        if (this.onTabSelectedListeners != null) {
            if (index >= 0) {
                if (index < this.onTabSelectedListeners.size()) {
                    onTabSelectedListener = this.onTabSelectedListeners.remove(index);
                }
            }
        }
        return onTabSelectedListener;
    }

    @Override
    public void onTabSelected(Tab tab) {
        if (this.onTabSelectedListeners != null) {
            Iterator<OnTabSelectedListener> iterator = this.onTabSelectedListeners.iterator();
            while (iterator.hasNext()) {
                OnTabSelectedListener next = iterator.next();
                if (next != null) {
                    next.onTabSelected(tab);
                }
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {
        if (this.onTabSelectedListeners != null) {
            Iterator<OnTabSelectedListener> iterator = this.onTabSelectedListeners.iterator();
            while (iterator.hasNext()) {
                OnTabSelectedListener next = iterator.next();
                if (next != null) {
                    next.onTabUnselected(tab);
                }
            }
        }
    }

    @Override
    public void onTabReselected(Tab tab) {
        if (this.onTabSelectedListeners != null) {
            Iterator<OnTabSelectedListener> iterator = this.onTabSelectedListeners.iterator();
            while (iterator.hasNext()) {
                OnTabSelectedListener next = iterator.next();
                if (next != null) {
                    next.onTabReselected(tab);
                }
            }
        }
    }

}
