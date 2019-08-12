package com.mbg.mbgsupport;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mbg.mbgsupport.fragment.DragFragment;
import com.mbg.mbgsupport.fragment.ImageLoaderFragment;
import com.mbg.mbgsupport.fragment.SnapShotFragment;
import com.mbg.mbgsupport.fragment.TextBannerFragment;
import com.mbg.module.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setStatusBarColor(getResources().getColor(R.color.design_default_color_primary),0);
        context=this;
        initView();
    }

    private void initView(){
        findViewById(R.id.btn_drag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragFragment.show(context);
            }
        });

        findViewById(R.id.btn_imageloader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoaderFragment.show(context);
            }
        });

        findViewById(R.id.btn_snapshot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapShotFragment.show(context);
            }
        });

        findViewById(R.id.btn_text_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBannerFragment.show(context);
            }
        });
    }
}
