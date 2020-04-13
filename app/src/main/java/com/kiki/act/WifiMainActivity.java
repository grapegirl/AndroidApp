package com.kiki.act;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kiki.android.R;
import com.kiki.android.mgr.wifi.IWiFiScanReceive;
import com.kiki.android.mgr.wifi.IWiFiStatusReceive;
import com.kiki.android.mgr.wifi.WiFiAP;
import com.kiki.android.mgr.wifi.WifiManager;
import com.kiki.android.mgr.wifi.WifiMonitor;
import com.kiki.android.view.custom.KProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :  WifiMainActivity
 * @Description : 와이파이 관련 메인 액티비티
 * @since 2015-07-09.
 */
public class WifiMainActivity extends Activity implements IWiFiStatusReceive, IWiFiScanReceive, View.OnClickListener, Handler.Callback {

    /**
     * AP 접속
     */
    private final int CONNECT_AP = 1;
    /**
     * AP 해제
     */
    private final int DISCONNET_AP = 2;
    /**
     * 리스트뷰
     */
    private ListView mListView;
    /**
     * wifi 매니저
     */
    private WifiManager mWifiMgr;
    /**
     * wifi 스캔 데이타
     */
    private ArrayList<WiFiAP> mWifitData;
    /**
     * 버튼
     */
    private Button mButton;
    /**
     * 핸들러
     */
    private Handler mHandler;
    /**
     * 어뎁터
     */
    private WifiManager mListAdpater;
    /**
     * 프로그래스바
     */
    private KProgressBar mProgressBar;
    /**
     * 접속할 AP 정보
     */
    private WiFiAP mConnectAp = null;

    /**
     * Wifi 모니터 객체
     */
    private WifiMonitor mWifiMonitor;


    @Override
    /***
     * 액티비티 생성 메소드
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.wifi_main_layout);

        //와이파이 모니터 객체 생성 및 리시버 등록
        mWifiMonitor = new WifiMonitor(this);
        IntentFilter receiverFilter = new IntentFilter();
        receiverFilter.addAction(android.net.wifi.WifiManager.WIFI_STATE_CHANGED_ACTION);
        receiverFilter.addAction(android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION);
        receiverFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiMonitor, receiverFilter);

        //mListView = (ListView) findViewById(R.id.wifi_main_listview);
        //registerForContextMenu(mListView);
        // mButton = (Button) findViewById(R.id.wifi_main_button);
        // mButton.setOnClickListener(this);

        mHandler = new Handler(this);
        mWifiMgr = new WifiManager(this, this);
        mProgressBar = new KProgressBar(this);
    }

    @Override
    /**
     * 컨텍스트 메뉴 선택시 메소드
     */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = contextMenuInfo.position;
        mConnectAp = mWifitData.get(index);
        boolean inputPassWord = (mConnectAp.getCapability().contains("WPA")) || (mConnectAp.getCapability().contains("WEP"));
        //연결하기
        if (item.getItemId() == CONNECT_AP) {
            if (inputPassWord) {
//                EditDialog dialog = new EditDialog(this, "AP 연결", "비밀번호를 입력해주세요", this);
//                dialog.showDialog();
            } else {
                mWifiMgr.connectAp(mConnectAp, "");
            }
        }// 연결해제하기
        else if (item.getItemId() == DISCONNET_AP) {
            mWifiMgr.disconnectAP();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWifiMonitor != null) {
            unregisterReceiver(mWifiMonitor);
        }

    }

    /***
     * Ap 리스트 결과 데이타 가공
     */
    public void onReceiveAction(Object obj) {
        List<ScanResult> dataList = (List<ScanResult>) obj;
        if (mWifitData == null) {
            mWifitData = new ArrayList<WiFiAP>();
        } else {
            mWifitData.clear();
        }
        for (int i = 0, n = dataList.size(); i < n; i++) {
            ScanResult data = dataList.get(i);
            WiFiAP Ap = new WiFiAP(data.SSID, String.valueOf(data.level), data.BSSID);
            Ap.setCapability(data.capabilities);
            mWifitData.add(Ap);
        }
        mProgressBar.setDataLoadingDialog(false, null);
        mHandler.sendEmptyMessage(0);
    }

    @Override
    /**
     * 클릭 메소드
     */
    public void onClick(View v) {
        boolean isScan = mWifiMgr.getIsScanning();
        if (!isScan) {
            mProgressBar.setDataLoadingDialog(true, "스캔 중입니다..");
            mWifiMgr.startScan();
        }
    }

    @Override
    /**
     * 메시지 처리 메소드
     */
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:// wifi 결과 표시
                // mListAdpater = new WifiListAdpater(this, R.layout.listview_wifi_line, mWifitData);
                // mListView.setAdapter(mListAdpater);
                break;
            case 1://비밀번호 입력완료시 접속
                String password = (String) msg.obj;
                mWifiMgr.connectAp(mConnectAp, password);
                break;
            case 2:
                String message = (String) msg.obj;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

//    @Override
//    public void onButtonItemSelected(int what, Object item) {
//        switch (what) {
//            case BUTTON_OK:
//                String password = (String) item;
//                mHandler.sendMessage(mHandler.obtainMessage(1, password));
//                break;
//        }
//    }

    @Override
    public void OnChangedStatus(int what, Object obj) {
        if (what == IWiFiStatusReceive.CONNECTED) {
            mHandler.sendMessage(mHandler.obtainMessage(2, "AP에 연결됨"));
        } else if (what == IWiFiStatusReceive.CONNECTING) {
            mHandler.sendMessage(mHandler.obtainMessage(2, "AP에 연결시도중.."));
        } else if (what == IWiFiStatusReceive.DISCONNECTING) {
            mHandler.sendMessage(mHandler.obtainMessage(2, "AP에 연결 안되는중..."));
        } else if (what == IWiFiStatusReceive.DISCONNECTED) {
            mHandler.sendMessage(mHandler.obtainMessage(2, "AP에 연결 안됨"));
        }
    }
}
