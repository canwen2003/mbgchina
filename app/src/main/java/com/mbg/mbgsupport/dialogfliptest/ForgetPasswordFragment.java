package com.mbg.mbgsupport.dialogfliptest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.mbg.mbgsupport.R;


public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnBackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnBackListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_forget_password, container, false);
        mView.findViewById(R.id.btn_back).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    OnBackListener mCallback;

    @Override
    public void onClick(View view) {
        mCallback.goBack();
    }

    public interface OnBackListener {
        void goBack();
    }
}
