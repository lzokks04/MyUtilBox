package com.lzokks04.myutilbox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.AppInfoAdapter;
import com.lzokks04.myutilbox.bean.AppInfo;
import com.lzokks04.myutilbox.utils.AppManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liu on 2016/9/9.
 */
public class RunningAppFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private Unbinder mUnbinder;
    private AppInfoAdapter adapter;
    private List<AppInfo> appInfoList;
    private AppManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applist, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        manager = new AppManager(getContext());
        recyclerview.setAdapter(adapter = new AppInfoAdapter(getContext(),
                appInfoList = manager.getRunningApp()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
