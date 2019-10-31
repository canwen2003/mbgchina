package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.blued.android.module.serviceloader.Router;
import com.mbg.mbgsupport.MainActivity;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.router.service.IBaseService;
import com.mbg.mbgsupport.router.service.ServiceKey;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.tool.HttpUtils;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;
import com.mbg.module.common.util.AppUtils;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.FileCacheUtils;
import com.mbg.module.common.util.LocaleUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.PermissionHelper;
import com.mbg.module.common.util.PermissionUtils;
import com.mbg.module.common.util.StringUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.common.util.UriUtils;
import com.mbg.module.common.util.consts.PermissionConsts;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

import java.util.List;
import java.util.Locale;

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

        if(!PermissionHelper.isPermissionGranted(PermissionConsts.CAMERA)){
            PermissionHelper
                    .permission(PermissionConsts.CAMERA)
                    .callback(new PermissionHelper.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            LogUtils.d("onGranted");
                        }

                        @Override
                        public void onDenied() {
                            LogUtils.d("onGranted");
                        }
                    }).request();
        }

        if(!PermissionHelper.isPermissionGranted(PermissionConsts.LOCATION)){
            PermissionHelper
                    .permission(PermissionConsts.LOCATION)
                    .callback(new PermissionHelper.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            LogUtils.d("onGranted");
                        }

                        @Override
                        public void onDenied() {
                            LogUtils.d("onGranted");
                        }
                    }).request();
        }

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                IBaseService service= Router.getService(IBaseService.class, ServiceKey.KEY_SERVICE);
                service.doInBackground();
            }
        }).start();

    }
    private void onTest3() {
        String key="Test";
        String str = FileCacheUtils.getContent(key);
        if (StringUtils.isEmpty(str)) {
            FileCacheUtils.saveContent(key,"Fisrt save,大中国。。");
            ToastUtils.show("Cache is empty!");
        } else {
            ToastUtils.show("Cache:" + str);
        }
    }
    private void onTest4(){

        DefaultHttpResponse response=new DefaultHttpResponse() {
            @Override
            protected void onUpdate(String data) {
                super.onUpdate(data);
                LogUtils.e("onUpdate:"+data);
            }

            @Override
            public void onUIStart() {
                super.onUIStart();
                LogUtils.e("onUIStart");
            }

            @Override
            public void onUICache(String data) {
                super.onUICache(data);
                LogUtils.e("onUICache:"+data);
            }

            @Override
            public void onUIError(Exception error) {
                super.onUIError(error);
                LogUtils.e("onUIError:"+error.getMessage());
            }

            @Override
            public void onUIFinish() {
                super.onUIFinish();
                LogUtils.e("onUIFinish");
            }
        };

        String url="http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18701539645";
        HttpManager.get(url,response).addHeader(HttpUtils.buildBaseHeader(false)).execute();
    }


    private void onTest5(){
        LocaleUtils.setLocale(AppUtils.getApplication(),new Locale(LocaleUtils.Language.ZH.getCode(),LocaleUtils.CountryArea.China.getCode()));
        AppUtils.rebootApplication(MainActivity.class);

    }
    private void onTest6(){
        LocaleUtils.setLocale(AppUtils.getApplication(),new Locale(LocaleUtils.Language.EN.getCode(),LocaleUtils.CountryArea.America.getCode()));
        AppUtils.rebootApplication(MainActivity.class);
    }

}
