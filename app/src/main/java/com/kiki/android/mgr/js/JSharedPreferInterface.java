package com.kiki.android.mgr.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.kiki.android.utils.SharedPreferenceUtils;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JSharedPreferInterface
 * @Description : 프레퍼런스 값 공유 인터페이스
 * @since 2015-09-03.
 */
public class JSharedPreferInterface {

    private IScriptReceive mIScriptReceive;
    private Context mContext;

    public JSharedPreferInterface(Context context, IScriptReceive scriptReceive) {
        mContext = context;
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    public void setPreferenceValue(String keyName, String value) {
        SharedPreferenceUtils.write(mContext, keyName, value);
    }


    @JavascriptInterface
    public String getPreferenceValue(String keyName) {
        return (String) SharedPreferenceUtils.read(mContext, keyName, SharedPreferenceUtils.SHARED_PREF_VALUE_STRING);
    }
}
