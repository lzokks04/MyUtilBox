package com.lzokks04.myutilbox.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.HardInfoFragmentAdapter;
import com.lzokks04.myutilbox.fragment.BatteryFragment;
import com.lzokks04.myutilbox.fragment.HardInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/13.
 */
public class HardInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragments;
    private HardInfoFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardinfo);
        ButterKnife.bind(this);
        initToolbar();
        initHeader();
    }

    private void initToolbar() {
        toolbar.setTitle("手机信息");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initHeader() {
        viewpager.setAdapter(adapter = new HardInfoFragmentAdapter
                (getSupportFragmentManager(), fragments = addFragment()));
        viewpager.setOffscreenPageLimit(2);
        tablayout.setupWithViewPager(viewpager);
    }

    private List<Fragment> addFragment() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new HardInfoFragment());
        list.add(new BatteryFragment());
        return list;
    }

}
