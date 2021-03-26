package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.fragment.constraint.ConstraintAnimationFragment;
import com.mbg.mbgsupport.fragment.constraint.ConstraintLayoutFragment;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class ConstraintFragment extends BaseFragment implements View.OnClickListener {
    public static void show(Context context) {
        TerminalActivity.show(context, ConstraintFragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_constraint_demo;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
        findViewById(R.id.btn_test5).setOnClickListener(this);
        findViewById(R.id.btn_test6).setOnClickListener(this);
        findViewById(R.id.btn_test7).setOnClickListener(this);
        findViewById(R.id.btn_test8).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (ClickUtils.isFastDoubleClick(viewId)) {
            return;
        }
        switch (viewId) {
            case R.id.btn_test1:
                onTest1();
                break;
            case R.id.btn_test2:
                onTest2();
                break;
            case R.id.btn_test3:
                onTest3();
                break;
            case R.id.btn_test4:
                onTest4();
                break;
            case R.id.btn_test5:
                onTest5();
                break;
            case R.id.btn_test6:
                onTest6();
            case R.id.btn_test7:
                onTest7();
                break;
            case R.id.btn_test8:
                onTest8();
                break;
        }

    }

    private void onTest1() {
        ConstraintAnimationFragment.show(getActivity());
    }

    private void onTest2() {
        ConstraintLayoutFragment.show(getActivity());
    }

    private void onTest3() {
    }

    private void onTest4() {

    }

    private void onTest5() {
    }

    private void onTest6() {
    }

    private void onTest7() {
    }

    private void onTest8() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
