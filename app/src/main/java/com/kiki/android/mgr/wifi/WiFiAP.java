package com.kiki.android.mgr.wifi;

import android.os.Parcel;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : WiFiAP
 * @Description : 와이파이 AP 정보 클래스
 * @since 2017-02-11.
 */
public class WiFiAP {

    /**
     * AP 맥 주소
     */
    private String mMAC;

    /**
     * AP 맥의 데시벨
     */
    private String mDecibel;

    /**
     * AP 맥의 이름
     */
    private String mSSID;

    /**
     * 보안설정
     */
    private String mCapability;

    /**
     * 생성자
     */
    public WiFiAP() {

    }

    public WiFiAP(Parcel parcel) {
        mMAC = parcel.readString();
        mDecibel = parcel.readString();
        mSSID = parcel.readString();
    }

    /**
     * 생성자
     *
     * @param mac 맥
     * @param deb 데시벨
     */
    public WiFiAP(String ssid, String deb, String mac) {
        mMAC = mac;
        mDecibel = deb;
        mSSID = ssid;
    }

    /**
     * @Description : 맥 주소 반환 메소든
     * @Return 맥주소
     */
    public String getMac() {
        return mMAC;
    }

    /**
     * @param mac 맥주소
     * @Description : 맥주소 설정 메소드
     */
    public void setMac(String mac) {
        mMAC = mac;
    }

    /**
     * @Description : 맥 이름 반환 메소든
     * @Return 맥주소
     */
    public String getSSID() {
        return mSSID;
    }

    /**
     * @param ssid 맥이름
     * @Description : 맥 이름 설정 메소드
     */
    public void setSSID(String ssid) {
        mSSID = ssid;
    }

    /**
     * @Description : 맥의 데시벨 값 반환 메소드
     * @Return 데시벨 값
     */
    public String getDecibel() {
        return mDecibel;
    }

    /**
     * @param decibel 데시벨
     * @Description : 데시벨 값 설정 메소드
     */
    public void setDecibel(String decibel) {
        mDecibel = decibel;
    }

    /**
     * @Description : 보안 반환 값 메소드
     * @Return 보안 값
     */
    public String getCapability() {
        return mCapability;
    }

    /**
     * @param capability 보안
     * @Description : 보안 값 설정 메소드
     */
    public void setCapability(String capability) {
        mCapability = capability;
    }


    @Override
    public String toString() {
        return "ssid: " + mSSID + "\n/deb: " + mDecibel + " /mac: " + mMAC;
    }

}
