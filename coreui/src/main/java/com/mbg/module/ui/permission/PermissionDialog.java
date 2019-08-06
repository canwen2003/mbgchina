package com.mbg.module.ui.permission;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获得返回键回调
 */

public class PermissionDialog extends Dialog {

    private OnBackCallBack mOnBackCallBack;

    public PermissionDialog(Context context) {
        super(context);
    }

    public PermissionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PermissionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public void showDialog(View view, OnBackCallBack onBackCallBack) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.setCancelable(false);

        Window dialogWindow = this.getWindow();

        //弹窗设置状态栏沉浸
        if (android.os.Build.VERSION.SDK_INT >= 21 && dialogWindow != null) {
            dialogWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialogWindow.setStatusBarColor(Color.TRANSPARENT);
        }

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        dialogWindow.setAttributes(lp);

        this.setOnBackCallBack(onBackCallBack);

        this.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isShowing()) {
            dismiss();
        }
        if (mOnBackCallBack != null) {
            mOnBackCallBack.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void setOnBackCallBack (OnBackCallBack onBackCallBack) {
        mOnBackCallBack = onBackCallBack;
    }

    public interface OnBackCallBack {
        void onBackPressed();
    }
}
