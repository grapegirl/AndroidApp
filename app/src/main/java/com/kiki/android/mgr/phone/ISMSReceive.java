package com.kiki.android.mgr.phone;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : ISMSReceive
 * @Description : SMS 정보 받기
 * @since 2017-02-11.
 */
public interface ISMSReceive {

    static final int RECEIVE_FAIL = -1;
    static final int RECEIVE_OK = 0;
    static final int SEND_OK = 1;
    static final int SEND_FAIL = 2;

    public void onSMSReceive(int type, Object obj);
}