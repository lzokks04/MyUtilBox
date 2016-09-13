package com.lzokks04.myutilbox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Liu on 2016/9/14.
 */
public class HardInfoFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] mTitle = {"硬件信息", "电池信息"};


    public HardInfoFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
