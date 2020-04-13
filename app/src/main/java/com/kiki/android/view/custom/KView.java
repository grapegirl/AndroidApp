package com.kiki.android.view.custom;

import android.app.Activity;
import android.content.Context;
import android.view.View;


/***
 * @author grape girl
 * @version 1.0
 * @Class Name : KView
 * @Description : 기본 뷰
 * @since 2017. 02. 11.
 */
public abstract class KView extends View {

    protected int mMenuIndex = -1;
    protected int mRes = -1;
    protected int mHistory = -1;
    protected Context mContext = null;
    protected View mCurrentView = null;
    protected Activity mAcitivity = null;

    public KView(Context context, int res, int index) {
        super(context);
        mContext = context;
        mRes = res;
        mMenuIndex = index;
    }

    public KView(Context context, int res, int index, Activity activity) {
        super(context);
        mContext = context;
        mRes = res;
        mMenuIndex = index;
        mAcitivity = activity;
    }

    protected abstract void initView();

    public View getCurrentView() {
        return mCurrentView;
    }
}
