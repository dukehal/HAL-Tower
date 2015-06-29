package org.droidplanner.android.hal.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.hal_fragment_altitude_profile, container, false);
        altitude_profile = (RelativeLayout) myFragmentView.findViewById(R.id.altitude_profile);
        if (altitude_profile == null) { Log.w("dbug", "altitude_profileO == null"); }
        return myFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //altitude_profile = (RelativeLayout) getActivity().findViewById(R.id.altitude_profile);
    }

    public void AddPoint(LatLong point) {
        Log.w("dbug", "addoing point!" + point.getLatitude() + ", " + point.getLongitude());
        View marker = LayoutInflater.from(getActivity()).inflate(R.layout.hal_altitude_profile_point, null, false);
        marker.setVisibility(View.INVISIBLE);


        ImageView tv = new ImageView(this.getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable myIcon = getResources().getDrawable( R.drawable.ic_wp_map );
        tv.setBackground(myIcon);
        altitude_profile.addView(tv, params);

        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(20, 20);

        int distance = 10; //dp

        params.leftMargin = 10 + distance * markers.size();
        params.topMargin = 10;
        //altitude_profile = (RelativeLayout) getView().findViewById(R.id.altitude_profile);
        if (altitude_profile == null) { Log.w("dbug", "altitude_profile5 == null"); }
        if (marker == null) { Log.w("dbug", "marker == null"); }
        if (params == null) { Log.w("dbug", "params == null"); }
        markers.add(marker);
        altitude_profile.addView(marker, params);
    }

}
