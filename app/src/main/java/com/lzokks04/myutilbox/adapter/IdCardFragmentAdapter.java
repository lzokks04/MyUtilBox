package com.lzokks04.myutilbox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Liu on 2016/9/14.
 */
public class IdCardFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] mTitle = {"身份证信息查询", "身份证泄露查询", "身份证挂失查询"};

    public IdCardFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentList = list;
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
