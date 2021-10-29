package com.mbg.module.ui.view.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Timer;
import java.util.TimerTask;

public class ScrollTextView extends AppCompatTextView {
    private int mOffsetX= 0;
    private final Rect mRect;
    private Timer mTimer;
    private TimerTask mTimerTask;
    public String mText="";

    private static final int SCROLL_SPEED = -8;

    public ScrollTextView(Context context) {
        this(context, null);
    }
    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect =new Rect();
    }

    private class MyTimerTask extends TimerTask {
       @Override
        public void run() {
            //如果View能容下所有文字直接返回
            /*if (mRect.right<getWidth()){
                return;
            }*/

            if (mOffsetX < - mRect.right - getPaddingEnd()){//左移时的情况
                mOffsetX = getPaddingStart();
            } else if (mOffsetX > getPaddingStart()){//右移时的情况
                mOffsetX = - mRect.right;
            }else {
                mOffsetX += SCROLL_SPEED;
            }
            postInvalidate();

        }
    }


    public void setScrollText(String text) {
       if (text!=null){
           mText=text;

           if (mTimer==null) {
               mTimer = new Timer();
               mTimerTask = new MyTimerTask();
               mTimer.schedule(mTimerTask, 1000, 1000 / 24);
           }
       }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取文本区域大小保存在mRect中。
        getPaint().getTextBounds(mText, 0, mText.length(), mRect);

        int y=mRect.height()/2+getMeasuredHeight()/2;
        canvas.drawText(mText, mOffsetX, y, getPaint());


       /* if (mRect.right < getWidth()){
            canvas.drawText(mText, 0, y, getPaint());
        }else {
            canvas.drawText(mText, mOffsetX, y, getPaint());
        }*/
    }

    /**
     * 视图移除时销毁任务和定时器
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }

        if (mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }
}
