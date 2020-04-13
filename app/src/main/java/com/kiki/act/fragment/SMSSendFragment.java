package com.kiki.act.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.kiki.android.R;
import com.kiki.android.utils.PermissionUtils;
import com.kiki.android.view.frame.popup.BasicPopup;
import com.kiki.android.view.impl.OnPopupEvent;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SMSSendFragment extends Fragment implements OnPopupEvent, View.OnClickListener {

    private int REQ_PERMISSION = 1000;
    private BasicPopup mBasicPopup;

    private int POPUP_PERMISSION    = 10;
    private int POPUP_ALERT         = 20;

    private Unbinder unbinder;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    @OnClick({R.id.button_second, R.id.button_send})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_second:
                NavHostFragment.findNavController(SMSSendFragment.this)
                        .navigate(R.id.action_SecondFragment_to_UIFragment);
                break;
            case R.id.button_send:
                if(PermissionUtils.checkPermission(getContext(), Manifest.permission.SEND_SMS) == false){
                    requestPermission(Manifest.permission.SEND_SMS, REQ_PERMISSION);
                    return;
                }
                regPendingIntent();

                String[] stringArr = {"test"};
                for (int i = 0; i < stringArr.length; i++) {
                    String[] msg;
                    try {
                        msg = getArrayToString(stringArr[i], 80);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        continue;
                    }

                    sendSMS("01050666713", msg);
                }
                break;
        }
    }

    public void requestPermission(String permission, int callback){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permission)) {
                // 한번이라도 거부를 한 케이스
                System.out.println("@@ requestPermission : shouldShowRequestPermissionRationale");
                mBasicPopup = new BasicPopup(this.getContext(), permission + "권한이 필요합니다.", R.layout.popup_layout, this, false, POPUP_PERMISSION);
                mBasicPopup.showDialog();
            }else{
                // 최초 케이
                requestPermissions(new String[]{permission}, callback);
                System.out.println("@@ requestPermission : requestPermissions ");
            }

        }
    }

    @Override
    public void onPopupAction(int popId, int what, Object obj) {
        mBasicPopup.closeDialog();
        if(popId == POPUP_PERMISSION){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQ_PERMISSION);
            System.out.println("@@ requestPermission : requestPermissions ");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_PERMISSION){
            System.out.println("@@ onActivity permissions.length : " + permissions.length);
            System.out.println("@@ onActivity grantResults.length : " + grantResults.length);
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                System.out.println("@@ onActivity SMS Permission Granted!");
            }else{
                System.out.println("@@ onActivity SMS Permission Not!  : " + grantResults[0]);
                mBasicPopup = new BasicPopup(this.getContext(),  "권한을 거부해서 정상적으로 기능을 사용할 수 없습니다.", R.layout.popup_layout, this, false, POPUP_ALERT);
                mBasicPopup.showDialog();
            }
        }

    }

    /**
     * 여러 문자보내기
     *
     * @param phone 보낼사람들
     * @param msg   보낼문자들
     */
    private void sendSMS(final String phone, final String[] msg) {
        final PendingIntent sentIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent("SMS_SENT_ACTION"), 0);
        final PendingIntent deliveredIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        for (int i = 0; i < msg.length; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("ddddddddd", "@@ InterruptedException Error!! ");
            }
            SmsManager sms = SmsManager.getDefault();
            if (msg[i].length() > 0)
                sms.sendTextMessage(phone, null, msg[i], sentIntent, deliveredIntent);
        }

    }

    public void regPendingIntent() {
        getContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        getContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

    }

    /**
     * 문자 바이트길이많금 잘라 배열로 가져오기
     *
     * @param data
     * @param len
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String[] getArrayToString(String data, int len) throws UnsupportedEncodingException {

        if (data == null) return null;
        String[] ary = null;
        // try {
        // raw 의 byte
        byte[] rawBytes = data.getBytes("euc-kr");
        int rawLength = rawBytes.length;

        if (rawLength > len) {
            int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
            ary = new String[aryLength];

            int endCharIndex = 0; // 문자열이 끝나는 위치
            String tmp;
            for (int i = 0; i < aryLength; i++) {

                if (i == (aryLength - 1)) {
                    tmp = data.substring(endCharIndex);
                } else {

                    int useByteLength = 0;
                    int rSize = 0;
                    for (; endCharIndex < data.length(); endCharIndex++) {

                        if (data.charAt(endCharIndex) > 0x007F) {
                            useByteLength += 2;
                        } else {
                            useByteLength++;
                        }
                        if (useByteLength > len) {
                            break;
                        }
                        rSize++;
                    }
                    tmp = data.substring((endCharIndex - rSize), endCharIndex);
                }

                ary[i] = tmp;
            }

        } else {
            ary = new String[]{data};
        }

        return ary;
    }
}
