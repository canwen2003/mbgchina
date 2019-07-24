package com.mbg.mbgsupport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mbg.mbgsupport.fragment.DragFragment;

public class MainActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initView();
    }

    private void initView(){
        findViewById(R.id.btn_drag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragFragment.show(context);
            }
        });
    }
}
