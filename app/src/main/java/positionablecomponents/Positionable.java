package positionablecomponents;

import android.view.View;

import com.google.android.gms.maps.model.Marker;

public interface Positionable {

    void setPositionID(String positionId);

    String getPositionID();

    View getVisualizeOnMapClickableView();

    void setMarker(Marker marker);

    Marker getMarker();

    void setName(String name);

    String getName();

    void setLatitude(float latitude);

    float getLatitude();

    void setLongitude(float longitude);

    float getLongitude();

    View getInsertClickableView();

    void addDistanceFrom(String position, int distanceValue);

}
