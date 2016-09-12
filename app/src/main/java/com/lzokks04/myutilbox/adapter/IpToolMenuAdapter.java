package com.lzokks04.myutilbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/8.
 */
public class IpToolMenuAdapter extends RecyclerView.Adapter<IpToolMenuAdapter.MyViewHolder> {

    private Context mContext;
    private String[] mTitle = {"IP归属地查询", "IP端口扫描", "IP段主机扫描", "PING查询", "TraceRoute路由跟踪"};
    private String[] mInfo = {"获取外网IP,查询IP归属地", "扫描指定IP的端口", "扫描IP段中活动的计算机",
            "查询本地到目标IP的延迟", "路由跟踪，查看本地到目标IP所经历的路线"};

    private OnItemClickListener listener;

    public IpToolMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void OnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvFunc.setText(mTitle[position]);
        holder.tvInfo.setText(mInfo[position]);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    listener.onItemClisk(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_func)
        TextView tvFunc;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClisk(View view, int position);
    }

}
