package com.mbg.module.common.core.net.wrapper.response;

import com.mbg.module.common.core.net.tool.StatusCodeUtils;

import okhttp3.Response;

public abstract class DefaultHttpResponse extends AbstractResponse<String> {
    @Override
    public void onFailure(Exception error, int statusCode, String content) {
        StatusCodeUtils.pareHttpFailure(error,statusCode,content,mUrl);
        onError(error);
    }

    @Override
    public void onSuccess(int statusCode, String content) {

    }

    @Override
    public void onSuccess(int statusCode, Response response) {

    }

    @Override
    public void onUIUpdate(String data) {

    }
}
