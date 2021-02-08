package com.mbg.mbgsupport.work;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mbg.mbgsupport.KotlinMain;
import com.mbg.module.common.util.LogUtils;

public class DemoWorker extends Worker {
    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
       Data data= getInputData();
        LogUtils.d("doWork:"+data.getString("doWork"));
        Intent intent=new Intent(getApplicationContext(), KotlinMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("dowork","Come from WorkManager ->doWork");
        getApplicationContext().startActivity(intent);
        return Result.success();
    }
}
