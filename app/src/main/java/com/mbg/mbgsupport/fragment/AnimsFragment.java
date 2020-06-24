package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class AnimsFragment extends BaseFragment implements View.OnClickListener{

    private LottieAnimationView mLottieAnimationView;
    public static void show(Context context){
        TerminalActivity.show(context, AnimsFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_anim;
    }

    @Override
    protected void initView() {
        mLottieAnimationView=findViewById(R.id.lt_anim_view);
        mLottieAnimationView.setImageResource(R.drawable.girl);
        mLottieAnimationView.setOnClickListener(this);
        mLottieAnimationView.setRepeatMode(LottieDrawable.RESTART);
        findViewById(R.id.btn_pop_out).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }

        if (viewId==R.id.lt_anim_view){
            mLottieAnimationView.setAnimation("LottieJson/flash_chat_friend_applying.json");
            mLottieAnimationView.setImageAssetsFolder("LottieImg");
            mLottieAnimationView.playAnimation();
        }else {
            mLottieAnimationView.setAnimation("LottieJson/flash_chat_friend_receive.json");
            mLottieAnimationView.setImageAssetsFolder("LottieImg");
            mLottieAnimationView.playAnimation();
        }
    }

}
