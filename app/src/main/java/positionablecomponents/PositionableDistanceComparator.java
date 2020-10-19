package positionablecomponents;

import android.graphics.PointF;

import java.util.Comparator;

import comparators.PointFDistanceComparator;

public class PositionableDistanceComparator implements Comparator<Positionable> {

    private PointFDistanceComparator pointFDistanceComparator;

    public PositionableDistanceComparator(PointF comparePoint, PointFDistanceComparator pointFDistanceComparator) {
        this.pointFDistanceComparator = pointFDistanceComparator;
        if (this.pointFDistanceComparator != null) {
            this.pointFDistanceComparator.setComparePoint(comparePoint);
        }
    }

    public void setComparePoint(PointF comparePoint) {
        if (this.pointFDistanceComparator != null) {
            this.pointFDistanceComparator.setComparePoint(comparePoint);
        }
    }


    @Override
    public int compare(Positionable one, Positionable two) {
        if (this.pointFDistanceComparator != null) {
            PointF pointOne = new PointF(one.getLatitude(), one.getLongitude());
            PointF pointTwo = new PointF(two.getLatitude(), two.getLongitude());
            return this.pointFDistanceComparator.compare(pointOne, pointTwo);
        }
        return 0;
    }

}
