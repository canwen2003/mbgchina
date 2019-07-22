package com.mbg.module.common.core.listener;

import android.view.View;

public interface OnItemClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}