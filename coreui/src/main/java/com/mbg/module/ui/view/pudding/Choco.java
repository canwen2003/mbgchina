package com.mbg.module.ui.view.pudding;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.ui.R;

import java.util.ArrayList;
import java.util.List;

public class Choco extends FrameLayout {
    private static final String TAG = Choco.class.getSimpleName();
    public static final long DISPLAY_TIME = 3000;
    public static final long ANIMATION_DURATION = 500;
    private static final int MUL = -0x1000000;
    private Context context;
    private View mRootView;
    private TextView mTitleView;
    private TextView mSubText;
    private ImageView mIcon;
    private ProgressBar progress;
    private ViewGroup buttonContainer;
    private ViewGroup mRootBody;


    private ObjectAnimator animEnter;
    private TimeInterpolator animEnterInterceptor = new OvershootInterpolator();
    private boolean enableIconPulse = true;

    public boolean enableInfiniteDuration = false;
    private boolean enableProgress = false;
    private boolean enabledVibration = false;
    private List<Button> buttons = new ArrayList<>();

    private boolean onlyOnce = true;
    public OnShow onShow;
    public OnDismiss onDismiss;
    public boolean isAttachedToWindow;


    public Choco(@NonNull Context context) {
        this(context,null);
    }

    public Choco(@NonNull Context context,@Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Choco(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        mRootView=View.inflate(context, R.layout.padding_choco,this);
        mTitleView =mRootView.findViewById(R.id.tv_title);
        mSubText =mRootView.findViewById(R.id.tv_subText);
        mIcon=mRootView.findViewById(R.id.img_icon);
        progress=mRootView.findViewById(R.id.progress);
        buttonContainer=mRootView.findViewById(R.id.buttonContainer);
        mRootBody=mRootView.findViewById(R.id.warn_root);
    }

    /**
     * 初始化配置，如loading 的显示 与 icon的动画 触摸反馈等
     */
    private void initConfiguration() {
        if (enableIconPulse&&mIcon!=null) {
            mIcon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alerter_pulse));
        }

        if (mIcon!=null) {
            mIcon.setVisibility(enableProgress?GONE:VISIBLE);
        }
        if (progress!=null) {
            progress.setVisibility(enableProgress?VISIBLE:GONE);
        }

        for (int i=0;i< buttons.size();i++){
            buttonContainer.addView(buttons.get(i));
        }


