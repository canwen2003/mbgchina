package com.mbg.module.common.core.net.wrapper.request;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.common.NetStatus;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.manager.OkHttpManager;
import com.mbg.module.common.core.net.tool.StatusCodeUtils;
import com.mbg.module.common.core.net.wrapper.response.AbstractResponse;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;
import com.mbg.module.common.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static okhttp3.internal.Util.EMPTY_REQUEST;

public class OkHttpRequest {

    private long startTime;

    public OkHttpRequest() {
    }

    public Call request(HttpRequest requestWrapper) {
        if (HttpManager.isDebug()) {
            LogUtils.d("start OKHttp execute");
        }
        AbstractResponse<?> responseHandler = requestWrapper.getResponseHandler();
        if (responseHandler==null){
            LogUtils.e("please setup response handler!!");
            return null;
        }

        //通知上层、开始创建请求
        responseHandler.onStart();
        startTime = System.currentTimeMillis();

        try {
            Request.Builder requestBuilder = new Request.Builder();
            HttpType httpType = requestWrapper.getType();
            switch (httpType) {
                case GET:
                    requestBuilder.get();
                    break;
                case POST:
                    requestBuilder.post(getRequestBody(requestWrapper));
                    break;
                case PUT:
                    requestBuilder.put(getRequestBody(requestWrapper));
                    break;
                case DELETE:
                    requestBuilder.delete();
                    break;
            }

            // 设置url
            requestBuilder.url(requestWrapper.getRealUrl());

            // 添加头信息
            Map<String, String> headerPairs = requestWrapper.getHeaders();
            if (headerPairs != null && headerPairs.size() > 0) {
                for (Map.Entry<String, String> header : headerPairs.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            Call call = OkHttpManager.get().newCall(requestBuilder.build());
            call.enqueue(getCallback(responseHandler));

            return call;

        } catch (Exception e) {
            responseHandler.onFailure(e, StatusCodeUtils.getStatusCode(e), e.getMessage());
            responseHandler.onFinish();
        }

        return null;
    }


    private Callback getCallback(final @NonNull AbstractResponse<?> responseHandler) {
        return new Callback() {
            @Override
            public void onFailure( Call call, @NonNull IOException e) {
                responseHandler.onFailure(e, StatusCodeUtils.getStatusCode(e), e.getMessage());
                responseHandler.onFinish();
                printTakeTime(null);
            }

            @Override
            public void onResponse( Call call, Response response) {
                if (response == null) {
                    responseHandler.onFailure(new Exception("response is null!"), NetStatus.RESPONSE_EMPTY, null);
                } else {
                   int statusCode = response.code();
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) {
                        responseHandler.onFailure(new Exception("response body is null!"), NetStatus.RESPONSE_EMPTY, null);
                    } else {
                        if (responseHandler instanceof DefaultHttpResponse) {
                            DefaultHttpResponse stringResponseHandler = (DefaultHttpResponse) responseHandler;
                            try {
                                String body = responseBody.string();
                                if (response.isSuccessful()) {
                                    stringResponseHandler.onSuccess(statusCode, body);
                                } else {
                                    stringResponseHandler.onFailure(new Exception("response is not successful!"), statusCode, "response is not successful!");
                                }
                            } catch (IOException e) {
                                if (statusCode == 0) {
                                    statusCode = StatusCodeUtils.getStatusCode(e);
                                }
                                stringResponseHandler.onFailure(e, statusCode, null);
                            }
                        } else {
                            responseHandler.onSuccess(statusCode, response);
                        }
                    }
                }


                responseHandler.onFinish();
                printTakeTime(response);
            }
        };
    }

    /**
     * 根据RequestParams参数，返回需要上传的body结构
     *
     * @return 对象
     */
    private RequestBody getRequestBody(HttpRequest requestWrapper) {
        RequestParams requestParameter = requestWrapper.getRequestParams();
        if (requestParameter == null) {
            return EMPTY_REQUEST;
        }

        if (requestParameter.jsonBody != null) {
            // 上传json
            return RequestBody.create(requestParameter.jsonBody, MediaType.parse("application/json"));

        } else if (requestParameter.formBody != null) {
            // 上传form数据
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> param : requestParameter.formBody.entrySet()) {
                builder.add(param.getKey(), param.getValue());
            }
            return builder.build();

        } else if (requestParameter.bytesBody != null) {
            // 上传二进制数据
            return RequestBody.create(requestParameter.bytesBody, MediaType.parse("application/octet-stream"));

        } else if (!TextUtils.isEmpty(requestParameter.uploadFilePath)) {
            // 上传文件
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            // Add string params
            for (ConcurrentHashMap.Entry<String, String> entry : requestParameter.urlParams.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }

            File file = new File(requestParameter.uploadFilePath);
            RequestBody uploadFile = createUploadProgressRequestBody(MediaType.parse("image/jpeg"), file, requestWrapper.getResponseHandler());
            if (TextUtils.isEmpty(requestParameter.uploadParameterName)) {
                builder.addFormDataPart(file.getName(), file.getName(), uploadFile);
            } else {
                builder.addFormDataPart(requestParameter.uploadParameterName, file.getName(), uploadFile);
            }
            return builder.build();

        } else if (requestParameter.urlParams != null) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> param : requestParameter.urlParams.entrySet()) {
                builder.add(param.getKey(), param.getValue());
            }
            return builder.build();
        }

        return EMPTY_REQUEST;
    }

    private static RequestBody createUploadProgressRequestBody(final MediaType contentType, final File file, final AbstractResponse<?> responseHandler) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(@NonNull BufferedSink sink) {
                Source source;
                try {
                    long total = contentLength();
                    long readCount = 0;
                    int lastPercent = -1;
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    for (long count; (count = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, count);
                        readCount += count;
                        if (responseHandler != null) {
                            int percent = (int) (((float) readCount * 100) / total);
                            if (percent != lastPercent) {
                                // responseHandler.sendProgressMessage(percent, (int) total);
                                lastPercent = percent;
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void printTakeTime(Response response) {
        if (HttpManager.isDebug()) {
            StringBuilder strBuilder = new StringBuilder("request take time:")
                    .append(System.currentTimeMillis() - startTime);
            if (null != response) {
                strBuilder
                        .append(" / real take time:")
                        .append(response.receivedResponseAtMillis() - response.sentRequestAtMillis())
                        .append(" [")
                        .append(response.request().url().toString())
                        .append("]");
            }

            LogUtils.e(strBuilder.toString());
        }
    }

}
