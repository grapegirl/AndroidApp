package com.kiki.android.mgr.net;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : INetReceive
 * @Description : HTTP 통신 결과
 * @since 2017-02-11
 */
public interface INetReceive {

    static final int NET_OK = 0;
    static final int NET_FAIL = -1;

    public void onNetReceive(int type, int actionId, Object obj);
}
