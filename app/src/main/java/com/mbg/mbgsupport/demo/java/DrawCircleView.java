package com.mbg.mbgsupport.demo.java;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawCircleView extends View {
    private final Paint paint1 = new Paint();
    private final Paint paint2 = new Paint();
    private final Paint paint3= new Paint();
    private int width = 0;
    private int height = 0;
    private int radius=60;
    public DrawCircleView(Context context) {
        this(context,null);
    }

    public DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint3.setAntiAlias(true);

        //画笔颜色
        paint1.setColor(Color.RED);
        paint2.setColor(Color.YELLOW);
        paint3.setColor(Color.BLUE);

        //画笔填充模式
        paint1.setStyle(Paint.Style.FILL);//实心圆
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);//实心圆+外边框
        paint3.setStyle(Paint.Style.STROKE);//只画外边框

        paint1.setStrokeWidth(10);//设置无用
        paint2.setStrokeWidth(10);//圆的半径没有包含边框的宽度
        paint3.setStrokeWidth(20);//圆的半径没有包含边框的宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         //得到屏幕宽高
         width = getMeasuredWidth();
         height = getMeasuredHeight();

        radius=width/8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width/6.0f,height/2.0f,radius,paint1);
        canvas.drawCircle(width/2.0f,height/2.0f,radius,paint2);
        canvas.drawCircle(width*5/6.0f,height/2.0f,radius,paint3);

    }
}
