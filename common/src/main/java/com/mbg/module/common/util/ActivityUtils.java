package com.mbg.module.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;



public class ActivityUtils {

    /**
     * 获取Activity 通过View
     * @param view 对象
     * @return
     */
    public static Activity getActivityByView(@NonNull View view) {
        return getActivityByContext(view.getContext());
    }

    /**
     * 获取Activity 通过context
     * @param context 对象
     * @return
     */
    public static Activity getActivityByContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * 检测Activity对象是否存在
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        return !(AppUtils.getApplication().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(AppUtils.getApplication().getPackageManager()) == null ||
                AppUtils.getApplication().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Start the activity.
     *
     * @param clz The activity class.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz) {
        Context context =AppUtils.getApplication();
        startActivity(context, null, context.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param clz     The activity class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        Context context = AppUtils.getApplication();
        startActivity(context, null, context.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = AppUtils.getApplication();
        startActivity(context, null, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param clz      The activity class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(AppUtils.getApplication(), null, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(AppUtils.getApplication(), null, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = AppUtils.getApplication();
        startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, null, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     final View... sharedElements) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras The Bundle of extras to add to this intent.
     * @param pkg    The name of the package.
     * @param cls    The name of the class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(AppUtils.getApplication(), extras, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param extras  The Bundle of extras to add to this intent.
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(AppUtils.getApplication(), extras, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = AppUtils.getApplication();
        startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param extras   The Bundle of extras to add to this intent.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, extras, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(activity, extras, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     final View... sharedElements) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param intent The description of the activity to start.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent) {
        return startActivity(intent, AppUtils.getApplication(), null);
    }

    /**
     * Start the activity.
     *
     * @param intent  The description of the activity to start.
     * @param options Additional options for how the Activity should be started.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @Nullable final Bundle options) {
        return startActivity(intent, AppUtils.getApplication(), options);
    }

    /**
     * Start the activity.
     *
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Context context = AppUtils.getApplication();
        boolean isSuccess = startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (isSuccess) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
            }
        }
        return isSuccess;
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param intent   The description of the activity to start.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent) {
        startActivity(intent, activity, null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param intent   The description of the activity to start.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @Nullable final Bundle options) {
        startActivity(intent, activity, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param intent         The description of the activity to start.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     final View... sharedElements) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode) {
        startActivityForResult(activity, extras, pkg, cls, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, extras, pkg, cls, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, extras, pkg, cls,
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, extras, pkg, cls,
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, activity, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(intent, activity, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param activity       The activity.
     * @param intent         The description of the activity to start.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(fragment, null, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(fragment, null, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param fragment       The fragment.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(fragment, null, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(fragment, null, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(fragment, extras, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(fragment, extras, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param fragment       The fragment.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(fragment, extras, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param fragment    The fragment.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(fragment, extras, AppUtils.getApplication().getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }

    /**
     * Start the activity for result.
     *
     * @param fragment    The fragment.
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode) {
        startActivityForResult(fragment, extras, pkg, cls, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param fragment    The fragment.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(fragment, extras, pkg, cls, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param fragment       The fragment.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(fragment, extras, pkg, cls,
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Fragment fragment,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(fragment, extras, pkg, cls,
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }

    /**
     * Start the activity for result.
     *
     * @param fragment    The fragment.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, fragment, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param fragment    The fragment.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(intent, fragment, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param fragment       The fragment.
     * @param intent         The description of the activity to start.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(intent, fragment,
                requestCode, getOptionsBundle(fragment, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param fragment    The fragment.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Fragment fragment,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(intent, fragment,
                requestCode, getOptionsBundle(fragment, enterAnim, exitAnim));
    }

    /**
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     */
    public static void startActivities(@NonNull final Intent[] intents) {
        startActivities(intents, AppUtils.getApplication(), null);
    }

    /**
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, AppUtils.getApplication(), options);
    }

    /**
     * Start activities.
     *
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        Context context = AppUtils.getApplication();
        startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start activities.
     *
     * @param activity The activity.
     * @param intents  The descriptions of the activities to start.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents) {
        startActivities(intents, activity, null);
    }

    /**
     * Start activities.
     *
     * @param activity The activity.
     * @param intents  The descriptions of the activities to start.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, activity, options);
    }

    /**
     * Start activities.
     *
     * @param activity  The activity.
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Return whether the activity is alive.
     *
     * @param context The context.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityAlive(final Context context) {
        return isActivityAlive(getActivityByContext(context));
    }

    /**
     * Return whether the activity is alive.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }



    /**
     * Finish the activity.
     *
     * @param activity The activity.
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * Finish the activity.
     *
     * @param activity   The activity.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * Finish the activity.
     *
     * @param activity  The activity.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishActivity(@NonNull final Activity activity,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }


    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      @Nullable final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static boolean startActivity(final Intent intent,
                                         final Context context,
                                         final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

    private static boolean isIntentAvailable(final Intent intent) {
        return AppUtils.getApplication()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    private static boolean startActivityForResult(final Activity activity,
                                                  final Bundle extras,
                                                  final String pkg,
                                                  final String cls,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        return startActivityForResult(intent, activity, requestCode, options);
    }

    private static boolean startActivityForResult(final Intent intent,
                                                  final Activity activity,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    private static void startActivities(final Intent[] intents,
                                        final Context context,
                                        @Nullable final Bundle options) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options);
        } else {
            context.startActivities(intents);
        }
    }

    private static boolean startActivityForResult(final Fragment fragment,
                                                  final Bundle extras,
                                                  final String pkg,
                                                  final String cls,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        return startActivityForResult(intent, fragment, requestCode, options);
    }

    private static boolean startActivityForResult(final Intent intent,
                                                  final Fragment fragment,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (fragment.getActivity() == null) {
            Log.e("ActivityUtils", "Fragment " + fragment + " not attached to Activity");
            return false;
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fragment.startActivityForResult(intent, requestCode, options);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    private static Bundle getOptionsBundle(final Fragment fragment,
                                           final int enterAnim,
                                           final int exitAnim) {
        Activity activity = fragment.getActivity();
        if (activity == null) return null;
        return ActivityOptionsCompat.makeCustomAnimation(activity, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Fragment fragment,
                                           final View[] sharedElements) {
        Activity activity = fragment.getActivity();
        if (activity == null) return null;
        return getOptionsBundle(activity, sharedElements);
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        if (sharedElements == null) return null;
        int len = sharedElements.length;
        if (len <= 0) return null;
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }
}
