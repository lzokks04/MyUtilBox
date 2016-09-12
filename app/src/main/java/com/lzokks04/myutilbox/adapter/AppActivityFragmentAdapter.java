package com.lzokks04.myutilbox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Liu on 2016/9/9.
 */
public class AppActivityFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> viewList;
    private String[] mTitle = {
            "正在运行的软件", "系统软件", "用户软件"
    };

    public AppActivityFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.viewList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return viewList.get(position);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
