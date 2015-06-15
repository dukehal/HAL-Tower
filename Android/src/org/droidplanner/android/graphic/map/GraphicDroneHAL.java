package org.droidplanner.android.graphic.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.attribute.AttributeType;
import com.o3dr.services.android.lib.drone.property.Attitude;
import com.o3dr.services.android.lib.drone.property.Gps;

import org.droidplanner.android.R;
import org.droidplanner.android.maps.MarkerInfo;

public class GraphicDroneHAL extends GraphicDrone {

	public GraphicDroneHAL(Drone drone) {
		super(drone);
	}

	@Override
	public float getAnchorU() {
		return 0.5f;
	}

	@Override
	public float getAnchorV() {
		return 0.5f;
	}

	@Override
	public float getInfoWindowAnchorU() {
		// Pretend Origin is at center of the square marker icon,
		// and the side length of the square is 2, so that a bounded
		// circle has radius 1.
//		float angle = this.getRotation();
//		double angleInRadians = -angle / 180.0 * Math.PI;
//		double xOffset = - Math.sin(angleInRadians);
//		xOffset = xOffset / 2 + 0.5;

		double scale = 1.5;

		float angle = this.getRotation();
		double angleInRadians = angle / 180.0 * Math.PI;
		double xOffset = - Math.sin(angleInRadians) / 2.0 * scale + 0.5;
//		xOffset = xOffset / 2 + 0.5;

		return (float) xOffset;
	}

	@Override
	public float getInfoWindowAnchorV() {
		// Pretend Origin is at center of the square marker icon,
		// and the side length of the square is 2, so that a bounded
		// circle has radius 1.
//		float angle = this.getRotation();
//		double angleInRadians = -angle / 180.0 * Math.PI;
//		double yOffset = - Math.cos(angleInRadians);
//		yOffset = -1 * yOffset / 2 + 0.5;

		double scale = 1.5;

		float angle = this.getRotation();
		double angleInRadians = angle / 180.0 * Math.PI;
		double yOffset = 0.5 - Math.cos(angleInRadians) / 2.0 * scale;

		return (float) yOffset;
	}

	@Override
	public LatLong getPosition() {
        Gps droneGps = drone.getAttribute(AttributeType.GPS);
        return isValid() ? droneGps.getPosition() :  null;
	}

	@Override
	public Bitmap getIcon(Resources res) {
		if (drone.isConnected()) {
			return BitmapFactory.decodeResource(res, R.drawable.drone_small);
		}
		return BitmapFactory.decodeResource(res, R.drawable.quad_disconnect);

	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean isFlat() {
		return true;
	}

	@Override
	public float getRotation() {
        Attitude attitude = drone.getAttribute(AttributeType.ATTITUDE);
		Log.w("dbug:info", "ROTATION: " + (attitude == null ? 0 : (float) attitude.getYaw()));
		return attitude == null ? 0 : (float) attitude.getYaw();
	}

	public boolean isValid() {
        Gps droneGps = drone.getAttribute(AttributeType.GPS);
		return droneGps != null && droneGps.isValid();
	}


	public String getTitle() {
		return "Drone Icon";
	}
}
