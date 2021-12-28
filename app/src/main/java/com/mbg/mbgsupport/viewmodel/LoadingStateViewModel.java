package com.mbg.mbgsupport.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/***
 * created by gap
 * 数据加载状态的ViewModel
 */
public class LoadingStateViewModel extends ViewModel {
    private final MutableLiveData<LoadingState> mLoadingState=new MutableLiveData<>();

    public MutableLiveData<LoadingState> getLoadingState(){
        return mLoadingState;
    }

    public void setLoadingSate(LoadingState loadingSate){
        if (loadingSate!=null){
            mLoadingState.postValue(loadingSate);
        }
    }

    public enum LoadingState{
        START,//开始加重
        FINISH //加重完成
    }
}
