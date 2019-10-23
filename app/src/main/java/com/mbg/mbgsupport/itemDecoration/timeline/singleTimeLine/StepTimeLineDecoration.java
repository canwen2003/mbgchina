package com.mbg.mbgsupport.itemDecoration.timeline.singleTimeLine;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.view.itemDecoration.timeline.SingleTimeLineDecoration;
import com.mbg.module.ui.view.itemDecoration.timeline.data.ITimeItem;


public class StepTimeLineDecoration extends SingleTimeLineDecoration {

    private Paint mRectPaint;

    public StepTimeLineDecoration(SingleTimeLineDecoration.Config config) {
        super(config);

        mRectPaint = new Paint();
        mRectPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        mDotPaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onDrawTitleItem(Canvas canvas, int left, int top, int right, int bottom, int pos) {
        ITimeItem item = timeItems.get(pos);

        int rectWidth = UiUtils.dip2px(120);
        int height = bottom - top;
        int paddingLeft = UiUtils.dip2px(10);
        mRectPaint.setColor(item.getColor());
        canvas.drawRoundRect(left+paddingLeft,top,left+rectWidth,bottom,UiUtils.dip2px(6),UiUtils.dip2px(6),mRectPaint);


        String title = item.getTitle();
        if(TextUtils.isEmpty(title))
            return;
        Rect mRect = new Rect();

        mTextPaint.getTextBounds(title,0,title.length(),mRect);
        int x = left + (rectWidth - mRect.width())/2;
        //int x = left + UIUtils.dip2px(20);
        int y = bottom - (height - mRect.height())/2;
        canvas.drawText(title,x,y,mTextPaint);
    }

    @Override
    protected void onDrawDotItem(Canvas canvas, int cx, int cy, int radius, int pos) {
        ITimeItem item = timeItems.get(pos);
        mDotPaint.setColor(item.getColor());
        canvas.drawCircle(cx,cy, UiUtils.dip2px(6),mDotPaint);
    }
}
