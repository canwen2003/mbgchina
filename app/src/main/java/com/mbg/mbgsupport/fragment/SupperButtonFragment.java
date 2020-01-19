package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.core.LifecycleHandler;
import com.mbg.module.common.core.WeakHandler;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class SupperButtonFragment extends BaseFragment{

    private WeakHandler weakHandler=new WeakHandler();
    private LifecycleHandler lifecycleHandler=new LifecycleHandler(this);

    public static void show(Context context){
        TerminalActivity.show(context, SupperButtonFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_super_button;
    }

    @Override
    protected void initView() {

        findViewById(R.id.round_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weakHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d("weakHandler:Run!!!");
                    }
                },5000);

                lifecycleHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d("lifecycleHandler:Run!!!");
                    }
                },4000);
            }
        });
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
