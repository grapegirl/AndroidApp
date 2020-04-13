package com.kiki.android.mgr.js;

import android.webkit.JavascriptInterface;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JLoadUrlInterface
 * @Description : 자바스크립트 페이지 이동 인터페이스
 * @since 2015-08-03.
 */
public class JLoadUrlInterface {

    private IScriptReceive mIScriptReceive;

    public JLoadUrlInterface(IScriptReceive scriptReceive) {
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    /**
     * 페이지 이동
     */
    public void loadURL() {
        mIScriptReceive.onScriptReceive(IScriptReceive.LOAD_PAGE_URL, null);
    }

    @JavascriptInterface
    /**
     * 페이지 이동
     * @param url 이동할 url 주소
     */
    public void loadHomePageURL(String url) { // must be final
        mIScriptReceive.onScriptReceive(IScriptReceive.LOAD_PAGE_URL, url);
    }
}
