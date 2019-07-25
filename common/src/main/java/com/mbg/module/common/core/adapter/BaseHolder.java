package com.mbg.module.common.core.adapter;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.mbg.module.common.core.listener.OnItemClickListener;
import com.mbg.module.common.core.manager.ThreadPoolManager;
import com.mbg.module.common.core.manager.ThreadPoolPriority;
import com.mbg.module.common.core.manager.ThreadPoolRunnable;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Gap
 * 创建RecyclerView.ViewHolder基础 holder类，用来进行数据缓存和控件操作
 */
public class BaseHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews=new SparseArray<>(24);
    private AsyncImageLoader mAsyncImageLoader=new AsyncImageLoader();


    public BaseHolder(View itemView) {
        super(itemView);
    }


    //根据控件ID获取控件对象
    public <T> T getView(Integer viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=itemView.findViewById(viewId);
            //缓存
            mViews.put(viewId,view);
        }
        return (T)view;
    }


    //根据TextView 的ID设置文本内容
    public BaseHolder setText(Integer viewId, String value){
        TextView view=getView(viewId);

        if (view!=null){
            view.setText(value);
        }

        return this;
    }

    //根据TextView的ID设置文本文字颜色
    public BaseHolder setTextColor(Integer viewId, int color){
        TextView view=getView(viewId);

        if (view!=null){
            view.setTextColor(color);
        }
        return this;
    }

    //根据TextView的ID设置文本内容和文字颜色
    public BaseHolder setText(Integer viewId, String value, int color){
        TextView view=getView(viewId);

        if (view!=null){
            view.setText(value);
            view.setTextColor(color);
        }

        return this;
    }

    //根据控件ID设置控件是否显示
    public BaseHolder setVisibility(Integer viewId, int visibility){
        View view=getView(viewId);

        if (view!=null){
            view.setVisibility(visibility);
        }

        return this;
    }

    //根据控件ID设置图形控件透明度
    public BaseHolder setImageAlpha(Integer viewId, int alpha){
        ImageView view=getView(viewId);

        if (view!=null){
            view.setImageAlpha(alpha);
        }
        return this;
    }

    //设置ImageView的图像
    public BaseHolder setImageResource(Integer viewId, Integer resId){
        ImageView view=getView(viewId);

        if (view!=null){
            view.setImageResource(resId);
        }

        return this;
    }

    //通过异步方式设置ImageView的图像
    public BaseHolder setImageResource(Integer viewId, String imageUrl, Integer defaultResId){
        final ImageView view=getView(viewId);
        Drawable drawable= mAsyncImageLoader.loadDrawable(imageUrl, new AsyncImageLoader.ImageCallback() {
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                if (view != null) {
                    view.setImageDrawable(imageDrawable);
                }
            }
        });

        if (drawable!=null){
            view.setImageDrawable(drawable);
        }
        else{
            view.setImageResource(defaultResId);
        }

        return this;
    }


   //设置RecyclerView的Item的点击和长按事件
    public BaseHolder setItemOnClickListener(Integer viewId, final OnItemClickListener listener){
        View view=getView(viewId);

        if ((view!=null)&&listener!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,(Integer) itemView.getTag());
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener!=null){
                        listener.onLongClick(v,(Integer) itemView.getTag());
                    }
                    return true;
                }
            });
        }

        return this;
    }

    //设置RecyclerView的Itemd的子控件的点击事件
    public BaseHolder setSubItemOnClickListener(Integer viewId, View.OnClickListener listener){
        View view=getView(viewId);

        if (view!=null&&listener!=null){
            view.setOnClickListener(listener);
        }

        return this;
    }

    //图像异步加载内部类，用于设置ImageView的异步加载和缓存
    private static class AsyncImageLoader {
        private static final String TAG=AsyncImageLoader.class.getSimpleName();
        private static volatile HashMap<String, SoftReference<Drawable>> imageCache;

        private AsyncImageLoader() {
            if (imageCache == null) {
                synchronized (AsyncImageLoader.class){
                    if (imageCache==null) {
                        imageCache = new HashMap<>();
                    }
                }
            }
        }

        public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
            //如果一级缓存里存在图片（内存），则用内存中的图片
            if (imageCache.containsKey(imageUrl)) {
                //Get from cached HashMap
                SoftReference<Drawable> softReference = imageCache.get(imageUrl);

                Drawable drawable = softReference.get();
                if (drawable != null) {
                    return drawable;
                }
            }

            //从网络端取数据
            final Handler handler = new AsyncImageLoader.ImageHandler(imageUrl, imageCallback);
            ThreadPoolManager.getInstance().start(new ThreadPoolRunnable(
                    "BaseHolderLoadImg", ThreadPoolPriority.THREAD_PRIORITY_HIGHEST) {
                @Override
                public void run() {
                    super.run();

                    Drawable drawable = loadImageFromUrl(imageUrl);
                    imageCache.put(imageUrl, new SoftReference<>(drawable));
                    Message message = handler.obtainMessage(0, drawable);
                    handler.sendMessage(message);
                }
            });

            return null;
        }



        private static class ImageHandler extends Handler {
            private String mImageUrl;
            private AsyncImageLoader.ImageCallback ImageCallback;

            private ImageHandler(final String imageUrl, final ImageCallback imageCallback) {
                mImageUrl = imageUrl;
                ImageCallback = imageCallback;
            }

            @Override
            public void handleMessage(Message msg) {
                ImageCallback.imageLoaded((Drawable) msg.obj, mImageUrl);
            }
        }

        public static Drawable loadImageFromUrl(String url) {
            URL m;
            InputStream inputStream = null;
            try {
                m = new URL(url);
                inputStream = (InputStream) m.getContent();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            return Drawable.createFromStream(inputStream, "src");
        }

        public interface ImageCallback {
             void imageLoaded(Drawable imageDrawable, String imageUrl);
        }
    }
}