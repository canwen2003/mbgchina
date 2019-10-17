package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.graphics.Typeface;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.PermissionUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.pudding.Pudding;

import java.util.List;

public class PuddingFragment extends BaseFragment implements View.OnClickListener {

    public static void show(Context context) {
        TerminalActivity.show(context, PuddingFragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_pudding;
    }

    @Override
    protected void initView() {
        PermissionUtils.checkStoragePermissson(getActivity(), new PermissionUtils.PermissionCallbacks() {
            @Override
            public void onPermissionsGranted(int requestCodes, List<String> perms) {

            }

            @Override
            public void onPermissionsDenied(int requestCodes, List<String> perms) {

            }
        });
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
        findViewById(R.id.btn_test5).setOnClickListener(this);
        findViewById(R.id.btn_test6).setOnClickListener(this);
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
                break;
        }

    }

    private void onTest1() {
        Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setTitle("This is a Title");
        pudding.show();

    }

    private void onTest2() {
        Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setTitle("This is a Title");
        pudding.setText("This is a Text");
        pudding.show();
    }

    private void onTest3() {
        Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setText("This is a Text");
        pudding.setIcon(R.drawable.ic_event_available_black_24dp);
        pudding.show();
    }

    private void onTest4() {
        Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setText("This is a Text");
        pudding.setIcon(R.drawable.ic_event_available_black_24dp);
        pudding.setEnableProgress(true);
        pudding.show();
    }

    private void onTest5() {
        Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setText("This is a Text");
        pudding.setIcon(R.drawable.ic_event_available_black_24dp);
        pudding.setTextTypeface(Typeface.DEFAULT_BOLD);
        pudding.setBackgroundColor(getResources().getColor(R.color.color_14c7de));
        pudding.show();
    }

    private void onTest6() {
        final Pudding pudding=Pudding.create((AppCompatActivity)getActivity());
        pudding.setText("This is a Text");
        pudding.setIcon(R.drawable.ic_event_available_black_24dp);
        pudding.setEnabledVibration(true);
        pudding.setBackgroundColor(getResources().getColor(R.color.color_14c7de));
        pudding.addButton("OK", R.style.PuddingButton, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("OK");
                pudding.showIcon(false);
            }
        });
        pudding.addButton("NO", R.style.PuddingButton, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("No");
                pudding.hide();
            }
        });
        pudding.enableSwipeToDismiss();
        pudding.setInfiniteDuration(true);
        pudding.show();
    }

}
