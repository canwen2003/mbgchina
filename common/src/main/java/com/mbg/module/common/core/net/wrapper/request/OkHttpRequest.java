package com.mbg.module.common.core.net.wrapper.request;

import android.text.TextUtils;

import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.common.NetStatus;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.tool.OkHttpUtils;
import com.mbg.module.common.core.net.tool.StatusCodeUtils;
import com.mbg.module.common.core.net.wrapper.response.AbstractResponse;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;
import com.mbg.module.common.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class OkHttpRequest {

    private long startTime;

    public OkHttpRequest() {
    }

    public Call request(HttpRequest requestWrapper) {
        if (HttpManager.isDebug()) {
            LogUtils.d("use okhttp to execute");
        }

        AbstractResponse<?> responseHandler = requestWrapper.getResponseHandler();

        if(responseHandler != null) {
            responseHandler.onStart();
        }

        startTime = System.currentTimeMillis();
        String url = requestWrapper.getRealUrl();

        try {
            Request.Builder requestBuild = new Request.Builder();
            HttpType httpType = requestWrapper.getType();
            switch (httpType) {
                case GET:
                    requestBuild.get();
                    break;
                case POST:
                    requestBuild.post(getBody(requestWrapper));
                    break;
                case PUT:
                    requestBuild.put(getBody(requestWrapper));
                    break;
                case DELETE:
                    requestBuild.delete();
                    break;
            }

            // 设置url
            requestBuild.url(url);

            // 添加头信息
            Map<String, String> headerPairs = requestWrapper.getHeaders();
            if (headerPairs != null && headerPairs.size() > 0) {
                Iterator<Map.Entry<String, String>> headersIter = headerPairs.entrySet().iterator();
                while (headersIter.hasNext()) {
                    Map.Entry<String, String> headerEntry = headersIter.next();
                    requestBuild.addHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }

            Call call = OkHttpUtils.newCall(requestBuild.build());
            call.enqueue(getCallback(responseHandler));

            return call;

        } catch (Exception e) {
            e.printStackTrace();
            if (responseHandler != null) {
                responseHandler.onFailure(e, StatusCodeUtils.getStatusCode(e), e.getMessage());
            }
        }

        return null;
    }

    /**
     *
     * @param responseHandler
     * @return
     */
    private Callback getCallback(final AbstractResponse<?> responseHandler) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (responseHandler != null) {
                    responseHandler.onFailure(e, StatusCodeUtils.getStatusCode(e), e.getMessage());
                    responseHandler.onFinish();
                    printTakeTime(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (responseHandler != null) {

                    int statusCode;
                    String url = call.request().url().toString();

                    if (response == null) {
                        responseHandler.onFailure(new Exception("response is null!"), NetStatus.RESPONSE_EMPTY,null);
                    } else {
                        statusCode = response.code();
                        if (response.body() == null) {
                            responseHandler.onFailure(new Exception("response body is null!"),NetStatus.RESPONSE_EMPTY,null);
                        } else {
                            if (responseHandler instanceof DefaultHttpResponse) {
                                DefaultHttpResponse stringResponseHandler = (DefaultHttpResponse) responseHandler;
                                String responseBody = "";
                                try {
                                    responseBody = response.body().string();
                                    if (response.isSuccessful()) {
                                        stringResponseHandler.onSuccess(statusCode, responseBody);
                                    } else {
                                        stringResponseHandler.onFailure(new Exception("response is not successful!"), statusCode,"response is not successful!");
                                    }
                                } catch (IOException e) {
                                    if (statusCode == 0) {
                                        statusCode = StatusCodeUtils.getStatusCode(e);
                                    }
                                    stringResponseHandler.onFailure(e, statusCode, responseBody);
                                }
                            } else {
                                responseHandler.onSuccess(statusCode,response);
                            }
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
     * @return
     */
    private RequestBody getBody(HttpRequest requestWrapper) {

        RequestParams requestParameter = requestWrapper.getRequestParams();
        if (requestParameter == null) {
            return RequestBody.create(null, "");
        }

        if (requestParameter.jsonBody != null) {
            // 上传json
            return RequestBody.create(MediaType.parse("application/json"), requestParameter.jsonBody);

        } else if (requestParameter.formBody != null) {
            // 上传form数据
            FormBody.Builder builder = new FormBody.Builder();
            Iterator<Map.Entry<String, String>> entryIter = requestParameter.formBody.entrySet().iterator();
            while (entryIter.hasNext()) {
                Map.Entry<String, String> entry = entryIter.next();
                builder.add(entry.getKey(), entry.getValue());
            }
            return builder.build();

        } else if (requestParameter.bytesBody != null) {
            // 上传二进制数据
            return RequestBody.create(MediaType.parse("application/octet-stream"), requestParameter.bytesBody);

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
            Iterator<Map.Entry<String, String>> entryIter = requestParameter.urlParams.entrySet().iterator();
            while (entryIter.hasNext()) {
                Map.Entry<String, String> entry = entryIter.next();
                builder.add(entry.getKey(), entry.getValue());
            }
            return builder.build();

        }

        return RequestBody.create(null, "");
    }

    // 为了实现上传文件增加进度回调的功能
    // 否则可直接使用RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
    private static RequestBody createUploadProgressRequestBody(final MediaType contentType, final File file, final AbstractResponse<?> responseHandler) {
        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return file.length();
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
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
                            int percent =  (int) (((float) readCount * 100) / total);
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
        }
    }

}
