package com.mbg.mbgsupport.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mbg.mbgsupport.R;


public class BlurMaskFilterView extends View {
    private final Paint mShadowPaint;
    private final Bitmap mSrcBitMap;
    private int x,y;

    public BlurMaskFilterView(Context context){
        this(context,null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        setLayerType(LAYER_TYPE_SOFTWARE,null);

        mShadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));


        mSrcBitMap= BitmapFactory.decodeResource(getResources(), R.drawable.icon_girl);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mSrcBitMap,0,20,mShadowPaint);
    }
}
