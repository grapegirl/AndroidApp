package com.kiki.android.mgr.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : BleConnectMgr
 * @Description : 블루투스 기기 등록 허용 클래스(Server)
 * @since 2017-02-11
 */
public class BleConnectMgr extends AsyncTask<Void, Void, Void> {

    private static final String TAG = BleConnectMgr.class.getSimpleName();
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
    private BluetoothAdapter mBluetoothAdapter;

    public BleConnectMgr(BluetoothDevice device, BluetoothAdapter bluetoothAdapter) {
        System.out.println("@@@ BleConnectMgr 생성자 ");
        BluetoothSocket tmp = null;
        mDevice = device;
        mBluetoothAdapter = bluetoothAdapter;
        try {
            // MY_UUID is the app’s UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@@ BleConnectMgr 생성자 IOException");
        }
        mSocket = tmp;
        System.out.println("@@@ BleConnectMgr 생성자 끝");
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BleConnectMgr doInBackground 시작");
        mBluetoothAdapter.cancelDiscovery();
        try {
            mSocket.connect();
            System.out.print("@@ BleConnectMgr doInBackground mSocket");
        } catch (IOException connectExption) {
            System.out.print("@@ BleConnectMgr doInBackground IOException 1 ");
            try {
                mSocket.close();
            } catch (IOException closeExption) {
                System.out.print("@@ BleConnectMgr doInBackground IOException 2 ");
                return null;
            }
        }
        System.out.println("@@@ BleConnectMgr doInBackground 끝");
        return null;
    }

    public void cancle() {
        try {
            mSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothServerConnectTask cancle IOException");
        }
    }

    public BluetoothSocket getSocket() {
        return mSocket;
    }
}
