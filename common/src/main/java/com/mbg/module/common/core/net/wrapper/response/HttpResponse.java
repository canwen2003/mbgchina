package com.mbg.module.common.core.net.wrapper.response;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mbg.module.common.core.net.model.HttpModel;
import com.mbg.module.common.util.FileCacheUtils;
import com.mbg.module.common.util.JsonUtils;
import com.mbg.module.common.util.ThreadUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpResponse<D extends HttpModel> extends AbstractResponse{
    private Type mDataType;
    private String mCacheKey;
    private boolean mReadCache = false; // 是否已经读取缓存
    private boolean mWriteCache = false; // 是否已经存储了缓存

    public HttpResponse() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Type[] typeArray = ((ParameterizedType) superClass).getActualTypeArguments();
            if (typeArray.length > 0) {
                this.mDataType = typeArray[0];
            }
        }
    }

    /***
     * 带cache功能的Response
     * @param cacheKey cache key值
     */
    public HttpResponse(@NonNull String cacheKey){
        this();
        mCacheKey=cacheKey;
    }

    @Override
    public void onUIStart() {

    }

    public void onUIUpdate(D data){}

    public void onUICache(D data) {}

    @Override
    public void onUIError(Exception error) {

    }

    @Override
    public void onUIFinish() {

    }

    @Override
    protected void onFailure(Throwable error, int statusCode, String content) {

    }

    @Override
    protected void onSuccess(int statusCode, String content) {
        Exception exception = null;
        D data = null;
        try {
            data = parseData(content);
            if (data == null && mDataType != null) {
                // response为空或不能正确解析数据, 抛出异常触发onFailure阶段
                throw new RuntimeException("response is null or parse data failed");
            }
        } catch (Exception e) {
            exception = e;
        }

        onSaveCache(content);
        final D dataCopy = data;
        onUpdate(dataCopy);
    }

    /**
     * 读取缓存
     * 目前的逻辑：如果缓存格式陈旧，导致解析失败，则直接不处理，也不会回调
     */
    public void onReadCache() {
        if (!mReadCache && !TextUtils.isEmpty(mCacheKey)) {
            try {
                mReadCache = true;
                ThreadUtils.postInThread(new Runnable() {
                    @Override
                    public void run() {
                        String cacheContent = FileCacheUtils.getContent(mCacheKey);
                        final D cacheData = parseData(cacheContent);
                        // 有无缓存都通知业务层进行处理
                        onCache(cacheData);
                        mReadCache=false;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存缓存
     * @param content
     */
    private void onSaveCache(final String content) {
        if (!TextUtils.isEmpty(content)) {
            if (!mWriteCache && !TextUtils.isEmpty(mCacheKey)) {
                mWriteCache = true;
                ThreadUtils.postInThread(new Runnable() {
                    @Override
                    public void run() {
                        FileCacheUtils.saveContent(mCacheKey, content);
                        mWriteCache = false;
                    }
                });
            }
        }
    }

    /**
     * 在非ui线程回调, 用于数据解析
     */
    protected D parseData(String response) {
        if (mDataType == null) {
            return null;
        }
        if (TextUtils.isEmpty(response)) {
            return null;
        }

        return JsonUtils.fromJson(response, mDataType);
    }
}
