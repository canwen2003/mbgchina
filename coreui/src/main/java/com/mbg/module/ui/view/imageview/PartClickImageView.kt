package com.mbg.module.ui.view.imageview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.widget.AppCompatImageView

class PartClickImageView: AppCompatImageView, View.OnClickListener {
    private var mOnLeftClickedListener:OnClickListener?=null
    private var mOnRightClickedListener:OnClickListener?=null
    private var mStartPosX = 0f
    private var mStartPosY = 0f
    private var mMovingThreshold = 0f
    constructor(context: Context):this(context,null)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int):super(context, attrs,defStyleAttr){
        mMovingThreshold = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
    }

    override fun onClick(p0: View?) {
        if (mStartPosX<=width/2){
            mOnLeftClickedListener?.onClick(this@PartClickImageView)
        }else{
            mOnRightClickedListener?.onClick(this@PartClickImageView)
        }
    }

    fun setLeftOnClickListener(listener: OnClickListener?){
        this.mOnLeftClickedListener=listener
        if (mOnLeftClickedListener!=null){
            setOnClickListener(this)
        }
    }

    fun setRightOnClickListener(listener: OnClickListener?){
        this.mOnRightClickedListener=listener
        if (mOnRightClickedListener!=null){
            setOnClickListener(this)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_DOWN ->{
                    mStartPosX=x
                    mStartPosY=y
                }
            }
        }
        return super.onTouchEvent(event)
    }
}