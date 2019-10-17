package com.mbg.module.ui.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;


import com.mbg.module.common.util.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 需要target version >= 23编译，才能生效。

 * 以下是危险权限，不检查就直接调用与权限相关的功能，就会crash.一般权限不用特别检查
 * 每个权限组只需申请一个权限即可

 *权限组	             权限
 *
 *CALENDAR        READ_CALENDAR
 *                WRITE_CALENDAR
 *
 * CAMERA         CAMERA
 *
 *CONTACTS        READ_CONTACTS
 *                WRITE_CONTACTS
 *                GET_ACCOUNTS
 *
 * LOCATION       ACCESS_FINE_LOCATION
 *                ACCESS_COARSE_LOCATION
 *
 * MICROPHONE     RECORD_AUDIO
 *
 * PHONE          READ_PHONE_STATE
 *                CALL_PHONE
 *                READ_CALL_LOG
 *                WRITE_CALL_LOG
 *                ADD_VOICEMAIL
 *                USE_SIP
 *                PROCESS_OUTGOING_CALLS
 *
 * SENSORS        BODY_SENSORS
 *
 *SMS             SEND_SMS
 *
 *RECEIVE_SMS     READ_SMS
 *                RECEIVE_WAP_PUSH
 *                RECEIVE_MMS
 *
 *STORAGE        READ_EXTERNAL_STORAGE
 *               WRITE_EXTERNAL_STORAGE
*/

