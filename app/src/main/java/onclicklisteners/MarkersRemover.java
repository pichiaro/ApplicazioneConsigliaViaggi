package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.maps.model.Marker;

import java.util.AbstractList;
import java.util.Iterator;

public class MarkersRemover implements OnClickListener {

    private AbstractList<Marker> markers;

    public MarkersRemover(AbstractList<Marker> markers) {
        this.markers = markers;
    }

    public void setMarkers(AbstractList<Marker> markers) {
        this.markers = markers;
    }

    public AbstractList<Marker> getMarkers() {
        return this.markers;
    }

    public void addMarkers(Marker...markers) {
        if (this.markers != null) {
            if (markers != null) {
                for (int index = 0; index < markers.length; index++) {
                    if (markers[index] != null) {
                        this.markers.add(markers[index]);
                    }
                }
            }
        }
    }

    public Marker getMarker(int index) {
        Marker marker = null;
        if (this.markers != null) {
            if (index >= 0) {
                if (index < this.markers.size()) {
                    marker = this.markers.get(index);
                }
            }
        }
        return marker;
    }

    public Marker removeMarker(int index) {
        Marker marker = null;
        if (this.markers != null) {
            if (index >= 0) {
                if (index < this.markers.size()) {
                    marker = this.markers.remove(index);
                }
            }
        }
        return marker;
    }

    @Override
    public void onClick(View view) {
        if (this.markers != null) {
            Iterator<Marker> iterator = this.markers.iterator();
            while (iterator.hasNext()) {
                Marker next = iterator.next();
                if (next != null) {
                    next.remove();
                    iterator.remove();
                }
            }
        }
    }

}