        if (enabledVibration) {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow=true;
        Log.e(TAG, "onAttachedToWindow");
        initConfiguration();
        if (onShow!=null){
            onShow.invoke();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow=false;
        Log.e(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure");

        if (onlyOnce) {
            onlyOnce = false;
            animEnter = ObjectAnimator.ofFloat(this, "translationY", -this.getMeasuredHeight(), -80F);
            animEnter.setInterpolator(animEnterInterceptor);
            animEnter.setDuration(ANIMATION_DURATION);
            animEnter.start();
        }
    }

    public void onShow( OnShow onShow) {
        this.onShow = onShow;
    }

    public void onDismiss(OnDismiss onDismiss) {
        this.onDismiss = onDismiss;
    }

    public void hide(boolean removeNow) {
        if (!isAttachedToWindow) {
            return;
        }
        final WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager==null){
            return;
        }
        if (removeNow) {
            if (isAttachedToWindow) {
                if (onDismiss!=null){
                    onDismiss.invoke();
                }
                windowManager.removeViewImmediate(this);
            }
            return;
        }
        mRootView.setClickable(false);
        Animator anim = ObjectAnimator.ofFloat(this, "translationY", -80F, -this.getMeasuredHeight());
        anim.setInterpolator(new AnticipateOvershootInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();
        ThreadUtils.postInUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAttachedToWindow) {
                    if (onDismiss!=null){
                        onDismiss.invoke();
                    }
                    windowManager.removeViewImmediate(Choco.this);
                }
            }
        },ANIMATION_DURATION);
    }


    public void setChocoBackgroundColor(@ColorInt int color) {
        mRootBody.setBackgroundColor(color);
    }

    /**
     * Sets the Choco Background Drawable Resource
     *
     * @param resource The qualified drawable integer
     */
    public void setChocoBackgroundResource(@DrawableRes int resource) {
        mRootBody.setBackgroundResource(resource);
    }

    /**
     * Sets the Choco Background Drawable
     *
     * @param drawable The qualified drawable
     */
    public void setChocoBackgroundDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRootBody.setBackground(drawable);
        } else {
            mRootBody.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Sets the Title of the Choco
     *
     * @param titleId String resource id of the Choco title
     */
    public void setTitle(@StringRes int titleId) {
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(titleId);
    }

    /**
     * Sets the Title of the Choco
     *
     * @param title String object to be used as the Choco title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        }
    }

    /**
     * Set the Title's mTitleView appearance of the Title
     *
     * @param textAppearance The style resource id
     */
    public void setTitleAppearance(@StyleRes int textAppearance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTitleView.setTextAppearance(textAppearance);
        } else {
            mTitleView.setTextAppearance(mTitleView.getContext(), textAppearance);
        }
    }

    /**
     * Set the Title's typeface
     *
     * @param typeface The typeface to use
     */
    public void setTitleTypeface(Typeface typeface) {
        mTitleView.setTypeface( typeface);
    }

    /**
     * Sets the Text of the Choco
     *
     * @param textId String resource id of the Choco mTitleView
     */
    public void setText(@StringRes int textId) {
        mSubText.setVisibility(View.VISIBLE);
        mSubText.setText(textId);
    }

    /**
     * Set the Text's typeface
     *
     * @param typeface The typeface to use
     */
    public void setTextTypeface(Typeface typeface) {
        mSubText.setTypeface(typeface);
    }

    /**
     * Sets the Text of the Choco
     *
     * @param text String resource id of the Choco mTitleView
     */
    public void setText( String text) {
        if (!TextUtils.isEmpty(text)) {
            mSubText.setVisibility(View.VISIBLE);
            mSubText.setText(text);
        }
    }

    /**
     * Set the Text's mTitleView appearance of the Title
     *
     * @param textAppearance The style resource id
     */
    public void setTextAppearance(@StyleRes  int textAppearance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSubText.setTextAppearance(textAppearance);
        } else {
            mSubText.setTextAppearance(mSubText.getContext(), textAppearance);
        }
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param iconId Drawable resource id of the icon to use in the Choco
     */
    public void setIcon(@DrawableRes int iconId) {
        mIcon.setImageDrawable(AppCompatResources.getDrawable(context, iconId));
    }

    /**
     * Set the icon color for the Choco
     *
     * @param color Color int
     */
    public void setIconColorFilter(@ColorInt int  color) {
        mIcon.setColorFilter(color);
    }

    /**
     * Set the icon color for the Choco
     *
     * @param colorFilter ColorFilter
     */
    public void setIconColorFilter(ColorFilter colorFilter) {
        mIcon.setColorFilter(colorFilter);

    }


    /**
     * Set the icon color for the Choco
     *
     * @param color Color int
     * @param mode  PorterDuff.Mode
     */
    public void setIconColorFilter(@ColorInt int color, PorterDuff.Mode mode ) {
        mIcon.setColorFilter(color, mode);
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param bitmap Bitmap image of the icon to use in the Choco.
     */
    public void setIcon(Bitmap bitmap) {
        mIcon.setImageBitmap(bitmap);
    }

    /**
     * Set the inline icon for the Choco
     *
     * @param drawable Drawable image of the icon to use in the Choco.
     */
    public void setIcon(Drawable drawable) {
        mIcon.setImageDrawable(drawable);
    }

    /**
     * Set whether to show the icon in the Choco or not
     *
     * @param showIcon True to show the icon, false otherwise
     */
    public void showIcon(boolean showIcon) {
        mIcon.setVisibility(showIcon?VISIBLE:GONE);
    }

    /**
     * Set if the Icon should pulse or not
     *
     * @param shouldPulse True if the icon should be animated
     */
    public void pulseIcon(boolean shouldPulse) {
        this.enableIconPulse = shouldPulse;
    }

    /**
     * Enable or disable progress bar
     *
     * @param enableProgress True to enable, False to disable
     */
    public void setEnableProgress(boolean enableProgress) {
        this.enableProgress = enableProgress;
    }

    /**
     * Set the Progress bar color from a color resource
     *
     * @param color The color resource
     */
    public void setProgressColorRes(@ColorRes int color) {
        if (progress!=null){
            Drawable drawable= progress.getProgressDrawable();
            if (drawable!=null){
                drawable.setColorFilter(new LightingColorFilter(MUL, ContextCompat.getColor(context, color)));
            }
        }
    }

    /**
     * Set the Progress bar color from a color resource
     *
     * @param color The color resource
     */
    public void setProgressColorInt(@ColorInt int color) {
        if (progress!=null){
           Drawable drawable= progress.getProgressDrawable();
           if (drawable!=null){
               drawable.setColorFilter(new LightingColorFilter(MUL, color));
           }
        }
    }

    /**
     * Enable or Disable haptic feedback
     *
     * @param enabledVibration True to enable, false to disable
     */
    public void setEnabledVibration(boolean enabledVibration) {
        this.enabledVibration = enabledVibration;
    }


    /**
     * Show a button with the given mTitleView, and on click listener
     *
     * @param text The mTitleView to display on the button
     * @param onClick The on click listener
     */
    public void addButton(String text, @StyleRes int style, View.OnClickListener onClick) {
        Button button=new Button( new ContextThemeWrapper(context,style),null,style);
        button.setText(text);
        button.setOnClickListener(onClick);
        buttons.add(button);
    }

    /**
     * Set whether to enable swipe to dismiss or not
     */
    public void enableSwipeToDismiss() {
        setOnTouchListener(new SwipeDismissTouchListener(this, new SwipeDismissTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss() {
                return true;
            }

            @Override
            public boolean onDismiss(View view) {
                hide(true);
                return true;
            }

            @Override
            public boolean onTouch(View view, Boolean touch) {
                return false;
            }
        }));
    }

    public interface OnShow{
        void invoke();
    }
    public interface OnDismiss{
        void invoke();
    }
}
