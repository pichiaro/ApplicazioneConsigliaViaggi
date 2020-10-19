package positionablecomponents;

import android.view.View;
import android.view.View.OnClickListener;

public class LastClickedPositionableSaver implements OnClickListener {

    private Positionable positionable;

    public Positionable getPositionable() {
        return this.positionable;
    }

    @Override
    public void onClick(View view) {
        while (!(view instanceof Positionable)) {
            view = (View) view.getParent();
        }
        if (view != null) {
            this.positionable = (Positionable) view;
        }
    }


}
