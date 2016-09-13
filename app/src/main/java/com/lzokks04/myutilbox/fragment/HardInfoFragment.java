package com.lzokks04.myutilbox.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.HardInfoAdapter;
import com.lzokks04.myutilbox.utils.recyclerview_itemdecoration.DividerItemDecoration;
import com.lzokks04.myutilbox.utils.MemoryUtil;
import com.lzokks04.myutilbox.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liu on 2016/9/13.
 */
public class HardInfoFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.tv_sysver)
    TextView tvSysver;
    private Unbinder unBinder;
    private HardInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hardinfo, container, false);
        unBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initRecyclerView();
    }

    private void init() {
        tvDevice.setText("设备名称:" + Build.MODEL);
        tvSysver.setText("系统版本:Android" + Build.VERSION.RELEASE);
    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(llm);
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerview.setAdapter(adapter = new HardInfoAdapter(getActivity(), addParameter()));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    private List<String> addParameter() {
        List<String> list = new ArrayList<String>();
        if (Utils.getCpuName().equals("0")) {
            list.add(Utils.getX86CPUName());
        } else {
            list.add(Utils.getCpuName());
        }

        list.add(Utils.getCpuCoreSize() + "");
        list.add(Formatter.formatFileSize(getActivity(), MemoryUtil.getTotalMem()));
        list.add(Formatter.formatFileSize(getActivity(), MemoryUtil.getAvailableMemory(getActivity())));
        list.add(Utils.getScreenWidth(getActivity()) + "X" + Utils.getScreenHeight(getActivity()));
        list.add(Utils.getBaseband());
        list.add(Utils.isRoot());

        if (Utils.isSdCard()) {
            list.add("无内存卡");
        } else {
            list.add(Utils.getSdCardSize(getActivity()));
        }
        return list;
    }

}
