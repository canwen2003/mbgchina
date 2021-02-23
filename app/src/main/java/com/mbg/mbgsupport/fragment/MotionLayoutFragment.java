package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class MotionLayoutFragment extends BaseFragment implements View.OnClickListener {

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
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (ClickUtils.isFastDoubleClick(viewId)) {
            return;
        }

    }
}
