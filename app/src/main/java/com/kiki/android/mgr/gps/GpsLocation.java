package com.kiki.android.mgr.gps;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : GpsLocation
 * @Description : Gps 위치 정보 클래스
 * @since 2017-02-11.
 */
public class GpsLocation {

    private double mLatitude;
    private double mLongitude;

    public GpsLocation() {
        mLatitude = 0;
        mLongitude = 0;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

}
