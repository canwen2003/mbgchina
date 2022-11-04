package com.mbg.mbgsupport.demo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.ViewLiveProgressBinding
import com.mbg.module.common.util.UiUtils
import com.mbg.module.ui.view.drawable.DrawableCreator
import com.mbg.module.ui.view.drawable.LayerBuilder


/****
 *created by zhaozhiyang
 * 闪聊推荐控件
 */
class LiveProgressView : FrameLayout{
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mViewBinding: ViewLiveProgressBinding? = null
    private var mFragment:Fragment?=null
    private var  layerBuilder: LayerBuilder?=null

    fun initView(fg: Fragment) {
        if (mViewBinding==null){
            clipChildren=false
            mViewBinding=ViewLiveProgressBinding.inflate(LayoutInflater.from(context), this, true)
        }

        mViewBinding?.run {

            val builder1: DrawableCreator.Builder = DrawableCreator.Builder()
            builder1.setSolidColor(ContextCompat.getColor(context, R.color.yellow_800))
                .setCornersRadius(UiUtils.dip2px(context, 10f).toFloat())

            val builder2: DrawableCreator.Builder = DrawableCreator.Builder()
            builder2.setGradientAngle(180)
                .setSolidColor(ContextCompat.getColor(context, R.color.color_FA404A))
                .setCornersRadius(0f,
                    UiUtils.dip2px(context, 9f).toFloat(),
                    0f,
                    UiUtils.dip2px(context, 9f).toFloat())
             layerBuilder = LayerBuilder.create(builder1.build(), builder2.build())
            setProgress(0,0)
        }
    }

    @JvmOverloads
    fun setProgress(left:Int=0,right:Int=0,marginDp:Float=0f){
        mViewBinding?.run {
            tvStartView.text=left.toString()
            tvEndView.text=right.toString()
            val paddingText=UiUtils.dip2px(context,marginDp)

            tvStartView.setPadding(0,0,paddingText,0)
            tvEndView.setPadding(paddingText,0,0,0)


            tvStartView.post {
                tvEndView.post {
                    val totalLength=tvEndView.left-tvStartView.right-imgMarkView.width
                    val margin=if (left+right==0) totalLength/2 else left*totalLength/(left+right)

                    val layoutParams=imgMarkView.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.marginStart=margin
                    imgMarkView.layoutParams=layoutParams

                    imgMarkView.post {
                        val mLeftLength=imgMarkView.left+imgMarkView.width/2


                        layerBuilder?.setMargin(1,
                            mLeftLength,
                            0,
                            0,
                            0)

                        progressView.background=layerBuilder?.build()
                    }
                }
            }
        }
    }

    fun onReleaseView() {
        mViewBinding=null
        mFragment = null
    }
}