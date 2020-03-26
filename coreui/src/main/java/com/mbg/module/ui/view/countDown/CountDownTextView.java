package com.mbg.module.ui.view.countDown;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CountDownTextView extends AppCompatTextView implements View.OnClickListener, LifecycleObserver {
    private CountDownTimer mCountDownTimer;
    private OnCountDownStartListener mOnCountDownStartListener;
    private OnCountDownTickListener mOnCountDownTickListener;
    private OnCountDownFinishListener mOnCountDownFinishListener;
    /**
     * 普通文本信息
     */
    private String mNormalText;
    private String mCountDownText;
    private OnClickListener mOnClickListener;
    //倒计时期间是否允许点击
    private boolean mClickable = false;
    //是否把时间格式化成时分秒
    private boolean mShowFormatTime = false;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        autoBindLifecycle(context);
    }

    /**
     * 控件自动绑定生命周期,宿主可以是activity或者fragment
     */
    private void autoBindLifecycle(Context context) {
        if (context instanceof AppCompatActivity) {
            // 宿主是activity
            AppCompatActivity activity = (AppCompatActivity) context;
            FragmentManager fm = activity.getSupportFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            if (fragments.size() <= 0) {
                return;
            }
            for (Fragment fragment : fragments) {
                View parent = fragment.getView();
                if (parent != null) {
                    View find = parent.findViewById(getId());
                    if (find == this) {
                        fragment.getLifecycle().addObserver(this);
                        return;
                    }
                }
            }
        }
        // 宿主是fragment
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mOnCountDownStartListener = null;
        mOnCountDownTickListener = null;
        mOnCountDownFinishListener = null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    /**
     * 非倒计时状态文本
     *
     * @param normalText 文本
     */
    @SuppressWarnings("unused")
    public CountDownTextView setNormalText(String normalText) {
        mNormalText = normalText;
        setText(normalText);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mOnCountDownStartListener = null;
        mOnCountDownTickListener = null;
        mOnCountDownFinishListener = null;
        return this;
    }

    @SuppressWarnings("unused")
    public void recycler() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mOnCountDownStartListener = null;
        mOnCountDownTickListener = null;
        mOnCountDownFinishListener = null;
    }

    /**
     * 设置倒计时文本内容
     *
     * @param front  倒计时文本前部分
     * @param latter 倒计时文本后部分
     */
    @SuppressWarnings("unused")
    public CountDownTextView setCountDownText(String front, String latter) {
        mCountDownText = front + "%1$s" + latter;
        return this;
    }

    /**
     * 顺序计时，非倒计时
     *
     * @param second 计时时间秒
     */
    @SuppressWarnings("unused")
    public void startCount(long second) {
        startCount(second, TimeUnit.SECONDS);
    }

    public void startCount(long time, final TimeUnit timeUnit) {
        count(time, timeUnit, false);
    }

    /**
     * 默认按秒倒计时
     *
     * @param second 多少秒
     */
    @SuppressWarnings("unused")
    public void startCountDown(long second) {
        startCountDown(second, TimeUnit.SECONDS);
    }

    public void startCountDown(long time, final TimeUnit timeUnit) {
        count(time,  timeUnit, true);
    }

    /**
     * 是否格式化时间
     *
     * @param formatTime 是否格式化
     */
    @SuppressWarnings("unused")
    public CountDownTextView setShowFormatTime(boolean formatTime) {
        mShowFormatTime = formatTime;
        return this;
    }

    /**
     * 计时方案
     *
     * @param time        计时时长
     * @param timeUnit    时间单位
     * @param isCountDown 是否是倒计时，false正向计时
     */
    private void count(final long time, final TimeUnit timeUnit, final boolean isCountDown) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        setEnabled(mClickable);
        // 转换成毫秒
        final long millisInFuture = timeUnit.toMillis(time) + 500;
        // 时间间隔为1s
        long interval = TimeUnit.MILLISECONDS.convert(1, timeUnit);
        if (mOnCountDownStartListener != null) {
            mOnCountDownStartListener.onStart();
        }
        if (TextUtils.isEmpty(mCountDownText)) {
            mCountDownText = getText().toString();
        }
        mCountDownTimer = new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long count = isCountDown ? millisUntilFinished : (millisInFuture - millisUntilFinished);
                long l = timeUnit.convert(count, TimeUnit.MILLISECONDS);
                String showTime;
                if (mShowFormatTime) {
                    showTime = generateTime(count);
                } else {
                    showTime = String.valueOf(l);
                }
                if (mOnCountDownTickListener != null) {
                    mOnCountDownTickListener.onTick(l, showTime, CountDownTextView.this);
                } else {
                    setText(String.format(mCountDownText, showTime));
                }
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                mCountDownTimer = null;
                if (!TextUtils.isEmpty(mNormalText)) {
                    setText(mNormalText);
                }
                if (mOnCountDownFinishListener != null) {
                    mOnCountDownFinishListener.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 倒计时期间，点击事件是否响应
     *
     * @param clickable 是否响应
     */
    @SuppressWarnings("unused")
    public CountDownTextView setCountDownClickable(boolean clickable) {
        mClickable = clickable;
        return this;
    }

    @SuppressWarnings("unused")
    public CountDownTextView setOnCountDownStartListener(OnCountDownStartListener onCountDownStartListener) {
        mOnCountDownStartListener = onCountDownStartListener;
        return this;
    }

    @SuppressWarnings("unused")
    public CountDownTextView setOnCountDownTickListener(OnCountDownTickListener onCountDownTickListener) {
        mOnCountDownTickListener = onCountDownTickListener;
        return this;
    }

    @SuppressWarnings("unused")
    public CountDownTextView setOnCountDownFinishListener(OnCountDownFinishListener onCountDownFinishListener) {
        mOnCountDownFinishListener = onCountDownFinishListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mCountDownTimer != null && !mClickable) {
            return;
        }
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        super.setOnClickListener(this);
    }

    public interface OnCountDownStartListener {
        /**
         * 开始倒计时
         */
        void onStart();
    }

    public interface OnCountDownTickListener {
        /**
         * 每秒自己刷新textView
         *
         * @param resetOfTime 剩余的时间
         * @param showTime    可以直接使用的显示时间
         * @param textView     控件
         */
        void onTick(long resetOfTime, String showTime, CountDownTextView textView);
    }

    public interface OnCountDownFinishListener {
        void onFinish();
    }

    /**
     * 将毫秒转时分秒
     */

    public String generateTime(long time) {
        String format;
        long totalSeconds = (time / 1000);
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        int hours = (int) ((totalSeconds / 3600) % 24);
        int days = (int) (totalSeconds / (3600 * 24));
        if (days > 0) {
            format = String.format(Locale.getDefault(),"%01d %02d:%02d:%02d", days, hours, minutes, seconds);
        } else if (hours > 0) {
            format = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            format = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        } else {
            format = String.format(Locale.getDefault(),"%2d", seconds);
        }
        return format;
    }
}