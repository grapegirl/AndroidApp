package com.kiki.android.view.frame.menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.kiki.android.view.custom.KView;


/***
 * @author grape girl
 * @version 1.0
 * @Class Name : MainMenuView
 * @Description : 메뉴 뷰
 * @since 2017. 02. 11.
 */
public class MenuView extends KView {

    public MenuView(Context context, int res, int index) {
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

    }
}
