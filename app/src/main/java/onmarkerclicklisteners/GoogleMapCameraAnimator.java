package onmarkerclicklisteners;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class GoogleMapCameraAnimator implements OnMarkerClickListener {

    private GoogleMap googleMap;
    private float offsetLatitude;
    private float offsetLongitude;
    private float zoomFactor;

    public GoogleMapCameraAnimator(GoogleMap googleMap, float offsetLatitude, float offsetLongitude, float zoomFactor) {
        this.googleMap = googleMap;
        this.offsetLatitude = offsetLatitude;
        this.offsetLongitude = offsetLongitude;
        this.zoomFactor = zoomFactor;
    }

    public void setOffsetLatitude(float offsetLatitude) {
        this.offsetLatitude = offsetLatitude;
    }

    public float getOffsetLatitude() {
        return this.offsetLatitude;
    }

    public void setOffsetLongitude(float offsetLongitude) {
        this.offsetLongitude = offsetLongitude;
    }

    public float getOffsetLongitude() {
        return this.offsetLongitude;
    }

    public void setZoomFactor(float zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public float getZoomFactor() {
        return this.zoomFactor;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.googleMap != null) {
            LatLng markerLatLng = marker.getPosition();
            if (this.offsetLatitude != 0 || this.offsetLongitude != 0) {
                markerLatLng = new LatLng(markerLatLng.latitude + this.offsetLatitude, markerLatLng.longitude + this.offsetLongitude);
            }
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLatLng, this.zoomFactor);
            this.googleMap.animateCamera(cameraUpdate);
            return true;
        }
        return false;
    }

}