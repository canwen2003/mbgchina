package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class MotionLayoutFragment extends BaseFragment implements View.OnClickListener {
    MotionLayout mMotionLayout;
    public static void show(Context context) {
        TerminalActivity.show(context, MotionLayoutFragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_motion_layout;
    }

    @Override
    protected void initView() {
        // fillAutoSpacingLayout();
        findViewById(R.id.img_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("OnClicked");
            }
        });

        mMotionLayout=findViewById(R.id.motion_layout);
        mMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int i1) {
                LogUtils.d("zzy:startId="+startId);
                ToastUtils.show("onTransitionStarted");
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int i1, float v) {
                //LogUtils.d("zzy:startId="+startId);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int startId) {
                LogUtils.d("zzy:startId="+startId);
                ToastUtils.show("onTransitionCompleted");
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int startId, boolean b, float v) {
                LogUtils.d("zzy:startId="+startId);
            }
        });
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (ClickUtils.isFastDoubleClick(viewId)) {
            return;
        }

    }
}
