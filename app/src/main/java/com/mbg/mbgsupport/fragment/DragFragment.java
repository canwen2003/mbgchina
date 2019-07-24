package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class DragFragment extends BaseFragment implements View.OnClickListener{

    public static void show(Context context){
        TerminalActivity.show(context,DragFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_dragview_test;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
        switch (viewId){
            case R.id.btn_test1:
                show(getContext());
                break;
            case R.id.btn_test2:
                break;
            case R.id.btn_test3:
                break;
            case R.id.btn_test4:
                break;
        }

    }
}
