package positionablecomponents;

import android.graphics.PointF;

import java.util.AbstractList;

public interface PositionableOperations {

    PointF getPosition();

    void setPosition(PointF pointF);

    void sortResultsByDistance(boolean isReverse);

    void applyLowerOrEqualDistanceFrom(String label, int radius);

    AbstractList<Positionable> getResults();

    void zoom(int radius);

    void resetMarkers();

}
