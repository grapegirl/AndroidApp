package com.kiki.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kiki.android.mgr.net.INetReceive;
import com.kiki.android.mgr.net.UrlManager;
import com.kiki.android.utils.StringUtils;

import java.util.HashMap;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HTTPUrlConnectionActivity
 * @Description : HTTP 통신
 * @since 2015-06-30.
 */
public class HTTPUrlConnectionActivity extends Activity implements View.OnClickListener, INetReceive, Handler.Callback {

    private Button mButton = null;
    private TextView mTextView = null;
    private Handler mHandler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        mTextView.setText(null);
        UrlManager manager = new UrlManager("http://210.220.248.236:8080/MemoServer/jsp/login.jsp", true, this, 0);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", "mihye");
        data.put("title", "title");
        data.put("content", "content");
        manager.execute(StringUtils.getHTTPPostSendData(data));
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0://데이타 설정
                String data = (String) msg.obj;
                mTextView.setText(data);
                break;
        }
        return false;
    }

    @Override
    public void onNetReceive(int type, int actionId, Object obj) {
        switch (type) {
            case NET_OK:
                String data = (String) obj;
                mHandler.sendMessage(mHandler.obtainMessage(0, data));
                break;
        }
    }
}
