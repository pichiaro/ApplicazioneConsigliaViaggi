package onmapreadycallbacks;

import com.google.android.gms.maps.model.MapStyleOptions;

public class CleanMapStyleSetter extends MapStyleSetter {

    private final static String CLEAN_JSON_STYLE = "[{\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"administrative\",\"elementType\":\"geometry\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"administrative.land_parcel\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"administrative.neighborhood\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"poi\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"road\",\"elementType\":\"labels.icon\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"visibility\":\"off\"}]}]";

    public CleanMapStyleSetter() {
        MapStyleOptions mapStyleOptions = new MapStyleOptions(CleanMapStyleSetter.CLEAN_JSON_STYLE);
        this.setMapStyleOptions(mapStyleOptions);
    }

}
