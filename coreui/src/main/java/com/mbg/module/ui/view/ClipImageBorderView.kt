package com.mbg.module.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class ClipImageBorderView @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyle: Int = 0) :
    View(context, attrs, defStyle) {
    /**
     * 水平方向与View的边距
     */
    private var leftPadding = 0
    private var rightPadding = 0
    private var topPadding = 0
    private var bottomPadding = 0
    private var isRealPadding = false
    private var mSizeRate = 1.0f //剪切窗口的高宽比

    /**
     * 绘制的矩形的宽度
     */
    private var mWidth = 0

    /**
     * 边框的颜色，默认为白色
     */
    private var mBorderColor = Color.parseColor("#FFFFFF")
    private var mOuterColor=Color.parseColor("#AA000000")

    /**
     * 边框的宽度 单位dp
     */
    private var mBorderWidth = 1
    private val mPaint: Paint

    init {
        mBorderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            mBorderWidth.toFloat(),
            resources.displayMetrics).toInt()
        mPaint = Paint()
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)        // 计算矩形区域的宽度
        mWidth = width - leftPadding - rightPadding

        // 计算距离屏幕垂直边界 的边距
        if (topPadding == 0 && bottomPadding == 0&&!isRealPadding) {
            topPadding = (height - mWidth * mSizeRate).toInt() / 2
            bottomPadding = topPadding
        }
        mPaint.color = mOuterColor
        mPaint.style = Paint.Style.FILL        // 绘制左边1
        canvas.drawRect(0f, 0f, leftPadding.toFloat(), height.toFloat(), mPaint)        // 绘制右边2
        canvas.drawRect((width - rightPadding).toFloat(),
            0f,
            width.toFloat(),
            height.toFloat(),
            mPaint)        // 绘制上边3
        canvas.drawRect(leftPadding.toFloat(),
            0f,
            (width - rightPadding).toFloat(),
            topPadding.toFloat(),
            mPaint)        // 绘制下边4
        canvas.drawRect(leftPadding.toFloat(),
            (height - bottomPadding).toFloat(),
            (width - rightPadding).toFloat(),
            height.toFloat(),
            mPaint)

        // 绘制外边框
        mPaint.color = mBorderColor
        mPaint.strokeWidth = mBorderWidth.toFloat()
        mPaint.style = Paint.Style.STROKE
        canvas.drawRect(leftPadding.toFloat(),
            topPadding.toFloat(),
            (width - rightPadding).toFloat(),
            (height - bottomPadding).toFloat(),
            mPaint)
    }

    fun setHorizontalPadding(mHorizontalPadding: Int) {
        leftPadding = mHorizontalPadding
        rightPadding = mHorizontalPadding
    }

    fun setBorderColor(color:Int){
        mBorderColor=color
    }

    fun setOutColor(color:Int){
        mOuterColor=color
    }

    fun setSizeRate(sizeRate: Float) {
        mSizeRate = sizeRate
    }

    fun setVerticalPadding(mVerticalPadding: Int) {
        topPadding = mVerticalPadding
        bottomPadding = mVerticalPadding
    }

    fun setViewPadding(left: Int, top: Int, right: Int, bottom: Int) {
        isRealPadding = true
        leftPadding = left
        topPadding = top
        rightPadding = right
        bottomPadding = bottom
        postInvalidate()
    }

}