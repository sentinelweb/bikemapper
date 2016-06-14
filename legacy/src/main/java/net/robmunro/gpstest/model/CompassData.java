package net.robmunro.gpstest.model;

import net.robmunro.gpstest.LocationTestActivity;
import net.robmunro.gpstest.model.Directions.Step;
import net.robmunro.gpstest.ui.CompassView;
import net.robmunro.gpstest.util.GeoUtils;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class CompassData {
	
	private LatLng currentLocation;
	public float[] oritentationValues;
	public float[] magneticFieldValues;
	public float[] accelFieldValues;
	public float[] calculatedOritentationValues;
	public float[] rotationMatrix;
	public float north;
	public float directionToTgt;
	public float bearingDirection;
	public LatLng target;
	public LatLng src;
	public long lastUpdated;
	public Step currDirection;
	public double currDirectionBearing;
	public Step nextDirection;
	public double nextDirectionBearing;
	
	public CompassView compasView;
	
	public CompassData(float[] oritentationValues,
			float[] magneticFieldValues, float[] accelFieldValues,
			float[] calculatedOritentationValues, float[] rotationMatrix,
			float north, float directionToTgt, float bearingDirection,
			long lastUpdated) {
		this.oritentationValues = oritentationValues;
		this.magneticFieldValues = magneticFieldValues;
		this.accelFieldValues = accelFieldValues;
		this.calculatedOritentationValues = calculatedOritentationValues;
		this.rotationMatrix = rotationMatrix;
		this.north = north;
		this.directionToTgt = directionToTgt;
		this.bearingDirection = bearingDirection;
		this.lastUpdated = lastUpdated;
	}
	
	public CompassView getV() {
		return compasView;
	}

	public void setV(CompassView v) {
		this.compasView = v;
	}

	public LatLng getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LatLng currentLocation) {
		this.currentLocation = currentLocation;
	}

	public float[] getOritentationValues() {
		return oritentationValues;
	}

	public void setOrientationValues(float[] oritentationValues) {
		//this.oritentationValues = oritentationValues;
		System.arraycopy(oritentationValues, 0, this.oritentationValues, 0, 3);
		if (compasView!=null) {compasView.invalidate();}
	}

	public float[] getMagneticFieldValues() {
		return magneticFieldValues;
	}

	public void setMagneticFieldValues(float[] magneticFieldValues) {
		//this.magneticFieldValues = magneticFieldValues;
		System.arraycopy(magneticFieldValues, 0, this.magneticFieldValues, 0, 3);
		if (this.accelFieldValues[0]!=0) {
			computeOrientation();
		}
		if (compasView!=null) {compasView.invalidate();}
	}

	public float[] getAccelFieldValues() {
		return accelFieldValues;
	}

	public void setAccelFieldValues(float[] accelFieldValues) {
		//this.accelFieldValues = accelFieldValues;
		System.arraycopy(accelFieldValues, 0, this.accelFieldValues, 0, 3);
		if (this.magneticFieldValues[0]!=0) {
			computeOrientation();
		}
		if (compasView!=null) {compasView.invalidate();}
	}

	public LatLng getTarget() {
		return target;
	}

	public void setTarget(LatLng target) {
		this.target = target;
		/*
		if (src != null && target != null){
			Double d = GeoUtils.bearing(src, target);
			directionToTgt = directionToTgt - d.floatValue();
		} else if (directionToTgt != 0){
			directionToTgt = 360 - directionToTgt;
		}
		*/
		if (src != null && target != null){
			Double d = GeoUtils.bearing(src, target);
			directionToTgt= d.floatValue();
			if (directionToTgt<0) {
				directionToTgt+=360;
			}
		}
	}

	public LatLng getSrc() {
		return src;
	}

	public void setSrc(LatLng src) {
		this.src = src;
	}
	
	public float getBearingDirection() {
		return bearingDirection;
	}

	public void setBearingDirection(float bearingDirection) {
		this.bearingDirection = bearingDirection;
	}
	
	float[][] opBufs = new float[3][];
	private float[] absZ(float[] input,int outIdx) {
		opBufs[outIdx][0] = input[0];
		opBufs[outIdx][1] = input[1];
		opBufs[outIdx][2] = Math.abs(input[2]);
		return opBufs[outIdx];
	}
	
	//TODO something strange happens to north when you turn the phone over, seem to reverse around a 160degree axis 
	// (i.e faceup : noth = 160 , face down north=160, but faceup north = 140 facedown north = 180 (and vice-versa))
	private void computeOrientation() {
		if (SensorManager.getRotationMatrix(rotationMatrix, null,magneticFieldValues, accelFieldValues)) {
			SensorManager.getOrientation(rotationMatrix, calculatedOritentationValues);
			north=calculatedOritentationValues[0]*57.2957795f;// 180/pi - cnv to deg
			if (north<0) {
				north=360.0f+north;
			}
			north=north%360.0f;
			Log.d(LocationTestActivity.LOG_TAG, "computeOrientation():"+north+" :: "+calculatedOritentationValues[0]+":"+calculatedOritentationValues[1]+":"+calculatedOritentationValues[2]+":");
			
		}
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Directions.Step getCurrDirection() {
		return currDirection;
	}

	public void setCurrDirection(Directions.Step currDirection) {
		this.currDirection = currDirection;
		this.currDirectionBearing = GeoUtils.bearing(currDirection.start, currDirection.end);
	}
	public void setNextDirection(Directions.Step nextDirection) {
		this.nextDirection = nextDirection;
		this.nextDirectionBearing = GeoUtils.bearing(nextDirection.start, nextDirection.end);
	}
}