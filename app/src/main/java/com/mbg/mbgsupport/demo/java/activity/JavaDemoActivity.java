package com.mbg.mbgsupport.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbg.mbgsupport.R;

public class JavaDemoActivity  extends AppCompatActivity {
    private TextView mTestView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin_demo);
        mTestView=findViewById(R.id.tv_test1);

        mTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
            }
        });
    }
}
