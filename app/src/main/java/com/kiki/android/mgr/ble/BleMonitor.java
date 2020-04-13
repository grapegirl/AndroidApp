package com.kiki.android.mgr.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.kiki.android.R;
import com.kiki.android.utils.KLog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : BleMonitor
 * @Description : 블루투스 상태 모니터 리시버
 * @since 2017-02-11
 */
public class BleMonitor {

    private static final String TAG = BleMonitor.class.getSimpleName();
    private BluetoothAdapter mBluetoothAdapter = null;
    private ArrayList<String> mArrayList = null;
    private ArrayList<String> mSearchArrayList = null;
    private Context mContext = null;
    private IBleReceive mIBleReceive = null;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("@@ onReceive action : " + action);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                    String data = device.getName() + "/" + device.getAddress() + "/" + rssi;
                    //검색 후 주변 디바이스가 리스트에 없는 기기이면 추가
                    if (!checkAddedListItem(data)) {
                        mSearchArrayList.add(data);
                        mIBleReceive.onReceiveAction(IBleReceive.SCAN_SEARCH_DEVICES, data);
                    }
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if ((state == BluetoothAdapter.STATE_OFF)) {
                    mIBleReceive.onReceiveAction(IBleReceive.SCAN_BLE_OFF, null);
                } else if ((state == BluetoothAdapter.STATE_ON)) {
                    mIBleReceive.onReceiveAction(IBleReceive.SCAN_BLE_ON, null);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //검색 후 완료되면 주변 디바이스 정보 반환
                mIBleReceive.onReceiveAction(IBleReceive.SCAN_COMPLE_SEARCH, true);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                int bondState = intent.getIntExtra(
                        BluetoothDevice.EXTRA_BOND_STATE, -1);
                if (bondState == BluetoothDevice.BOND_NONE) {
                    System.out.println("연결안됨");
                } else if (bondState == BluetoothDevice.BOND_BONDED) {
                    System.out.println("연결됨");
                    mIBleReceive.onReceiveAction(IBleReceive.SCAN_ADDED_IST, getAddedList());
                }
            }
        }
    };

    public BleMonitor(Context context, IBleReceive bleReceive) {
        mContext = context;
        mIBleReceive = bleReceive;
        mArrayList = new ArrayList<String>();
        mSearchArrayList = new ArrayList<String>();

        // 블루투스 어뎁터
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, mContext.getString(R.string.blutooth_main_notsupport), Toast.LENGTH_LONG).show();
            return;
        } else {
            KLog.d(this.getClass().getSimpleName(), mContext.getString(R.string.blutooth_main_support));
        }

        // 브로드캐스트 리시버 등록
        IntentFilter Filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, Filter);
        Filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, Filter);

        Filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mReceiver, Filter);
        Filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, Filter);
    }

    /**
     * zoyi 에서 사용하는 맥변환 코드
     * 5gh 대역대의 mac address 를 2.5g 대역대로 변환해주는 소스
     * <p/>
     *
     * @param mac   가공할 맥어드레스(5G)  -   (1) 맥어드레스 +1
     * @param value 번호
     *              <p/>
     *              1) 2.4G WIFI & LAN 맥어드레스
     *              장비 하단에 쓰여있는 맥어드레스와 동일합니다.
     *              2) WAN 맥 어드레스
     *              (1) 맥어드레스 -1
     *              3) 블루투스 맥 어드레스
     *              (1) 맥어드레스 +2
     */
    public static String changeMacAddress(String mac, int value) {
        String splitMac = mac.replace(":", "");

        //hex 코드로 변환
        String hexMac = "";
        for (int i = 0; i < splitMac.length(); i++) {
            hexMac += String.format("%02X ", (int) splitMac.charAt(i));
        }

        //변환된 hex 코드로 , zoyi 룰에 맞게 쪼갠 후 , 합침
        ArrayList<String> arrChangeMac = new ArrayList<String>();
        String[] arrMac = hexMac.split(" ");
        for (int i = 0; i < arrMac.length; i++) {
            if (i == arrMac.length - 1) {
                int nLastMac = Integer.valueOf(arrMac[i]);
                String strLastMacChange;
                if (value == 1) {
                    strLastMacChange = Integer.toString(nLastMac - 1);
                } else if (value == 2) {
                    strLastMacChange = Integer.toString(nLastMac - 2);
                } else {
                    strLastMacChange = Integer.toString(nLastMac + 1);
                }
                arrChangeMac.add(strLastMacChange);

            } else {
                arrChangeMac.add(arrMac[i]);
            }
        }

        //변환 후 , 주소를 자료에 담음
        String finalStrMac = "";
        for (int k = 0; k < arrChangeMac.size(); k++) {
            finalStrMac += arrChangeMac.get(k);
        }

        //담음 hex 코드를 다시 String 으로 변환
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < finalStrMac.length(); i += 2) {
            str.append((char) Integer.parseInt(finalStrMac.substring(i, i + 2), 16));
        }

        return str.toString();
    }

    public boolean getBlutoothAdpaterStatus() {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.isEnabled();
        } else {
            return false;
        }
    }

    public boolean getBlutoothAdpaterDiscoveringStatus() {
        return mBluetoothAdapter.isDiscovering();
    }

    public void setBluetoothStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.enable();
        } else {
            mBluetoothAdapter.disable();
        }
    }

    public void setBluetoothSearchStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.startDiscovery();
        } else {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private boolean checkAddedListItem(String newItem) {
        for (int i = 0; i < mSearchArrayList.size(); i++) {
            String item = mSearchArrayList.get(i);
            if (item.equals(newItem)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getAddedList() {
        if (mArrayList != null) {
            mArrayList.clear();
        }
        if (mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            System.out.println("페어링된 기기 리스트 : " + pairedDevices.size());
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    mArrayList.add(device.getName() + "\n" + device.getAddress());
                    System.out.println("@@ 페어링된 기기 리스트 : " + device.getName() + "," + device.getAddress());
                }
            }
            return mArrayList;
        } else {
            return null;
        }
    }

    public void close() {
        mContext.unregisterReceiver(mReceiver);
        if (mArrayList != null) {
            mArrayList.clear();
            mArrayList = null;
        }
        if (mSearchArrayList != null) {
            mSearchArrayList.clear();
            mSearchArrayList = null;
        }
    }

    public boolean setUnpairDevice(String currentMac) {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        try {
            Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
            Method removeBondMethod = btDeviceInstance.getMethod("removeBond");

            boolean cleared = false;
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                String mac = bluetoothDevice.getAddress();
                if (mac.equals(currentMac)) {
                    removeBondMethod.invoke(bluetoothDevice);
                    System.out.println("Cleared Pairing");
                    cleared = true;
                    break;
                }
            }
            if (!cleared) {
                System.out.println("Not Paired");
                return false;
            }
        } catch (Throwable th) {
            System.out.println("Error pairing");
            return false;
        }
        return true;
    }

    public boolean setPairingDevice(String currentMac) {
        try {
            boolean cleared = false;
            Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
            Method createBondMethod = btDeviceInstance.getMethod("createBond");

            BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(currentMac);
            cleared = (boolean) createBondMethod.invoke(bluetoothDevice);
            if (!cleared) {
                System.out.println("Not Paired");
                return false;
            }
        } catch (Throwable th) {
            System.out.println("Not Pairing");
            return false;
        }
        return true;
    }

    public BluetoothDevice getBluetoothDevice(String mac) {
        return mBluetoothAdapter.getRemoteDevice(mac);
    }
}