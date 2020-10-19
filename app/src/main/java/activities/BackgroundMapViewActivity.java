package activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.maps.MapView;

public abstract class BackgroundMapViewActivity extends SlidingUpBaseActivity {

    private String mapViewBundleKey;
    private MapView mapView;
    private LinearLayout panelLayout;

    @Override
    protected void onCreate(Bundle stateBundle) {
        super.onCreate(stateBundle);
        this.mapView = new MapView(this);
        this.panelLayout = new LinearLayout(this);
        this.panelLayout.setOrientation(LinearLayout.VERTICAL);
        this.setLayout(this.mapView, this.panelLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle stateBundle) {
        super.onSaveInstanceState(stateBundle);
        if (this.mapViewBundleKey != null) {
            Bundle bundle = stateBundle.getBundle(this.mapViewBundleKey);
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putBundle(this.mapViewBundleKey, bundle);
            }
            if (this.mapView != null) {
                this.mapView.onSaveInstanceState(bundle);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mapView != null) {
            this.mapView.onResume();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mapView != null) {
            this.mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.mapView != null) {
            this.mapView.onStop();
        }
    }

    @Override
    protected void onPause() {
        if (this.mapView != null) {
            this.mapView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (this.mapView != null) {
            this.mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.mapView != null) {
            this.mapView.onLowMemory();
        }
    }

    protected void setMapViewBundleKey(String mapViewBundleKey) {
        this.mapViewBundleKey = mapViewBundleKey;
    }

    public String getMapViewBundleKey() {
        return this.mapViewBundleKey;
    }

    public void initializeCleanGoogleMapMapView(Bundle stateBundle) {
        if (this.mapViewBundleKey != null && stateBundle != null) {
            Bundle mapViewBundle = stateBundle.getBundle(this.mapViewBundleKey);
            this.mapView.onCreate(mapViewBundle);
        }
        else {
            this.mapView.onCreate(null);
        }
    }

    public void setMapView(MapView mapView) {
        if (this.mapView != null) {
            this.mapViewBundleKey = null;
        }
        this.mapView = mapView;
    }

    public MapView getMapView() {
        return this.mapView;
    }

    public LinearLayout getPanelLayout() {
        return this.panelLayout;
    }

}
