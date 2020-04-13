package com.kiki.android.mgr.js;


import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JAlertInterface
 * @Description : 자바스크립트 팝업 인터페이스
 * @since 2015-08-03.
 */
public class JAlertInterface {

    private Context mContext;

    public JAlertInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    /**
     * 팝업 표시
     */
    public void setMessage(String arg) { // must be final
        Toast.makeText(mContext, arg, Toast.LENGTH_LONG).show();
    }

}
