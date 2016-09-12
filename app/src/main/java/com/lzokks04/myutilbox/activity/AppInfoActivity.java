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
import com.lzokks04.myutilbox.adapter.AppActivityFragmentAdapter;
import com.lzokks04.myutilbox.fragment.RunningAppFragment;
import com.lzokks04.myutilbox.fragment.SystemAppFragment;
import com.lzokks04.myutilbox.fragment.UserAppFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/9.
 */
public class AppInfoActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Fragment> fragmentList;
    private AppActivityFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar() {
        toolbar.setTitle("软件管理");
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

    private void init() {
        viewpager.setAdapter(adapter = new AppActivityFragmentAdapter
                (getSupportFragmentManager(), fragmentList = addFragment()));
        viewpager.setOffscreenPageLimit(3);
        tablayout.setupWithViewPager(viewpager);
    }

    private List<Fragment> addFragment() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new RunningAppFragment());
        list.add(new SystemAppFragment());
        list.add(new UserAppFragment());
        return list;
    }

}
