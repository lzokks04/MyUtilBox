package com.lzokks04.myutilbox.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.activity.iptool.IpMenuActivity;
import com.lzokks04.myutilbox.adapter.MenuItemAdapter;
import com.lzokks04.myutilbox.bean.AppInfo;
import com.lzokks04.myutilbox.utils.AppManager;
import com.lzokks04.myutilbox.utils.recyclerview_itemdecoration.DividerGridItemDecoration;
import com.lzokks04.myutilbox.utils.MemoryUtil;
import com.lzokks04.myutilbox.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_memory)
    TextView tvMemory;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MenuItemAdapter adapter;
    private long exitTime;
    private List<AppInfo> killAppList;
    private AppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initList();
        initRecyclerView();
        getMemeryInfo();
    }

    private void initList() {
        manager = new AppManager(this);
        killAppList = manager.getRunningApp();
    }

    private void initToolbar() {
        toolbar.setTitle("手机工具箱");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerview.setAdapter(adapter = new MenuItemAdapter(this));
        adapter.onItemClickListener(new MenuItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Utils.startIntent(MainActivity.this, AppInfoActivity.class);
                        break;
                    case 1:
                        Utils.showToast(getApplicationContext(), "垃圾清理");
                        break;
                    case 2:
                        Utils.startIntent(MainActivity.this, HardInfoActivity.class);
                        break;
                    case 3:
                        Utils.startIntent(MainActivity.this, IpMenuActivity.class);
                        break;
                    case 4:
                        Utils.startIntent(MainActivity.this, WeixinJingXuanActivity.class);
                        break;
                    case 5:
                        Utils.showToast(getApplicationContext(), "其它");
                        break;
                }
            }
        });
    }

    @OnClick(R.id.iv_clear)
    public void onClick() {
        killUserProcess();
        getMemeryInfo();
    }

    private void getMemeryInfo() {
        long availableMemory = MemoryUtil.getAvailableMemory(this);
        long totalMemory = MemoryUtil.getTotalMem();
        long usedMemory = totalMemory - availableMemory;
        int percent = (int) (usedMemory / (float) totalMemory * 100);
        tvMemory.setText("内存占用" + percent + "%");
    }

    private void killUserProcess() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (AppInfo appinfo : killAppList) {
            am.killBackgroundProcesses(appinfo.getPackageName());
        }
        Utils.showToast(this, "成功");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMemeryInfo();
    }

    // 再次按下返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime > 2000)) {
                Utils.showToast(MainActivity.this, "再次按下返回键退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
