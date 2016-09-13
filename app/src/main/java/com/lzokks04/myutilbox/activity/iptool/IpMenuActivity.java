package com.lzokks04.myutilbox.activity.iptool;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.IpToolMenuAdapter;
import com.lzokks04.myutilbox.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/8.
 */
public class IpMenuActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private IpToolMenuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipmenu);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        toolbar.setTitle("IP工具");
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

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new IpToolMenuAdapter(this));
        adapter.OnItemClickListener(new IpToolMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClisk(View view, int position) {
                switch (position) {
                    case 0:
                        Utils.startIntent(IpMenuActivity.this, IpSearchActivity.class);
                        break;
                    case 1:
                        Utils.showToast(getApplicationContext(), "功能暂未实现");
                        break;
                    case 2:
                        Utils.showToast(getApplicationContext(), "功能暂未实现");
//                        Utils.startIntent(IpMenuActivity.this, IpScanActivity.class);
                        break;
                    case 3:
                        Utils.startIntent(IpMenuActivity.this, PingActivity.class);
                        break;
                    case 4:
                        Utils.showToast(getApplicationContext(), "功能暂未实现");
                        break;
                }
            }
        });
    }
}
