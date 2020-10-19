package utilities;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class PositionUtility extends LocationCallback {

    public final static double EARTH_RADIUS = 6371000;
    private final static double ZOOM_CONSTANT_FACTOR = 0.950;
    public final static double METERS_ON_PIXEL = 156543.03392;
    public final static double MIN_LATITUDE = -90;
    public final static double MAX_LATITUDE = 90;
    public final static double ZOOM_FACTOR_MAX_METERS_RADIUS = 1000000;
    public final static double MIN_ZOOM_FACTOR = 2.0;
    private final static double ZOOM_FACTOR_DECREMENT = 0.25;
    private final static long DEFAULT_INTERVAL = 1000;
    private final static long DEFAULT_FAST_INTERVAL = 500;
    final static double DEGREE_ONE = 0;
    final static double DEGREE_TWO = 90;
    final static double DEGREE_THREE = 180;
    final static double DEGREE_FOUR = 270;
    private final static int REQUEST_CODE = 1;

    private final PointF actualLocation = new PointF();
    private final LocationRequest locationRequest = new LocationRequest();
    private Geocoder geocoder;

    public PositionUtility() {
        super();
        this.locationRequest.setInterval(PositionUtility.DEFAULT_INTERVAL);
        this.locationRequest.setFastestInterval(PositionUtility.DEFAULT_FAST_INTERVAL);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void createGeocoder(Context context, Locale locale) {
        this.geocoder = new Geocoder(context, locale);
    }

    public LocationRequest getLocationRequest() {
        return this.locationRequest;
    }

    public Geocoder getGeocoder() {
        return this.geocoder;
    }

    public PointF getActualLocation() {
        return this.actualLocation;
    }

    public void assignLocationRequest(Context context) {
        Looper mainLooper = context.getMainLooper();
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this, mainLooper);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        List<Location> locationList = locationResult.getLocations();
        int size = locationList.size();
        if (size > 0) {
            int latestLocationIndex = size - 1;
            Location location = locationList.get(latestLocationIndex);
            float latitude = (float) location.getLatitude();
            float longitude = (float) location.getLongitude();
            this.actualLocation.set(latitude, longitude);
        }
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }

    public static boolean isAccessFineLocationGranted(Context context) {
        if (context instanceof Activity) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                Activity activityCast = (Activity) context;
                if (ContextCompat.checkSelfPermission(applicationContext, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissionString = {permission.ACCESS_FINE_LOCATION};
                    ActivityCompat.requestPermissions(activityCast, permissionString, PositionUtility.REQUEST_CODE);
                }
                if (ContextCompat.checkSelfPermission(applicationContext, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public PointF getLocationLatAndLon(String location) {
        if (this.geocoder != null) {
            try {
                List<Address> addresses = this.geocoder.getFromLocationName(location, 1);
                if (addresses != null) {
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        PointF position = new PointF((float) address.getLatitude(), (float) address.getLongitude());
                        return position;
                    }
                }
            } catch (Exception exception) {

            }
        }
        return null;
    }

    public static PointF getDerivedPosition(PointF point, double range, double bearing) {
        if (point != null) {
            double pointLatitude = Math.toRadians(point.x);
            double pointLongitude = Math.toRadians(point.y);
            double angularDistance = range / PositionUtility.EARTH_RADIUS;
            double trueCourse = Math.toRadians(bearing);
            double latitude = Math.asin(Math.sin(pointLatitude) * Math.cos(angularDistance) + Math.cos(pointLatitude) * Math.sin(angularDistance) * Math.cos(trueCourse));
            double derivedLatitude = Math.atan2(Math.sin(trueCourse) * Math.sin(angularDistance) * Math.cos(pointLatitude), Math.cos(angularDistance) - Math.sin(pointLatitude) * Math.sin(latitude));
            double longitude = ((pointLongitude + derivedLatitude + Math.PI) % (Math.PI * 2)) - Math.PI;
            latitude = Math.toDegrees(latitude);
            longitude = Math.toDegrees(longitude);
            PointF pointF = new PointF((float) latitude, (float) longitude);
            return pointF;
        }
        return null;
    }

    public static float getDistanceBetweenTwoPoints(PointF pointOne, PointF pointTwo) {
        double deltaLatitude = Math.toRadians(pointTwo.x - pointOne.x);
        double deltaLongitude = Math.toRadians(pointTwo.y - pointOne.y);
        double pointOneLatitude = Math.toRadians(pointOne.x);
        double pointTwoLatitude = Math.toRadians(pointTwo.x);
        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) + Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2) * Math.cos(pointOneLatitude) * Math.cos(pointTwoLatitude);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = PositionUtility.EARTH_RADIUS * c;
        return (float) distance;
    }

    public static float getProportionalZoomFactor(double radiusInMeters, int viewWidthInDp, double latitude) {
        float zoomFactor = (float) PositionUtility.MIN_ZOOM_FACTOR;
        if (radiusInMeters > 0 && radiusInMeters <= PositionUtility.ZOOM_FACTOR_MAX_METERS_RADIUS && viewWidthInDp > 0 && latitude > PositionUtility.MIN_LATITUDE && latitude < PositionUtility.MAX_LATITUDE) {
            double diameterInKilometers = (radiusInMeters * 2) / 1000;
            double metersPerDp;
            double kilometersPerViewWidthInDp;
            do {
                zoomFactor = (float) (zoomFactor + PositionUtility.ZOOM_FACTOR_DECREMENT);
                metersPerDp = PositionUtility.METERS_ON_PIXEL * Math.cos(latitude * Math.PI / 180) / Math.pow(2, zoomFactor);
                kilometersPerViewWidthInDp = (metersPerDp * viewWidthInDp) / 1000;
            } while (kilometersPerViewWidthInDp > diameterInKilometers);
        }
        if (zoomFactor >= zoomFactor + PositionUtility.ZOOM_FACTOR_DECREMENT) {
            zoomFactor = (float) (zoomFactor - PositionUtility.ZOOM_FACTOR_DECREMENT);
        }
        return (float) (zoomFactor * PositionUtility.ZOOM_CONSTANT_FACTOR);
    }
}
