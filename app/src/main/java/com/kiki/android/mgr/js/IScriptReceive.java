package com.kiki.android.mgr.js;

/**
 * @author grape girl
 * @version 1.1
 * @Class Name : IScriptReceive
 * @Description : 자바스크립트로 부터 메소드 호출
 * @since 2017-02-11.
 */
public interface IScriptReceive {

    static final int LOAD_PAGE_URL = 0;
    static final int SEND_FILE_ATTACKED = 1;
    static final int ON_PAGE_STARTED = 3;
    static final int ON_PAGE_FINISHED = 4;
    static final int ON_PROGRESS_CHAGNED = 5;
    static final int HISTORY_BACK = 6;

    public void onScriptReceive(int what, Object obj);
}
