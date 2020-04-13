package com.kiki.android.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/***
 * @author grapegirl
 * @version 1.1
 * @Class Name : TabPageAdapter
 * @Description : 탭 페이지 어뎁터
 * @since 2017-02-11
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFagmentList = null;

    public TabPageAdapter(ArrayList<Fragment> fagmentList, FragmentManager fm) {
        super(fm);
        mFagmentList = fagmentList;
    }

    @Override
    public Fragment getItem(int index) {
        return mFagmentList.get(index);
    }

    @Override
    public int getCount() {
        return mFagmentList.size();
    }
}
