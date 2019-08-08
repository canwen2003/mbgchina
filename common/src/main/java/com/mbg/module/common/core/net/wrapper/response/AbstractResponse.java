package com.mbg.module.common.core.net.wrapper.response;


import com.mbg.module.common.core.net.common.MessageType;
import com.mbg.module.common.util.ThreadUtils;
import com.mbg.module.common.util.TypeUtils;

public abstract class AbstractResponse<D> implements IResponse<D> {

    public void onStart(){
        sendResponse(MessageType.START,null);
    }

    public void onCache(D obj){
        sendResponse(MessageType.CACHE,obj);
    }

    public void onUpdate(D obj){
        sendResponse(MessageType.UPDATE,obj);
    }

    public void onError(Exception e){
        sendResponse(MessageType.ERROR,e);
    }

    public void onFinish(){
        sendResponse(MessageType.FINISH,null);
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
                        Exception exception=TypeUtils.cast(obj);
                        onUIError(exception);
                    case FINISH:
                        onUIFinish();
                        break;
                }
            }
        });

    }
}
