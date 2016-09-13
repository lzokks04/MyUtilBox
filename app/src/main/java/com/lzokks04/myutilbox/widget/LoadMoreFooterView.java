package com.lzokks04.myutilbox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Liu on 2016/9/13.
 */
public class LoadMoreFooterView extends TextView implements SwipeTrigger, SwipeLoadMoreTrigger {

    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("正在拼命加载数据...");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        if (!b) {
            if (i <= -getHeight()) {
                setText("松开载入...");
            } else {
                setText("上拉载入更多...");
            }
        } else {

        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        setText("载入成功...");
    }

    @Override
    public void onReset() {
        setText("");
    }
}
