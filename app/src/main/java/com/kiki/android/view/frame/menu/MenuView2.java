package com.kiki.android.view.frame.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kiki.android.R;
import com.kiki.android.view.custom.KView;
import com.kiki.android.view.frame.popup.BasicPopup;
import com.kiki.android.view.impl.OnPopupEvent;


/***
 * @author grape girl
 * @version 1.0
 * @Class Name : MainMenuView
 * @Description : 메뉴 뷰
 * @since 2017. 02. 11.
 */
public class MenuView2 extends KView implements OnPopupEvent, android.view.View.OnClickListener {

    private BasicPopup mBasicPoup = null;

    public MenuView2(Context context, int res, int index) {
        super(context, res, index);
        initView();
    }

    @Override
    protected void initView() {
        Log.d("mh", "@@ MainMenuView initView");
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCurrentView = inflater.inflate(mRes, null);
        Log.d("mh", "@@ MainMenuView initView mContext : " + mContext);
        Log.d("mh", "@@ MainMenuView initView mRes : " + mRes);
        Log.d("mh", "@@ MainMenuView initView mCurrentView : " + mCurrentView);

        mCurrentView.findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (mBasicPoup == null) {
                    mBasicPoup = new BasicPopup(mContext, "Test", R.layout.popup_layout, this, true, 0);
                    mBasicPoup
                            .showDialog();
                }
                break;

        }
    }

    @Override
    public void onPopupAction(int popId, int what, Object obj) {
        switch (what) {
            // 확인 버튼
            case POPUP_BTN_OK:
                // 취소 버튼
            case POPUP_BTN_CLOSEE:
                if (mBasicPoup.isShowing()) {
                    mBasicPoup.closeDialog();
                    mBasicPoup = null;
                }
                break;
        }
    }
}
