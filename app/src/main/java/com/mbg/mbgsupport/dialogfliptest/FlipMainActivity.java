package com.mbg.mbgsupport.dialogfliptest;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.BaseActivity;


public class FlipMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_main);

        final MyDialog dialog = new MyDialog(this);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FlipMainActivity.this, LoginActivity.class));
            }
        });
        findViewById(R.id.btn_3d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
}
