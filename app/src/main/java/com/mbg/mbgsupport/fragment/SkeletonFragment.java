package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.MainActivity;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.fragment.skeleton.SkeletonRecyclerViewFragment;
import com.mbg.mbgsupport.fragment.skeleton.SkeletonViewFragment;
import com.mbg.mbgsupport.fragment.skeleton.ViewReplacerFragment;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class SkeletonFragment extends BaseFragment{
    public static void show(Context context){
        TerminalActivity.show(context, SkeletonFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_skeleton;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonRecyclerViewFragment.show(getActivity(), SkeletonRecyclerViewFragment.TYPE_LINEAR);
            }
        });
        findViewById(R.id.btn_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonRecyclerViewFragment.show(getActivity(), SkeletonRecyclerViewFragment.TYPE_GRID);
            }
        });
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonViewFragment.show(getActivity(), SkeletonViewFragment.TYPE_VIEW);
            }
        });
        findViewById(R.id.btn_Imgloading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonViewFragment.show(getActivity(), SkeletonViewFragment.TYPE_IMG_LOADING);
            }
        });

        findViewById(R.id.btn_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewReplacerFragment.show(getActivity());
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
