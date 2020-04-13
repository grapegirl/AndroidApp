package com.kiki.android.mgr.gps;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : IGPSReceive
 * @Description : GPS 정보 받기
 * @since 2017-02-11
 */
public interface IGPSReceive {

    static final int RECEIVE_OK = 0;
    static final int RECEIVE_FAIL = -1;
    static final int RECEIVE_UPDATE = 1;

    public void onGpsReceive(int type, Object obj);
}
