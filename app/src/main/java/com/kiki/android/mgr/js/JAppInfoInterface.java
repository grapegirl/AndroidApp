package com.kiki.android.mgr.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.kiki.android.utils.AppUtils;

/***
 * @author grape girl
 * @version 1.0
 * @Class Name : JMenuInterface
 * @Description : 앱 정보 인터페이스 클래스
 * @since 2015-08-21.
 */
public class JAppInfoInterface {

    private IScriptReceive mIScriptReceive;

    private Context mContext;

    public JAppInfoInterface(Context context, IScriptReceive scriptReceive) {
        mContext = context;
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    public int getAppVersionCode() {
        return AppUtils.getVersionCode(mContext);
    }

    @JavascriptInterface
    public String getAppVersionName() {
        return AppUtils.getVersionName(mContext);
    }

    @JavascriptInterface
    public String getUserPhoneLanguage() {
        return AppUtils.getUserPhoneLanuage(mContext);
    }
}
