package onmarkerclicklisteners;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;

import java.util.Vector;

public class MarkerIconSetter implements OnMarkerClickListener {

    private Marker lastClickedMarker;
    final private Vector<String> titleVector = new Vector<>();
    final private Vector<BitmapDescriptor> clickedBitmapDescriptorVector = new Vector<>();
    final private Vector<BitmapDescriptor> notClickedBitmapDescriptorVector = new Vector<>();

    public Marker getLastClickedMarker() {
        return this.lastClickedMarker;
    }

    public void setAssociation(String title, BitmapDescriptor clickedBitmapDescriptor, BitmapDescriptor notClickedBitmapDescriptor) {
        this.titleVector.add(title);
        this.clickedBitmapDescriptorVector.add(clickedBitmapDescriptor);
        this.notClickedBitmapDescriptorVector.add(notClickedBitmapDescriptor);
    }

    public void removeAssociation(int index) {
        if (index >= 0) {
            if (index < this.titleVector.size()) {
                this.titleVector.remove(index);
                this.notClickedBitmapDescriptorVector.remove(index);
                this.clickedBitmapDescriptorVector.remove(index);
            }
        }
    }

    public String getTitle(int index) {
        if (index >= 0) {
            if (index < this.titleVector.size()) {
                return this.titleVector.get(index);
            }
        }
        return null;
    }

    public BitmapDescriptor getClickedBitmapDescriptor(int index) {
        if (index >= 0) {
            if (index < this.clickedBitmapDescriptorVector.size()) {
                return this.clickedBitmapDescriptorVector.get(index);
            }
        }
        return null;
    }

    public BitmapDescriptor getNotClickedBitmapDescriptor(int index) {
        if (index >= 0) {
            if (index < this.notClickedBitmapDescriptorVector.size()) {
                return this.notClickedBitmapDescriptorVector.get(index);
            }
        }
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();
        if (title != null) {
            int index = this.titleVector.indexOf(title);
            if (index >= 0) {
                BitmapDescriptor clickedBitmapDescriptor = this.getClickedBitmapDescriptor(index);
                if (this.lastClickedMarker != null) {
                    title = this.lastClickedMarker.getTitle();
                    if (title != null) {
                        index = this.titleVector.indexOf(title);
                        BitmapDescriptor notClickedBitmapDescriptor = this.getNotClickedBitmapDescriptor(index);
                        try {
                            this.lastClickedMarker.setIcon(notClickedBitmapDescriptor);
                        }
                        catch(IllegalArgumentException exception) {
                            this.lastClickedMarker = null;
                        }
                    }
                }
                try {
                    marker.setIcon(clickedBitmapDescriptor);
                }
                catch (IllegalArgumentException exception) {
                    this.lastClickedMarker = marker;
                    return false;
                }
                this.lastClickedMarker = marker;
                return true;
            }
        }
        return false;
    }

}

