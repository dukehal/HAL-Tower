package org.droidplanner.android.hal.adapterViews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.droidplanner.android.R;
import org.droidplanner.android.hal.wrappers.MapWrapperLayout;

/**
 * Created by tedzhu on 6/6/15.
 */
public class DroneContextMenuAdapter implements GoogleMap.InfoWindowAdapter {

    private MapWrapperLayout mapWrapperLayout;
    private View view;

    public DroneContextMenuAdapter(View window, MapWrapperLayout mapWrapperLayout) {
        //LayoutInflater layoutInflater = LayoutInflater.from(context);
        this.view = window;
        this.mapWrapperLayout = mapWrapperLayout;
        //view = layoutInflater.inflate(R.layout.drone_context_menu, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
//        Log.w("dbug:mthd", "DroneContextMenuAdapter.getInfoWindow() (first) called.");
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        Log.w("dbug:mthd", "DroneContextMenuAdapter.getInfoContents() (second) called.");
        if (marker.getTitle() != null && marker.getTitle().equals("Drone Icon")) {
            mapWrapperLayout.setMarkerWithInfoWindow(marker, view);
            return view;
        } else {
            return null;
        }
    }
}
