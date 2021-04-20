package com.mbg.mbgsupport.demo.kotlin.inlinefun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.mbg.mbgsupport.R

class KotlinDemoActivity : AppCompatActivity() {
    private lateinit var mTestView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)
        mTestView=findViewById(R.id.tv_test1)

        mTestView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Todo
            }
        })

       /* mTestView.setOnClickListener({
            v:View?->
            //Todo
        })

        mTestView.setOnClickListener({
            v->
            //Todo
        })

        mTestView.setOnClickListener({
            //Todo
        })

        mTestView.setOnClickListener(){
            //Todo
        }*/

        mTestView?.setOnClickListener{
            //Todo
        }

       val str:String= mTestView.let {
           it.text="StringTest"//在函数体内使用it替代object对象去访问其公有的属性和方法



           it.text.toString()
        }?:"init"

        val str1:String= with(mTestView){
           text="StringTest"//在函数体内直接其公有的属性和方法



           text.toString()
       }

        val str2:String= mTestView.run {
            text="StringTest"//在函数体内直接其公有的属性和方法



            text.toString()
        }

       val pView:TextView= mTestView.apply {
           text="StringTest"//在函数体内直接其公有的属性和方法

       }

        val pView1:TextView= mTestView.also {
            it.text="StringTest"//在函数体内直接其公有的属性和方法

        }


    }
}