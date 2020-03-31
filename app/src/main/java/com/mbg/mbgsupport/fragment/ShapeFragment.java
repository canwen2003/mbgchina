package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.banner.ConvenientBanner;
import com.mbg.module.ui.view.banner.holder.CBViewHolderCreator;
import com.mbg.module.ui.view.banner.listener.OnItemClickListener;
import com.mbg.module.ui.view.countDown.CountDownTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ShapeFragment extends BaseFragment{
    private CountDownTextView countDownTextView;
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<>();
    public static void show(Context context){
        TerminalActivity.show(context, ShapeFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_shape;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        countDownTextView = findViewById(R.id.count_down_view);
        countDownTextView.bindLifecycle(getViewLifecycleOwner())
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
                        ToastUtils.debugShow("计时开始");
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        ToastUtils.debugShow("计时结束 ");
                    }

                }).setNormalText("计时结束后显示的文本").
                startCountDown(1, TimeUnit.MINUTES);

        convenientBanner = findViewById(R.id.convenientBanner);

        for (int position = 0; position < 7; position++) {
            localImages.add(UiUtils.getResId("ic_test_" + position, R.drawable.class));
        }

        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.view_banner_localimage;
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(R.drawable.ic_page_indicator_focused,R.drawable.ic_page_indicator)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
        ;

//        convenientBanner.setManualPageable(false);//设置不能手动影响

        initData();
    }


    private void initData() {


    }

    @Override
    public void onResume() {
        super.onResume();
        super.onResume();
        //开始自动播放
        convenientBanner.startTurning();
    }

    @Override
    public void onStop() {
        //停止播放
        convenientBanner.stopTurning();
        super.onStop();
    }

}
