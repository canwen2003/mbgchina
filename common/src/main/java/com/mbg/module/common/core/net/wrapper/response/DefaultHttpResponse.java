package com.mbg.module.common.core.net.wrapper.response;

public abstract class DefaultHttpResponse extends AbstractResponse<String> {
    @Override
    protected void onFailure(Throwable error, int statusCode, String content) {

    }

    @Override
    protected void onSuccess(int statusCode, String content) {

    }

    @Override
    public void onUIStart() {

    }

    @Override
    public void onUIUpdate(String data) {

    }

    @Override
    public void onUICache(String data) {

    }

    @Override
    public void onUIError(Exception error) {

    }

    @Override
    public void onUIFinish() {

    }
}
