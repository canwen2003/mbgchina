package com.mbg.module.ui.view.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.mbg.module.ui.R;
import com.mbg.module.ui.view.listener.OnTriggerDragListener;
import com.mbg.module.ui.view.listener.TriggerDraggable;

/**
 * 可拖拽ImageView
 */

public class DraggableImageView extends TransformativeImageView implements TriggerDraggable {
    private static final String TAG = DraggableImageView.class.getSimpleName();
    private static final int DEFAULT_TRIGGER_DISTANCE = 100; // 默认触发拖拽的距离为100px
    private OnTriggerDragListener mOnTriggerDragListener; // 拖拽事件监听器

    /**
     * 可触发拖拽事件的边界,若数组某个index的变量为true，则表示该index对应的边界可以触发拖拽事件；
     * 默认所有边界均不可触发拖拽事件
     * index: {0, 1, 2, 3} -> boundary: {left, top, right, bottom}
     *
     * 例：mBoundary = {true, false, false, true} 表示左边界与下边界可触发拖拽事件
     */
    private boolean[] mBoundary = new boolean[4];
    private float mTriggerDistance = DEFAULT_TRIGGER_DISTANCE; // 触发拖拉事件的距离

    public DraggableImageView(Context context) {
        this(context, null);
    }

    public DraggableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attributes) {
        if (attributes == null) return;
        TypedArray typedArray =
                context.obtainStyledAttributes(attributes, R.styleable.DraggableImageView);

        mBoundary[0] = typedArray.getBoolean(
                R.styleable.DraggableImageView_boundary_left, false);
        mBoundary[1] = typedArray.getBoolean(
                R.styleable.DraggableImageView_boundary_top, false);
        mBoundary[2] = typedArray.getBoolean(
                R.styleable.DraggableImageView_boundary_right, false);
        mBoundary[3] = typedArray.getBoolean(
                R.styleable.DraggableImageView_boundary_bottom, false);
        mTriggerDistance = typedArray.getDimension(
                R.styleable.DraggableImageView_trigger_distance, DEFAULT_TRIGGER_DISTANCE);

        typedArray.recycle();
    }

    private boolean isPointerCountChanged = false; // 本次触摸事件流中触点数量是否减少
    private boolean mCanDrag = false; // 是否可以将图片拖拽出控件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                /* 只有处于不可拖拽状态时才判断是否触发拖拽事件，
                 * 且本次触摸事件流触点数量未减少的情况，才判断是否触发拖拽事件
                 */
                if (!mCanDrag && !isPointerCountChanged && triggerDrag(event)) {
                    mCanDrag = true;
                }
                // 调用拖拽监听方法
                if (mCanDrag && mOnTriggerDragListener != null) {
                    mOnTriggerDragListener.onDrag(event);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mCanDrag && mOnTriggerDragListener != null) {
                    mOnTriggerDragListener.onDragFinish(event);
                }
                mCanDrag = false;
                // ACTION_UP意味着本次事件流结束，所以将记录触点数量是否减少的标志位清除
                isPointerCountChanged = false;
                resetImage();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isPointerCountChanged = true;
                break;
        }

        return true;
    }

    public boolean getDragEnable(){
        return mDragEnabled;
    }

    public void setDragEnable(Boolean dragEnabled){
        this.mDragEnabled=dragEnabled;
    }

    public boolean getCanReplaced(){
        return  mCanReplaced;
    }

    public void setCanReplaced(boolean canReplaced){
        this.mCanReplaced=canReplaced;
    }

    /**
     * 重写平移方法，此方法会在{@link TransformativeImageView#onTouchEvent(MotionEvent)}中调用
     * @param midPoint 当前触点的中点
     */
    @Override
    protected void translate(PointF midPoint) {
        float dx = midPoint.x - mLastMidPoint.x;
        float dy = midPoint.y - mLastMidPoint.y;
        mLastMidPoint.set(midPoint);
        // TODO px转为dp
        // 图片不能被拖动到不可见，当只剩15px可见时不可拖动
        if (mImageRect.left + dx > getWidth() - 15
                || mImageRect.right + dx < 15
                || mImageRect.top + dy > getHeight() - 15
                || mImageRect.bottom + dy < 15) {
            return;
        }
        mMatrix.postTranslate(dx, dy);
    }

    /**
     * 判断是否触发拖拽事件
     * @param event 触摸事件
     * @return 符合触发条件则返回true，否则返回false
     */
    @Override
    public boolean triggerDrag(MotionEvent event) {
        boolean canDrag = false;
        // 当前触点坐标
        final float x = event.getX();
        final float y = event.getY();

        // 判断某个边界是否可触发拖拽事件并且达到了触发条件
        if (mBoundary[0] && -x > mTriggerDistance
                || mBoundary[1] && -y > mTriggerDistance
                || mBoundary[2] && x - getWidth() > mTriggerDistance
                || mBoundary[3] && y - getHeight() > mTriggerDistance) {
            canDrag = true;
        }
        return canDrag;
    }

    /**
     * 设置触发监听器
     * @param listener 拖拽事件监听器
     */
    @Override
    public void setOnTriggerDragListener(OnTriggerDragListener listener) {
        this.mOnTriggerDragListener = listener;
    }
}
