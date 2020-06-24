package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class AnimsFragment extends BaseFragment implements View.OnClickListener{

    private LottieAnimationView mLottieAnimationView;
    private ImageView mImageView;
    private Bitmap mSourceBitmap;
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
        findViewById(R.id.btn_fun1).setOnClickListener(this);
        findViewById(R.id.btn_fun2).setOnClickListener(this);
        findViewById(R.id.btn_fun3).setOnClickListener(this);
        findViewById(R.id.btn_fun4).setOnClickListener(this);
        mImageView =findViewById(R.id.show_result);
        mSourceBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.girl);
    }

    private float progress=0.01f;
    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId==R.id.lt_anim_view){
            mLottieAnimationView.setAnimation("LottieJson/flash_chat_friend_applying.json");
            mLottieAnimationView.setImageAssetsFolder("LottieImg");
            mLottieAnimationView.playAnimation();
            progress=0.01f;
        }else if (viewId==R.id.btn_pop_out){

            if (progress==0.01f) {
                mLottieAnimationView.setAnimation("LottieJson/flash_chat_friend_receive.json");
                mLottieAnimationView.setImageAssetsFolder("LottieImg");
            }
            mLottieAnimationView.setProgress(progress);
            progress+=0.01f;
           // mLottieAnimationView.playAnimation();
        }else if (viewId==R.id.btn_fun1){
            usingRoundRect();
        }
        else if (viewId==R.id.btn_fun2){
            usingCircle();
        }
        else if (viewId==R.id.btn_fun3){
            usingOval();
        }
        else if (viewId==R.id.btn_fun4){
            usingArc();
        }
    }

    //圆角矩形
    public void usingRoundRect(){
        Bitmap b = Bitmap.createBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
        p.setColor(Color.RED);
        c.drawRoundRect(0,0,mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),150,150,p);
        Bitmap result = getResultBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),b);
        mImageView.setImageBitmap(result);
    }

    //
    public void usingArc(){
        Bitmap b = Bitmap.createBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
        p.setColor(Color.RED);

        RectF rectF = new RectF(0, 0,mSourceBitmap.getWidth(),mSourceBitmap.getHeight());
        c.drawArc(rectF, 240, 60, true, p);

        Bitmap result = getResultBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),b);
        mImageView.setImageBitmap(result);
    }

    //圆形
    public void usingCircle(){
        Bitmap b = Bitmap.createBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
        p.setColor(Color.RED);
        c.drawCircle(mSourceBitmap.getWidth()/2,mSourceBitmap.getHeight()/2,Math.min(mSourceBitmap.getWidth()/2,mSourceBitmap.getHeight()/2),p);
        Bitmap result = getResultBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),b);
        mImageView.setImageBitmap(result);
    }

    //椭圆
    public void usingOval(){
        Bitmap b = Bitmap.createBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
        p.setColor(Color.RED);
        c.drawOval(0,0,mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),p);
        Bitmap result = getResultBitmap(mSourceBitmap.getWidth(),mSourceBitmap.getHeight(),b);
        mImageView.setImageBitmap(result);
    }

    private Bitmap getResultBitmap(int width,int height,Bitmap maskBitmap){
        Bitmap b = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.FILTER_BITMAP_FLAG);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        c.drawBitmap(mSourceBitmap,0,0,p);
        p.setXfermode(xfermode);
        c.drawBitmap(maskBitmap,0,0,p);
        p.setXfermode(null);
        return b;
    }


}
