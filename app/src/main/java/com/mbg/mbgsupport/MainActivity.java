package com.mbg.mbgsupport;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.util.Printer;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mbg.mbgsupport.databinding.ActivityMainBinding;
import com.mbg.mbgsupport.fragment.AnimsFragment;
import com.mbg.mbgsupport.fragment.ConstraintLayoutFragment;
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
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.ui.activity.BaseViewBindingActivity;

import java.io.IOException;
import java.util.List;

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
                Intent intent=new Intent(context,KotlinMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mViewBinding.btnConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayoutFragment.show(context);
            }
        });

       LogUtils.d("位置："+getAddress(39.898566,116.464244));


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

        ThreadUtils.postInUIThreadDelayed(new Runnable() {
            int Loop=1000;
            @Override
            public void run() {
                while (Loop>0){
                    try {
                        Thread.sleep(10);
                        Loop--;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        },2000);

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
