package com.mbg.module.ui.view.banner.holder;

import android.view.View;

/****
 *  Banner ViewHolder创建器
 */
public interface CBViewHolderCreator {
	Holder createHolder(View itemView);
	int getLayoutId();
}
