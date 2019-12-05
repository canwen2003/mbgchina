package com.mbg.module.ui.view.showmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.mbg.module.ui.R;

public class ShowMoreTextView extends AppCompatTextView {
    private int showingLine = 1;
    private String showMoreText = "More";
    private String showLessText = "Less";
    private String ellipsis_dots = "...";

    private int MAGIC_NUMBER = 5;

    private int showMoreTextColor = Color.RED;
    private int showLessTextColor = Color.RED;

    private String mAllContent;
    public boolean isCollapse = true;
    private boolean isAlreadySet;
    private boolean isShowLessText=true;
    private boolean isShowMoreTextDotEnable=true;


    public ShowMoreTextView(Context context) {
        this(context,null);
    }

    public ShowMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ShowMoreTextView, 0, 0);
        if (typeArray!=null){
            showMoreTextColor=typeArray.getColor(R.styleable.ShowMoreTextView_show_more_text_color,Color.RED);
            if (typeArray.hasValue(R.styleable.ShowMoreTextView_show_more_text)) {
                showMoreText = typeArray.getString(R.styleable.ShowMoreTextView_show_more_text);
            }else {
                showMoreText="More";
            }
            isShowMoreTextDotEnable=typeArray.getBoolean(R.styleable.ShowMoreTextView_show_more_text_dot_enable, true);
            showingLine=typeArray.getInteger(R.styleable.ShowMoreTextView_show_more_line,1);

            showLessTextColor=typeArray.getColor(R.styleable.ShowMoreTextView_show_less_text_color,Color.RED);
            isShowLessText = typeArray.getBoolean(R.styleable.ShowMoreTextView_show_less_text_enable, true);
            if (typeArray.hasValue(R.styleable.ShowMoreTextView_show_less_text)) {
                showLessText = typeArray.getString(R.styleable.ShowMoreTextView_show_less_text);
            }else {
                showMoreText="More";
            }
            typeArray.recycle();
        }

        if (isShowMoreTextDotEnable){
            ellipsis_dots="...";
        }else {
            ellipsis_dots="";
        }
        if (showingLine<1){
            showingLine=1;
        }

        addShowMore();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAllContent = getText().toString();
    }

    private void addShowMore() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                String text = getText().toString();
                if (!isAlreadySet) {
                    mAllContent = getText().toString();
                    isAlreadySet = true;
                }
                String showingText = "";

                if (showingLine >= getLineCount()) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
                int start = 0;
                int end;
                for (int i = 0; i < showingLine; i++) {
                    end = getLayout().getLineEnd(i);
                    showingText += text.substring(start, end);
                    start = end;
                }

                String newText = showingText.substring(0, showingText.length() - (ellipsis_dots.length() + showMoreText.length() + MAGIC_NUMBER));
                newText += ellipsis_dots + showMoreText;

                isCollapse = true;

                setText(newText);

                setShowMoreColoringAndClickable();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
    }

    private void setShowMoreColoringAndClickable() {
        final SpannableString spannableString = new SpannableString(getText());
        spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        ds.setUnderlineText(false);
                                    }

                                    @Override
                                    public void onClick(@Nullable View view) {
                                        setMaxLines(Integer.MAX_VALUE);
                                        setText(mAllContent);
                                        isCollapse = false;
                                        if (isShowLessText) {
                                            showLessButton();
                                        }
                                    }
                                },
                getText().length() - (ellipsis_dots.length() + showMoreText.length()),
                getText().length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(showMoreTextColor),
                getText().length() - showMoreText.length(),
                getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setMovementMethod(LinkMovementMethod.getInstance());
        setText(spannableString, BufferType.SPANNABLE);
    }

    private void showLessButton() {
        String text = getText() + ellipsis_dots + showLessText;
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void updateDrawState(@NonNull TextPaint ds) {
                                        ds.setUnderlineText(false);
                                    }

                                    @Override
                                    public void onClick(@Nullable View view) {
                                        setMaxLines(showingLine);
                                        addShowMore();
                                    }
                                },
                text.length() - (ellipsis_dots.length() + showLessText.length()),
                text.length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(showLessTextColor),
                text.length() - (ellipsis_dots.length() + showLessText.length()),
                text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setMovementMethod(LinkMovementMethod.getInstance());
        setText(spannableString, BufferType.SPANNABLE);
    }


    public void setShowLessTextEnable(boolean enable){
        isShowLessText=enable;
    }

    public void setShowMoreTextDotEnable(boolean enable){
        isShowMoreTextDotEnable=enable;
        if (isShowMoreTextDotEnable){
            ellipsis_dots="...";
        }else {
            ellipsis_dots="";
        }
    }

    public void setShowingLine(int lineNumber) {
        if (lineNumber == 0||showingLine==lineNumber) {
            return;
        }

        showingLine = lineNumber;
        setMaxLines(showingLine);
    }


    public void addShowMoreText(String text) {
        showMoreText = text;
    }


    public void addShowLessText(String text) {
        showLessText = text;
    }

    public void setShowMoreColor(int color) {
        showMoreTextColor = color;
    }

    public void setShowLessTextColor(int color) {
        showLessTextColor = color;
    }
}
