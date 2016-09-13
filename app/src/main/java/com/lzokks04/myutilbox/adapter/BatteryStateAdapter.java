package com.lzokks04.myutilbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/14.
 */
public class BatteryStateAdapter extends RecyclerView.Adapter<BatteryStateAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private String[] mTitle = {"当前电量", "最大电量", "温度", "电压", "电池状态", "电池使用状态","充电类型"};

    public BatteryStateAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hardinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTag.setText(mTitle[position]);
        holder.tvParameter.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_parameter)
        TextView tvParameter;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
