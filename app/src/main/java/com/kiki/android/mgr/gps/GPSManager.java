package com.kiki.android.mgr.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.kiki.android.utils.KLog;


/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : GPSManager
 * @Description : 내위치 찾기 매니저
 * @since 2017-02-11
 */
public class GPSManager {

    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private final long MIN_TIMEE_CHANGE_FOR_UPDATES = 0;
    private String TAG = this.getClass().getSimpleName();
    private GpsLocation mLocation = null;
    private Context mContext = null;
    private IGPSReceive mGPSReceiver = null;
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                mLocation.setLongitude(location.getLongitude());
                mLocation.setLatitude(location.getLatitude());
                KLog.d(TAG, "@@ onLocationChanged GPS 위치정보 : " + location.getLatitude() + "," + location.getLongitude());
                mGPSReceiver.onGpsReceive(IGPSReceive.RECEIVE_UPDATE, mLocation);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(mContext, "GPS 상태 변경 : " + status, Toast.LENGTH_LONG).show();
            KLog.d(TAG, "@@ onStatusChanged GPS 상태 변경 : " + provider);
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(mContext, "GPS 상태 가능 : " + provider, Toast.LENGTH_LONG).show();
            KLog.d(TAG, "@@ onProviderEnabled GPS 상태 가능 : " + provider);
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(mContext, "GPS 상태 불가능 : " + provider, Toast.LENGTH_LONG).show();
            KLog.d(TAG, "@@ onProviderDisabled GPS 상태 불가능 : " + provider);
        }
    };

    public GPSManager(Context context, IGPSReceive receive) {
        mContext = context;
        mGPSReceiver = receive;
        mLocation = new GpsLocation();
    }

    public GpsLocation getLocation() {
        return mLocation;
    }

    public void startLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Location location = null;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGps) {
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } else {
            boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if (location != null) {
            mLocation.setLatitude(location.getLatitude());
            mLocation.setLongitude(location.getLongitude());
            mGPSReceiver.onGpsReceive(IGPSReceive.RECEIVE_OK, mLocation);
        }
    }

    public void stopLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationListener);
    }
}
