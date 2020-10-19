package positionablecomponents;

import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;



public class OnMarkerClickExecuterForPositionableSubView implements OnClickListener{

    private OnMarkerClickListener onMarkerClickListener;
    private Positionable positionable;

    public OnMarkerClickExecuterForPositionableSubView(OnMarkerClickListener onMarkerClickListener) {
        this.onMarkerClickListener = onMarkerClickListener;
    }

    public OnMarkerClickListener getOnMarkerClickListener() {
        return this.onMarkerClickListener;
    }

    public void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        this.onMarkerClickListener = onMarkerClickListener;
    }

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
            Marker marker = this.positionable.getMarker();
            if (this.onMarkerClickListener != null) {
                this.onMarkerClickListener.onMarkerClick(marker);
            }
        }
    }
}
