package com.kiki.android.mgr.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : BleAcceptMgr
 * @Description : 블루투스 기기 등록 요청 수락 클래스
 * @since 2017-02-11
 */
public class BleAcceptMgr extends AsyncTask<Void, Void, Void> {

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    private static final String TAG = BleAcceptMgr.class.getSimpleName();
    private static final String SERVER_NAME = "BluetoothChat";
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothServerSocket mServerSocket;
    private IBleReceive mBlListener = null;
    private BleSendMgr mConnectedThread;
    private int mState = -1;

    public BleAcceptMgr(BluetoothAdapter bluetoothAdapter, IBleReceive mBlConnectionListener) {
        mBlListener = mBlConnectionListener;
        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVER_NAME, SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@ BleAcceptMgr 생성자 IOException");
        }
        mServerSocket = serverSocket;
        mState = STATE_NONE;
    }

    @Override
    protected Void doInBackground(Void... params) {
        BluetoothSocket socket = null;
        try {
            socket = mServerSocket.accept();
        } catch (IOException e) {
            System.out.println("@@@ BleAcceptMgr doInBackground  IOException");
        }
        connected(socket, socket.getRemoteDevice());
        return null;
    }

    private void connected(BluetoothSocket socket, BluetoothDevice
            device) {
        mConnectedThread = new BleSendMgr(socket, mBlListener);
        mState = STATE_CONNECTED;
    }

    public void cancle() {
        try {
            mServerSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }

    public void setStats(int sts) {
        mState = sts;
    }
}
