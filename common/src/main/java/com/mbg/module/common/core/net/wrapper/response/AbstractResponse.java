package com.mbg.module.common.core.net.wrapper.response;


import com.mbg.module.common.core.net.common.MessageType;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.common.util.TypeUtils;

import okhttp3.Response;

public abstract class AbstractResponse<D> implements IResponse<D> {
    protected String mUrl;

    public void onStart(){
        sendResponse(MessageType.START,null);
    }

    public void onCache(D obj){
        sendResponse(MessageType.CACHE,obj);
    }

    protected void onUpdate(D obj){
        sendResponse(MessageType.UPDATE,obj);
    }

    protected void onError(Exception e){
        sendResponse(MessageType.ERROR,e);
    }

    public void onFinish(){
        sendResponse(MessageType.FINISH,null);
    }

    @Override
    public void onUIStart() {

    }

    @Override
    public void onUICache(D data) {

    }


    @Override
    public void onUIUpdate(D data) {

    }

    @Override
    public void onUIError(Exception error) {

    }

    @Override
    public void onUIFinish() {

    }

    public abstract void onFailure(Exception error, int statusCode, String content);

    public abstract void onSuccess(int statusCode, String content);

    public abstract void onSuccess(int statusCode, Response response);

    public void setUrl(String url){
        mUrl=url;
    }

    private void sendResponse(final MessageType type,final Object obj){

        ThreadUtils.postInUIThread(new Runnable() {
            @Override
            public void run() {
                switch (type){
                    case START:
                        onUIStart();
                        break;
                    case CACHE:
                        D cacheData=TypeUtils.cast(obj);
                        onUICache(cacheData);
                        break;
                    case UPDATE:
                        D updateData=TypeUtils.cast(obj);
                        onUIUpdate(updateData);
                        break;
                    case ERROR:
                        if (obj instanceof Exception) {
                            Exception exception = TypeUtils.cast(obj);
                            onUIError(exception);
                        }else {
                            onUIError(new Exception("unKnow Error"));
                        }
                        break;
                    case FINISH:
                        onUIFinish();
                        break;
                }
            }
        });

    }
}
