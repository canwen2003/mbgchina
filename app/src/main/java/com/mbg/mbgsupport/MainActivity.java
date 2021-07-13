package com.mbg.mbgsupport;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.util.Printer;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.mbg.mbgsupport.databinding.ActivityMainBinding;
import com.mbg.mbgsupport.demo.kotlin.mvp.AlphaTranFragment;
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoGestureFragment;
import com.mbg.mbgsupport.demo.kotlin.viewbinding.DemoViewBindingActivity;
import com.mbg.mbgsupport.fragment.AnimsFragment;
import com.mbg.mbgsupport.fragment.ConstraintFragment;
import com.mbg.mbgsupport.fragment.FlexboxLayoutFragment;
import com.mbg.mbgsupport.fragment.MotionLayoutFragment;
import com.mbg.mbgsupport.fragment.ShapeFragment;
import com.mbg.mbgsupport.fragment.ShimmerFragment;
import com.mbg.mbgsupport.fragment.SkeletonFragment;
import com.mbg.mbgsupport.fragment.appbar.AppBarLayoutFragment;
import com.mbg.mbgsupport.fragment.BigImageLoaderFragment;
import com.mbg.mbgsupport.fragment.BubbleViewFragment;
import com.mbg.mbgsupport.fragment.DragFragment;
import com.mbg.mbgsupport.fragment.FlowLayoutFragment;
import com.mbg.mbgsupport.fragment.ImageLoaderFragment;
import com.mbg.mbgsupport.fragment.PuddingFragment;
import com.mbg.mbgsupport.fragment.SlidingFragment;
import com.mbg.mbgsupport.fragment.SnapShotFragment;
import com.mbg.mbgsupport.fragment.SupperButtonFragment;
import com.mbg.mbgsupport.fragment.SystemFlowLayoutFragment;
import com.mbg.mbgsupport.fragment.TextBannerFragment;
import com.mbg.mbgsupport.fragment.TimeLineFragment;
import com.mbg.mbgsupport.fragment.UtilsDemoFragment;
import com.mbg.mbgsupport.fragment.ViewPager2Fragment;
import com.mbg.mbgsupport.fragment.seekbar.SeekBarFragment;
import com.mbg.mbgsupport.fragment.tab.CommonTabFragment;
import com.mbg.mbgsupport.fragment.tab.SegmentTabFragment;
import com.mbg.mbgsupport.fragment.tab.SlidingTabFragment;
import com.mbg.mbgsupport.kotlin.KotlinMain;
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel;
import com.mbg.mbgsupport.work.DemoWorker;
import com.mbg.module.common.datastore.AppDataStore;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.activity.BaseViewBindingActivity;
import com.mbg.module.ui.kotlin.activity.PhoneActivity;
import com.mbg.module.ui.view.drawable.DrawableCreator;
import com.mbg.module.ui.view.drawable.LayerBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> {
    private Context context;

    protected void  onDataLoadingStart(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingStart");
    }

    protected void  onDataLoadingFinish(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingFinish");
    }

    @Override
    public void initView(){

        context=this;
        new ViewModelProvider(this).get(LoadingStateViewModel.class).getLoadingState().observe(this, new Observer<LoadingStateViewModel.LoadingState>() {
            @Override
            public void onChanged(LoadingStateViewModel.LoadingState loadingState) {
                if (loadingState!=null){
                    switch (loadingState){
                        case START:
                            onDataLoadingStart();
                            break;
                        case FINISH:
                            onDataLoadingFinish();
                            break;
                    }
                }
            }
        });

        DrawableCreator.Builder drawableBuilder = new DrawableCreator.Builder()
                .setCornersRadius(UiUtils.dip2px(30))
                .setSolidColor(getResources().getColor(R.color.amber_a100))
                .setStrokeColor(getResources().getColor(R.color.amber_100))
                .setStrokeWidth(UiUtils.dip2px(2));
        mViewBinding.btnDragView.setBackground(drawableBuilder.build());
                drawableBuilder.setSolidColor(getResources().getColor(R.color.trans))
                .setStrokeColor(getResources().getColor(R.color.red_50));
        Drawable drawable1=drawableBuilder.build();


        drawableBuilder
                .setSolidColor(getResources().getColor(R.color.black))
                .setStrokeColor(getResources().getColor(R.color.red_50));

        mViewBinding.btnImageloader.setBackground(drawableBuilder.build());

        drawableBuilder
                .setSolidColor(getResources().getColor(R.color.black))
                .setStrokeWidth(0);
        Drawable drawable2=drawableBuilder.build();

        drawableBuilder =new DrawableCreator.Builder()
                .setGradientColor(getResources().getColor(R.color.green_50),getResources().getColor(R.color.green_700))
                .setGradientAngle(0)
                .setCornersRadius(0,UiUtils.dip2px(30),0,UiUtils.dip2px(30));

        Drawable drawable3=drawableBuilder.build();
        mViewBinding.btnSnapshot.setBackground(drawableBuilder.build());

        LayerBuilder layerBuilder=LayerBuilder.create(drawable1,drawable2,drawable3)
                .setMargin(1,UiUtils.dip2px(2),UiUtils.dip2px(2),UiUtils.dip2px(40),UiUtils.dip2px(2))
                .setMargin(2,UiUtils.dip2px(60),UiUtils.dip2px(2),UiUtils.dip2px(2),UiUtils.dip2px(2));

        mViewBinding.btnDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragFragment.show(context);
            }
        });

        mViewBinding.btnImageloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoaderFragment.show(context);
            }
        });

        mViewBinding.btnSnapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapShotFragment.show(context);
            }
        });

        mViewBinding.btnTextBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBannerFragment.show(context);
            }
        });

        mViewBinding.btnUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsDemoFragment.show(context);
            }
        });

        mViewBinding.btnSupper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SupperButtonFragment.show(context);
            }
        });

        mViewBinding.btnBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BubbleViewFragment.show(context);
            }
        });

        mViewBinding.btnPudding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PuddingFragment.show(context);
            }
        });

        mViewBinding.btnBigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImageLoaderFragment.show(context);
            }
        });

        mViewBinding.btnSlid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingFragment.show(context);
            }
        });

        mViewBinding.btnViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager2Fragment.show(context);
            }
        });

        mViewBinding.btnTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeLineFragment.show(context);
            }
        });

        mViewBinding.btnFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlowLayoutFragment.show(context);
            }
        });
        mViewBinding.btnSystemFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemFlowLayoutFragment.show(context);
            }
        });

        findViewById(R.id.btn_common_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonTabFragment.show(context);
            }
        });

        mViewBinding.btnSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SegmentTabFragment.show(context);
            }
        });

        mViewBinding.btnSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingTabFragment.show(context);
            }
        });
        mViewBinding.btnAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBarLayoutFragment.show(context);
            }
        });

        mViewBinding.btnSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeekBarFragment.show(context);
            }
        });
        mViewBinding.btnShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShapeFragment.show(context);
            }
        });

        mViewBinding.btnShimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShimmerFragment.show(context);
            }
        });

        mViewBinding.btnSkeleton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonFragment.show(context);
            }
        });

        mViewBinding.btnLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimsFragment.show(context);
            }
        });

        mViewBinding.btnKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, KotlinMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mViewBinding.btnConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintFragment.show(context);
            }
        });

        mViewBinding.btnConstraint.setBackground(layerBuilder.build());

        mViewBinding.btnFlexbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlexboxLayoutFragment.show(context);
            }
        });

        mViewBinding.btnMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotionLayoutFragment.show(context);
            }
        });

        mViewBinding.btnKotlinBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DemoViewBindingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

       LogUtils.d("位置："+getAddress(39.898566,116.464244));

        mViewBinding.btnMvpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneActivity.show(context, AlphaTranFragment.class, null);
            }
        });

        mViewBinding.btnGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneActivity.show(context, DemoGestureFragment.class, null);
            }
        });

        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x==null) {
                    return;
                }

                if (x.startsWith(START)){
                    mStartOfMsg=System.currentTimeMillis();
                }

                if (x.startsWith(END)){
                    long subTime=System.currentTimeMillis()-mStartOfMsg;
                    if (subTime>=160) {
                        LogUtils.d("smooth = " + (System.currentTimeMillis() - mStartOfMsg));
                        LogUtils.d("smooth = " + x);
                    }
                }
            }
        });

        Constraints constraints=new Constraints.Builder()
                //.setRequiresCharging(true)//在充电状态
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络连接时
                .setRequiresBatteryNotLow(true)//电池电量不能为低
                .build();
        Data data=new Data.Builder()
                .putString("doWork",""+System.currentTimeMillis())
                .build();

        OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(DemoWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setInputData(data)
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);

    }
    private long mStartOfMsg=0;


    public String getAddress(double latitude, double longitude) {
        String cityName = "";
        List<Address> addList = null;
        List<Address> addressList = null;
        Geocoder ge = new Geocoder(context);
        try {
            addList = ge.getFromLocation(latitude, longitude, 40);//可用
            addressList=ge.getFromLocationName("天安门",40);//基本查询不到，不好用
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address ad = addList.get(i);
                cityName += ad.getCountryName() + "," + ad.getLocality()+","+ad.getFeatureName();
            }
        }

        if (addressList != null && addressList.size() > 0) {
            for (int i = 0; i < addressList.size(); i++) {
                Address ad = addressList.get(i);
                cityName += ad.getCountryName() + "," + ad.getLocality()+","+ad.getFeatureName();
            }
        }
        return cityName;
    }
}
