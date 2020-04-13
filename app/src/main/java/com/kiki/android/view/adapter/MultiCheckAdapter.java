/**
 *
 */
package com.kiki.android.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kiki.android.R;

import java.util.ArrayList;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : MultiCheckAdapter.java
 * @Description : 체크박스 여러개 선택가능한 Adpater
 * @since 2014.08.01
 */
public class MultiCheckAdapter extends BaseAdapter implements OnClickListener {
    private static final int MAX_SELECTED_CNT = 2;
    private Context mContext = null;
    private int mRes = 0;
    private ArrayList<String> mDataList = null;
    private ArrayList<String> mCodeList = null;
    private int mSelectIndex = -1;
    private boolean[] m_radioBtn = null;

    public MultiCheckAdapter(Context context, ArrayList<String> data, ArrayList<String> listCodeData, int res) {
        mContext = context;
        mRes = res;
        mDataList = data;
        mCodeList = listCodeData;
        m_radioBtn = new boolean[mDataList.size()];
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mRes, null);
        }
        ((TextView) view.findViewById(R.id.spiner_item_textview)).setText(mDataList.get(position));
        CheckBox radio = (CheckBox) view.findViewById(R.id.spiner_item_checkbox);
        radio.setChecked(m_radioBtn[position]);
        radio.setTag(String.valueOf(position));
        radio.setOnClickListener((OnClickListener) this);
        view.setTag(String.valueOf(position));
        view.setOnClickListener((OnClickListener) this);
        return view;
    }

    public void setDefaultChecked(int index) {
        if (mSelectIndex > 0) {
            mSelectIndex = index;
            m_radioBtn[mSelectIndex] = true;
        }
    }

    public String getSelectedItem() {
        return mDataList.get(mSelectIndex);
    }

    public int getSelectedIndex() {
        return mSelectIndex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spiner_item_checkbox://라디오 버튼 선택
            case R.id.spiner_list_panel://글자 선택
                mSelectIndex = Integer.parseInt((String) v.getTag());
                if (checkStatusCnt() < MAX_SELECTED_CNT) {
                    m_radioBtn[mSelectIndex] = !m_radioBtn[mSelectIndex];
                } else {
                    if (m_radioBtn[mSelectIndex] == true) {
                        m_radioBtn[mSelectIndex] = false;
                    }
                }
                notifyDataSetChanged();
                break;
        }
    }

    private int checkStatusCnt() {
        int count = 0;
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true) {
                count++;
            }
        }
        return count;
    }

    public String getSelectedItemList() {
        String data = "";
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true)
                data += mDataList.get(i) + ",";
        }
        return data;
    }

    public String getSelectedCodeList() {
        String data = "";
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true)
                data += mCodeList.get(i) + ",";
        }
        return data;
    }
}
