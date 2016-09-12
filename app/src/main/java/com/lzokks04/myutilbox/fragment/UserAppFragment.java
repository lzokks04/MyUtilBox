package com.lzokks04.myutilbox.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.UserAppAdapter;
import com.lzokks04.myutilbox.bean.AppInfo;
import com.lzokks04.myutilbox.utils.AppManager;
import com.lzokks04.myutilbox.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liu on 2016/9/9.
 */
public class UserAppFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private Unbinder mUnbinder;
    private List<AppInfo> appInfoList;
    private AppManager manager;
    private UserAppAdapter adapter;

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
        recyclerview.setAdapter(adapter = new UserAppAdapter(getContext(),
                appInfoList = manager.getUserAppInfo()));
        adapter.setOnItemClickLitener(new UserAppAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getContext(), "这是第" + position + "个", Toast.LENGTH_SHORT).show();
                String appName = appInfoList.get(position).getName();
                String packageName = appInfoList.get(position).getPackageName();
                showDialog(appName, packageName);
            }
        });
    }

    private void showDialog(String appName, final String packageName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("确定要删除" + appName + "?")
                .setCancelable(false)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + packageName));
                        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                        startActivityForResult(intent, 0);
                    }
                });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Utils.showToast(getContext(), "卸载成功");
            appInfoList.clear();
            appInfoList.addAll(manager.getUserAppInfo());
            adapter.notifyDataSetChanged();
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Utils.showToast(getContext(), "卸载失败");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
