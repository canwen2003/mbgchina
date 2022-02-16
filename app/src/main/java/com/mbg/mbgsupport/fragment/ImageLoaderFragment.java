package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;
import com.mbg.module.ui.image.view.RecyclerImageView;
import com.mbg.module.ui.image.view.RoundedImageView;
import com.mbg.module.ui.view.imageview.DraggableImageView;
import com.mbg.module.ui.view.imageview.PartClickImageView;
import com.mbg.module.ui.view.imageview.TransformativeImageView;
import com.mbg.module.ui.view.listener.OnImageChangedListener;

public class ImageLoaderFragment extends BaseFragment implements View.OnClickListener{
    public static void show(Context context){
        TerminalActivity.show(context, ImageLoaderFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_imageloader;
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
        String uri1="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1595582922&di=2b92c490ce75942dfff7fae7b569afb0&src=http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg";
        String uri2="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714639&di=70771c60403e8e7292ea67336b6f26fb&imgtype=0&src=http%3A%2F%2Fdtimage.oss-cn-shanghai.aliyuncs.com%2F2017%2F0226%2F26014658p0tt.jpg";
        LoadOptions options= LoadOptions.get().setEnableCache(true).setDefaultImageResId(R.drawable.icon_common_empty).setImageOnFail(R.drawable.icon_common_empty);
        imgView.loadImage(uri,options);
        imgView1.loadImage(uri1,options);
        imgView2.loadImage(uri,options);

        TransformativeImageView transformativeImageView=findViewById(R.id.transformative_image_view);
        final DraggableImageView draggableImageView1=findViewById(R.id.draggable_image_view_1);
        draggableImageView1.setDragEnable(false);
        draggableImageView1.setCanReplaced(true);
        draggableImageView1.setImageToken("1");
        draggableImageView1.setOnImageChangedListener(new OnImageChangedListener() {
            @Override
            public void onChanged(String imageToken) {
                LogUtils.d("imageToken="+imageToken);
            }
        });
        draggableImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("imageToken="+draggableImageView1.getImageToken());
            }
        });

        final DraggableImageView draggableImageView2=findViewById(R.id.draggable_image_view_2);
        draggableImageView2.setCanReplaced(true);
        draggableImageView2.setDragEnable(true);
        draggableImageView2.setImageToken("2");
        draggableImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("imageToken="+draggableImageView2.getImageToken());
            }
        });

        DraggableImageView draggableImageView3=findViewById(R.id.draggable_image_view_3);
        draggableImageView3.setDragEnable(true);
        draggableImageView3.setCanReplaced(true);
        draggableImageView3.setImageToken("3");

        DraggableImageView draggableImageView4=findViewById(R.id.draggable_image_view_4);
        draggableImageView4.setDragEnable(true);
        draggableImageView4.setCanReplaced(true);

        DraggableImageView draggableImageView5=findViewById(R.id.draggable_image_view_5);
        draggableImageView5.setDragEnable(true);
        draggableImageView5.setCanReplaced(true);


        ImageLoader imageLoader=ImageLoader.getInstance();
        if (imageLoader!=null) {
            imageLoader.displayImage(uri, transformativeImageView);
            imageLoader.displayImage(uri,draggableImageView1);
            imageLoader.displayImage(uri1,draggableImageView2);
            imageLoader.displayImage(uri2,draggableImageView3);
        }

        PartClickImageView imageView=findViewById(R.id.img_part);
        imageView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("Left Clicked");
            }
        });


        imageView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("right Clicked");
            }
        });


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

        ViewModelProviders.of(getActivity()).get("LoadingState", LoadingStateViewModel.class).getLoadingState().observe(this, new Observer<LoadingStateViewModel.LoadingState>() {
            @Override
            public void onChanged(LoadingStateViewModel.LoadingState loadingState) {
                if (loadingState!=null){
                    switch (loadingState){
                        case START:
                            onDataLoadingStart();
                            break;
                        case FINISH:
                            onDataLoadingFinish();
                            break;
                    }
                }
            }
        });
    }

    protected void  onDataLoadingStart(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingStart");
    }

    protected void  onDataLoadingFinish(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingFinish");
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
