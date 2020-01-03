package com.mbg.module.ui.view.layout.round;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.R;

public class RoundViewDelegate {
    private View view;
    private Context context;
    private GradientDrawable gd_background = new GradientDrawable();
    private GradientDrawable gd_background_press = new GradientDrawable();
    private int backgroundColor;
    private int backgroundPressColor;
    private int cornerRadius;
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;
    private int strokeWidth;
    private int strokeColor;
    private int strokePressColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isRippleEnable;
    private float[] radiusArr = new float[8];

    public RoundViewDelegate(View view, Context context, AttributeSet attrs) {
        this.view = view;
        this.context = context;
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        if (ta!=null) {
            backgroundColor = ta.getColor(R.styleable.RoundLayout_backgroundColor, Color.TRANSPARENT);
            backgroundPressColor = ta.getColor(R.styleable.RoundLayout_backgroundPressColor, Integer.MAX_VALUE);
            cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundLayout_cornerRadius, 0);
            strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundLayout_strokeWidth, 0);
            strokeColor = ta.getColor(R.styleable.RoundLayout_strokeColor, Color.TRANSPARENT);
            strokePressColor = ta.getColor(R.styleable.RoundLayout_strokePressColor, Integer.MAX_VALUE);
            isRadiusHalfHeight = ta.getBoolean(R.styleable.RoundLayout_isRadiusHalfHeight, false);
            isWidthHeightEqual = ta.getBoolean(R.styleable.RoundLayout_isWidthHeightEqual, false);
            topLeftRadius = ta.getDimensionPixelSize(R.styleable.RoundLayout_topLeftRadius, 0);
            topRightRadius = ta.getDimensionPixelSize(R.styleable.RoundLayout_topRightRadius, 0);
            bottomLeftRadius = ta.getDimensionPixelSize(R.styleable.RoundLayout_bottomLeftRadius, 0);
            bottomRightRadius = ta.getDimensionPixelSize(R.styleable.RoundLayout_bottomRightRadius, 0);
            isRippleEnable = ta.getBoolean(R.styleable.RoundLayout_isRippleEnable, true);

            ta.recycle();
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBgSelector();
    }

    public void setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        setBgSelector();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = UiUtils.dip2px(context,cornerRadius);
        setBgSelector();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = UiUtils.dip2px(context,strokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBgSelector();
    }

    public void setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public void setTopLeftRadius(int radius) {
        this.topLeftRadius = radius;
        setBgSelector();
    }

    public void setTopRightRadius(int radius) {
        this.topRightRadius = radius;
        setBgSelector();
    }

    public void setBottomLeftRadius(int radius) {
        this.bottomLeftRadius = radius;
        setBgSelector();
    }

    public void setBottomRightRadius(int radius) {
        this.bottomRightRadius = radius;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public int getTopLeftRadius() {
        return topLeftRadius;
    }

    public int getTopRightRadius() {
        return topRightRadius;
    }

    public int getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public int getBottomRightRadius() {
        return bottomRightRadius;
    }


    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);

        if (topLeftRadius > 0 || topRightRadius > 0 || bottomRightRadius > 0 || bottomLeftRadius > 0) {
            /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
            radiusArr[0] = topLeftRadius;
            radiusArr[1] = topLeftRadius;
            radiusArr[2] = topRightRadius;
            radiusArr[3] = topRightRadius;
            radiusArr[4] = bottomRightRadius;
            radiusArr[5] = bottomRightRadius;
            radiusArr[6] = bottomLeftRadius;
            radiusArr[7] = bottomLeftRadius;
            gd.setCornerRadii(radiusArr);
        } else {
            gd.setCornerRadius(cornerRadius);
        }

        gd.setStroke(strokeWidth, strokeColor);
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            setDrawable(gd_background, backgroundColor, strokeColor);
            RippleDrawable rippleDrawable = new RippleDrawable(
                    getPressedColorSelector(backgroundColor, backgroundPressColor), gd_background, null);
            view.setBackground(rippleDrawable);
        } else {
            setDrawable(gd_background, backgroundColor, strokeColor);
            bg.addState(new int[]{-android.R.attr.state_pressed}, gd_background);
            if (backgroundPressColor != Integer.MAX_VALUE || strokePressColor != Integer.MAX_VALUE) {
                setDrawable(gd_background_press, backgroundPressColor == Integer.MAX_VALUE ? backgroundColor : backgroundPressColor,
                        strokePressColor == Integer.MAX_VALUE ? strokeColor : strokePressColor);
                bg.addState(new int[]{android.R.attr.state_pressed}, gd_background_press);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
                view.setBackground(bg);
            } else {
                //noinspection deprecation
                view.setBackgroundDrawable(bg);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated},
                        new int[]{}
                },
                new int[]{
                        pressedColor,
                        pressedColor,
                        pressedColor,
                        normalColor
                }
        );
    }
}
