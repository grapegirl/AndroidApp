package com.kiki.android.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kiki.android.R;
import com.kiki.android.view.impl.OnPopupEvent;

/***
 * @author grapegirl
 * @version 1.1
 * @Class Name : MultiDialog
 * @Description : 최상위 팝업
 * @since 2017. 02. 11.
 */
public abstract class MultiDialog implements DialogInterface.OnKeyListener {

    public static final int TYPE_BASIC_POPUP = 0;
    public static final int TYPE_LIST_POPUP = 1;
    public static final int TYPE_RADIO_POPUP = 2;
    protected Context m_Context = null;
    protected int m_Res = 0;
    protected View m_CurrentView = null;
    protected Dialog mDialog = null;
    protected OnPopupEvent mPopupEvent = null;
    protected int mPopId;
    protected boolean mCancleable = false;
    protected OnClickListener m_ClickListener = null;
    protected android.content.DialogInterface.OnClickListener mDialogClickListener = null;
    protected OnMultiChoiceClickListener m_MultiChoiceClickListener;
    private int m_nDefaultWidth = 656;
    private int m_nDefaultHeight = 500;

    public MultiDialog(Context context, int res, OnPopupEvent l) {
        m_Context = context;
        m_Res = res;
        mPopupEvent = l;
        createPopupUI();
        init();
    }

    private void createPopupUI() {
        LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_CurrentView = inflater.inflate(m_Res, null, false);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) m_Context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        if (m_nDefaultWidth == -1) {
            m_nDefaultWidth = metrics.widthPixels;
        }
        if (m_nDefaultHeight == -1) {
            m_nDefaultHeight = metrics.heightPixels / 2;
        }

        mDialog = new Dialog(m_Context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        setContentView(0, null);
        mDialog.setContentView(m_CurrentView, new LinearLayout.LayoutParams(m_nDefaultWidth, 194 + m_nDefaultHeight));
    }

    protected void setTitle(String title) {
        RelativeLayout titleView = (RelativeLayout) m_CurrentView.findViewById(R.id.popup_view_title);
        TextView textView = new TextView(m_Context);
        textView.setText(title);
        textView.setPadding(50, 0, 0, 0);
        textView.setTextColor(Color.BLACK);
        titleView.addView(textView);
        titleView.setBackgroundColor(Color.GRAY);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, 97));
    }

    protected void setTitle(int drawbale, String title) {
        RelativeLayout titleView = (RelativeLayout) m_CurrentView.findViewById(R.id.popup_view_title);
        ImageView img = new ImageView(m_Context);
        img.setBackgroundResource(drawbale);
        img.setLayoutParams(new LayoutParams(200, 150));
        img.setPadding(50, 0, 0, 0);

        titleView.addView(img);
        TextView textView = new TextView(m_Context);
        textView.setText(title);
        textView.setPadding(250, 30, 0, 0);
        textView.setTextColor(Color.WHITE);
        titleView.addView(textView);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, 97));
    }

    protected void setContentView(int flag, Object obj) {
        switch (flag) {
            case TYPE_BASIC_POPUP:
                String msg = (String) obj;
                RelativeLayout contentView = (RelativeLayout) m_CurrentView.findViewById(R.id.popup_view_content);
                ScrollView scrollview = new ScrollView(m_Context);
                scrollview.setMinimumWidth(m_nDefaultWidth - 100);
                scrollview.setMinimumHeight(m_nDefaultHeight - 100);
                scrollview.setPadding(50, 50, 100, 50);
                TextView textView = new TextView(m_Context);
                textView.setText(msg);
                textView.setTextColor(Color.BLACK);
                scrollview.addView(textView);
                contentView.addView(scrollview);
                contentView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, m_nDefaultHeight));
                contentView.setBackgroundColor(Color.WHITE);
                break;
            case TYPE_LIST_POPUP:
                break;
            case TYPE_RADIO_POPUP:
                break;
        }
        mDialog.setContentView(m_CurrentView, new LayoutParams(m_nDefaultWidth, 194 + m_nDefaultHeight));
    }

    protected void setButton(String[] btnNames, int[] btnIDs, OnClickListener onClickListener) {
        m_ClickListener = onClickListener;
        LinearLayout buttonView = (LinearLayout) m_CurrentView.findViewById(R.id.popup_view_button);
        buttonView.setBackgroundColor(Color.WHITE);
        int btnSize = btnNames.length;
        int btnWidth = m_nDefaultWidth / btnSize;
        int btnHeight = m_nDefaultHeight / btnSize;
        for (int i = 0; i < btnSize; i++) {
            Button button = new Button(m_Context);
            button.setWidth(btnWidth);
            button.setHeight(btnHeight);
            button.setId(btnIDs[i]);
            button.setOnClickListener((OnClickListener) m_ClickListener);
            button.setText(btnNames[i]);
            buttonView.addView(button);
        }
    }

    protected abstract void init();

    public void initPopup(Object obj) {
    }

    public void setPopupData(Object obj) {
    }

    public void setOpnePopup() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void setCancleable(boolean isCancleable) {
        mCancleable = isCancleable;
        mDialog.setCancelable(mCancleable);
    }

    public void closePopup() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        } else {
            return false;
        }
    }

    public void onPopupBackPressed() {
        if (mCancleable) {
            mPopupEvent.onPopupAction(mPopId, OnPopupEvent.POPUP_BACK, null);
        } else {
            mPopupEvent.onPopupAction(mPopId, OnPopupEvent.POPUP_BTN_OK, null);
        }
    }

    protected void dispose() {
        if (m_CurrentView != null) {
            m_CurrentView = null;
        }
        m_Context = null;
        m_Res = 0;
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            onPopupBackPressed();
        }
        return true;
    }

    public void setPopupSize(int width, int height) {
        m_nDefaultWidth = width;
        m_nDefaultHeight = height;
    }
}
