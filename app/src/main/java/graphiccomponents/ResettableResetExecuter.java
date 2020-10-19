package graphiccomponents;

import android.view.View;
import android.view.View.OnClickListener;

public class ResettableResetExecuter implements OnClickListener {

    private Resettable resettable;

    public ResettableResetExecuter(Resettable resettable) {
        this.resettable = resettable;
    }

    public void setResettable(Resettable resettable) {
        this.resettable = resettable;
    }

    public Resettable getResettable() {
        return this.resettable;
    }


    @Override
    public void onClick(View view) {
        if (this.resettable != null) {
            this.resettable.reset();
        }
    }

}
