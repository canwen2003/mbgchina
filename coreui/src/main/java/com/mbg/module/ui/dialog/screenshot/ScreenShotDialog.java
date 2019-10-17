package com.mbg.module.ui.dialog.screenshot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;

import com.mbg.module.common.util.RingtoneUtils;
import com.mbg.module.ui.R;


public class ScreenShotDialog {
    private Dialog mDialog;
    private View mRootView;
    private ImageView mScreenView;
    private ProgressBar mProgressBar;


    public ScreenShotDialog init(Context context, @LayoutRes int resId){
        mRootView = LayoutInflater.from(context).inflate(resId, null);
        mDialog = new AlertDialog.Builder(context).create();
        mScreenView=mRootView.findViewById(R.id.img_screen);
        mProgressBar=mRootView.findViewById(R.id.avLoading);
        return this;
    }

    public void showDialog(){
        if (mDialog!=null&&!mDialog.isShowing()) {
            mDialog.show();
            mScreenView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mDialog.setContentView(mRootView);
            RingtoneUtils.PlayNotification();
        }
    }

    public void updateScreen(Bitmap bitmap){
        if (mScreenView!=null){
            mScreenView.setImageBitmap(bitmap);
            mScreenView.setVisibility(View.VISIBLE);
        }
        if (mProgressBar!=null){
            mProgressBar.setVisibility(View.GONE);
        }
    }


    public ScreenShotDialog setPositiveButton(String str, final OnClickListener listener) {
        Button button = mRootView.findViewById(R.id.btn_one);
        button.setText(str);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    mDialog.dismiss();
                } else {
                    listener.OnClick(v);
                }
            }
        });
        return this;
    }

    public ScreenShotDialog setCancelButton(String str, final OnClickListener listener) {
        Button button = mRootView.findViewById(R.id.btn_two);
        button.setText(str);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    mDialog.dismiss();
                } else {
                    listener.OnClick(v);
                    mDialog.dismiss();
                }
            }
        });
        return this;
    }


    public interface OnClickListener {
        void OnClick(View view);
    }
}
