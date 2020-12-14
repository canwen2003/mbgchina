package com.mbg.module.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbg.module.common.core.listener.OnItemClickListener;

import java.util.List;

@SuppressWarnings("unused")
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> implements View.OnClickListener, View.OnLongClickListener {
    private final int mLayoutId;
    private final List<T> mList;
    protected int mSelectIndex = -1;//用来标识选择的item
    protected int mDownloadIndex = -1;//用来标记选择需要下载的item
    protected OnItemClickListener mOnItemClickListener; //每个Item的点击事件
    protected Context mContext;

    public BaseAdapter(Context context, int layoutId, List<T> list) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mList = list;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        BaseHolder holder = new BaseHolder(view);
        holder.setIsRecyclable(false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        T item = mList.get(position);
        holder.itemView.setTag(position);
        convert(holder, item, position);
    }

    protected void convert(final BaseHolder holder, T item, int position) {

    }

    public void setSelectIndex(int index) {
        mSelectIndex = index;
    }

    public void setDownloadIndex(int downloadIndex){
        mDownloadIndex=downloadIndex;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}