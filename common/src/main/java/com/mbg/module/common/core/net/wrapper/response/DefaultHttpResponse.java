package com.mbg.module.common.core.net.wrapper.response;

import com.mbg.module.common.core.net.common.NetStatus;
import com.mbg.module.common.core.net.tool.StatusCodeUtils;
import com.mbg.module.common.util.StringUtils;

import okhttp3.Response;

public abstract class DefaultHttpResponse extends AbstractResponse<String> {
    @Override
    public void onFailure(Exception error, int statusCode, String content) {
        StatusCodeUtils.pareHttpFailure(error,statusCode,content,mUrl);
        onError(error);
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        if (StringUtils.isEmpty(content)){
            onFailure(new Exception("response is null!"), NetStatus.RESPONSE_EMPTY, null);
            return;
        }
        onUpdate(content);
    }

    @Override
    public void onSuccess(int statusCode, Response response) {
        if (response == null) {
            onFailure(new Exception("response is null!"), NetStatus.RESPONSE_EMPTY, null);
            return;
        }
        onSuccess(statusCode,response);
    }

    @Override
    public void onUIUpdate(String data) {

    }
}
