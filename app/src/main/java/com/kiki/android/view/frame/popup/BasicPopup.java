package com.kiki.android.view.frame.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kiki.android.R;
import com.kiki.android.view.custom.KDialog;
import com.kiki.android.view.impl.OnPopupEvent;

/**
 * Created by mihyeKim on 2017-02-12.
 */

public class BasicPopup extends KDialog implements View.OnClickListener  {

    public BasicPopup(Context context, String title, int contentView, OnPopupEvent popupEventListener, boolean isCancle, int popId) {
        super(context, title, contentView, popupEventListener,
                isCancle, popId);
    }

    @Override
    protected void initDialog() {
        ((TextView)mDialogView.findViewById(R.id.popup_view_title)).setText("팝업");
        ((TextView)mDialogView.findViewById(R.id.popup_view_content)).setText(mContent);
        ((Button)mDialogView.findViewById(R.id.popup_view_button)).setText("확인");

        mDialogView.findViewById(R.id.popup_view_button).setOnClickListener(this);
    }

    @Override
    protected void destroyDialog() {
    }

    @Override
    public void onClick(View v) {
        mPopupEventListener.onPopupAction(mPopId, OnPopupEvent.POPUP_BTN_OK, null);
        closeDialog();
    }
}
