package com.mbg.mbgsupport.dialogfliptest;

import android.app.FragmentTransaction;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbg.mbgsupport.R;


/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2017/8/16.
 * <p>
 * <h3>Description:</h3>
 * <p/>
 * <p/>
 */


public class LoginActivity extends AppCompatActivity implements LoginFragment.OnForgetListener, ForgetPasswordFragment.OnBackListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.fl_content, new LoginFragment()).commit();
    }

    @Override
    public void forgetPassword() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.fl_content, new ForgetPasswordFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void goBack() {
        getFragmentManager().popBackStack();
    }

}
