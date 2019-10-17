package com.mbg.module.ui.permission;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.ui.activity.BaseFragmentActivity;


/**
 * 检查权限时，如果上下文对象不是activity,需借助透明activity实现回调
 */

public class PermissionActivity extends BaseFragmentActivity {

    private static final String TAG = "PermissionActivity";

    private int mPermission = -1;

    public static final String ACTION = "action";

    @Override
    protected void onCreate(Bundle args) {
        Log.i(TAG, "onCreate");

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null ) {
                mPermission = bundle.getInt(ACTION);
            }
        }
        if (mPermission == Permissions.PERMISSION_CALENDAR) {
            PermissionHelper.checkCallendar(this, null);
        } else if (mPermission == Permissions.PERMISSION_CAMERA) {
            PermissionHelper.checkCamera(this, null);
        } else if (mPermission == Permissions.PERMISSION_CONTACTS) {
            PermissionHelper.checkContact(this, null);
        } else if (mPermission == Permissions.PERMISSION_LOCATION) {
            PermissionHelper.checkLocation(this, null);
        } else if (mPermission == Permissions.PERMISSION_MICROPHONE) {
            PermissionHelper.checkMicroPhone(this, null);
        } else if (mPermission == Permissions.PERMISSION_PHONE) {
            PermissionHelper.checkPhone(this, null);
        } else if (mPermission == Permissions.PERMISSION_SENSORS) {
            PermissionHelper.checkSensor(this, null);
        } else if (mPermission == Permissions.PERMISSION_SMS_READ) {
            PermissionHelper.checkSMSReadPermissson(this, null);
        } else if (mPermission == Permissions.PERMISSION_SMS_SEND) {
            PermissionHelper.checkSMSSendPermissson(this, null);
        } else if (mPermission == Permissions.PERMISSION_STORAGE) {
            PermissionHelper.checkStoragePermissson(this, null);
        } else if (mPermission == Permissions.PERMISSION_MIC_CAMERA) {
            PermissionHelper.checkCameraAndMIC(this, null);
        } else if (mPermission == Permissions.PERMISSION_LAUNCH){
            PermissionHelper.checkLaunch(this, null);
        } else {
            finish();
        }
        super.onCreate(args);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ThreadUtils.postInUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                PermissionActivity.this.finish();
                Log.i(TAG, "finish");
            }
        },500);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
