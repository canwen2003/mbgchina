package com.mbg.mbgsupport.router.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.blued.android.module.interfaces.annotation.RouterService;



@RouterService(interfaces = IBaseService.class,key=ServiceKey.KEY_SERVICE,singleton = true)
public class ServiceDemoImpl implements IBaseService {
    public int count=0;
    private Handler handler;

    public ServiceDemoImpl(){
        Log.d(getModuleName(),"Run in doInBackgrond ServiceDemoImpl");
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public void doInBackground() {
        Log.d(getModuleName(),"Run in doInBackgrond method"+count++);
        Log.d(getModuleName(),"Run in doInBackgrond Thread="+Thread.currentThread().getName());
        if (handler==null){
            if (Looper.myLooper()==null){
                Looper.prepare();
            }
            handler=new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.d(getModuleName(),"doInBackgrond handleMessage Thread="+Thread.currentThread().getName());

                }
            };

            Looper.loop();
        }

        if (handler!=null){
            handler.sendEmptyMessage(23);
        }

    }
}
