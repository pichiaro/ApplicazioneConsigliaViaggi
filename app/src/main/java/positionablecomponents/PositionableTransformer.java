package positionablecomponents;

import android.graphics.PointF;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import comparators.PointFDistanceComparator;
import models.ObjectsModelTransformable;
import onmapreadycallbacks.MapStyleSetter;
import models.ObjectsModel;
import utilities.PositionUtility;

public class PositionableTransformer extends Observable implements Observer, ObjectsModelTransformable, PositionableOperations {

    private LinkedList<Marker> markers;
    private LinkedList<Positionable> positionables;
    private GoogleMap googleMap;
    private int mapWidth;
    private PointF position;
    private PositionableBuildable positionableBuildable;
    private PositionableDistanceComparator positionableDistanceComparator;

    public PositionableTransformer(PositionableBuildable positionableBuildable, int mapWidth) {
        this.positionableBuildable = positionableBuildable;
        this.mapWidth = mapWidth;
        this.markers = new LinkedList<>();
        this.positionables = new LinkedList();
        this.positionableDistanceComparator = new PositionableDistanceComparator(null, new PointFDistanceComparator(null));
    }

    public void setPositionableBuildable(PositionableBuildable positionableBuildable) {
        this.positionableBuildable = positionableBuildable;
    }

    public PositionableBuildable getPositionableBuildable() {
        return this.positionableBuildable;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    @Override
    public PointF getPosition() {
        return this.position;
    }

    @Override
    public AbstractList<Positionable> getResults() {
        return this.positionables;
    }

    public LinkedList<Marker> getMarkers() {
        return markers;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public GoogleMap getGoogleMap() {
        return this.googleMap;
    }

    @Override
    public void resetMarkers() {
        Iterator<Marker> iterator = this.markers.iterator();
        while (iterator.hasNext()) {
            Marker next = iterator.next();
            if (next != null) {
                next.remove();
            }
            iterator.remove();
        }
    }

    @Override
    public void transform(AbstractList<ObjectsModel> objectsModels, int option) {
        this.positionables.removeAll(this.positionables);
        if (this.positionableBuildable != null) {
            if (this.googleMap != null) {
                if (objectsModels.size() > 0) {
                    Iterator<ObjectsModel> iterator = objectsModels.iterator();
                    while (iterator.hasNext()) {
                        ObjectsModel objectsModel = iterator.next();
                        if (objectsModel != null) {
                            Positionable positionable = this.positionableBuildable.buildPositionable(objectsModel);
                            float latitudine = positionable.getLatitude();
                            float longitudine = positionable.getLongitude();
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(latitudine, longitudine);
                            markerOptions.position(latLng);
                            BitmapDescriptor bitmapDescriptor = this.positionableBuildable.buildBitmapDescriptor(positionable);
                            markerOptions.icon(bitmapDescriptor);
                            Marker marker = this.googleMap.addMarker(markerOptions);
                            String title = this.positionableBuildable.buildTitle(positionable);
                            View view = positionable.getVisualizeOnMapClickableView();
                            marker.setTag(view);
                            marker.setTitle(title);
                            positionable.setMarker(marker);
                            this.markers.addFirst(marker);
                            this.positionables.addFirst(positionable);
                        }
                    }
                    this.setChanged();
                }
            }
        }
    }

    @Override
    public void sortResultsByDistance(boolean isReverse) {
        if (this.position != null) {
            this.positionableDistanceComparator.setComparePoint(this.position);
            this.positionables.sort(this.positionableDistanceComparator);
            if (isReverse) {
                Collections.reverse(this.positionables);
            }
        }
    }

    @Override
    public void applyLowerOrEqualDistanceFrom(String label, int radius) {
        if (this.position != null) {
            int addCount = this.positionables.size();
            while (addCount > 0) {
                Positionable positionable = this.positionables.removeFirst();
                Marker marker = positionable.getMarker();
                LatLng latLng = marker.getPosition();
                PointF markerPosition = new PointF((float) latLng.latitude, (float) latLng.longitude);
                float distance = PositionUtility.getDistanceBetweenTwoPoints(this.position, markerPosition);
                if (distance <= radius) {
                    positionable.addDistanceFrom(label, (int) distance);
                    this.positionables.addLast(positionable);
                }
                addCount--;
            }
        }
    }

    @Override
    public void zoom(int radius) {
        if (this.position != null) {
            if (this.googleMap != null) {
                float zoomFactor = PositionUtility.getProportionalZoomFactor(radius, this.mapWidth, this.position.x);
                LatLng latLng = new LatLng(this.position.x, this.position.y);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomFactor);
                this.googleMap.animateCamera(cameraUpdate);
            }
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        MapStyleSetter mapStyleSetter = (MapStyleSetter) observable;
        this.googleMap = mapStyleSetter.getGoogleMap();
    }

}
