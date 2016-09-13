package com.lzokks04.myutilbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzokks04.myutilbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/8.
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {

    private Context mContext;
    private String[] mTitle = {"软件管理", "垃圾清理", "手机信息", "IP工具", "微信精选", "其它"};
    private int[] mImage = {R.drawable.ic_pie_chart_black_36dp,
            R.drawable.ic_file_upload_black_36dp,
            R.drawable.ic_smartphone_black_36dp,
            R.drawable.ic_location_on_black_36dp,
            R.drawable.ic_near_me_black_36dp,
            R.drawable.ic_more_horiz_black_36dp};
    private onItemClickListener listener;

    public MenuItemAdapter(Context context) {
        this.mContext = context;
    }

    public void onItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menuselect, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvName.setText(mTitle[position]);
        Glide.with(mContext).load(mImage[position]).into(holder.ivIcon);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    listener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
}
