package com.lzokks04.myutilbox.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.BatteryStateAdapter;
import com.lzokks04.myutilbox.utils.recyclerview_itemdecoration.DividerItemDecoration;
import com.lzokks04.myutilbox.widget.BatteryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liu on 2016/9/13.
 */
public class BatteryFragment extends Fragment {
    @BindView(R.id.batteryview)
    BatteryView batteryview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private Unbinder unBinder;
    private BatteryStateAdapter adapter;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        unBinder = ButterKnife.bind(this, view);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getActivity().getApplicationContext().registerReceiver(receiver, filter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerview.setAdapter(adapter = new BatteryStateAdapter(getActivity(), list = new ArrayList<String>()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getApplicationContext().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            list.clear();
            int procress = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryview.setProgress(procress);

            // 获取当前电量
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            list.add(level + "%");
            // 获取最大电量
            int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            list.add(maxLevel + "%");
            // 获取温度
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            list.add(temperature + "°");
            // 获取电压 mV:毫伏
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            list.add(voltage + "mV");

            // 获取电池状态
            String state = "";
            switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0)) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    state = "充电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    state = "放电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    state = "已充满";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    state = "未充电状态";
                    break;
                default:
                    state = "未知状态";
                    break;
            }
            list.add(state);

            // 获取电池使用状态
            String useState = "";
            switch (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    useState = "状态良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    useState = "状态过冷";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    useState = "电池死亡";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    useState = "状态过热";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    useState = "未知状态";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    useState = "电压过高";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    useState = "不明故障";
                    break;
            }
            list.add(useState);

            //电流种类
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            if (usbCharge) {
                list.add("USB");
            } else if (acCharge) {
                list.add("Ac");
            } else {
                list.add("不在充电");
            }
            adapter.notifyDataSetChanged();
        }
    };
}
