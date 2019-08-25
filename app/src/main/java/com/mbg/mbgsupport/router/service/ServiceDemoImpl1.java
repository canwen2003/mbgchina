package com.mbg.mbgsupport.router.service;

import android.util.Log;

import com.blued.android.module.interfaces.annotation.RouterService;


@RouterService(interfaces = IBaseService.class,key=ServiceKey.KEY_SERVICE1)
public class ServiceDemoImpl1 implements IBaseService {
    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public void doInBackground() {
        Log.d(getModuleName(),"Run in doInBackgrond method1");
    }
}
