package com.mbg.module.ui.view.pudding;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.mbg.module.common.util.ThreadUtils;

import java.util.concurrent.ConcurrentHashMap;

public class Pudding implements LifecycleObserver {
    private WindowManager mWindowManager;
    private Choco mChoco;



    // each Activity hold itself pudding list
    private static ConcurrentHashMap<String, Pudding> puddingMapX= new ConcurrentHashMap<>();


    // after create
    public void show() {
        if (mWindowManager!=null){
            try {
                mWindowManager.addView(mChoco,initLayoutParameter());
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        // time over dismiss
        mChoco.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mChoco.enableInfiniteDuration) {
                    return;
                }
                mChoco.hide(false);
            }
        },Choco.DISPLAY_TIME);

        // click dismiss
        mChoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChoco.hide(false);
            }
        });

    }

    private WindowManager.LayoutParams initLayoutParameter() {
        // init layout params
        WindowManager.LayoutParams layoutParams = new  WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0,
                PixelFormat.TRANSPARENT
        );
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.TOP;

        layoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| // 不获取焦点，以便于在弹出的时候 下层界面仍然可以进行操作
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR; // 确保你的内容不会被装饰物(如状态栏)掩盖.
        // popWindow的层级为 TYPE_APPLICATION_PANEL
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;

        return layoutParams;
    }

    // must invoke first
    private void setActivity(AppCompatActivity activity) {

        mChoco = new  Choco(activity);
        mWindowManager= activity.getWindowManager();

        activity.getLifecycle().addObserver(this);
    }


    public  static Pudding create(final AppCompatActivity activity){
         final Pudding pudding = new Pudding();
         pudding.setActivity(activity);
        ThreadUtils.postInUIThread(new Runnable() {
            @Override
            public void run() {
                 final Pudding item=puddingMapX.get(activity.toString());
                 if (item!=null&&item.mChoco !=null&&item.mChoco.isAttachedToWindow){

                     ViewCompat.animate(item.mChoco).alpha(0F).withEndAction(new Runnable() {
                         @Override
                         public void run() {
                             activity.getWindowManager().removeViewImmediate(item.mChoco);
                         }
                     });

                 }
                puddingMapX.put(activity.toString(),pudding);
            }
        });


        return pudding;
    }

    public void setTitle(String title){
        mChoco.setTitle(title);
    }

    public void setTitle(@StringRes int  resId){
        mChoco.setTitle(resId);
    }

    public void setTitleAppearance(@StyleRes int textAppearance ){
        mChoco.setTitleAppearance(textAppearance);
    }

    public void setTitleTypeface(Typeface typeface ){
        mChoco.setTitleTypeface(typeface);
    }

    public void setText(String title){
        mChoco.setText(title);
    }

    public void setText(@StringRes int  resId){
        mChoco.setText(resId);
    }

    public void setTextAppearance(@StyleRes int textAppearance ){
        mChoco.setTextAppearance(textAppearance);
    }

    public void setTextTypeface(Typeface typeface ){
        mChoco.setTextTypeface(typeface);
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param iconId Drawable resource id of the icon to use in the Choco
     */
    public void setIcon(@DrawableRes int iconId) {
        mChoco.setIcon(iconId);
    }

    /**
     * Set the icon color for the Choco
     *
     * @param color Color int
     */
    public void setIconColorFilter(@ColorInt int  color) {
        mChoco.setIconColorFilter(color);
    }

    /**
     * Set the icon color for the Choco
     *
     * @param colorFilter ColorFilter
     */
    public void setIconColorFilter(ColorFilter colorFilter) {
        mChoco.setIconColorFilter(colorFilter);

    }


    /**
     * Set the icon color for the Choco
     *
     * @param color Color int
     * @param mode  PorterDuff.Mode
     */
    public void setIconColorFilter(@ColorInt int color, PorterDuff.Mode mode ) {
        mChoco.setIconColorFilter(color, mode);
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param bitmap Bitmap image of the icon to use in the Choco.
     */
    public void setIcon(Bitmap bitmap) {
        mChoco.setIcon(bitmap);
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param drawable Drawable image of the icon to use in the Choco.
     */
    public void setIcon(Drawable drawable) {
        mChoco.setIcon(drawable);
    }

    /**
     * Set whether to show the icon in the Choco or not
     *
     * @param showIcon True to show the icon, false otherwise
     */
    public void showIcon(boolean showIcon) {
        mChoco.showIcon(showIcon);
    }

    /**
     * Set if the Icon should pulse or not
     *
     * @param shouldPulse True if the icon should be animated
     */
    public void pulseIcon(boolean shouldPulse) {
        mChoco.pulseIcon(shouldPulse);
    }

    /**
     * Enable or disable progress bar
     *
     * @param enableProgress True to enable, False to disable
     */
    public void setEnableProgress(boolean enableProgress) {
        mChoco.setEnableProgress(enableProgress);
    }

    /**
     * Set the Progress bar color from a color resource
     *
     * @param color The color resource
     */
    public void setProgressColorRes(@ColorRes int color) {
        mChoco.setProgressColorRes(color);
    }

    /**
     * Set the Progress bar color from a color resource
     *
     * @param color The color resource
     */
    public void setProgressColorInt(@ColorInt int color) {
        mChoco.setProgressColorInt(color);
    }

    /**
     * Enable or Disable haptic feedback
     *
     * @param enabledVibration True to enable, false to disable
     */
    public void setEnabledVibration(boolean enabledVibration) {
        mChoco.setEnabledVibration(enabledVibration);
    }


    /**
     * Show a button with the given mTitleView, and on click listener
     *
     * @param text The mTitleView to display on the button
     * @param onClick The on click listener
     */
    public void addButton(String text, @StyleRes int style, View.OnClickListener onClick) {
        mChoco.addButton(text,style,onClick);
    }

    /**
     * Set whether to enable swipe to dismiss or not
     */
    public void enableSwipeToDismiss() {
        mChoco.enableSwipeToDismiss();
    }


    public void setBackgroundColor(@ColorInt int color) {
        mChoco.setChocoBackgroundColor(color);
    }

    /**
     * Sets the Choco Background Drawable Resource
     *
     * @param resource The qualified drawable integer
     */
    public void setBackgroundResource(@DrawableRes int resource) {
        mChoco.setChocoBackgroundResource(resource);
    }

    /**
     * Sets the Choco Background Drawable
     *
     * @param drawable The qualified drawable
     */
    public void setBackgroundDrawable(Drawable drawable) {
        mChoco.setChocoBackgroundDrawable(drawable);
    }

    /***
     * 设置是否永久显示
     * @param enableInfiniteDuration 是否
     */
    public void setInfiniteDuration(boolean enableInfiniteDuration) {
        mChoco.enableInfiniteDuration=enableInfiniteDuration;
    }

    public void hide(){
        mChoco.hide(false);
    }

    // window manager must associate activity's lifecycle
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner lifecycleOwner) {
        // this owner is your activity instance
        mChoco.hide(true);
        if (lifecycleOwner!=null) {
            lifecycleOwner.getLifecycle().removeObserver(this);
        }
        if (lifecycleOwner!=null&&puddingMapX!=null&&puddingMapX.containsKey(lifecycleOwner.toString())) {
            puddingMapX.remove(lifecycleOwner.toString());
        }
    }
}
