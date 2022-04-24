package com.mbg.mbgsupport

import android.content.Context
import com.mbg.mbgsupport.fragment.UtilsDemoFragment.Companion.show
import com.mbg.module.ui.kotlin.activity.PhoneActivity.Companion.show
import com.mbg.module.ui.activity.BaseViewBindingActivity
import androidx.lifecycle.ViewModelProvider
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel.LoadingState
import com.mbg.module.ui.view.drawable.DrawableCreator
import com.mbg.module.common.util.UiUtils
import com.mbg.module.ui.view.drawable.LayerBuilder
import com.mbg.mbgsupport.fragment.tab.CommonTabFragment
import com.mbg.mbgsupport.fragment.tab.SegmentTabFragment
import com.mbg.mbgsupport.fragment.tab.SlidingTabFragment
import com.mbg.mbgsupport.fragment.appbar.AppBarLayoutFragment
import com.mbg.mbgsupport.fragment.seekbar.SeekBarFragment
import android.content.Intent
import android.location.Address
import com.mbg.mbgsupport.kotlin.KotlinMain
import com.mbg.mbgsupport.demo.kotlin.viewbinding.DemoViewBindingActivity
import com.mbg.mbgsupport.demo.kotlin.mvp.AlphaTranFragment
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoGestureFragment
import com.mbg.mbgsupport.dialogfliptest.FlipMainActivity
import com.mbg.mbgsupport.demo.UPVDemoActivity
import android.os.Looper
import com.mbg.mbgsupport.work.DemoWorker
import android.location.Geocoder
import android.util.Printer
import androidx.core.content.ContextCompat
import androidx.work.*
import com.mbg.mbgsupport.databinding.ActivityMainBinding
import com.mbg.mbgsupport.demo.kotlin.inlinefun.SnackbarDemoActivity
import com.mbg.mbgsupport.fragment.*
import com.mbg.module.common.util.LogUtils
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : BaseViewBindingActivity<ActivityMainBinding?>() {
    private lateinit var context: Context

    private fun onDataLoadingStart() {
        LogUtils.d("LoadingStateViewModel:onDataLoadingStart")
    }

    private fun onDataLoadingFinish() {
        LogUtils.d("LoadingStateViewModel:onDataLoadingFinish")
    }

    override fun initView() {
        context = this
        ViewModelProvider(this).get(LoadingStateViewModel::class.java).loadingState.observe(this) { loadingState ->
            if (loadingState != null) {
                when (loadingState) {
                    LoadingState.START -> onDataLoadingStart()
                    LoadingState.FINISH -> onDataLoadingFinish()
                }
            }
        }
        var builder =
            DrawableCreator.Builder().setCornersRadius(UiUtils.dip2px(30f).toFloat())
                .setSolidColor(ContextCompat.getColor(context, R.color.amber_a100))
                .setStrokeColor(ContextCompat.getColor(context, R.color.amber_100))
                .setStrokeWidth(UiUtils.dip2px(2f).toFloat())

        mViewBinding?.run {
            btnDragView.background = builder.build()

            builder.setSolidColor(ContextCompat.getColor(context, R.color.trans))
                .setStrokeColor(ContextCompat.getColor(context, R.color.red_50))
            val drawable1 = builder.build()
            builder.setSolidColor(ContextCompat.getColor(context, R.color.black))
                .setStrokeColor(ContextCompat.getColor(context, R.color.red_50))
            btnImageloader.background = builder.build()

            builder.setSolidColor(ContextCompat.getColor(context, R.color.black))
                .setStrokeWidth(0f)
            val drawable2 = builder.build()
            builder = DrawableCreator.Builder()
                .setGradientColor(ContextCompat.getColor(context, R.color.green_50),
                    ContextCompat.getColor(context, R.color.green_700)).setGradientAngle(0)
                .setCornersRadius(0f,
                    UiUtils.dip2px(30f).toFloat(),
                    0f,
                    UiUtils.dip2px(30f).toFloat())
            val drawable3 = builder.build()
            btnSnapshot.background = builder.build()


            val layerBuilder = LayerBuilder.create(drawable1, drawable2, drawable3).setMargin(1,
                UiUtils.dip2px(2f),
                UiUtils.dip2px(2f),
                UiUtils.dip2px(40f),
                UiUtils.dip2px(2f)).setMargin(2,
                UiUtils.dip2px(60f),
                UiUtils.dip2px(2f),
                UiUtils.dip2px(2f),
                UiUtils.dip2px(2f))
            btnDragView.setOnClickListener { DragFragment.show(context) }
            btnImageloader.setOnClickListener { ImageLoaderFragment.show(context) }
            btnSnapshot.setOnClickListener { SnapShotFragment.show(context) }
            btnTextBanner.setOnClickListener { TextBannerFragment.show(context) }
            btnUtils.setOnClickListener { show(context) }
            btnSupper.setOnClickListener { SupperButtonFragment.show(context) }
            btnBubble.setOnClickListener { BubbleViewFragment.show(context) }
            btnPudding.setOnClickListener { PuddingFragment.show(context) }
            btnBigImage.setOnClickListener { BigImageLoaderFragment.show(context) }
            btnSlid.setOnClickListener { SlidingFragment.show(context) }
            btnViewpager.setOnClickListener { ViewPager2Fragment.show(context) }
            btnTimeline.setOnClickListener { TimeLineFragment.show(context) }
            btnFlowlayout.setOnClickListener { FlowLayoutFragment.show(context) }
            btnSystemFlowlayout.setOnClickListener { SystemFlowLayoutFragment.show(context)}
            btnCommonTab.setOnClickListener { CommonTabFragment.show(context) }
            btnSegment.setOnClickListener { SegmentTabFragment.show(context) }
            btnSliding.setOnClickListener { SlidingTabFragment.show(context) }
            btnAppBar.setOnClickListener { AppBarLayoutFragment.show(context) }
            btnSeekBar.setOnClickListener { SeekBarFragment.show(context) }
            btnShape.setOnClickListener { ShapeFragment.show(context) }
            btnShimmer.setOnClickListener { ShimmerFragment.show(context) }
            btnSkeleton.setOnClickListener { SkeletonFragment.show(context) }
            btnLottie.setOnClickListener { AnimsFragment.show(context) }
            btnKotlin.setOnClickListener {
                val intent = Intent(context, KotlinMain::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            btnConstraint.setOnClickListener { ConstraintFragment.show(context) }
            btnConstraint.background = layerBuilder.build()
            btnFlexbox.setOnClickListener { FlexboxLayoutFragment.show(context) }
            btnMotion.setOnClickListener { MotionLayoutFragment.show(context) }
            btnKotlinBinding.setOnClickListener {
                val intent = Intent(context, DemoViewBindingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            btnMvpTest.setOnClickListener {
                show(context, AlphaTranFragment::class.java, null)
            }
            btnGesture.setOnClickListener {
                show(context, DemoGestureFragment::class.java, null)
            }
            btnFlip.setOnClickListener {
                val intent = Intent(context, FlipMainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            btnSnackbar.setOnClickListener {
                val intent= Intent(context, SnackbarDemoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            btnTransformer.setOnClickListener {
                ViewPagerTransformerFragment.show(context)
            }

            btnBlur.setOnClickListener {
                show(context, ViewBlurFragment::class.java, null)
            }

            btnSvga.setOnClickListener { SvgaDemoFragment.show(context) }

            btnRange.setOnClickListener {
                val intent = Intent(context, UPVDemoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }


            Looper.getMainLooper().setMessageLogging(object : Printer {
                private val START = ">>>>> Dispatching"

                private val END = "<<<<< Finished"
                override fun println(x: String?) {
                    if (x == null) {
                        return
                    }
                    if (x.startsWith(START)) {
                        mStartOfMsg = System.currentTimeMillis()
                    }
                    if (x.startsWith(END)) {
                        val subTime = System.currentTimeMillis() - mStartOfMsg
                        if (subTime >= 160) {
                            LogUtils.d("smooth = " + (System.currentTimeMillis() - mStartOfMsg))
                            LogUtils.d("smooth = $x")
                        }
                    }
                }
            })
        }
        val constraints = Constraints.Builder() //.setRequiresCharging(true)//在充电状态
            .setRequiredNetworkType(NetworkType.CONNECTED) //网络连接时
            .setRequiresBatteryNotLow(true) //电池电量不能为低
            .build()
        val data = Data.Builder().putString("doWork", "" + System.currentTimeMillis()).build()
        val oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(DemoWorker::class.java).setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS).setInputData(data).build()

         LogUtils.d("位置：" + getAddress(39.898566, 116.464244))
         LogUtils.d("位置：" + getAddress(38.898566, 117.464244))
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
    }

    private var mStartOfMsg: Long = 0
    private fun getAddress(latitude: Double, longitude: Double): String {
        var cityName = ""
        var addList: List<Address>? = null
        var addressList: List<Address>? = null
        val ge = Geocoder(context)
        try {
            addList = ge.getFromLocation(latitude, longitude, 40) //可用
            addressList = ge.getFromLocationName("天安门", 40) //基本查询不到，不好用
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addList != null && addList.isNotEmpty()) {
            for (i in addList.indices) {
                val ad = addList[i]
                cityName += ad.countryName + "," + ad.locality + "," + ad.featureName
            }
        }
        if (addressList != null && addressList.isNotEmpty()) {
            for (i in addressList.indices) {
                val ad = addressList[i]
                cityName += ad.countryName + "," + ad.locality + "," + ad.featureName
            }
        }
        return cityName
    }
}