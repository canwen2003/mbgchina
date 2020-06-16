package com.mbg.module.common.util;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SpannableUtils {

    public static void setViewSpannable(Context context, TextView textView, List<SpannableFilter> spannableFilters){
        if (textView!=null) {
            setViewSpannable(context, textView, textView.getText().toString(), spannableFilters);
        }
    }

    public static void setViewSpannable(Context context, TextView textView, String orgString,List<SpannableFilter> spannableFilters){
        if(textView==null||StringUtils.isEmpty(orgString)||spannableFilters==null||spannableFilters.size()==0){
            return;
        }

        SpannableString spannableString = new SpannableString(orgString);

        try {
            LinkClickSpan clickSpann;
            for(SpannableFilter spannableFilter:spannableFilters){
                int start=orgString.indexOf(spannableFilter.keywords);
                clickSpann = new LinkClickSpan (context,spannableFilter.keywordsColor,spannableFilter.isUnderLine,spannableFilter.onClickListener); //设置超链接
                spannableString.setSpan(clickSpann, start, start+spannableFilter.keywords.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            textView.setHighlightColor(Color.TRANSPARENT);//去掉选中文字出现的背景色
            textView.setMovementMethod(LinkMovementMethod.getInstance());//可点击
            textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static class SpannableFilter{
         public View.OnClickListener onClickListener;
         public int keywordsColor;
         public String keywords;
         public boolean isUnderLine;
    }

    private static class LinkClickSpan extends ClickableSpan {
        Context context;
        View.OnClickListener onClickListener;
        int textColor;
        boolean isUnderLine;

        private LinkClickSpan(Context context,int textColor,boolean isUnderLine,View.OnClickListener onClickListener) {
            this.context = context;
            this.textColor=textColor;
            this.isUnderLine=isUnderLine;
            this.onClickListener=onClickListener;
        }
        @Override
        public void onClick(@NonNull View view) {
            if(onClickListener!=null) onClickListener.onClick(view);
        }
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);

            ds.setColor(textColor);//设置连接的文本颜色
            //ds.bgColor
            ds.setUnderlineText(isUnderLine); //设置下划线
            ds.clearShadowLayer();
        }
    }
}
