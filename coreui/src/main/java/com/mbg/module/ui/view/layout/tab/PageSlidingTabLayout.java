package com.mbg.module.ui.view.layout.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mbg.module.ui.R;
import com.mbg.module.ui.view.layout.tab.constant.TabConsts;
import com.mbg.module.ui.view.layout.tab.listener.OnTabSelectListener;
import com.mbg.module.ui.view.layout.tab.utils.UnreadMsgUtils;
import com.mbg.module.ui.view.layout.tab.widget.MsgView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mbg.module.ui.view.layout.tab.constant.TabConsts.TabStyle.STYLE_BLOCK;
import static com.mbg.module.ui.view.layout.tab.constant.TabConsts.TabStyle.STYLE_NORMAL;
import static com.mbg.module.ui.view.layout.tab.constant.TabConsts.TabStyle.STYLE_TEXT;
import static com.mbg.module.ui.view.layout.tab.constant.TabConsts.TabStyle.STYLE_TRIANGLE;

/** 滑动TabLayout,对于ViewPager的依赖性强 */
    public class PageSlidingTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
        private Context mContext;
        private ViewPager mViewPager;
        private ArrayList<String> mTitles;
        private LinearLayout mTabsContainer;
        private int mCurrentTab;
        private float mCurrentPositionOffset;
        private int mTabCount;
        /** 用于绘制显示器 */
        private Rect mIndicatorRect = new Rect();
        private List<Rect> mTabBgRect;
        /** 用于实现滚动居中 */
        private Rect mTabRect = new Rect();
        private GradientDrawable mIndicatorDrawable = new GradientDrawable();
        private List<GradientDrawable> mTabBGDrawable;

        private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Path mTrianglePath = new Path();
        private int mIndicatorStyle = STYLE_NORMAL;
        /** tab */
        private float mTabPadding;
        private boolean mTabSpaceEqual;
        private float mTabWidth;
        private float mTabMarginLeft;
        private float mTabMarginTop;
        private float mTabMarginRight;
        private float mTabMarginBottom;
        private int mTabColor;
        private float mTabCornerRadius;


        /** indicator */
        private int mIndicatorColor;
        private float mIndicatorHeight;
        private float mIndicatorWidth;
        private float mIndicatorCornerRadius;
        private float mIndicatorMarginLeft;
        private float mIndicatorMarginTop;
        private float mIndicatorMarginRight;
        private float mIndicatorMarginBottom;
        private int mIndicatorGravity;
        private boolean mIndicatorWidthEqualTitle;

        /** underline */
        private int mUnderlineColor;
        private float mUnderlineHeight;
        private int mUnderlineGravity;

        /** divider */
        private int mDividerColor;
        private float mDividerWidth;
        private float mDividerPadding;

        /** title */
        private static final int TEXT_BOLD_NONE = 0;
        private static final int TEXT_BOLD_WHEN_SELECT = 1;
        private static final int TEXT_BOLD_BOTH = 2;
        private float mTextSize;
        private float mTextSelectSize;
        private int mTabTextAppearance;
        private int mTabSelectTextAppearance;
        private int mTextGravity=TabConsts.TabTextGravity.CENTER;

        private int mTextSelectColor;
        private int mTextUnSelectColor;


        private int mTextBold;
        private boolean mTextAllCaps;

        private int mLastScrollX;
        private int mHeight;
        private boolean mSnapOnTabClick;

        public PageSlidingTabLayout(Context context) {
            this(context, null, 0);
        }

        public PageSlidingTabLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public PageSlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setFillViewport(true);//设置滚动视图是否可以伸缩其内容以填充视口
            setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
            setClipChildren(false);
            setClipToPadding(false);

            this.mContext = context;
            mTabsContainer = new LinearLayout(context);
            addView(mTabsContainer);

            obtainAttributes(context, attrs);


            //get layout_height
            String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

            if (height.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
            } else if (height.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
            } else {
                int[] systemAttrs = {android.R.attr.layout_height};
                TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
                mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                a.recycle();
            }
        }

        private void obtainAttributes(Context context, AttributeSet attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PageSlidingTabLayout);

            mTabColor = ta.getColor(R.styleable.PageSlidingTabLayout_tab_color, Color.TRANSPARENT);
            mTabCornerRadius = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_corner_radius, 0);

            mIndicatorStyle = ta.getInt(R.styleable.PageSlidingTabLayout_indicator_style, STYLE_NORMAL);
            mIndicatorColor = ta.getColor(R.styleable.PageSlidingTabLayout_indicator_color, Color.parseColor(mIndicatorStyle == STYLE_BLOCK ? "#4B6A87" : "#ffffff"));
            mTabMarginLeft = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_margin_left, dp2px(0));
            mTabMarginTop = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_margin_top, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
            mTabMarginRight = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_margin_right, dp2px(0));
            mTabMarginBottom = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_margin_bottom, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));

            mIndicatorHeight = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_height,
                    dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 4 : (mIndicatorStyle == STYLE_BLOCK ? -1 : 2)));
            mIndicatorWidth = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_width, dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 10 : -1));
            mIndicatorCornerRadius = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_corner_radius, dp2px(mIndicatorStyle == STYLE_BLOCK ? -1 : 0));
            mIndicatorMarginLeft = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_margin_left, dp2px(0));
            mIndicatorMarginTop = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_margin_top, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
            mIndicatorMarginRight = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_margin_right, dp2px(0));
            mIndicatorMarginBottom = ta.getDimension(R.styleable.PageSlidingTabLayout_indicator_margin_bottom, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
            mIndicatorGravity = ta.getInt(R.styleable.PageSlidingTabLayout_indicator_gravity, Gravity.BOTTOM);
            mIndicatorWidthEqualTitle = ta.getBoolean(R.styleable.PageSlidingTabLayout_indicator_width_equal_title, false);

            mUnderlineColor = ta.getColor(R.styleable.PageSlidingTabLayout_underline_color, Color.parseColor("#ffffff"));
            mUnderlineHeight = ta.getDimension(R.styleable.PageSlidingTabLayout_underline_height, dp2px(0));
            mUnderlineGravity = ta.getInt(R.styleable.PageSlidingTabLayout_underline_gravity, Gravity.BOTTOM);

            mDividerColor = ta.getColor(R.styleable.PageSlidingTabLayout_divider_color, Color.parseColor("#ffffff"));
            mDividerWidth = ta.getDimension(R.styleable.PageSlidingTabLayout_divider_width, dp2px(0));
            mDividerPadding = ta.getDimension(R.styleable.PageSlidingTabLayout_divider_padding, dp2px(12));

            mTextSize = ta.getDimension(R.styleable.PageSlidingTabLayout_textSize, sp2px(14));
            mTextSelectSize = ta.getDimension(R.styleable.PageSlidingTabLayout_textSelectSize, mTextSize);
            mTabTextAppearance=ta.getResourceId(R.styleable.PageSlidingTabLayout_tabTextAppearance,0);
            mTabSelectTextAppearance =ta.getResourceId(R.styleable.PageSlidingTabLayout_tabSelectTextAppearance,mTabTextAppearance);
            mTextGravity=ta.getInt(R.styleable.PageSlidingTabLayout_textGravity,TabConsts.TabTextGravity.CENTER);

            mTextSelectColor = ta.getColor(R.styleable.PageSlidingTabLayout_textSelectColor, Color.parseColor("#ffffff"));
            mTextUnSelectColor = ta.getColor(R.styleable.PageSlidingTabLayout_textUnSelectColor, Color.parseColor("#AAffffff"));
            mTextGravity=ta.getInt(R.styleable.PageSlidingTabLayout_textGravity,TabConsts.TabTextGravity.CENTER);
            mTextBold = ta.getInt(R.styleable.PageSlidingTabLayout_textBold, TEXT_BOLD_NONE);
            mTextAllCaps = ta.getBoolean(R.styleable.PageSlidingTabLayout_textAllCaps, false);

            mTabSpaceEqual = ta.getBoolean(R.styleable.PageSlidingTabLayout_tab_space_equal, false);
            mTabWidth = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_width, dp2px(-1));
            mTabPadding = ta.getDimension(R.styleable.PageSlidingTabLayout_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(20));

            ta.recycle();
        }


        /** 关联ViewPager */
        public void setViewPager(@NonNull ViewPager vp) {
            if (vp.getAdapter() == null) {
                throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
            }

            this.mViewPager = vp;

            this.mViewPager.removeOnPageChangeListener(this);
            this.mViewPager.addOnPageChangeListener(this);
            notifyDataSetChanged();
        }

        /** 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况 */
        public void setViewPager(@NonNull ViewPager vp, @NonNull String[] titles) {
            if (vp.getAdapter() == null) {
                throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
            }

            if (titles.length == 0) {
                throw new IllegalStateException("Titles can not be EMPTY !");
            }

            if (titles.length != vp.getAdapter().getCount()) {
                throw new IllegalStateException("Titles length must be the same as the page count !");
            }

            this.mViewPager = vp;
            mTitles = new ArrayList<>();
            Collections.addAll(mTitles, titles);

            this.mViewPager.removeOnPageChangeListener(this);
            this.mViewPager.addOnPageChangeListener(this);
            mTabBGDrawable=null;
            notifyDataSetChanged();
        }

        /** 关联ViewPager,用于连适配器都不想自己实例化的情况 */
        public void setViewPager(@NonNull ViewPager vp, @NonNull String[] titles, @NonNull FragmentManager fm, @NonNull ArrayList<Fragment> fragments) {

            if (titles.length == 0) {
                throw new IllegalStateException("Titles can not be EMPTY !");
            }

            this.mViewPager = vp;
            this.mViewPager.setAdapter(new InnerPagerAdapter(fm, fragments, titles));

            this.mViewPager.removeOnPageChangeListener(this);
            this.mViewPager.addOnPageChangeListener(this);
            mTabBGDrawable=null;
            notifyDataSetChanged();
        }

        /** 更新数据 */
        public void notifyDataSetChanged() {
            mTabsContainer.removeAllViews();
            this.mTabCount = mTitles == null ? mViewPager.getAdapter().getCount() : mTitles.size();
            View tabView;
            for (int i = 0; i < mTabCount; i++) {
                tabView = View.inflate(mContext, R.layout.view_layout_tab, null);
                CharSequence pageTitle = mTitles == null ? mViewPager.getAdapter().getPageTitle(i) : mTitles.get(i);
                addTab(i, pageTitle.toString(), tabView);
            }

            updateTabStyles();
        }

        public void addNewTab(String title) {
            View tabView = View.inflate(mContext, R.layout.view_layout_tab, null);
            if (mTitles != null) {
                mTitles.add(title);
            }

            CharSequence pageTitle = mTitles == null ? mViewPager.getAdapter().getPageTitle(mTabCount) : mTitles.get(mTabCount);
            addTab(mTabCount, pageTitle.toString(), tabView);
            this.mTabCount = mTitles == null ? mViewPager.getAdapter().getCount() : mTitles.size();

            updateTabStyles();
        }

        /** 创建并添加tab */
        private void addTab(final int position, String title, View tabView) {
            final TextView tv_tab_title =  tabView.findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {
                if (title != null) {
                    tv_tab_title.setText(title);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_tab_title.getLayoutParams();
                    lp.leftMargin = (int) mTabMarginLeft;
                    lp.rightMargin=(int) mTabMarginRight;
                    lp.topMargin=(int) mTabMarginTop;
                    lp.bottomMargin=(int) mTabMarginBottom;
                    if(mTextGravity==TabConsts.TabTextGravity.TOP){
                        tv_tab_title.setGravity(Gravity.TOP);
                    }else if (mTextGravity==TabConsts.TabTextGravity.CENTER){
                        tv_tab_title.setGravity(Gravity.CENTER);
                    }else {
                        tv_tab_title.setGravity(Gravity.BOTTOM);
                    }

                    tv_tab_title.setLayoutParams(lp);
                    tv_tab_title.post(new Runnable() {
                        @Override
                        public void run() {
                            initTabBackGround();
                            PageSlidingTabLayout.this.invalidate();
                        }
                    });

                }
            }

            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = mTabsContainer.indexOfChild(v);
                    if (position != -1) {
                        if (mViewPager.getCurrentItem() != position) {
                            if (mSnapOnTabClick) {
                                mViewPager.setCurrentItem(position, false);
                            } else {
                                mViewPager.setCurrentItem(position);
                            }

                            if (mListener != null) {
                                mListener.onTabSelect(position);
                            }
                        } else {
                            if (mListener != null) {
                                mListener.onTabReselect(position);
                            }
                        }
                    }
                }
            });

            /** 每一个Tab的布局参数 */
            LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                    new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (mTabWidth > 0) {
                lp_tab = new LinearLayout.LayoutParams((int) mTabWidth, LayoutParams.MATCH_PARENT);
            }

            mTabsContainer.addView(tabView, position, lp_tab);

        }

        private void updateTabStyles() {
            for (int i = 0; i < mTabCount; i++) {
                View v = mTabsContainer.getChildAt(i);
                TextView tv_tab_title =  v.findViewById(R.id.tv_tab_title);
                if (tv_tab_title != null) {
                    boolean select= i == mCurrentTab;
                    tv_tab_title.setTextColor(select ? mTextSelectColor : mTextUnSelectColor);
                    tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, select?mTextSelectSize:mTextSize);
                    tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
                    if (mTabTextAppearance>0&&mTabSelectTextAppearance>0){
                        tv_tab_title.setTextAppearance(getContext(),select?mTabSelectTextAppearance:mTabTextAppearance);
                    }

                    if (mTextAllCaps) {
                        tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
                    }

                    if (mTextBold == TEXT_BOLD_BOTH) {
                        tv_tab_title.getPaint().setFakeBoldText(true);
                    } else if (mTextBold == TEXT_BOLD_NONE) {
                        tv_tab_title.getPaint().setFakeBoldText(false);
                    }
                }

            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /**
             * position:当前View的位置
             * mCurrentPositionOffset:当前View的偏移量比例.[0,1)
             */
            this.mCurrentTab = position;
            this.mCurrentPositionOffset = positionOffset;
            scrollToCurrentTab();
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            updateTabSelection(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        /** HorizontalScrollView滚到当前tab,并且居中显示 */
        private void scrollToCurrentTab() {
            if (mTabCount <= 0) {
                return;
            }

            int offset = (int) (mCurrentPositionOffset * mTabsContainer.getChildAt(mCurrentTab).getWidth());
            /**当前Tab的left+当前Tab的Width乘以positionOffset*/
            int newScrollX = mTabsContainer.getChildAt(mCurrentTab).getLeft() + offset;

            if (mCurrentTab > 0 || offset > 0) {
                /**HorizontalScrollView移动到当前tab,并居中*/
                newScrollX -= getWidth() / 2 - getPaddingLeft();
                calcIndicatorRect();
                newScrollX += ((mTabRect.right - mTabRect.left) / 2);
            }

            if (newScrollX != mLastScrollX) {
                mLastScrollX = newScrollX;
                /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
                 *  x:表示离起始位置的x水平方向的偏移量
                 *  y:表示离起始位置的y垂直方向的偏移量
                 */
                smoothScrollTo(newScrollX, 0);
            }
        }

        private void updateTabSelection(int position) {
            for (int i = 0; i < mTabCount; ++i) {
                View tabView = mTabsContainer.getChildAt(i);
                final boolean isSelect = i == position;
                TextView tab_title =  tabView.findViewById(R.id.tv_tab_title);

                if (tab_title != null) {
                    tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnSelectColor);
                    tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, isSelect?mTextSelectSize:mTextSize);
                    tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
                    if (mTabTextAppearance>0&&mTabSelectTextAppearance>0){
                        tab_title.setTextAppearance(getContext(),isSelect?mTabSelectTextAppearance:mTabTextAppearance);
                    }

                    if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                        tab_title.getPaint().setFakeBoldText(isSelect);
                    }
                }
            }
        }

        private float margin;

        private void calcIndicatorRect() {
            View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
            float left = currentTabView.getLeft();
            float right = currentTabView.getRight();

            //for mIndicatorWidthEqualTitle
            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                TextView tab_title =  currentTabView.findViewById(R.id.tv_tab_title);
                mTextPaint.setTextSize(mTextSize);
                float textWidth = mTextPaint.measureText(tab_title.getText().toString());
                margin = (right - left - textWidth) / 2;
            }

            if (this.mCurrentTab < mTabCount - 1) {
                View nextTabView = mTabsContainer.getChildAt(this.mCurrentTab + 1);
                float nextTabLeft = nextTabView.getLeft();
                float nextTabRight = nextTabView.getRight();

                left = left + mCurrentPositionOffset * (nextTabLeft - left);
                right = right + mCurrentPositionOffset * (nextTabRight - right);

                //for mIndicatorWidthEqualTitle
                if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                    TextView next_tab_title =  nextTabView.findViewById(R.id.tv_tab_title);
                    mTextPaint.setTextSize(mTextSize);
                    float nextTextWidth = mTextPaint.measureText(next_tab_title.getText().toString());
                    float nextMargin = (nextTabRight - nextTabLeft - nextTextWidth) / 2;
                    margin = margin + mCurrentPositionOffset * (nextMargin - margin);
                }
            }

            mIndicatorRect.left = (int) left;
            mIndicatorRect.right = (int) right;
            //for mIndicatorWidthEqualTitle
            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                mIndicatorRect.left = (int) (left + margin - 1);
                mIndicatorRect.right = (int) (right - margin - 1);
            }

            mTabRect.left = (int) left;
            mTabRect.right = (int) right;

            if (mIndicatorWidth >= 0) { //indicatorWidth大于0时,圆角矩形以及三角形
                float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

                if (this.mCurrentTab < mTabCount - 1) {
                    View nextTab = mTabsContainer.getChildAt(this.mCurrentTab + 1);
                    indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2 + nextTab.getWidth() / 2);
                }

                mIndicatorRect.left = (int) indicatorLeft;
                mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
            }
        }

        private void initTabBackGround() {
            if (mIndicatorStyle != STYLE_BLOCK||mTabBGDrawable!=null){
                return;
            }

            mTabBGDrawable=new ArrayList<>(mTabCount);
            mTabBgRect=new ArrayList<>(mTabCount);
            GradientDrawable gradientDrawable;
            Rect tabBgRect;
            for (int i=0;i < mTabCount;i++) {
                View tabView = mTabsContainer.getChildAt(i);
                tabBgRect=new Rect();
                tabBgRect.left = tabView.getLeft();
                tabBgRect.right = tabView.getRight();
                gradientDrawable=new GradientDrawable();

                mTabBgRect.add(tabBgRect);
                mTabBGDrawable.add(gradientDrawable);
            }
        }

        private void drawTabBackGround(Canvas canvas) {
            if (mIndicatorStyle != STYLE_BLOCK){
                return;
            }

            if (mTabBGDrawable==null) {
                initTabBackGround();
            }

            if (mIndicatorHeight > 0) {
                int paddingLeft = getPaddingLeft();

                for (int i=0;i < mTabCount;i++) {
                    GradientDrawable gradientDrawable=mTabBGDrawable.get(i);
                    Rect tabBgRect=mTabBgRect.get(i);

                    if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                        mIndicatorCornerRadius = mIndicatorHeight / 2;
                    }

                    gradientDrawable.setColor(mTabColor);
                    gradientDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + tabBgRect.left,
                            (int) mIndicatorMarginTop, (int) (paddingLeft + tabBgRect.right - mIndicatorMarginRight),
                            (int) (mIndicatorMarginTop + mIndicatorHeight));
                    gradientDrawable.setCornerRadius(mTabCornerRadius);
                    gradientDrawable.draw(canvas);
                }

            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (isInEditMode() || mTabCount <= 0) {
                return;
            }

            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            // draw divider
            if (mDividerWidth > 0) {
                mDividerPaint.setStrokeWidth(mDividerWidth);
                mDividerPaint.setColor(mDividerColor);
                for (int i = 0; i < mTabCount - 1; i++) {
                    View tab = mTabsContainer.getChildAt(i);
                    canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
                }
            }

            // draw underline
            if (mUnderlineHeight > 0) {
                mRectPaint.setColor(mUnderlineColor);
                if (mUnderlineGravity == Gravity.BOTTOM) {
                    canvas.drawRect(paddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + paddingLeft, height, mRectPaint);
                } else {
                    canvas.drawRect(paddingLeft, 0, mTabsContainer.getWidth() + paddingLeft, mUnderlineHeight, mRectPaint);
                }
            }

            //draw indicator line

            calcIndicatorRect();
            if (mIndicatorStyle == STYLE_TRIANGLE) {
                if (mIndicatorHeight > 0) {
                    mTrianglePaint.setColor(mIndicatorColor);
                    mTrianglePath.reset();
                    mTrianglePath.moveTo(paddingLeft + mIndicatorRect.left, height);
                    mTrianglePath.lineTo(paddingLeft + mIndicatorRect.left / 2 + mIndicatorRect.right / 2, height - mIndicatorHeight);
                    mTrianglePath.lineTo(paddingLeft + mIndicatorRect.right, height);
                    mTrianglePath.close();
                    canvas.drawPath(mTrianglePath, mTrianglePaint);
                }
            } else if (mIndicatorStyle == STYLE_BLOCK) {
                drawTabBackGround(canvas);
                if (mIndicatorHeight < 0) {
                    mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
                }

                if (mIndicatorHeight > 0) {
                    if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                        mIndicatorCornerRadius = mIndicatorHeight / 2;
                    }

                    mIndicatorDrawable.setColor(mIndicatorColor);
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                            (int) (mIndicatorMarginTop + mIndicatorHeight));
                    mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                    mIndicatorDrawable.draw(canvas);
                }
            }else if (mIndicatorStyle == STYLE_TEXT){

            } else {
                if (mIndicatorHeight > 0) {
                    mIndicatorDrawable.setColor(mIndicatorColor);

                    if (mIndicatorGravity == Gravity.BOTTOM) {
                        mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                                height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                                paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                                height - (int) mIndicatorMarginBottom);
                    } else {
                        mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                                (int) mIndicatorMarginTop,
                                paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                                (int) mIndicatorHeight + (int) mIndicatorMarginTop);
                    }
                    mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                    mIndicatorDrawable.draw(canvas);
                }
            }
        }

        //setter and getter
        public void setCurrentTab(int currentTab) {
            this.mCurrentTab = currentTab;
            mViewPager.setCurrentItem(currentTab);

        }

        public void setCurrentTab(int currentTab, boolean smoothScroll) {
            this.mCurrentTab = currentTab;
            mViewPager.setCurrentItem(currentTab, smoothScroll);
        }

        public void setIndicatorStyle(int indicatorStyle) {
            this.mIndicatorStyle = indicatorStyle;
            invalidate();
        }

        public void setTabPadding(float tabPadding) {
            this.mTabPadding = dp2px(tabPadding);
            updateTabStyles();
        }

        public void setTabSpaceEqual(boolean tabSpaceEqual) {
            this.mTabSpaceEqual = tabSpaceEqual;
            updateTabStyles();
        }

        public void setTabWidth(float tabWidth) {
            this.mTabWidth = dp2px(tabWidth);
            updateTabStyles();
        }

        public void setIndicatorColor(int indicatorColor) {
            this.mIndicatorColor = indicatorColor;
            invalidate();
        }

        public void setIndicatorHeight(float indicatorHeight) {
            this.mIndicatorHeight = dp2px(indicatorHeight);
            invalidate();
        }

        public void setIndicatorWidth(float indicatorWidth) {
            this.mIndicatorWidth = dp2px(indicatorWidth);
            invalidate();
        }

        public void setIndicatorCornerRadius(float indicatorCornerRadius) {
            this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
            invalidate();
        }

        public void setIndicatorGravity(int indicatorGravity) {
            this.mIndicatorGravity = indicatorGravity;
            invalidate();
        }

        public void setIndicatorMargin(float indicatorMarginLeft, float indicatorMarginTop,
                                       float indicatorMarginRight, float indicatorMarginBottom) {
            this.mIndicatorMarginLeft = dp2px(indicatorMarginLeft);
            this.mIndicatorMarginTop = dp2px(indicatorMarginTop);
            this.mIndicatorMarginRight = dp2px(indicatorMarginRight);
            this.mIndicatorMarginBottom = dp2px(indicatorMarginBottom);
            invalidate();
        }

        public void setIndicatorWidthEqualTitle(boolean indicatorWidthEqualTitle) {
            this.mIndicatorWidthEqualTitle = indicatorWidthEqualTitle;
            invalidate();
        }

        public void setUnderlineColor(int underlineColor) {
            this.mUnderlineColor = underlineColor;
            invalidate();
        }

        public void setUnderlineHeight(float underlineHeight) {
            this.mUnderlineHeight = dp2px(underlineHeight);
            invalidate();
        }

        public void setUnderlineGravity(int underlineGravity) {
            this.mUnderlineGravity = underlineGravity;
            invalidate();
        }

        public void setDividerColor(int dividerColor) {
            this.mDividerColor = dividerColor;
            invalidate();
        }

        public void setDividerWidth(float dividerWidth) {
            this.mDividerWidth = dp2px(dividerWidth);
            invalidate();
        }

        public void setDividerPadding(float dividerPadding) {
            this.mDividerPadding = dp2px(dividerPadding);
            invalidate();
        }

        public void setTextsize(float textsize) {
            this.mTextSize = sp2px(textsize);
            updateTabStyles();
        }

        public void setTextSelectColor(int textSelectColor) {
            this.mTextSelectColor = textSelectColor;
            updateTabStyles();
        }

        public void setTextUnselectColor(int textUnselectColor) {
            this.mTextUnSelectColor = textUnselectColor;
            updateTabStyles();
        }

        public void setTextBold(int textBold) {
            this.mTextBold = textBold;
            updateTabStyles();
        }

        public void setTextAllCaps(boolean textAllCaps) {
            this.mTextAllCaps = textAllCaps;
            updateTabStyles();
        }

        public void setSnapOnTabClick(boolean snapOnTabClick) {
            mSnapOnTabClick = snapOnTabClick;
        }


        public int getTabCount() {
            return mTabCount;
        }

        public int getCurrentTab() {
            return mCurrentTab;
        }

        public int getIndicatorStyle() {
            return mIndicatorStyle;
        }

        public float getTabPadding() {
            return mTabPadding;
        }

        public boolean isTabSpaceEqual() {
            return mTabSpaceEqual;
        }

        public float getTabWidth() {
            return mTabWidth;
        }

        public int getIndicatorColor() {
            return mIndicatorColor;
        }

        public float getIndicatorHeight() {
            return mIndicatorHeight;
        }

        public float getIndicatorWidth() {
            return mIndicatorWidth;
        }

        public float getIndicatorCornerRadius() {
            return mIndicatorCornerRadius;
        }

        public float getIndicatorMarginLeft() {
            return mIndicatorMarginLeft;
        }

        public float getIndicatorMarginTop() {
            return mIndicatorMarginTop;
        }

        public float getIndicatorMarginRight() {
            return mIndicatorMarginRight;
        }

        public float getIndicatorMarginBottom() {
            return mIndicatorMarginBottom;
        }

        public int getUnderlineColor() {
            return mUnderlineColor;
        }

        public float getUnderlineHeight() {
            return mUnderlineHeight;
        }

        public int getDividerColor() {
            return mDividerColor;
        }

        public float getDividerWidth() {
            return mDividerWidth;
        }

        public float getDividerPadding() {
            return mDividerPadding;
        }

        public float getTextsize() {
            return mTextSize;
        }

        public int getTextSelectColor() {
            return mTextSelectColor;
        }

        public int getTextUnselectColor() {
            return mTextUnSelectColor;
        }

        public int getTextBold() {
            return mTextBold;
        }

        public boolean isTextAllCaps() {
            return mTextAllCaps;
        }

        public TextView getTitleView(int tab) {
            View tabView = mTabsContainer.getChildAt(tab);
            TextView tv_tab_title =  tabView.findViewById(R.id.tv_tab_title);
            return tv_tab_title;
        }

        //setter and getter

        // show MsgTipView
        private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private SparseArray<Boolean> mInitSetMap = new SparseArray<>();

        /**
         * 显示未读消息
         *
         * @param position 显示tab位置
         * @param num      num小于等于0显示红点,num大于0显示数字
         */
        public void showMsg(int position, int num) {
            if (position >= mTabCount) {
                position = mTabCount - 1;
            }

            View tabView = mTabsContainer.getChildAt(position);
            MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
            if (tipView != null) {
                UnreadMsgUtils.show(tipView, num);

                if (mInitSetMap.get(position) != null && mInitSetMap.get(position)) {
                    return;
                }

                setMsgMargin(position, 4, 2);
                mInitSetMap.put(position, true);
            }
        }

        /**
         * 显示未读红点
         *
         * @param position 显示tab位置
         */
        public void showDot(int position) {
            if (position >= mTabCount) {
                position = mTabCount - 1;
            }
            showMsg(position, 0);
        }

        /** 隐藏未读消息 */
        public void hideMsg(int position) {
            if (position >= mTabCount) {
                position = mTabCount - 1;
            }

            View tabView = mTabsContainer.getChildAt(position);
            MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
            if (tipView != null) {
                tipView.setVisibility(View.GONE);
            }
        }

        /** 设置未读消息偏移,原点为文字的右上角.当控件高度固定,消息提示位置易控制,显示效果佳 */
        public void setMsgMargin(int position, float leftPadding, float bottomPadding) {
            if (position >= mTabCount) {
                position = mTabCount - 1;
            }
            View tabView = mTabsContainer.getChildAt(position);
            MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
            if (tipView != null) {
                TextView tv_tab_title =  tabView.findViewById(R.id.tv_tab_title);
                mTextPaint.setTextSize(mTextSize);
                float textWidth = mTextPaint.measureText(tv_tab_title.getText().toString());
                float textHeight = mTextPaint.descent() - mTextPaint.ascent();
                MarginLayoutParams lp = (MarginLayoutParams) tipView.getLayoutParams();
                lp.leftMargin = mTabWidth >= 0 ? (int) (mTabWidth / 2 + textWidth / 2 + dp2px(leftPadding)) : (int) (mTabPadding + textWidth + dp2px(leftPadding));
                lp.topMargin = mHeight > 0 ? (int) (mHeight - textHeight) / 2 - dp2px(bottomPadding) : 0;
                tipView.setLayoutParams(lp);
            }
        }

        /** 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置 */
        public MsgView getMsgView(int position) {
            if (position >= mTabCount) {
                position = mTabCount - 1;
            }
            View tabView = mTabsContainer.getChildAt(position);
            MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
            return tipView;
        }

        private OnTabSelectListener mListener;

        public void setOnTabSelectListener(OnTabSelectListener listener) {
            this.mListener = listener;
        }

        class InnerPagerAdapter extends FragmentPagerAdapter {
            private ArrayList<Fragment> fragments;
            private String[] titles;

            public InnerPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
                super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                this.fragments = fragments;
                this.titles = titles;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
                // super.destroyItem(container, position, object);
            }

            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        }

        @Override
        protected Parcelable onSaveInstanceState() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("instanceState", super.onSaveInstanceState());
            bundle.putInt("mCurrentTab", mCurrentTab);
            return bundle;
        }

        @Override
        protected void onRestoreInstanceState(Parcelable state) {
            if (state instanceof Bundle) {
                Bundle bundle = (Bundle) state;
                mCurrentTab = bundle.getInt("mCurrentTab");
                state = bundle.getParcelable("instanceState");
                if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                    updateTabSelection(mCurrentTab);
                    scrollToCurrentTab();
                }
            }
            super.onRestoreInstanceState(state);
        }

        protected int dp2px(float dp) {
            final float scale = mContext.getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        protected int sp2px(float sp) {
            final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
            return (int) (sp * scale + 0.5f);
        }
    }

