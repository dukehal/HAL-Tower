package org.droidplanner.android.hal.listeners;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by tedzhu on 6/7/15.
 */

// Following the solution of:
// http://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go

public abstract class OnInfoWindowElemTouchListener implements View.OnTouchListener {

    private final View view;
    private final Drawable bgDrawableNormal;
    private final Drawable bgDrawablePressed;
    private final Handler handler = new Handler();

    private Marker marker;
    private boolean pressed = false;

    public OnInfoWindowElemTouchListener(View view, Drawable bgDrawableNormal, Drawable bgDrawablePressed) {
        this.view = view;
        this.bgDrawableNormal = bgDrawableNormal;
        this.bgDrawablePressed = bgDrawablePressed;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean onTouch(View vv, MotionEvent event) {

        Log.w("dbug:mthd", "OnInfoWindowElemTouchListener.onTouch() called.");
        if (0 <= event.getX() && event.getX() <= view.getWidth() &&
                0 <= event.getY() && event.getY() <= view.getHeight()) {

            Log.w("dbug:info", "OnInfoWindowElemTouchListener.onTouch() determined to be INSIDE view area.");
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: startPress(); break;

                // We need to delay releasing of the View a little so it shows the pressed state on the screen.
                case MotionEvent.ACTION_UP: handler.postDelayed(confirmClickRunnable, 150); break;

                case MotionEvent.ACTION_CANCEL: endPress(); break;
                default: break;
            }

        } else {
            Log.w("dbug:info", "OnInfoWindowElemTouchListener.onTouch() determined to be OUTSIDE view area.");
            // If the touch goes outside of the view's area
            // (like when moving finger out of the pressed button)
            // just release the press.
            endPress();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startPress() {
        if (!pressed) {
            pressed = true;
            handler.removeCallbacks(confirmClickRunnable);
            view.setBackground(bgDrawablePressed);
            if (marker != null) {
                marker.showInfoWindow();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean endPress() {
        if (pressed) {
            this.pressed = false;
            handler.removeCallbacks(confirmClickRunnable);
            view.setBackground(bgDrawableNormal);
            if (marker != null) {
                marker.showInfoWindow();
            }
            return true;
        } else {
            return false;
        }
    }

    private final Runnable confirmClickRunnable = new Runnable() {
        public void run() {
            if (endPress()) {
                onClickConfirmed(view, marker);
            }
        }
    };

    /**
     * This is called after a successful click.
     */
    protected abstract void onClickConfirmed(View v, Marker marker);


}
