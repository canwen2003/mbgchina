package com.mbg.mbgsupport.dialogfliptest;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.mbg.mbgsupport.R;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2017/8/16.
 * <p>
 * <h3>Description:</h3>
 * <p/>
 * <p/>
 */


public class LoginFragment extends DialogFragment implements View.OnClickListener {

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mCallback = (OnForgetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnForgetListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_login, container, false);
        mView.findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    OnForgetListener mCallback;

    @Override
    public void onClick(View view) {
        mCallback.forgetPassword();
    }

    public interface OnForgetListener {
        void forgetPassword();
    }
}
