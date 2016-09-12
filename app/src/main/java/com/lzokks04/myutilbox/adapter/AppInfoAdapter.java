package com.lzokks04.myutilbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.bean.AppInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/9.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.MyViewHolder> {

    private Context context;
    private List<AppInfo> appInfoList;


    public AppInfoAdapter(Context context, List<AppInfo> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_appinfo, parent, false));
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.ivIcon.setImageDrawable(appInfoList.get(position).getIcon());
        holder.tvAppname.setText(appInfoList.get(position).getName());
        holder.tvPackname.setText(appInfoList.get(position).getPackageName());
        holder.tvSize.setText("大小:" + Formatter.formatFileSize(context, (long) appInfoList.get(position).getSize()));
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_appname)
        TextView tvAppname;
        @BindView(R.id.tv_packname)
        TextView tvPackname;
        @BindView(R.id.tv_size)
        TextView tvSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
