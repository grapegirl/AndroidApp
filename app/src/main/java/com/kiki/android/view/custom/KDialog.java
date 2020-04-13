package com.kiki.android.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kiki.android.view.frame.menu.MainConstants;
import com.kiki.android.view.impl.OnPopupEvent;


/***
 * @author grape gir
 * @version 1.1
 * @Class Name : KDialog
 * @Description : 다이얼로그
 * @since 2017. 02. 11.
 */
public abstract class KDialog implements DialogInterface.OnKeyListener {

    protected static Dialog mDialog = null;
    protected Context mContext = null;
    protected View mDialogView = null;
    protected OnPopupEvent mPopupEventListener = null;
    protected int mPopId;
    protected boolean mCancleable = false;

    protected String mContent;

    public KDialog(Context context, String content, int contentView, OnPopupEvent popupEventListener, boolean isCancle, int popId) {
        mContext = context;
        mPopupEventListener = popupEventListener;
        mPopId = popId;
        mCancleable = isCancle;
        mContent = content;

        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(mCancleable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(this);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mDialogView = inflater.inflate(contentView, null);

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        mDialog.setContentView(mDialogView, new ViewGroup.LayoutParams(MainConstants.POPUP_WIDTH, MainConstants.POPUP_HEIGHT));
        initDialog();
    }

    protected abstract void initDialog();

    protected abstract void destroyDialog();

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void closeDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            destroyDialog();
        }
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }


    public void setCanceledOnTouchOutside(boolean flag) {
        mDialog.setCanceledOnTouchOutside(flag);
    }

    public void onPopupBackPressed() {
        if (mCancleable) {
            mPopupEventListener.onPopupAction(mPopId, OnPopupEvent.POPUP_BACK, null);
        } else {
            mPopupEventListener.onPopupAction(mPopId, OnPopupEvent.POPUP_BTN_OK, null);
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            onPopupBackPressed();
        }
        return false;
    }

}
