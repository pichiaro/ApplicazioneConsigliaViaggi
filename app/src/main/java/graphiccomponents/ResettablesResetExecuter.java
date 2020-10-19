package graphiccomponents;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.AbstractList;
import java.util.Iterator;

public class ResettablesResetExecuter implements OnClickListener {

    private AbstractList<Resettable> resettables;

    public ResettablesResetExecuter(AbstractList<Resettable> resettables) {
        this.resettables = resettables;
    }

    public void setResettables(AbstractList<Resettable> resettables) {
        this.resettables = resettables;
    }

    public AbstractList<Resettable> getResettables() {
        return this.resettables;
    }

    public void addResettables(Resettable...resettables) {
        if (this.resettables != null) {
            if (resettables != null) {
                for (int index = 0; index < resettables.length; index++) {
                    if (resettables[index] != null) {
                        if (resettables[index] != null) {
                            this.resettables.add(resettables[index]);
                        }
                    }
                }
            }
        }
    }

    public Resettable getResettable(int index) {
        Resettable resettable = null;
        if (this.resettables != null) {
            if (index >= 0) {
                if (index < this.resettables.size()) {
                    resettable = this.resettables.get(index);
                }
            }
        }
        return resettable;
    }

    public Resettable removeResettable(int index) {
        Resettable resettable = null;
        if (this.resettables != null) {
            if (index >= 0) {
                if (index < this.resettables.size()) {
                    resettable = this.resettables.remove(index);
                }
            }
        }
        return resettable;
    }

    @Override
    public void onClick(View view) {
        if (this.resettables != null) {
            Iterator<Resettable> iterator = this.resettables.iterator();
            while (iterator.hasNext()) {
                Resettable next = iterator.next();
                if (next != null) {
                    next.reset();
                }
            }
        }
    }

}
