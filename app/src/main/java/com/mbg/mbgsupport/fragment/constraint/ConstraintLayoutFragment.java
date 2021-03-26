package com.mbg.mbgsupport.fragment.constraint;

import android.content.Context;

import androidx.constraintlayout.widget.Placeholder;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class ConstraintLayoutFragment extends BaseFragment{

    private Placeholder placeholder;
    public static void show(Context context){
        TerminalActivity.show(context, ConstraintLayoutFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_constrainst_layout;
    }

    @Override
    protected void initView() {
        placeholder=findViewById(R.id.placeholder);
        placeholder.setContentId(R.id.tv_4);

        ThreadUtils.postInUIThreadDelayed(()->placeholder.setContentId(R.id.tv_7),2000);
        ThreadUtils.postInUIThreadDelayed(()->placeholder.setContentId(R.id.tv_4),4000);
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
