package com.kiki.android.mgr.phone;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : SMSManager
 * @Description : SMS 문자 관련 매니저
 * @since 2017-02-11.
 */
public class SMSManager {

    private static SMSManager INSTANCE;
    private final String TAG = this.getClass().getSimpleName();
    private Context m_Context;
    private ISMSReceive mISMSReceive;
    private boolean mbIsUsed;
    private BroadcastReceiver mReceiverSendAction = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mISMSReceive != null) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        mISMSReceive.onSMSReceive(ISMSReceive.SEND_OK, true);
                        break;
                    default:
                        mISMSReceive.onSMSReceive(ISMSReceive.SEND_FAIL, false);
                        break;
                }
            }
        }
    };
    private BroadcastReceiver mReceiverDelveredAction = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mISMSReceive != null) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        mISMSReceive.onSMSReceive(ISMSReceive.RECEIVE_OK, false);
                        break;
                    case Activity.RESULT_CANCELED:
                        mISMSReceive.onSMSReceive(ISMSReceive.RECEIVE_FAIL, false);
                        break;
                }
            }
        }
    };

    public static SMSManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SMSManager();
        }
        return INSTANCE;
    }

    public void setContext(Context context) {
        m_Context = context;
        m_Context.registerReceiver(mReceiverSendAction, new IntentFilter("SMS_SENT_ACTION"));
        m_Context.registerReceiver(mReceiverDelveredAction, new IntentFilter("SMS_DELIVERED_ACTION"));
    }

    public void sendMessage(String phoneNum, String msg, ISMSReceive SMSReceive) throws IllegalAccessError {
        if (mbIsUsed)
            throw new IllegalAccessError("Sending SMS");
        mbIsUsed = true;
        mISMSReceive = SMSReceive;
        final PendingIntent sentIntent = PendingIntent.getBroadcast(m_Context, 0, new Intent("SMS_SENT_ACTION"), 0);
        final PendingIntent deliveredIntent = PendingIntent.getBroadcast(m_Context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNum, null, msg, sentIntent, deliveredIntent);
    }
}
