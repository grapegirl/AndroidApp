package com.kiki.android.mgr.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Build;

import com.kiki.android.utils.KLog;

import java.util.List;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : WifiManger.java
 * @Description : 와이파이 스캐너
 * @since 2017-02-11.
 */
public class WifiManager {

    private android.net.wifi.WifiManager mWifiManger = null;
    private IWiFiScanReceive mIScanListener = null;
    private Context mContext = null;

    private boolean isScanning = false;
    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            stopScan();
            if (intent.getAction().equals(android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                KLog.d(this.getClass().getSimpleName(), this.getClass() + " wifiReciever ");
                List<ScanResult> scanLists = mWifiManger.getScanResults();
                mIScanListener.onReceiveAction(scanLists);
            }
        }
    };

    public WifiManager(Context context, IWiFiScanReceive scanListener) {
        mContext = context;
        mIScanListener = scanListener;
        mWifiManger = (android.net.wifi.WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    public void startScan() {
        if (Build.VERSION.SDK_INT >= 18) {
            //4.3버전 이상에서 항상 검색 허용인 경우
            // 바로 스캔
            if (mWifiManger.isScanAlwaysAvailable()) {
                mWifiManger.startScan();
                isScanning = true;
                mContext.registerReceiver(mWifiReceiver, new IntentFilter(android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                KLog.d(this.getClass().getSimpleName(), this.getClass() + " startScan");
                return;
            }
        }
        if (mWifiManger.getWifiState() == android.net.wifi.WifiManager.WIFI_STATE_DISABLED) {
            mWifiManger.setWifiEnabled(true);
        }
        mWifiManger.startScan();
        isScanning = true;
        mContext.registerReceiver(mWifiReceiver, new IntentFilter(android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " startScan");
    }

    public void stopScan() {
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " stopScan");
        isScanning = false;
        mContext.unregisterReceiver(mWifiReceiver);
    }

    public boolean getIsScanning() {
        return isScanning;
    }

    public boolean connectAp(WiFiAP ap, String pass) {
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " connectAp ");
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " ssid :  " + ap.getSSID());
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " bssid :  " + ap.getMac());
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " getCapability :  " + ap.getCapability());
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " getDecibel :  " + ap.getDecibel());
        KLog.d(this.getClass().getSimpleName(), this.getClass() + " pass :  " + pass);

        //접속할 AP에대한 환경설정 하긴
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ap.getSSID());
        if (ap.getCapability().contains("WEP")) {
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        } else if (ap.getCapability().contains("WPA")) {
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.preSharedKey = "\"".concat(pass).concat("\"");
        } else {//개방형
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        }

        int netId = mWifiManger.addNetwork(wifiConfig);
        mWifiManger.disconnect();
        mWifiManger.enableNetwork(netId, true);
        boolean isResult = mWifiManger.reconnect();
        return isResult;
    }

    public boolean disconnectAP() {
        boolean isResult = mWifiManger.disconnect();
        return isResult;
    }
}
