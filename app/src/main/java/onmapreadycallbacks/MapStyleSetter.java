package onmapreadycallbacks;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.Observable;

public class MapStyleSetter extends Observable implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapStyleOptions mapStyleOptions;

    public GoogleMap getGoogleMap() {
        return this.googleMap;
    }

    public void setMapStyleOptions(MapStyleOptions mapStyleOptions) {
        this.mapStyleOptions = mapStyleOptions;
    }

    public MapStyleOptions getMapStyleOptions() {
        return this.mapStyleOptions;
    }

    public void zoom(LatLng latLng, float zoomFactor) {
        if (this.googleMap != null) {
            if (latLng != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomFactor);
                this.googleMap.animateCamera(cameraUpdate);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (this.mapStyleOptions != null) {
            this.googleMap.setMapStyle(this.mapStyleOptions);
        }
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        if (this.googleMap != null) {
            super.notifyObservers();
        }
    }

    @Override
    public void notifyObservers(Object object) {
        if (this.googleMap != null) {
            super.notifyObservers(object);
        }
    }

}
