package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.subscaleview.ImageSource;
import com.mbg.module.ui.view.subscaleview.SubsamplingScaleImageView;

public class BigImageLoaderFragment extends BaseFragment implements View.OnClickListener{

    public static void show(Context context){
        TerminalActivity.show(context, BigImageLoaderFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_bigimage_loader;
    }

    @Override
    protected void initView() {
        SubsamplingScaleImageView scaleImageView=findViewById(R.id.img_scale);
        scaleImageView.setImage(ImageSource.resource(R.drawable.icon_girl));
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }

    }
}
