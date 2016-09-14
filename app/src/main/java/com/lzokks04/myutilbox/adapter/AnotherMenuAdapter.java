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
 * Created by Liu on 2016/9/14.
 */
public class AnotherMenuAdapter extends RecyclerView.Adapter<AnotherMenuAdapter.MyViewHolder> {

    private Context context;
    private String[] mTitle = {"身份证查询", "QQ号码测试", "星座运势", "老黄历", "手机号码归属地", "笑话大全"};
    private String[] mInfo = {"查询身份证基本信息，是否泄露，是否挂失", "测试QQ号的凶吉", "每日的星座运势", "每天的吉凶宜忌、生肖运程"
            , "手机号码归属地信息查询", "笑一笑，十年少"};


    private OnItemClickListener listener;

    public AnotherMenuAdapter(Context mContext) {
        this.context = mContext;
    }

    public void OnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ipmenu, parent, false));
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
