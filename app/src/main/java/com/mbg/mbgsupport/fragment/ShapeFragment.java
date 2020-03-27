package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.countDown.CountDownTextView;

import java.util.concurrent.TimeUnit;

public class ShapeFragment extends BaseFragment{
    private CountDownTextView countDownTextView;
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

        countDownTextView=findViewById(R.id.count_down_view);
        countDownTextView.bindLifecycle(getViewLifecycleOwner());
        countDownTextView.setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
            @Override
            public void onStart() {
                ToastUtils.debugShow("Count Down Start");
            }
        }).setNormalText("0").setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
            @Override
            public void onFinish() {
                ToastUtils.debugShow("Count Down finish");
            }
        }).startCountDown(1, TimeUnit.MINUTES);
        initData();
    }


    private void initData() {


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
