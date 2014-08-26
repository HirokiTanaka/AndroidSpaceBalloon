package kh.spaceclub.spaceballoon.location;

import java.util.Timer;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener {

	private LocationManager mLocationManager;
	
	private Location mCurrentLocation;
	public Location getCurrentLocation() {
		return mCurrentLocation;
	}
	
	private Timer mTimer;
	private long mInterval;
	
	public LocationHelper(LocationManager locationManager) {
		this(locationManager, 60000L);
	}
	
	public LocationHelper(LocationManager locationManager, long interval) {
		mLocationManager = locationManager;
		mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);
		Location lc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lc != null) {
			mCurrentLocation = lc;
		}
		mInterval = interval;
	}
	
	public void start() {
		stop();

		if (mLocationManager == null || !mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			return;
		
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mInterval, 0, this);
	}
	
	public void stop() {
		if (mLocationManager != null) {
			if (mTimer != null) {
				mTimer.cancel();
				mTimer.purge();
				mTimer = null;
			}

			mLocationManager.removeUpdates(this);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			mCurrentLocation = location;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

}
