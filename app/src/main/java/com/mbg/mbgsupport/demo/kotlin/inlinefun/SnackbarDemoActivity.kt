package com.mbg.mbgsupport.demo.kotlin.inlinefun

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mbg.mbgsupport.R
import com.mbg.module.ui.view.snackbar.SnackbarUtils


class SnackbarDemoActivity : AppCompatActivity() {
    private lateinit var mTestView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)
        mTestView=findViewById(R.id.tv_test)
        mTestView.setOnClickListener {
           // var view=LayoutInflater.from(this).inflate(R.layout.view_async_load,null)
            /*val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.girl)*/
            SnackbarUtils.Custom(mTestView, "",5000).margins(50).radius(40.0f).addView(R.layout.view_snack_bar,-1).show()
        }

    }
}