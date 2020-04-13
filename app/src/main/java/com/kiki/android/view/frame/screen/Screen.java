package com.kiki.android.view.frame.screen;

import android.content.Context;

import java.util.Stack;

/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : Screen
 * @Description : 화면 히스토리 관리
 * @since 2016-02-11.
 */
public class Screen {
    private Context mContext = null;
    private Stack<Integer> mStackHistory = new Stack<Integer>();
    private int mScreenMode = ScreenConstants.MODE_PORTRAIT;

    public Screen(Context context) {
        mContext = context;
        initialize();
    }

    private void initialize() {
        changeScreenMode(ScreenConstants.MODE_PORTRAIT);
    }

    private void changeScreenMode(int nScreenMode) {
        mScreenMode = nScreenMode;
    }

    private void changeView(int nMenuIndex, boolean bHistory) {
        if (bHistory) {
            pushHistory(nMenuIndex);
        }
    }

    private void changeBackView() {
        if (mStackHistory.size() > 0) {
            mStackHistory.pop();
        }
    }

    private int getViewMenuIndex() {
        if (mStackHistory.size() > 0)
            return mStackHistory.get(mStackHistory.size() - 1);
        else
            return -1;
    }

    private void pushHistory(int nMenuIndex) {
        if (mStackHistory.contains(nMenuIndex)) {
            mStackHistory.removeElement(nMenuIndex);
            mStackHistory.push(nMenuIndex);
        } else {
            mStackHistory.push(nMenuIndex);
        }
    }
}
