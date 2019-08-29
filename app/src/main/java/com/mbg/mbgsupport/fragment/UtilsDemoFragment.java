package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.PermissionUtils;
import com.mbg.module.common.util.UriUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

import java.util.List;

public class UtilsDemoFragment extends BaseFragment implements View.OnClickListener{
    private ImageView mShowImageView;
    public static void show(Context context){
        TerminalActivity.show(context, UtilsDemoFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_utils_demo;
    }

    @Override
    protected void initView() {
        PermissionUtils.checkStoragePermissson(getActivity(), new PermissionUtils.PermissionCallbacks() {
            @Override
            public void onPermissionsGranted(int requestCodes, List<String> perms) {

            }

            @Override
            public void onPermissionsDenied(int requestCodes, List<String> perms) {

            }
        });
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
        findViewById(R.id.btn_test5).setOnClickListener(this);
        findViewById(R.id.btn_test6).setOnClickListener(this);
        mShowImageView=findViewById(R.id.img_show);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
        switch (viewId){
            case R.id.btn_test1:
                onTest1();
                break;
            case R.id.btn_test2:
                onTest2();
                break;
            case R.id.btn_test3:
                onTest3();
                break;
            case R.id.btn_test4:
                onTest4();
                break;
            case R.id.btn_test5:
                onTest5();
                break;
            case R.id.btn_test6:
                onTest6();
                break;
        }

    }

     private void onTest1(){

        String path= Environment.getExternalStorageDirectory().getPath()+"/DCIM/Image/a001.png";
        Uri uri= UriUtils.getUriFromFile(path);
        LogUtils.v(uri.toString());
        mShowImageView.setImageURI(uri);
     }
    private void onTest2(){

    }
    private void onTest3(){

    }
    private void onTest4(){

    }
    private void onTest5(){

    }
    private void onTest6(){

    }

}
