package com.kiki.android.mgr.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : WifiMonitor
 * @Description : 와이파이 상태 모니터 리시버
 * @since 2017-02-11.
 */
public class WifiMonitor extends BroadcastReceiver {

    private static final String TAG = WifiMonitor.class.getSimpleName();
    private IWiFiStatusReceive mIWiFiStatusListener;

    public WifiMonitor(IWiFiStatusReceive listener) {
        mIWiFiStatusListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String strAction = intent.getAction();
        //KLog.d(this.getClass().getSimpleName(), this.getClass() + " onReceive action :  " + strAction);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (strAction.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.CONNECTED, null);
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.DISCONNECTED, null);
            } else if (networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.CONNECTING, null);
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTING) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.DISCONNECTING, null);
            } else if (networkInfo.getState() == NetworkInfo.State.UNKNOWN) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.ETC, null);
            } else if (networkInfo.getState() == NetworkInfo.State.SUSPENDED) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.ETC, null);
            }
        }
    }
}
