package com.kiki.android.view.custom;

import android.app.ProgressDialog;
import android.content.Context;


/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : KProgressBar
 * @Description : 프로그래스바
 * @since 2015. 6. 30.
 */
public class KProgressBar {
    private ProgressDialog mDialog = null;
    private Context m_Context = null;

    public KProgressBar(Context m_context) {
        m_Context = m_context;
    }

    public void setDataLoadingDialog(boolean flag, String msg) {
        if (flag) {
            mDialog = new ProgressDialog(m_Context);
            mDialog.setMessage(msg);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }

    public boolean getDialogStatus() {
        return mDialog.isShowing();
    }
}
