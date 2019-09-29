package com.mbg.module.common.core.net.wrapper.response;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mbg.module.common.core.net.common.NetStatus;
import com.mbg.module.common.core.net.model.HttpModel;
import com.mbg.module.common.core.net.tool.StatusCodeUtils;
import com.mbg.module.common.util.FileCacheUtils;
import com.mbg.module.common.util.JsonUtils;
import com.mbg.module.common.util.ThreadUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class HttpResponse<D extends HttpModel> extends AbstractResponse<D>{
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
    public void onFailure(Exception e, int statusCode, String content) {
        StatusCodeUtils.pareHttpFailure(e,statusCode,content,mUrl);
        onError(e);
    }

    @Override
    public void onUIUpdate(D data) {

    }

    @Override
    public void onSuccess(int statusCode, String content) {
        D data;
        try {
            data = parseData(content);
            if (data == null && mDataType != null) {
                onFailure(new Exception("parseData is null!"), NetStatus.PARSEDATA_ERROR, null);
                return;
            }
        } catch (Exception e) {
            onFailure(new Exception("Parse Data is null!"), NetStatus.PARSEDATA_ERROR, null);
            return;
        }

        onUpdate(data);
        onSaveCache(content);
    }

    @Override
    public void onSuccess(int statusCode, Response response) {
            if (response == null) {
                onFailure(new Exception("response is null!"), NetStatus.RESPONSE_EMPTY, null);
                return;
            }

            ResponseBody body = response.body();
            if (body!=null){
                try {
                    String content=body.string();
                    onSuccess(response.code(),content);
                }catch (Exception e){
                    onFailure(new Exception("Parse Body is null!"), NetStatus.PARSEDATA_ERROR, null);
                }

            }else {
                onFailure(new Exception("response Body is null!"), NetStatus.RESPONSE_BODY_EMPTY, null);
            }
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
        if (mDataType == null||TextUtils.isEmpty(response)) {
            return null;
        }

        return JsonUtils.fromJson(response, mDataType);
    }
}
