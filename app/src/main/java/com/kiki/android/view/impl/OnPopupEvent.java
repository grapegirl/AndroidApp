package com.kiki.android.view.impl;

/***
 * @author grapegirl
 * @version 1.1
 * @Class Name : PopupListener
 * @Description : 팝업 관련 리스너
 * @since 2017. 02. 11.
 */
public interface OnPopupEvent {

    public static final int POPUP_BTN_OK = 0;
    public static final int POPUP_BTN_CLOSEE = 1;
    public static final int POPUP_BACK = 2;
    public static final int POPUP_DISPOSE = 3;

    public void onPopupAction(int popId, int what, Object obj);
}
