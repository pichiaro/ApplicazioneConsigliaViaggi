package comparators;

import android.graphics.PointF;
import android.location.Location;

import java.util.Comparator;

public class PointFDistanceComparator implements Comparator<PointF> {

    private PointF comparePoint;

    public PointFDistanceComparator(PointF comparePoint) {
        this.comparePoint = comparePoint;
    }

    public void setComparePoint(PointF comparePoint) {
        this.comparePoint = comparePoint;
    }

    public PointF getComparePoint() {
        return this.comparePoint;
    }

    @Override
    public int compare(PointF initialPoint, PointF finalPoint) {
        if (this.comparePoint != null) {
            float resultOne[] = new float[3];
            Location.distanceBetween(this.comparePoint.x, this.comparePoint.y, initialPoint.x, initialPoint.y, resultOne);
            float resultTwo[] = new float[3];
            Location.distanceBetween(this.comparePoint.x, this.comparePoint.y, finalPoint.x, finalPoint.y, resultTwo);
            if (resultOne[0] > resultTwo[0]) {
                return 1;
            } else {
                if (resultOne[0] < resultTwo[0]) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
        return 0;
    }

}
