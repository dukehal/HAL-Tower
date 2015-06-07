package org.droidplanner.android.hal.wrappers;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by tedzhu on 6/7/15.
 */

// Following the solution of:
// http://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go

public class MapWrapperLayout extends RelativeLayout {

    /**
     * Reference to GoogleMap object.
     */
    private GoogleMap map;

    /**
     * Vertical offset in pixels between the bottom edge of our InfoWindow and the marker
     * position (by default it's bottom edge too). It's a good idea to use custom markers and also
     * the InfoWindow frame, because we probably can't rely on the sizes of the default
     * marker and frame.
     */
    private int bottomOffsetPixels;

    /**
     * A currently selected marker.
     */
    private Marker marker;

    /**
     * Our custom view which is returned from either the InfoWindowAdapter.getInfoContents
     * or InfoWindowAdaper.getInfoWindow.
     */
    private View infoWindow;


    public MapWrapperLayout(Context context) {
        super(context);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Must be called before we can route the touch events.
     */
    public void init(GoogleMap map, int bottomOffsetPixels) {
        this.map = map;
        this.bottomOffsetPixels = bottomOffsetPixels;
        Log.w("dbug:mthd", "MapWrapperLayout.init() called.");
        Log.w("dbug:info", "oot view class name: " + getRootView().getClass().getName());

    }

    /**
     * Best to be called from either the InfoWindowAdapter.getInfoContents
     * or InfoWindowAdapater.getInfoWindow.
     */
    public void setMarkerWithInfoWindow(Marker marker, View infoWindow) {
        this.marker = marker;
        this.infoWindow = infoWindow;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.w("dbug:info", "Touch event on MapWrapperLayout!!");

        boolean ret = false;

        // Make sure that the infoWindow is shown and we have all the needed references.
        if (marker != null && marker.isInfoWindowShown() && map != null && infoWindow != null) {

            Log.w("dbug:info", "All needed references loaded in MapWrapperLayout.dispatchTouchEvent().");

            // Get the marker position on the screen.
            Point point = map.getProjection().toScreenLocation(marker.getPosition());
            Log.w("dbug:info", "point.x: " + point.x);
            Log.w("dbug:info", "point.y: " + point.y);

            Log.w("dbug:info", "infoWindow.getWidth(): " + infoWindow.getWidth());
            Log.w("dbug:info", "infoWindow.getHeight(): " + infoWindow.getHeight());

            Log.w("dbug:info", "offset.x: " + (-point.x + (infoWindow.getWidth() / 2)));
            Log.w("dbug:info", "offset.y: " + (-point.y + infoWindow.getHeight() + bottomOffsetPixels));


            // Make a copy of the MotionEvent and adjust its location
            // so it is relative to the infoWindow left top corner.
            MotionEvent copyEv = MotionEvent.obtain(ev);
            copyEv.offsetLocation(
                    -point.x + (infoWindow.getWidth() / 2),
                    -point.y + infoWindow.getHeight() + bottomOffsetPixels);


            // Dispatch the adjusted MotionEvent to the infoWindow.
            ret = infoWindow.dispatchTouchEvent(copyEv);

        }

        // If the infoWindow consumed tbe touch event, then just return true.
        // Otherwise, pass this event to the super class and return its result.
        return ret || super.dispatchTouchEvent(ev);

    }

}
