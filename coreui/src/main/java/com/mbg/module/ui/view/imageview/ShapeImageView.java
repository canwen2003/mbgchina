package com.mbg.module.ui.view.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.mbg.module.ui.R;
import com.mbg.module.ui.view.drawable.DrawableCreator;

public class ShapeImageView extends RelativeLayout {
   private final ShapeableImageView mImageView;
   private Float mCornerSize;
   private Float mCornerSizePercent;
   private final DrawableCreator.Builder mBuilder=new DrawableCreator.Builder();


    public ShapeImageView(Context context) {
        super(context);
        mImageView= new ShapeableImageView(context);
        setGravity(Gravity.CENTER);
        addView(mImageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init(context,null);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
        mImageView=new ShapeableImageView(context,attrs);
        addView(mImageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init(context,attrs);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(Gravity.CENTER);
        mImageView=new ShapeableImageView(context,attrs,defStyle);
        addView(mImageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (attrs!=null) {
            TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MaterialShape);
            if (styledAttributes!=null) {
                int shapeAppearanceResId = styledAttributes.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
                TypedArray a = context.obtainStyledAttributes(shapeAppearanceResId, R.styleable.ShapeAppearance);
                if (a != null) {
                    CornerSize cornerSize = getCornerSize(a, R.styleable.ShapeAppearance_cornerSize);
                    if (cornerSize!=null) {
                        if (cornerSize instanceof AbsoluteCornerSize) {
                            mCornerSize = ((AbsoluteCornerSize) cornerSize).getCornerSize();
                        } else if (cornerSize instanceof RelativeCornerSize) {
                            mCornerSizePercent = ((RelativeCornerSize) cornerSize).getRelativePercent();
                        }
                    }
                    a.recycle();
                }
                styledAttributes.recycle();
            }

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView);
            if (ta!=null) {
                int start = ta.getColor(R.styleable.ShapeImageView_sv_stroke_start_color, Color.TRANSPARENT);
                int middle = ta.getColor(R.styleable.ShapeImageView_sv_stroke_middle_color, start);
                int end = ta.getColor(R.styleable.ShapeImageView_sv_stroke_end_color, start);
                int strokeWidth = ta.getDimensionPixelSize(R.styleable.ShapeImageView_sv_stroke_width, 0);
                int corners = ta.getDimensionPixelSize(R.styleable.ShapeImageView_sv_stroke_corner_radius, -1);
                int angle = ta.getInt(R.styleable.ShapeImageView_sv_stroke_angle, 0);

                if (corners >= 0) {
                    ShapeAppearanceModel shapeAppearanceModel = mImageView.getShapeAppearanceModel();
                    mImageView.setShapeAppearanceModel(shapeAppearanceModel.withCornerSize(corners));
                    mCornerSize=(float)corners;
                }

                ta.recycle();



                mImageView.setPadding(strokeWidth,strokeWidth,strokeWidth,strokeWidth);
                mBuilder.setGradientColor(start, middle, end);
                mBuilder.setGradientAngle(angle);
            }


        }
    }

    public ShapeableImageView getImageView(){

        return mImageView;
    }

    private static CornerSize getCornerSize(@NonNull TypedArray a, int index) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return null;
        }

        if (value.type == TypedValue.TYPE_DIMENSION) {
            return new AbsoluteCornerSize(TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
        } else if (value.type == TypedValue.TYPE_FRACTION) {
            return new RelativeCornerSize(value.getFraction(1.0f, 1.0f));
        } else {
            return null;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            if (mCornerSize !=null){
                setBackground(mBuilder.setCornersRadius(mCornerSize).build());
            }else if (mCornerSizePercent !=null){
                setBackground(mBuilder.setCornersRadius(getWidth()* mCornerSizePercent).build());
            }
        }
    }
}
