package com.lzokks04.myutilbox.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Liu on 2016/9/7.
 */
public class CirCleView extends View {

    private RectF mRectF;
    private Paint mPaint;

    public CirCleView(Context context) {
        super(context);
    }

    public CirCleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) 3.0);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public CirCleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        mRectF = new RectF(320, 320, 320, 320);
        canvas.drawArc(mRectF, -90, 270, false, mPaint);
    }
}
