package com.mbg.module.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.mbg.module.ui.fragment.BaseFragment;

public class TerminalActivity extends BaseFragmentActivity {


    public static void show(Context context, Class<? extends BaseFragment> baseFragment,Bundle bundle){
        Intent intent = new Intent(context, TerminalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ARG_FRAGMENT_CLASS_NAME,baseFragment.getName());
        intent.putExtra(ARG_FRAGMENT_ARGS,bundle);
        context.startActivity(intent);
    }

}
