package com.kiki.android.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kiki.android.R;

import java.util.ArrayList;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : ListAdpater
 * @Description : 리스트 목록 어뎁터
 * @since 2017-02-11
 */
public class ListAdpater extends BaseAdapter {
    private Context mContext = null;
    private int mRes = -1;
    private ArrayList<Object> mListItem = null;

    public ListAdpater(Context context, int res, ArrayList<Object> list) {
        mContext = context;
        mRes = res;
        mListItem = list;
    }

    @Override
    public int getCount() {
        if (mListItem != null) {
            return mListItem.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mListItem != null) {
            return mListItem.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mListItem != null) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mRes, null);
        }
        ((TextView) view.findViewById(R.id.listview_line_Textview)).setText(mListItem.get(position).toString());
        return view;
    }

    public void setDataList(ArrayList<Object> list) {
        mListItem = list;
        notifyDataSetChanged();
    }
}
