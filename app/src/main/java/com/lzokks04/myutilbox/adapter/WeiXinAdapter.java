package com.lzokks04.myutilbox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.bean.WeiXinDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/13.
 */
public class WeiXinAdapter extends RecyclerView.Adapter<WeiXinAdapter.MyViewHolder> {

    private Context context;
    private List<WeiXinDetailBean> beanList;
    private onItemClickListener listener;

    public WeiXinAdapter(Context context, List<WeiXinDetailBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void setOnItemClickLitener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weixin, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context)
                .load(beanList.get(position).getFirstImg())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.ivImage.setImageBitmap(resource);
                    }
                });
        holder.tvTitle.setText(beanList.get(position).getTitle());
        holder.tvSource.setText(beanList.get(position).getSource());
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
        return beanList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_source)
        TextView tvSource;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressbar)
        ProgressBar progressBar;

        public FootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

}
