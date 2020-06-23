package com.mbg.mbgsupport.fragment.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.fragment.skeleton.adapter.TopicAdapter;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.skeleton.Skeleton;
import com.mbg.module.ui.view.skeleton.SkeletonScreen;

import java.lang.ref.WeakReference;


public class SkeletonViewFragment extends BaseFragment{
    private static final String PARAMS_TYPE = "params_type";
    public static final String TYPE_IMG_LOADING = "type_img";
    public static final String TYPE_VIEW = "type_view";
    private SkeletonScreen skeletonScreen;
    private String mType;


    public static void show(Context context,String type){
        Bundle intent = new Bundle();
        intent.putString(PARAMS_TYPE, type);
        TerminalActivity.show(context, SkeletonViewFragment.class,intent);
    }


    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_skeleton_vew;
    }

    @Override
    protected void initView() {
        mType = getArguments().getString(PARAMS_TYPE);
        init();
    }


    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TopicAdapter adapter = new TopicAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        View rootview=findViewById(R.id.rootView);
        if (TYPE_VIEW.equals(mType)) {
            skeletonScreen = Skeleton.bind(rootview)
                    .load(R.layout.fragment_view_skeleton)
                    .duration(1000)
                    .color(R.color.shimmer_color_for_image)
                    .angle(30)
                    .show();
        }
        if (TYPE_IMG_LOADING.equals(mType)) {
            skeletonScreen = Skeleton.bind(rootview)
                    .load(R.layout.layout_img_skeleton)
                    .duration(1000)
                    .color(R.color.shimmer_color_for_image)
                    .show();
        }
        MyHandler myHandler = new MyHandler(this);
        myHandler.sendEmptyMessageDelayed(1, 3000);
    }
    public static class MyHandler extends android.os.Handler {
        private final WeakReference<SkeletonViewFragment> activityWeakReference;

        MyHandler(SkeletonViewFragment activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().skeletonScreen.hide();
            }
        }
    }

}
