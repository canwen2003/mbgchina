package com.mbg.mbgsupport.fragment.constraint;

import android.content.Context;
import android.view.View;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class ConstraintAnimationFragment extends BaseFragment implements View.OnClickListener{
    private final ConstraintSet applyConstraintSet = new ConstraintSet();
    private final ConstraintSet resetConstraintSet = new ConstraintSet();
    private ConstraintLayout constraintLayout;
    public static void show(Context context){
        TerminalActivity.show(context, ConstraintAnimationFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_constrainst_animation;
    }

    @Override
    protected void initView() {
         findViewById(R.id.btn_apply).setOnClickListener(this);
         findViewById(R.id.btn_reset).setOnClickListener(this);
         findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
         constraintLayout=(ConstraintLayout)mRootView;
         resetConstraintSet.clone(constraintLayout);
         applyConstraintSet.clone(resetConstraintSet);
    }

    private int mClickedCount=0;
    @Override
    public void onClick(View v) {
        int viewId=v.getId();
        if (viewId==R.id.btn_apply){

            switch (mClickedCount%8){
                case 0:
                    apply1();
                    break;
                case 1:
                    apply2();
                    break;
                case 2:
                    apply3();
                    break;
                case 3:
                    apply4();
                    break;
                case 4:
                    apply5();
                    break;
                case 5:
                    apply6();
                    break;
                case 6:
                    apply7();
                    break;
                case 7:
                    apply8();
                    break;
            }

            mClickedCount++;

        }else if (viewId==R.id.btn_reset){
            reset1();
        }else {
            reset1();
        }
    }

    private void apply1(){
        applyConstraintSet.clone(resetConstraintSet);
        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,8);
        applyConstraintSet.applyTo(constraintLayout);
    }

    private void apply2(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,8);
        applyConstraintSet.applyTo(constraintLayout);
    }

    private void apply3(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.centerHorizontally(R.id.button1, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerHorizontally(R.id.button2, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerHorizontally(R.id.button3, ConstraintSet.PARENT_ID);
        applyConstraintSet.applyTo(constraintLayout);
    }
    private void apply4(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,0);
        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.END,0);
        applyConstraintSet.setMargin(R.id.button2,ConstraintSet.START,0);
        applyConstraintSet.setMargin(R.id.button2,ConstraintSet.END,0);
        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.START,0);
        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.END,0);


        applyConstraintSet.centerHorizontally(R.id.button1, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerHorizontally(R.id.button2, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerHorizontally(R.id.button3, ConstraintSet.PARENT_ID);
        applyConstraintSet.applyTo(constraintLayout);
    }
    private void apply5(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.setVisibility(R.id.button2,ConstraintSet.GONE);
        applyConstraintSet.setVisibility(R.id.button3,ConstraintSet.GONE);
        applyConstraintSet.clear(R.id.button1);
        applyConstraintSet.connect(R.id.button1,ConstraintSet.LEFT,R.id.main,ConstraintSet.LEFT,0);
        applyConstraintSet.connect(R.id.button1,ConstraintSet.RIGHT,R.id.main,ConstraintSet.RIGHT,0);
        applyConstraintSet.connect(R.id.button1,ConstraintSet.TOP,R.id.main,ConstraintSet.TOP,0);
        applyConstraintSet.connect(R.id.button1,ConstraintSet.BOTTOM,R.id.main,ConstraintSet.BOTTOM, UiUtils.dip2px(100));

        applyConstraintSet.applyTo(constraintLayout);
    }
    private void apply6(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.clear(R.id.button1);
        applyConstraintSet.clear(R.id.button2);
        applyConstraintSet.clear(R.id.button3);

        applyConstraintSet.constrainWidth(R.id.button1,ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button2,ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button3,ConstraintSet.WRAP_CONTENT);

        applyConstraintSet.constrainHeight(R.id.button1,ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button2,ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button3,ConstraintSet.WRAP_CONTENT);

        applyConstraintSet.applyTo(constraintLayout);
    }
    private void apply7(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        /*applyConstraintSet.clear(R.id.button1);
        applyConstraintSet.clear(R.id.button2);
        applyConstraintSet.clear(R.id.button3);*/

        applyConstraintSet.centerVertically(R.id.button1, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerVertically(R.id.button2, ConstraintSet.PARENT_ID);
        applyConstraintSet.centerVertically(R.id.button3, ConstraintSet.PARENT_ID);

        applyConstraintSet.applyTo(constraintLayout);
    }
    private void apply8(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,8);
        applyConstraintSet.applyTo(constraintLayout);
    }

    private void reset1(){
        applyConstraintSet.clone(resetConstraintSet);
        TransitionManager.beginDelayedTransition(constraintLayout);
        resetConstraintSet.applyTo(constraintLayout);
    }

}
