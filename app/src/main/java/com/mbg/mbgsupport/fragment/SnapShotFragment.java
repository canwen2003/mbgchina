package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;


import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.ScreenShotUtils;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.dialog.screenshot.ScreenShotDialog;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.image.view.RecyclerImageView;

public class SnapShotFragment extends BaseFragment implements View.OnClickListener{
    private  RecyclerImageView imageView;
    private ScreenShotDialog screenShotDialog;


    public static void show(Context context){
        TerminalActivity.show(context, SnapShotFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_snapshot;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);

        String uri="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565331017819&di=073b748022ef53419c66ead5f0054a76&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201508%2F07%2F20150807191914_VdHki.thumb.700_0.jpeg";
        imageView=findViewById(R.id.img_test);
        imageView.loadImage(uri);
        screenShotDialog=new ScreenShotDialog();
        screenShotDialog.init(getActivity(),R.layout.dialog_screenshot);
        screenShotDialog.setPositiveButton("Create", new ScreenShotDialog.OnClickListener() {
            @Override
            public void OnClick(View view) {

            }
        });
        screenShotDialog.setCancelButton("Cancel", new ScreenShotDialog.OnClickListener() {
            @Override
            public void OnClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
        switch (viewId){
            case R.id.btn_test1: {
                Bitmap bitmap = ScreenShotUtils.snapShotWithStatusBar(getActivity());
                imageView.setImageBitmap(bitmap);
            }
                break;
            case R.id.btn_test2: {
                final Bitmap bitmap = ScreenShotUtils.snapShotWithoutStatusBar(getActivity());
                //imageView.setImageBitmap(bitmap);
                screenShotDialog.showDialog();
                ThreadUtils.postInUIThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        screenShotDialog.updateScreen(bitmap);
                    }
                },1000);


            }
                break;
            case R.id.btn_test3:
            {
                Bitmap bitmap = ScreenShotUtils.snapShotByViwGroup((ViewGroup) mRootView);
                imageView.setImageBitmap(bitmap);
            }
                break;
            case R.id.btn_test4:
                finish();
                break;
        }

    }
}
