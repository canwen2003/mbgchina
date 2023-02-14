package com.mbg.mbgsupport.fragment

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.shape.*
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentShapeimageviewDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.common.util.LogUtils
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled

class ShapeableImageViewFragment : MvpFragment<DemoPresenter,FragmentShapeimageviewDemoBinding>() {

    private var mTestStr:String?=null

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setLogEnabled(true)
    }

    override fun initView() {
        mTestStr?.run {
            LogUtils.d("mTestStr:run")
        }?:kotlin.run {
            LogUtils.d("mTestStr:run else")
        }

        mTestStr?.let {
            LogUtils.d("mTestStr:let")
        }?:kotlin.run {
            LogUtils.d("mTestStr:let else")
        }

        mTestStr?.apply {
            LogUtils.d("mTestStr:apply")
        }?:kotlin.run {
            LogUtils.d("mTestStr:apply else")
        }

        mTestStr?.also {
            LogUtils.d("mTestStr:also")
        }?:kotlin.run {
            LogUtils.d("mTestStr:also else")
        }


        mViewBinding?.run {
            val shapeAppearanceModel2 = ShapeAppearanceModel.builder().apply {
                setAllCorners(RoundedCornerTreatment())
                setAllCornerSizes(30f)
            }.build()

            val drawable2 = MaterialShapeDrawable(shapeAppearanceModel2).apply {
                setTint(ContextCompat.getColor(requireContext(), R.color.color_end))
                paintStyle = Paint.Style.FILL_AND_STROKE
                strokeWidth = 2f
                strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.red_200)
            }

            tvTest.background=drawable2

            val shapeAppearanceModel3 = ShapeAppearanceModel.builder().apply {
                setAllCorners(RoundedCornerTreatment())
                setAllCornerSizes(20f)
                setRightEdge(object : TriangleEdgeTreatment(20f, false) {
                    // center 位置 ， interpolation 角的大小
                    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
                        super.getEdgePath(length, 35f, interpolation, shapePath)
                    }
                })
            }.build()

            (tvTest1.parent as ViewGroup).clipChildren = false // 不限制子view在其范围内
            tvTest1.background = MaterialShapeDrawable(shapeAppearanceModel3).apply {
                setTint(ContextCompat.getColor(requireContext(), R.color.color_end))
                paintStyle = Paint.Style.FILL_AND_STROKE
                strokeWidth = 2f
                strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.red_200)
            }

            tvTest2.background = MaterialShapeDrawable(shapeAppearanceModel3).apply {
                setTint(ContextCompat.getColor(requireContext(), R.color.color_end))
                paintStyle = Paint.Style.FILL
            }

            shapeView()
        }


    }

    private fun shapeView(){
        mViewBinding?.run {

            val shapeAppearanceModel = ShapeAppearanceModel.builder().apply {
                setAllCorners(RoundedCornerTreatment())
                setAllCornerSizes(20f)
                setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(20f, false),0f))
            }.build()

            (tvTest3.parent as ViewGroup).clipChildren=false
            tvTest3.background = MaterialShapeDrawable(shapeAppearanceModel).apply {
                setTint(ContextCompat.getColor(requireContext(), R.color.color_end))
                shadowCompatibilityMode=MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
                setShadowColor(ContextCompat.getColor(requireContext(), R.color.colorGreen50))
                initializeElevationOverlay(context)
                elevation=20f
                paintStyle = Paint.Style.FILL_AND_STROKE
                strokeWidth = 2f
                strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.red_200)
            }



            tvTest3.background=MaterialShapeDrawable(shapeAppearanceModel).apply {
                setTint(ContextCompat.getColor(requireContext(),R.color.colorGreen50))
                        paintStyle = Paint.Style.FILL
            }


        }
    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, ShapeableImageViewFragment::class.java, null)
        }
    }
}