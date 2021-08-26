package com.mbg.module.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mbg.module.common.util.UiUtils;

public class NavBarBackgroundView extends View {
    private final Paint mPaint=new Paint();
    private final RectF mLineRect = new RectF();
    private final RectF mMarkRect = new RectF();
    private float mLineHeight;
    private float mMarkWidth;

    public NavBarBackgroundView(Context context) {
        this(context,null);
    }

    public NavBarBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NavBarBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(Color.RED);
        mLineHeight = UiUtils.dip2px(context,2.0f);
        mMarkWidth = UiUtils.dip2px(context,2.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mLineRect.top = getHeight()/2.0f-mLineHeight/2;
        mLineRect.left = 0;
        mLineRect.right = getWidth();
        mLineRect.bottom = getHeight()/2.0f+mLineHeight/2;

        mMarkRect.top = 0;
        mMarkRect.left = getWidth()-mMarkWidth;
        mMarkRect.right = getWidth();
        mMarkRect.bottom = getHeight();

        canvas.drawRoundRect(mLineRect,mLineHeight/2,mLineHeight/2,mPaint);
        canvas.drawRoundRect(mMarkRect,mMarkWidth/2,mMarkWidth/2,mPaint);
    }

    public void setLineHeight(float lineHeight){
        mLineHeight=lineHeight;
        invalidate();
    }

    public void setMarkWidth(float width){
        mMarkWidth=width;
        invalidate();
    }
}
