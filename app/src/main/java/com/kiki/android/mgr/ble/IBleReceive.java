package com.kiki.android.mgr.ble;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : IBleReceive
 * @Description : 블루투스 관련 모든 인터페이스 정의
 * @since 2017-02-11
 */
public interface IBleReceive {

    static final int RECEIVE_ERROR = -1;
    static final int RECEIVE_OK = 0;
    static final int SEND_OK = 1;
    static final int SEND_FAIL = 2;

    static final int SCAN_ADDED_IST = 100;
    static final int SCAN_SEARCH_DEVICES = 101;
    static final int SCAN_COMPLE_SEARCH = 102;
    static final int SCAN_BLE_OFF = 103;
    static final int SCAN_BLE_ON = 104;

    public void onReceiveAction(int what, Object obj);
}
