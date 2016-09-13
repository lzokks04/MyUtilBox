package com.lzokks04.myutilbox.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lzokks04.myutilbox.R;

/**
 * Created by Liu on 2016/9/13.
 */
public class BatteryView extends View {

    private Paint mPaint;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;

    private int mWidth;

    private int mHeight;

    private int mProgress;

    /**
     * 电池图形线条大小
     */
    private int mBorderWidth;

    private int mBorderPadding = 5;

    /**
     * 电池电量颜色
     */
    private int mBatteryColor;//2afe6b

    private int mTextSize;

    private int mExtraWidth = 20;

    private Rect mBounds;

    private int mTextStartX;

    private int mTextEndX;

    private int mTextStartY;
    /**
     * 电池百分比文字
     */
    private String mContent;


    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取命名空间字段值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BatteryView, defStyleAttr, 0);

        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                //电池内百分比文字大小
                case R.styleable.BatteryView_contentTextsize:
                    mTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics()));
                    break;
                //电池图形线条大小
                case R.styleable.BatteryView_borderWidther:
                    mBorderWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    break;
                //电池电量颜色
                case R.styleable.BatteryView_batteryColor:
                    mBatteryColor = array.getColor(attr, Color.parseColor("#2afe6b"));
                    break;
                default:
                    break;
            }
        }
        //回收
        array.recycle();
        //画笔抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //字体文字颜色设置为黑色
        mTextPaint.setColor(Color.BLACK);
        //文字大小从命名空间获取
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        int result = 100;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result += mTextSize;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, size) : result;
        return result + getPaddingLeft() + getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        int result = 50;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result += mTextSize;
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, size) : result;
        return result + getPaddingTop() + getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.WHITE);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(Color.BLACK);

        RectF rect = new RectF(mBorderPadding, mBorderPadding, mWidth - 25, mHeight - mBorderPadding);
        canvas.drawRoundRect(rect, 3, 3, mPaint);
        RectF mSmallRectf = new RectF(mWidth - 25, mHeight / 3, mWidth - mBorderPadding, mHeight * 2 / 3);
        canvas.drawRoundRect(mSmallRectf, 3, 3, mPaint);


        int CurrentX = mProgress * (mWidth - mBorderPadding * 2 - mExtraWidth) / 100;
        Rect rectProgress = new Rect(mBorderPadding * 2, mBorderPadding * 2, CurrentX, mHeight - mBorderPadding * 2);
        mPaint.setColor(mBatteryColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectProgress, mPaint);


        mBounds = new Rect();
        mContent = "" + mProgress + "%";
        mTextPaint.getTextBounds(mContent, 0, mContent.length(), mBounds);
        mTextStartX = ((mWidth - 20) / 2) - mBounds.width() / 2 - 2;
        mTextStartY = mHeight / 2 + mBounds.height() / 2;
        mTextEndX = (mWidth - 20) / 2 + mBounds.width() / 2 + 2;

        if (CurrentX <= mTextStartX) {
            mTextPaint.setColor(Color.BLACK);
            canvas.drawText(mContent, mTextStartX, mTextStartY, mTextPaint);
        } else if (CurrentX > mTextStartX && CurrentX < mTextEndX) {
            drawText(mContent, Color.WHITE, canvas, mTextStartX, CurrentX);
            drawText(mContent, Color.BLACK, canvas, CurrentX, mTextEndX);
        } else {
            mTextPaint.setColor(Color.WHITE);
            canvas.drawText(mContent, mTextStartX, mTextStartY, mTextPaint);
        }


    }

    /**
     * 画电池百分比文字
     *
     * @param text
     * @param color
     * @param canvas
     * @param startX
     * @param endX
     */
    private void drawText(String text, int color, Canvas canvas, int startX, int endX) {
        mTextPaint.setColor(color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());
        canvas.drawText(text, mTextStartX, mTextStartY, mTextPaint);
        canvas.restore();
    }

}
