package com.thoughtworks.whatyourward.util;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

import java.util.List;

public class KmlUtil {
    @Nullable
    public static KmlPlacemark containsInAnyPolygon(KmlLayer kmlLayer, LatLng latLng) {
        int i=0;
        for (KmlContainer container: kmlLayer.getContainers()) {
            for (KmlPlacemark kmlPlacemark : container.getPlacemarks()) {
                KmlPolygon geometry = null;

                if (kmlPlacemark.getGeometry() instanceof KmlPolygon) {
                    geometry = (KmlPolygon) kmlPlacemark.getGeometry();
                }

                if (geometry == null)
                    continue;
                List<List<LatLng>> geometryObjects = geometry.getGeometryObject();

                for (List<LatLng> obj : geometryObjects) {
                    boolean contains = PolyUtil.containsLocation(latLng, obj, true);
                    if (contains) {
                        return kmlPlacemark;
                    }
                }
            }
        }

        return null;
    }
}
