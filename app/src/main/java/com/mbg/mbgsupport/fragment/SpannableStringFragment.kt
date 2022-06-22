package com.mbg.mbgsupport.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import androidx.core.content.ContextCompat
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentSpannableDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.common.util.LogUtils
import com.mbg.module.common.util.ToastUtils
import com.mbg.module.common.util.UiUtils
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled

class SpannableStringFragment : MvpFragment<DemoPresenter,FragmentSpannableDemoBinding>() {

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setLogEnabled(true)
        initView()
    }

    override fun initView() {
        mViewBinding?.run {
            val builder=SpannableStringBuilder()
            builder.append("设置部分文字颜色:设置我吧")
            //设置颜色
            val colorSpan=ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.amber_200))
            builder.setSpan(colorSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView0.text=builder

            //设置大小，并包含上面的设置颜色
            val sizeSpan=AbsoluteSizeSpan(UiUtils.dip2px(16f))
            builder.setSpan(sizeSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView1.text=builder

            //背景验证，并包含上面的
            val bgColorSpan=BackgroundColorSpan(ContextCompat.getColor(requireContext(), R.color.cyan_900))
            builder.setSpan(bgColorSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView2.text=builder

            //设置文字粗细，并包含上面的
            val styleSpan=StyleSpan(Typeface.BOLD)
            builder.setSpan(styleSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView3.text=builder

            //设置下滑线，并包含上面的
            val underlineSpan=UnderlineSpan()
            builder.setSpan(underlineSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView4.text=builder

            //设置删除线，并包含上面的
            val strikethroughSpan=StrikethroughSpan()
            builder.setSpan(strikethroughSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView5.text=builder

            //设置删除线，并包含上面的
            val clickableSpan=object :ClickableSpan(){
                override fun onClick(view: View) {
                   ToastUtils.debugShow("别点击我")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isStrikeThruText=false
                    ds.color=ContextCompat.getColor(requireContext(), R.color.indigo_900)
                }
            }
            builder.setSpan(clickableSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView6.text=builder
            tvSpanView6.movementMethod=LinkMovementMethod.getInstance()


            //设置删除线，并包含上面的
            val builder1=SpannableStringBuilder()
            builder1.append("设置部分文字颜色:设置我吧")
            val textAppearanceSpan=TextAppearanceSpan(null,Typeface.NORMAL,UiUtils.dip2px(20f),ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.pink_400)),null)
            builder1.setSpan(textAppearanceSpan,"设置部分文字颜色:".length,"设置部分文字颜色:设置我吧".length,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            tvSpanView7.text=builder1


            chronometer.base=0
            chronometer.format="MM:SS"
            chronometer.setOnChronometerTickListener {
                LogUtils.d("zzy: ${it.drawingTime}")
            }
            chronometer.start()
        }

    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, SpannableStringFragment::class.java, null)
        }
    }
}