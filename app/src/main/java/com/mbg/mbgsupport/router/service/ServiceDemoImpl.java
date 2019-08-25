package com.mbg.mbgsupport.router.service;

import android.util.Log;

import com.blued.android.module.interfaces.annotation.RouterService;


@RouterService(interfaces = IBaseService.class,key=ServiceKey.KEY_SERVICE,singleton = true)
public class ServiceDemoImpl implements IBaseService {
    public int count=0;
    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public void doInBackground() {
        Log.d(getModuleName(),"Run in doInBackgrond method"+count++);
    }
}
