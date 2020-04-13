package com.kiki.android.mgr.wifi;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : IWiFiStatusReceive
 * @Description : 와이파이 상태 변경 리스너
 * @since 2017-02-11.
 */
public interface IWiFiStatusReceive {

    static final int CONNECTED = 1001;
    static final int DISCONNECTED = 1002;
    static final int CONNECTING = 1003;
    static final int DISCONNECTING = 1004;
    static final int ETC = 1005;

    public void OnChangedStatus(int what, Object obj);

}
