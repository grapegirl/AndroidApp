package com.kiki.android.mgr.ble;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BleSendMgr
 * @Description : 블루투스 파일 전송 관련 클래스
 * @since 2017-02-11
 */
public class BleSendMgr extends AsyncTask<Void, Void, Void> {

    private static final String TAG = BleSendMgr.class.getSimpleName();
    private final BluetoothSocket mSocket;
    private final InputStream mInStream;
    private final OutputStream mOutStream;
    private IBleReceive mBlListener = null;


    public BleSendMgr(BluetoothSocket bluetoothSocket, IBleReceive mBlConnectionListener) {
        mSocket = bluetoothSocket;
        mBlListener = mBlConnectionListener;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("@@ BleSendMgr 생성자 IOException");
        }
        mInStream = inputStream;
        mOutStream = outputStream;
    }

    @Override
    protected Void doInBackground(Void... params) {
        byte[] buffer = new byte[1024];
        int bytes;
        while (true) {
            try {
                bytes = mInStream.read(buffer);
            } catch (IOException e) {
                System.out.println("@@@ BleSendMgr doInBackground IOException");
                break;
            }
        }
        mBlListener.onReceiveAction(mBlListener.RECEIVE_OK, null);
        return null;
    }

    public void write(byte[] buffer) {
        OutputStream outputStream;
        try {
            outputStream = mSocket.getOutputStream();
            buffer[buffer.length - 1] = 0;
            outputStream.write(buffer);
            mBlListener.onReceiveAction(mBlListener.SEND_OK, "전송완료");
        } catch (IOException e) {
            System.out.println("@@@ BleSendMgr write IOException");
        }
    }

    public boolean getOutputStream() {
        return mOutStream != null ? true : false;
    }

    public boolean getInputStream() {
        return mInStream != null ? true : false;
    }

    public void cancle() {
        try {
            mSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }
}
