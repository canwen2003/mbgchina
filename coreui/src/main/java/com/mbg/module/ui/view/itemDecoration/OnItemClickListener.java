package com.mbg.module.ui.view.itemDecoration;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener {
    void onClick(Context context, View v, int position, int spanIndex, RecyclerView.State state);
}
