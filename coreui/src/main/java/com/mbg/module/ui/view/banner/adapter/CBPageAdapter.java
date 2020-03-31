package com.mbg.module.ui.view.banner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mbg.module.ui.view.banner.holder.CBViewHolderCreator;
import com.mbg.module.ui.view.banner.holder.Holder;
import com.mbg.module.ui.view.banner.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter extends RecyclerView.Adapter<Holder>{
    protected List<?> mData;
    private CBViewHolderCreator creator;
    private boolean canLoop;
    private OnItemClickListener onItemClickListener;

    public CBPageAdapter(CBViewHolderCreator creator, List<?> data, boolean canLoop) {
        this.creator = creator;
        this.mData = data;
        this.canLoop = canLoop;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = creator.getLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return creator.createHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int realPosition = position% mData.size();

        holder.updateUI(mData.get(realPosition));

        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new OnPageClickListener(realPosition));
        }
    }

    @Override
    public int getItemCount() {
        //根据模式决定长度
        if(mData.size() == 0) return 0;
        return canLoop ? 3* mData.size() : mData.size();
    }

    public void setCanLoop(boolean canLoop){
        this.canLoop = canLoop;
    }

    public int getRealItemCount(){
        return mData !=null? mData.size():0;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class OnPageClickListener implements View.OnClickListener {
        private int position;

        public OnPageClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(position);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
