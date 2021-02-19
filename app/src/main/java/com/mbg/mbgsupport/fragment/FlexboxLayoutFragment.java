package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class FlexboxLayoutFragment extends BaseFragment implements View.OnClickListener{

    public static void show(Context context){
        TerminalActivity.show(context, FlexboxLayoutFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_flexbox_layout;
    }

    @Override
    protected void initView() {
       // fillAutoSpacingLayout();
    }

        private void fillAutoSpacingLayout() {
            FlexboxLayout flowLayout = findViewById(R.id.flow);
            String[] dummyTexts = {"One","Two","Three","Four"};

            for (String text : dummyTexts) {
                TextView textView = buildLabel(text);
                flowLayout.addView(textView);
            }
        }

        private TextView buildLabel(String text) {
            TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setPadding((int)dpToPx(16), (int)dpToPx(8), (int)dpToPx(16), (int)dpToPx(8));
            textView.setTextColor(getResources().getColor(R.color.color_14c7de));
            textView.setBackgroundResource(R.drawable.common_label_unselected_bg);

            return textView;
        }

        private float dpToPx(float dp){
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        }


    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }

    }
}
