package com.mbg.mbgsupport.fragment.skeleton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.fragment.skeleton.adapter.NewsAdapter;
import com.mbg.mbgsupport.fragment.skeleton.adapter.PersonAdapter;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.skeleton.Skeleton;
import com.mbg.module.ui.view.skeleton.SkeletonScreen;


public class SkeletonRecyclerViewFragment extends BaseFragment{
    private static final String PARAMS_TYPE = "params_type";

    public static final String TYPE_LINEAR = "type_linear";
    public static final String TYPE_GRID = "type_grid";
    private String mType;


    public static void show(Context context,String type){
        Bundle intent = new Bundle();
        intent.putString(PARAMS_TYPE, type);
        TerminalActivity.show(context, SkeletonRecyclerViewFragment.class,intent);
    }


    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_skeleton_recyclerivew;
    }

    @Override
    protected void initView() {
        mType = getArguments().getString(PARAMS_TYPE);
        init();
    }


    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (TYPE_LINEAR.equals(mType)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            NewsAdapter adapter = new NewsAdapter();
            final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                    .adapter(adapter)
                    .shimmer(true)
                    .angle(20)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .load(R.layout.item_skeleton_news)
                    .show(); //default count is 10
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skeletonScreen.hide();
                }
            }, 3000);
            return;
        }
        if (TYPE_GRID.equals(mType)) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            PersonAdapter adapter = new PersonAdapter();
            final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                    .adapter(adapter)
                    .load(R.layout.item_skeleton_person)
                    .shimmer(true)
                    .show();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skeletonScreen.hide();
                }
            }, 3000);
        }
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
