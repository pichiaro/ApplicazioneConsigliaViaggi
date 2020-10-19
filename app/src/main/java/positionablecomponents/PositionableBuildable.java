package positionablecomponents;

import com.google.android.gms.maps.model.BitmapDescriptor;

import models.ObjectsModel;

public interface PositionableBuildable {

    public abstract BitmapDescriptor buildBitmapDescriptor(Positionable positionable);

    public abstract String buildTitle(Positionable positionable);

    public abstract Positionable buildPositionable(ObjectsModel objectsModel);

}
