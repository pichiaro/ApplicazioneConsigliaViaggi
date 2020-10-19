package onmarkerclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class OnClickExecuterForTag implements OnMarkerClickListener {

    private OnClickListener onClickListener;

    public OnClickExecuterForTag(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return this.onClickListener;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Object tag = marker.getTag();
        if (tag != null) {
            if (tag instanceof View) {
                View cast = (View) tag;
                if (this.onClickListener != null) {
                    this.onClickListener.onClick(cast);
                }
            }
        }
        return true;
    }

}
