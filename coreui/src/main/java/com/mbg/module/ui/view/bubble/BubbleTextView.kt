package com.mbg.module.ui.view.bubble

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.shape.*
import com.mbg.module.ui.R
import com.mbg.module.ui.view.bubble.BubbleDrawable

/**
 * Created by lgp on 2015/3/24.
 */
class BubbleTextView : AppCompatTextView {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?=null, defStyle: Int=0) : super(context, attrs, defStyle) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.BubbleTextView)
            val arrowSize = array.getDimension(R.styleable.BubbleTextView_bt_arrowSize, 10f)
            val arrowOffset = array.getDimension(R.styleable.BubbleTextView_bt_position,0f)
            val cornerSizes = array.getDimension(R.styleable.BubbleTextView_cornerRadius, 0f)
            val bubbleColor = array.getColor(R.styleable.BubbleTextView_bt_Color, Color.RED)
            array.recycle()

            val shapeAppearanceModel = ShapeAppearanceModel.builder().apply {
                setAllCorners(RoundedCornerTreatment())
                setAllCornerSizes(cornerSizes)
                setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(arrowSize, false),arrowOffset))
            }.build()

            background= MaterialShapeDrawable(shapeAppearanceModel).apply {
                setTint(bubbleColor)
                paintStyle = Paint.Style.FILL
            }
        }
    }
}