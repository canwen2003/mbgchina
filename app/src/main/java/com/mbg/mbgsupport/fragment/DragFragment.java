package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.image.view.RecyclerImageView;

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

        RecyclerImageView imgView=findViewById(R.id.img_test);
        imgView.loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714258&di=3a57d4b83d48df3194bad83a74fe81ad&imgtype=0&src=http%3A%2F%2Fimg.book118.com%2Fsr1%2FM00%2F0B%2F17%2FwKh2Al0LMjeIW64PAAEqr-JkQOQAAeGqwD9g2gAASrH405.jpg");
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
        switch (viewId){
            case R.id.btn_test1:
                show(getActivity());
                break;
            case R.id.btn_test2:
                break;
            case R.id.btn_test3:
                break;
            case R.id.btn_test4:
                finish();
                break;
        }

    }
}
