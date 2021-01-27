package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blued.android.module.serviceloader.Router;
import com.mbg.mbgsupport.MainActivity;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.router.service.IBaseService;
import com.mbg.mbgsupport.router.service.ServiceKey;
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel;
import com.mbg.module.common.core.LifecycleHandler;
import com.mbg.module.common.core.WeakHandler;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.tool.HttpUtils;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;
import com.mbg.module.common.core.sharedpreference.FastSharedPreferences;
import com.mbg.module.common.util.AppUtils;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.FileCacheUtils;
import com.mbg.module.common.util.KeyboardUtils;
import com.mbg.module.common.util.LocaleUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.PermissionHelper;
import com.mbg.module.common.util.PermissionUtils;
import com.mbg.module.common.util.SpannableUtils;
import com.mbg.module.common.util.StringUtils;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.common.util.ToastUtils;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.common.util.UriUtils;
import com.mbg.module.common.util.consts.PermissionConsts;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.inflate.AsyncLayoutInflatePlus;
import com.mbg.module.ui.view.inflate.OnInflateFinishedListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class UtilsDemoFragment extends BaseFragment implements View.OnClickListener{
    private ImageView mShowImageView;
    private LoadingStateViewModel mLoadingStateViewModel;
    private WeakHandler weakHandler=new WeakHandler(this);
    private LifecycleHandler lifecycleHandler=new LifecycleHandler(this);
    private TextView weakTextView;
    private TextView lifeTextView;
    private View mGlobalView;
    private ViewGroup mRootView;


    public static void show(Context context){
        TerminalActivity.show(context, UtilsDemoFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_utils_demo;
    }

    @Override
    protected void initView() {
        mRootView=findViewById(R.id.root_view);
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
        findViewById(R.id.btn_test7).setOnClickListener(this);
        findViewById(R.id.btn_test8).setOnClickListener(this);
        findViewById(R.id.btn_test9).setOnClickListener(this);
        findViewById(R.id.btn_show_global).setOnClickListener(this);
        findViewById(R.id.btn_hide_global).setOnClickListener(this);
        mGlobalView=View.inflate(getActivity(),R.layout.view_global_demo,null);

        weakTextView=findViewById(R.id.btn_weak_handle);
        weakTextView.setOnClickListener(this);
        lifeTextView=findViewById(R.id.btn_life_handle);
        lifeTextView.setOnClickListener(this);

        final TextView inputRoot=findViewById(R.id.tv_test_input);

        SpannableUtils.SpannableFilter spannableFilter=new SpannableUtils.SpannableFilter();
        List<SpannableUtils.SpannableFilter> spannableFilters=new ArrayList<>();
        spannableFilter.keywords="测试";
        spannableFilter.keywordsColor= Color.RED;
        spannableFilter.isUnderLine=false;
        spannableFilter.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.debugShow("测试");
            }
        };
        spannableFilters.add(spannableFilter);

        spannableFilter=new SpannableUtils.SpannableFilter();
        spannableFilter.keywords="键盘";
        spannableFilter.keywordsColor= Color.GRAY;
        spannableFilter.isUnderLine=true;
        spannableFilter.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.debugShow("键盘");
            }
        };
        spannableFilters.add(spannableFilter);

        SpannableUtils.setViewSpannable(getActivity(),inputRoot,spannableFilters);

        mShowImageView=findViewById(R.id.img_show);


        KeyboardUtils.registerSoftInputChangedListener(getActivity(), new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                ToastUtils.debugShow("onSoftInputChanged:height="+height);
                if (height>0){
                    inputRoot.setTranslationY(-height);
                }
            }
        });

        mLoadingStateViewModel=ViewModelProviders.of(getActivity()).get("LoadingState", LoadingStateViewModel.class);
        mLoadingStateViewModel.getLoadingState().observe(getActivity(), new Observer<LoadingStateViewModel.LoadingState>() {
            @Override
            public void onChanged(LoadingStateViewModel.LoadingState loadingState) {
                if (loadingState!=null){
                    switch (loadingState){
                        case START:
                            //onDataLoadingStart();
                            break;
                        case FINISH:
                            onDataLoadingFinish();
                            break;
                    }
                }
            }
        });

        ThreadUtils.postInUIThreadDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingStateViewModel.setLoadingSate(LoadingStateViewModel.LoadingState.FINISH);
            }
        },8000);


        new AsyncLayoutInflatePlus(getActivity()).inflate(R.layout.view_async_load, null, new OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resId, @Nullable ViewGroup parent) {
                mRootView.addView(view);
            }
        });

    }

    protected void  onDataLoadingStart(){

        HashSet hs = new HashSet();
        long ks = System.currentTimeMillis();
        for (int i=1;i<99999;i++)
            hs.add(i);
        Iterator it = hs.iterator();
        while (it.hasNext())
            it.next();
        boolean add=hs.add(1);
        hs.remove(5555);
        hs.clear();
        long js = System.currentTimeMillis();


        //LinkedHashSet效率
        LinkedHashSet lh = new LinkedHashSet();
        long ks1 = System.currentTimeMillis();
        for (int i=1;i<99999;i++)
            lh.add(i);


        Iterator it1 = lh.iterator();
        while (it1.hasNext())
            it1.next();
        add=lh.add(1);
        lh.remove(5555);
        lh.clear();
        long js1 = System.currentTimeMillis();


        //TreeSet效率
        TreeSet ts = new TreeSet();
        long ks2 = System.currentTimeMillis();
        for (int i=1;i<99999;i++)
            ts.add(i);


        Iterator it2 = ts.iterator();
        while (it2.hasNext())
            it2.next();
        add=ts.add(1);
        add=ts.add(0);
        ts.remove(5555);
        ts.clear();
        long js2 = System.currentTimeMillis();

        LogUtils.d("HashSet共花费时间："+(js-ks)+"ms");
        LogUtils.d("LinkedHashSet共花费时间："+(js1-ks1)+"ms");
        LogUtils.d("TreeSet共花费时间："+(js2-ks2)+"ms");

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
                onTest1();
                FastSharedPreferences.get("FSP_DATA_USER").edit().putBoolean("keyBool",true);
                FastSharedPreferences.get("FSP_DATA_USER").edit().putString("keyString","btn_test1").apply();
                break;
            case R.id.btn_test2:
                FastSharedPreferences.get("FSP_DATA_USER").edit().putBoolean("keyBool",false);
                FastSharedPreferences.get("FSP_DATA_USER").edit().putString("keyString","btn_test2").apply();
                onTest2();
                break;
            case R.id.btn_test3:
                ToastUtils.show("keyBool="+ FastSharedPreferences.get("FSP_DATA_USER").getBoolean("keyBool",false));
               // onTest3();
                break;
            case R.id.btn_test4:
                ToastUtils.show("keyString="+ FastSharedPreferences.get("FSP_DATA_USER").getString("keyString",""));
                //onTest4();
                break;
            case R.id.btn_test5:
                onTest5();
                break;
            case R.id.btn_test6:
                onTest6();
            case R.id.btn_test7:
                onTest7();
                break;
            case R.id.btn_test8:
                onTest8();
                break;
            case R.id.btn_test9:
                onTest9();
                break;
            case R.id.btn_weak_handle:
                for (int i=0;i<1000;i++) {
                    weakHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            weakTextView.setText("我3秒前被点击了");
                            weakTextView.setTextColor(Color.GREEN);
                        }
                    }, 10000);
                }
                break;
            case R.id.btn_life_handle:
                lifecycleHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lifeTextView.setText("我3秒前被点击了");
                        lifeTextView.setTextColor(Color.RED);
                    }
                },3000);
                break;
            case R.id.btn_show_global:
                UiUtils.showGlobal(getActivity(), mGlobalView, R.id.view_global, 500, new UiUtils.ViewCallback() {
                    @Override
                    public boolean onView(View view) {
                        return true;//返回true,表示不重新加入
                    }
                });
                break;
            case R.id.btn_hide_global:
                UiUtils.hideGlobal(getActivity(),R.id.view_global,500);
                break;
        }

    }

     private void onTest1(){

       /* String path= Environment.getExternalStorageDirectory().getPath()+"/DCIM/Image/a001.png";
        Uri uri= UriUtils.getUriFromFile(path);
        LogUtils.v(uri.toString());
        mShowImageView.setImageURI(uri);*/
         mLoadingStateViewModel.setLoadingSate(LoadingStateViewModel.LoadingState.START);
     }
    private void onTest2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                IBaseService service= Router.getService(IBaseService.class, ServiceKey.KEY_SERVICE);
                service.doInBackground();
            }
        }).start();
        mLoadingStateViewModel.setLoadingSate(LoadingStateViewModel.LoadingState.FINISH);

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
        LocaleUtils.setLocale(getActivity(),new Locale(LocaleUtils.Language.ZH.getCode(),LocaleUtils.CountryArea.China.getCode()));
        AppUtils.rebootApplication(MainActivity.class);

    }
    private void onTest6(){
        LocaleUtils.setLocale(getActivity(),new Locale(LocaleUtils.Language.EN.getCode(),LocaleUtils.CountryArea.America.getCode()));
        AppUtils.rebootApplication(MainActivity.class);
    }

    private void onTest7(){
        KeyboardUtils.showSoftInput(getActivity());
    }
    private void onTest8(){
       KeyboardUtils.hideSoftInput(getActivity());
    }
    private void onTest9(){
        //showContent(ImageLoaderFragment.class);
      Map<Thread,StackTraceElement[]> map= Thread.getAllStackTraces();
        for(Map.Entry<Thread,StackTraceElement[]> entry : map.entrySet()){
            Thread mapKey = entry.getKey();
            System.out.println("Thread:"+mapKey.getName()+" ,isAlive:"+mapKey.isAlive()+" ,ThreadID:"+mapKey.getId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        weakTextView=null;
        lifeTextView=null;
    }
}
