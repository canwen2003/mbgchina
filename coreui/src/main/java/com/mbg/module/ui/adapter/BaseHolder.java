package com.mbg.module.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbg.module.common.core.listener.OnItemClickListener;
import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.cache.engine.factory.DisplayImageOptionsFactory;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;

/**
 * Created by Gap
 * 创建RecyclerView.ViewHolder基础 holder类，用来进行数据缓存和控件操作
 */
public class BaseHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews=new SparseArray<>(24);
    private LoadOptions mLoadOptions;

    public BaseHolder(View itemView) {
        super(itemView);
        mLoadOptions=LoadOptions.get().setEnableCache(true);

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
        ImageLoader imageLoader=ImageLoader.getInstance();
        if (imageLoader!=null) {
            mLoadOptions.setImageOnFail(defaultResId);
            mLoadOptions.setDefaultImageResId(defaultResId);
            imageLoader.displayImage(imageUrl, view, DisplayImageOptionsFactory.get().getImageOptions(mLoadOptions));
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

}