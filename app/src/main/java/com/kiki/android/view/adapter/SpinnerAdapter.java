package com.kiki.android.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kiki.android.R;

import java.util.ArrayList;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : SpinnerAdapter.java
 * @Description : 체크박스 1개 선택해야하는 Adpater
 * @since 2017-02-11
 */
public class SpinnerAdapter extends BaseAdapter {
    private Context mContext = null;
    private int mRes = 0;
    private int[] mIdList = {R.id.spiner_item_checkbox, R.id.spiner_item_textview};
    private ArrayList<String> mDataList = null;
    private boolean[] mRadioBtn;
    private int mSelIndex = 0;

    public SpinnerAdapter(Context context, int res, ArrayList<String> m_ArrList) {
        mContext = context;
        mRes = res;
        mDataList = m_ArrList;
        mRadioBtn = new boolean[mDataList.size()];
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public String getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mRes, null);
        }
        ((TextView) view.findViewById(mIdList[0])).setText(mDataList.get(position));
        if (position == mSelIndex) {
            ((RadioButton) view.findViewById(mIdList[1])).setChecked(true);
        } else {
            ((RadioButton) view.findViewById(mIdList[1])).setChecked(false);
        }
        return view;
    }

    public void setSelectIndex(int index) {
        mSelIndex = index;
    }
}