public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    public static PermissionCallbacks mPermissionCallBack;

    //系统权限窗是否正在显示
    public static boolean isSysPerDialogShowing = false;

    //检查日历权限
    public static void checkCallendar(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_CALENDAR, getCallendarGroup());
    }

    //检查照相权限
    public static void checkCamera(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_CAMERA, Manifest.permission.CAMERA);
    }

    //检查联系人权限
    public static void checkContact(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_CONTACTS, getContactsGroup());
    }

    //检查位置权限
    public static void checkLocation(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_LOCATION, getLocationGroup());
    }

    //检查录音权限
    public static void checkMicroPhone(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_MICROPHONE, Manifest.permission.RECORD_AUDIO);
    }

    //检查电话权限
    public static void checkPhone(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_PHONE, Manifest.permission.READ_PHONE_STATE);
    }

    //检查感应器权限
    public static void checkSensor(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_SENSORS, Manifest.permission.BODY_SENSORS);
    }

    //检查读短信权限
    public static void checkSMSReadPermissson(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_SMS_READ, Manifest.permission.READ_SMS);
    }

    //检查发短信权限
    public static void checkSMSSendPermissson(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_SMS_SEND, Manifest.permission.SEND_SMS);
    }

    //检查读写权限
    public static void checkStoragePermissson(Object object, PermissionCallbacks callback) {
        requestPermissions(object, callback, Permissions.PERMISSION_STORAGE, getStorageGroup());
    }

    //检查照相和麦克风和感应器权限
    public static void checkCameraAndMIC(Context context, PermissionCallbacks callback) {
        String[] perms = null;
        boolean isHasCamera = hasPermissions(Manifest.permission.CAMERA);
        boolean isHasMic = hasPermissions(Manifest.permission.RECORD_AUDIO);

        List<String> list = new ArrayList<>();

        if (!isHasCamera) {
            list.add(Manifest.permission.CAMERA);
        }
        if (!isHasMic) {
            list.add(Manifest.permission.RECORD_AUDIO);
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }

        requestPermissions(context, callback, Permissions.PERMISSION_MIC_CAMERA, perms);
    }


    //程序启动需要检查的权限
    public static void checkLaunch(Activity activity, PermissionCallbacks callback) {
        requestPermissions(activity, callback, Permissions.PERMISSION_LAUNCH, getLaunchGroup());
    }

    //是否有运行时必要的权限
    public static boolean isHasLaunchPermission() {
        return hasPermissions(getLaunchGroup());
    }


    private static String[] getLaunchGroup() {
        String[] perms = null;
        boolean isHasPhone = hasPermissions(Manifest.permission.READ_PHONE_STATE);
        boolean isHasReadStorage = hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean isHasWriteStorage = hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> list = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isHasPhone) {
                list.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (!isHasReadStorage) {
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!isHasWriteStorage) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            if (!isHasPhone) {
                list.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (!isHasWriteStorage) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }
        return perms;
    }

    private static String[] getStorageGroup() {
        String[] perms = null;
        boolean isHasReadStorage = hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean isHasWriteStorage = hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> list = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isHasReadStorage) {
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!isHasWriteStorage) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else {
            if (!isHasWriteStorage) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }
        return perms;
    }


    private static String[] getLocationGroup() {
        String[] perms = null;
        boolean isHasFine = hasPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean isHasCoarse = hasPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> list = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isHasFine) {
                list.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (!isHasCoarse) {
                list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        } else {
            if (!isHasFine) {
                list.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }
        return perms;
    }

    private static String[] getCallendarGroup() {
        String[] perms = null;
        boolean isHasRead = hasPermissions(Manifest.permission.READ_CALENDAR);
        boolean isHasWrite = hasPermissions(Manifest.permission.WRITE_CALENDAR);

        List<String> list = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isHasRead) {
                list.add(Manifest.permission.READ_CALENDAR);
            }
            if (!isHasWrite) {
                list.add(Manifest.permission.WRITE_CALENDAR);
            }
        } else {
            if (!isHasRead) {
                list.add(Manifest.permission.READ_CALENDAR);
            }
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }
        return perms;
    }

    private static String[] getContactsGroup() {
        String[] perms = null;
        boolean isHasRead = hasPermissions(Manifest.permission.READ_CONTACTS);
        boolean isHasWrite = hasPermissions(Manifest.permission.WRITE_CONTACTS);
        boolean isHasGet = hasPermissions(Manifest.permission.GET_ACCOUNTS);

        List<String> list = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isHasRead) {
                list.add(Manifest.permission.READ_CONTACTS);
            }
            if (!isHasWrite) {
                list.add(Manifest.permission.WRITE_CONTACTS);
            }
            if (!isHasGet) {
                list.add(Manifest.permission.GET_ACCOUNTS);
            }
        } else {
            if (!isHasRead) {
                list.add(Manifest.permission.READ_CONTACTS);
            }
        }

        perms = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            perms[i] = list.get(i);
        }
        return perms;
    }

    //权限回调
    public static void onRequestPermissionsResult(Context context, int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult:" + requestCode);

        isSysPerDialogShowing = false;
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        ArrayList<String> permissionStillNotGet = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
                Log.v(TAG, "requestCode PERMISSION_GRANTED: " + perm);
            } else {
                denied.add(perm);
                Log.v(TAG, "equestCode PERMISSION_DENIED: " + perm);
            }
            if (!hasPermissions(permissions[i])) {
                permissionStillNotGet.add(perm);
            }
        }

        if (!granted.isEmpty() && denied.isEmpty() && permissionStillNotGet.isEmpty()) {
            if (mPermissionCallBack != null) {
                mPermissionCallBack.onPermissionsGranted(requestCode, granted);
                Log.v(TAG, "blued permission Granted because of agree, requestCode:" + requestCode);
                mPermissionCallBack = null;
            }
        }

        if (!denied.isEmpty() || !permissionStillNotGet.isEmpty()) {
            ArrayList<String> permissionRequest = new ArrayList<>();
            permissionRequest.addAll(denied);

            for (String per : permissionStillNotGet) {//小米手机特别处理，二逼的小米
                boolean isAdd = true;
                for (String perDenied : denied) {
                    if (TextUtils.equals(per, perDenied)) {
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    permissionRequest.add(per);
                    Log.v(TAG, "blued permission permissionStillNotGet: " + per);
                }
            }
        }

    }

    /**
     * 请求权限
     *
     * @param object      当无法获得activity对象时，可以传context对象。其他对象暂不支持
     * @param callback    可以为null
     * @param requestCode 请求码
     * @param perms       权限列表
     */
    public synchronized static void requestPermissions(final Object object, PermissionCallbacks callback, final int requestCode, final String... perms) {
        if (hasPermissions(perms)) {
            Log.v(TAG, "blued permission has Permissions");
            if (callback != null) {
                callback.onPermissionsGranted(requestCode, Arrays.asList(perms));
            }
            return;
        }
        if (callback != null) {
            mPermissionCallBack = callback;
        }
        if (checkCallingObjectSuitability(object)) {
            executePermissionsRequest(object, perms, requestCode);
            isSysPerDialogShowing = true;
        } else if (object instanceof Context) {//借助完全透明activity实现回调
            Bundle bundle = new Bundle();
            bundle.putInt(PermissionActivity.ACTION, requestCode);
            Intent intent = new Intent((Context) object, PermissionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            ((Context) object).startActivity(intent);
        }
    }

    //检查权限是否都打开了
    public static boolean hasPermissions(String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(AppUtils.getApplication(), perm) ==
                    PackageManager.PERMISSION_GRANTED);

            if (hasPerm && isMiPhone()) {//小米手机特别处理，二逼的小米
                hasPerm = PermissionChecker.checkPermission(AppUtils.getApplication(), perm, android.os.Process.myPid(), android.os.Process.myUid(), AppUtils.getApplication().getPackageName())
                        == PackageManager.PERMISSION_GRANTED;
            }
            Log.i(TAG, perm + ": " + hasPerm);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkCallingObjectSuitability(Object object) {
        if (object == null) {
            return false;
        }
        // Make sure Object is an Activity or Fragment
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                return false;
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return false;
        } else {
            return false;
        }
    }


    static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        if (!checkCallingObjectSuitability(object)) {
            return;
        }
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            Log.i(TAG, "blued permission not support for fragment, requestCode:" + requestCode);
        }
    }

    //检查是不是有些权限已经设置了不再提醒
    public static boolean somePermissionPermanentlyDenied(Object object, List<String> deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean permissionPermanentlyDenied(Object object, String deniedPermission) {
        return !shouldShowRequestPermissionRationale(object, deniedPermission);
    }

    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    //是不是小米手机
    public static boolean isMiPhone() {
        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return true;
        } else {
            return false;
        }
    }

}
