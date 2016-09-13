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
public class HardInfoAdapter extends RecyclerView.Adapter<HardInfoAdapter.MyViewHolder> {

    private Context context;
    private String[] mTag = {"CPU名称", "CPU核心", "所有可用内存", "剩余可用内存", "手机分辨率",
            "基带版本", "是否root", "内存卡已用大小/总大小"
    };
    private List<String> parameterList;

    public HardInfoAdapter(Context context, List<String> parameterList) {
        this.context = context;
        this.parameterList = parameterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hardinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTag.setText(mTag[position]);
        holder.tvParameter.setText(parameterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTag.length;
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
