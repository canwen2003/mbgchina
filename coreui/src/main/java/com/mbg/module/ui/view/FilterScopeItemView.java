package com.mbg.module.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.R;
import com.mbg.module.ui.view.seekBar.OnRangeChangedListener;
import com.mbg.module.ui.view.seekBar.RangeSeekBar;
import com.mbg.module.ui.view.seekBar.SeekBar;


public class FilterScopeItemView extends FrameLayout implements OnRangeChangedListener {

    private TextView mTitle, mScopeContent;
    // 右边icon
    private ImageView mShowScope;
    private LinearLayout mScopeBody;
    private RangeSeekBar mRangeSeekBar;
    private View mVipLimitView;

    // 标题和单位
    private String titleStr, mUnitStr;
    private float mMax;
    private float mMin;
    private int mDefaultStart;
    private int mDefaultEnd;
    private boolean mVipLimit;//是否有VIP权限限制

    private OnItemClickListener itemClickLis;
    private OnValueChangeListener mOnValueChangeListener;

    public FilterScopeItemView(Context context) {
        this(context, null);
    }

    public FilterScopeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterScopeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_filter_scope_item_view, this);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FilterScopeItemView);
            int titleResId = a.getResourceId(R.styleable.FilterScopeItemView_item_title_text, 0);
            String titleContent = a.getString(R.styleable.FilterScopeItemView_item_title_text);
            int unitResId = a.getResourceId(R.styleable.FilterScopeItemView_item_scopeUnit_text,0);
            String unitContent = a.getString(R.styleable.FilterScopeItemView_item_scopeUnit_text);

            if (titleResId != 0) {
                titleStr = context.getString(titleResId);
            } else {
                titleStr = titleContent;
            }
            if (unitResId != 0) {
                mUnitStr = context.getString(unitResId);
            } else {
                mUnitStr = unitContent;
            }
            mMax=a.getFloat(R.styleable.FilterScopeItemView_item_max,0.0f);
            mMin=a.getFloat(R.styleable.FilterScopeItemView_item_min,0.0f);
            mVipLimit=a.getBoolean(R.styleable.FilterScopeItemView_item_vip_limit,false);

            a.recycle();
        }

        mTitle = rootView.findViewById(R.id.tv_title);
        mScopeContent = rootView.findViewById(R.id.tv_scope_content);
        mShowScope = rootView.findViewById(R.id.iv_show_scope);
        mShowScope.setSelected(false);
        mScopeBody = rootView.findViewById(R.id.ll_scope_body);
        mRangeSeekBar=rootView.findViewById(R.id.seek_bar_view);
        mRangeSeekBar.setOnRangeChangedListener(this);
        mRangeSeekBar.setRange(mMin,mMax);
        mVipLimitView=rootView.findViewById(R.id.vip_limit_view);

        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean click = true;
                if (itemClickLis != null && itemClickLis.onClick()) {
                    click = false;
                }
                if (click) {
                    if (mShowScope.isSelected()) {
                        mShowScope.setSelected(false);
                        hideScopeBody();
                    } else {
                        mShowScope.setSelected(true);
                        showScopeBody();
                    }
                }
            }
        });


        if (!TextUtils.isEmpty(titleStr)) {
            mTitle.setText(titleStr);
        }

        mScopeBody.setVisibility(GONE);
        setVipLimit(mVipLimit);

    }

    public void setVipLimit(boolean vipLimit){
        this.mVipLimit=vipLimit;
        post(()->{
            LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)mRangeSeekBar.getLayoutParams();
            if (mVipLimit){
                mVipLimitView.setVisibility(VISIBLE);
                mVipLimitView.getLayoutParams().width=(int)(getWidth()*0.7f);
                layoutParams.width=(int)(getWidth()*0.3f)+UiUtils.dip2px(getContext(),15);
                layoutParams.leftMargin= -UiUtils.dip2px(getContext(),15);
            }else {
                mVipLimitView.setVisibility(GONE);
                layoutParams.width=getWidth();
                layoutParams.leftMargin=0;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickLis = listener;
    }


    private void hideScopeBody() {
        mScopeBody.setVisibility(GONE);
    }

    private void showScopeBody() {
        mScopeBody.setVisibility(VISIBLE);
    }



    public void setTitle(String title) {
        this.titleStr = title;
        if (!TextUtils.isEmpty(titleStr)) {
            mTitle.setText(titleStr);
        }
    }


    @Override
    public void onRangeChanged(RangeSeekBar rangeSeekBar, float startValue, float endValue, boolean isStart) {
        this.mDefaultStart=(int)startValue;
        this.mDefaultEnd=(int)endValue;
        if (mOnValueChangeListener!=null){
            mOnValueChangeListener.onValueChange((int) startValue,(int)endValue);
        }

        if (mMax==mDefaultEnd){
            mScopeContent.setText(mDefaultStart + " - " + mDefaultEnd + "+ " + mUnitStr);
        }else {
            mScopeContent.setText(mDefaultStart + " - " + mDefaultEnd + " " + mUnitStr);
        }
    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar rangeSeekBar, boolean isStart) {

    }

    @Override
    public void onStopTrackingTouch(RangeSeekBar rangeSeekBar, boolean isStart) {

    }

    /**
     * 需要在调用setScopeArray前调用才能显示单位
     *
     * @param unitStr
     */
    public void setUnit(String unitStr) {
        this.mUnitStr = unitStr;
    }


    public void setIndicatorTextDecimalFormat(SeekBar.OnIndicatorShowListener listener) {
        mRangeSeekBar.setIndicatorTextStringFormat(listener);
    }

    public void setDefaultValue(int defaultLeftValue, int defaultRightValue) {
        this.mDefaultStart=defaultLeftValue;
        this.mDefaultEnd=defaultRightValue;

        mRangeSeekBar.setProgress(mDefaultStart,mDefaultEnd);
        if (mMax==mDefaultEnd){
            mScopeContent.setText(mDefaultStart + " - " + mDefaultEnd + "+ " + mUnitStr);
        }else {
            mScopeContent.setText(mDefaultStart + " - " + mDefaultEnd + " " + mUnitStr);
        }
    }

    public void setMax(float max) {
        this.mMax=max;
        mRangeSeekBar.setRange(mMin,mMax);
    }

    public void setMin(float min) {
        this.mMin=min;
        mRangeSeekBar.setRange(mMin,mMax);
    }

    public void setRange(float min,float max){
        this.mMin=min;
        this.mMax=max;
        mRangeSeekBar.setRange(mMin,mMax);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener){
        this.mOnValueChangeListener=onValueChangeListener;
    }

    public interface OnValueChangeListener {
        void onValueChange(int start, int end);
    }

    public interface OnItemClickListener {
        // 返回true进行拦截
        boolean onClick();
    }
}
