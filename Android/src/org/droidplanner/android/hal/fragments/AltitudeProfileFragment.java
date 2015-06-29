package org.droidplanner.android.hal.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.o3dr.services.android.lib.coordinate.LatLong;

import org.droidplanner.android.R;

import java.util.ArrayList;
import java.util.List;

public class AltitudeProfileFragment extends Fragment {

    private ArrayList<LatLong> points = new ArrayList<>();
    private ArrayList<View> markers = new ArrayList<>();;
    private RelativeLayout altitude_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hal_fragment_altitude_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        altitude_profile = (RelativeLayout) getActivity().findViewById(R.id.altitude_profile);
    }

    public void AddPoint(LatLong point) {
        Log.w("dbug", "addoing point!" + point.getLatitude() + ", " + point.getLongitude());
        View marker = LayoutInflater.from(getActivity()).inflate(R.layout.hal_altitude_profile_point, null, false);
        marker.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(12, 12);

        int distance = 10; //dp

        params.leftMargin = 10 + distance * markers.size();
        params.topMargin = 30;
        altitude_profile = (RelativeLayout) getActivity().findViewById(R.id.altitude_profile);
        if (altitude_profile == null) { Log.w("dbug", "altitude_profile2 == null"); }
        if (marker == null) { Log.w("dbug", "marker == null"); }
        if (params == null) { Log.w("dbug", "params == null"); }
        altitude_profile.addView(marker, params);
    }

}
