package com.mbg.mbgsupport.fragment.skeleton;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.skeleton.ViewReplacer;


public class ViewReplacerFragment extends BaseFragment{
    private ViewReplacer mViewReplacer;


    public static void show(Context context){
        TerminalActivity.show(context, ViewReplacerFragment.class,null);
    }


    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_skeleton_replacer;
    }

    @Override
    protected void initView() {
        mViewReplacer = new ViewReplacer(findViewById(R.id.tv_content));
        findViewById(R.id.btn_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewReplacer.replace(R.layout.layout_progress);
            }
        });

        findViewById(R.id.btn_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewReplacer.replace(R.layout.layout_error);
            }
        });

        findViewById(R.id.btn_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewReplacer.replace(R.layout.layout_empty_view);
            }
        });

        findViewById(R.id.btn_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewReplacer.restore();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
