package com.kiki.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kiki.android.R;
import com.kiki.android.view.custom.KProgressBar;
import com.kiki.android.view.frame.menu.MenuView;
import com.kiki.android.view.frame.menu.MenuView2;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : HomeActivity
 * @since 2017-02-12.
 */
public class DrawerLayoutAct extends Activity implements ListView.OnItemClickListener {


    String[] mDrawerTitles;
    @BindView(R.id.main_content) RelativeLayout mBaseLayout;

    @BindView(R.id.navigtaion_main_listview)  ListView mDrawerListView;
    @BindView(R.id.navigation_main_drawerlayout)
    androidx.drawerlayout.widget.DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);

        mDrawerTitles = getResources().getStringArray(R.array.navigation_menu);
        //네비게이션 드로어 설정
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDrawerTitles));

        //액션바에 토글 생성
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_open_drawer, R.string.navigation_close_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    @Override
    /***
     * 네비게이션 드로어 아이템 선택시 호출 메소드
     */
    @OnItemClick({R.id.navigtaion_main_listview})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mBaseLayout.setBackgroundColor(Color.parseColor("#A52A2A"));
                break;
            case 1:
                mBaseLayout.setBackgroundColor(Color.parseColor("#5F9EA0"));
                break;
            case 2:
                mBaseLayout.setBackgroundColor(Color.parseColor("#556B2F"));
                break;
            case 3:
                mBaseLayout.setBackgroundColor(Color.parseColor("#FF8C00"));
                break;
            case 4:
                mBaseLayout.setBackgroundColor(Color.parseColor("#DAA520"));
                break;
        }
        mDrawerLayout.closeDrawer(mDrawerListView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
