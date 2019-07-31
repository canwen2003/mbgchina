package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.view.RecyclerImageView;
import com.mbg.module.ui.image.view.RoundedImageView;

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
        RecyclerImageView imgView1=findViewById(R.id.img_test1);
        RecyclerImageView imgView2=findViewById(R.id.img_test2);
        String uri="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714258&di=3a57d4b83d48df3194bad83a74fe81ad&imgtype=0&src=http%3A%2F%2Fimg.book118.com%2Fsr1%2FM00%2F0B%2F17%2FwKh2Al0LMjeIW64PAAEqr-JkQOQAAeGqwD9g2gAASrH405.jpg";
        String uri1="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714640&di=208a8f28127ab3aa7e4e96eee8f9a537&imgtype=0&src=http%3A%2F%2Fimg246.ph.126.net%2FH0reIPvlHNk5nR-2mhXhew%3D%3D%2F2234348365130522269.gif";
        String uri2="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714639&di=70771c60403e8e7292ea67336b6f26fb&imgtype=0&src=http%3A%2F%2Fdtimage.oss-cn-shanghai.aliyuncs.com%2F2017%2F0226%2F26014658p0tt.jpg";
        LoadOptions options= LoadOptions.get().setEnableCache(true).setDefaultImageResId(R.drawable.icon_common_empty).setImageOnFail(R.drawable.icon_common_empty);
        imgView.loadImage(uri,options);
        imgView1.loadImage(uri1,options);
        imgView2.loadImage(uri,options);


        RoundedImageView roundedImageView1=findViewById(R.id.img_test3);
        RoundedImageView roundedImageView2=findViewById(R.id.img_test4);
        RoundedImageView roundedImageView3=findViewById(R.id.img_test5);
        RoundedImageView roundedImageView4=findViewById(R.id.img_test6);
        final RoundedImageView roundedImageView5=findViewById(R.id.img_test7);
        final String uri3="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564565361478&di=81576c9c9acbdda02e967b26f7488807&imgtype=0&src=http%3A%2F%2Ftvax4.sinaimg.cn%2Fcrop.0.0.512.512.1024%2F007azY3dly8fykbthnagdj30e80e80tm.jpg%3FExpires%3D1562678507%26ssig%3DEcWVulc6xh%26KID%3Dimgbed%2Ctva";
        roundedImageView1.loadImage(uri);
        roundedImageView2.loadImage(uri1);
        roundedImageView3.loadImage(uri2);
        roundedImageView4.loadImage(uri3);
        roundedImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundedImageView5.loadImage(uri3);
            }
        });
        roundedImageView5.loadImage(uri3);
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
