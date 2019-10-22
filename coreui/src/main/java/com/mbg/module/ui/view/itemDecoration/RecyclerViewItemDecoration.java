package com.mbg.module.ui.view.itemDecoration;

import android.graphics.Rect;
import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mbg.module.common.core.annotations.Dp;
import com.mbg.module.common.util.UiUtils;

/****
 *
 * Created by Gap
 * 用于RecyclerView的padding和item之间margin的设置
 */
public class RecyclerViewItemDecoration extends BaseItemDecoration {

    /**
     * 一般用于LinearLayoutManager
     * @param spacing 间距 dp
     */
    public RecyclerViewItemDecoration(@Dp int spacing ){

        super( 1, UiUtils.dip2px(spacing));
    }


    /***
     * 一般用于GridLayoutManager或瀑布模型的间距的设置
     *
     * @param spanCount item的列数
     * @param spacing  列间距和行间距
     */
    public RecyclerViewItemDecoration(int spanCount, @Dp int spacing ){
        super( spanCount, UiUtils.dip2px(spacing) );
    }

    @Override
    public void setPadding(RecyclerView rv, @Dp int padding ){
        super.setPadding( rv, UiUtils.dip2px(padding));
    }

    @Override
    public void setPadding(RecyclerView rv, @Dp int top, @Dp int bottom, @Dp int left, @Dp int right ){
        super.setPadding( rv, UiUtils.dip2px(top), UiUtils.dip2px(bottom), UiUtils.dip2px(left), UiUtils.dip2px(right));
    }

    @Override
    public void setOnItemClickListener( OnItemClickListener listener ){
        super.setOnItemClickListener( listener );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state ){
        super.getItemOffsets( outRect, view, parent, state );
        int orientation = OrientationHelper.VERTICAL;
        boolean isInverse = false;

        int position = parent.getChildAdapterPosition( view );

        int spanCurrent = position % getSpanCount();
        if( parent.getLayoutManager() instanceof StaggeredGridLayoutManager){
            orientation = ( (StaggeredGridLayoutManager) parent.getLayoutManager() ).getOrientation();
            isInverse = ( (StaggeredGridLayoutManager) parent.getLayoutManager() ).getReverseLayout();
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            spanCurrent = lp.getSpanIndex();
        }else if( parent.getLayoutManager() instanceof GridLayoutManager){
            orientation = ( (GridLayoutManager) parent.getLayoutManager() ).getOrientation();
            isInverse = ( (GridLayoutManager) parent.getLayoutManager() ).getReverseLayout();
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            spanCurrent = lp.getSpanIndex();

        }else if( parent.getLayoutManager() instanceof LinearLayoutManager){
            orientation = ( (LinearLayoutManager) parent.getLayoutManager() ).getOrientation();
            isInverse = ( (LinearLayoutManager) parent.getLayoutManager() ).getReverseLayout();
            position = parent.getChildAdapterPosition( view ); // item position
            spanCurrent = 0;
        }

        setupClickLayoutMarginItem( parent.getContext(), view, position, spanCurrent, state );
        calculateMargin(
                outRect,
                position,
                spanCurrent,
                state.getItemCount(),
                orientation,
                isInverse);
    }

}
