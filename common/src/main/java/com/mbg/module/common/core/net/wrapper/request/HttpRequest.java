package com.mbg.module.common.core.net.wrapper.request;


import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.manager.OkHttpManager;
import com.mbg.module.common.core.net.wrapper.response.AbstractResponse;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;
import com.mbg.module.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


public class HttpRequest implements IRequest{
	private HttpType mHttpType;
	private String mUrl;
	private String mContentType;
	private RequestParams mRequestParams;
	private Map<String, String> mHeaderMap = new HashMap<>();
	private AbstractResponse<?> mHttpResponse;
	private Call mHttpExecutor = null;
	private Object mData = null;
	private boolean mHttpDnsEnable;

	public HttpRequest(HttpType type, String url) {
		this.mHttpType = type;
		this.mUrl = url;
	}

	public HttpRequest downloadFile(String saveFilePath) {
		_getRequestParams().downloadFile(saveFilePath);
		return this;
	}

	public HttpRequest uploadFile(String uploadFilePath, String uploadParameterName) {
		_getRequestParams().uploadFile(uploadFilePath, uploadParameterName);
		return this;
	}


	public HttpRequest setCall(Call request) {
		this.mHttpExecutor = request;
		return this;
	}

	public HttpRequest setData(Object data) {
		this.mData = data;
		return this;
	}

	public Object getData() {
		return mData;
	}

	public String getRealUrl() {
		String realUrl = getUrl();
		if (getType() == HttpType.GET && getRequestParams() != null) {
			realUrl = RequestParams.getUrlWithQueryString(realUrl, getRequestParams());
		}
		return realUrl;
	}

	@Override
	public HttpRequest setType(HttpType type) {
		this.mHttpType = type;
		return this;
	}

	@Override
	public HttpType getType() {
		return mHttpType;
	}

	@Override
	public HttpRequest setUrl(String url) {
		this.mUrl = url;
		return this;
	}

	@Override
	public String getUrl() {
		return mUrl;
	}

	@Override
	public HttpRequest setContentType(String contentType) {
		this.mContentType = contentType;
		return this;
	}

	@Override
	public String getContentType() {
		return mContentType;
	}

	@Override
	public HttpRequest addRequestParams(String key, String value) {
		_getRequestParams().put(key, value);
		return this;
	}

	@Override
	public HttpRequest addRequestParams(Map<String, String> params) {
		if (params == null || params.size() <= 0) {
			return this;
		}

		for (Map.Entry<String, String> entry:params.entrySet()) {
			addRequestParams(entry.getKey(), entry.getValue());
		}
		return this;
	}

	@Override
	public RequestParams getRequestParams() {
		return mRequestParams;
	}

	@Override
	public HttpRequest setResponseHandler(AbstractResponse<?> responseHandler) {
		this.mHttpResponse = responseHandler;
		// 设置默认的response, 方便查看http返回信息以及上传网络日志
		if (this.mHttpResponse == null) {
			this.mHttpResponse = new DefaultHttpResponse() {
				@Override
				public void onSuccess(int statusCode, String content) {
					super.onSuccess(statusCode, content);
				}

				@Override
				public void onFailure(Exception error, int statusCode, String content) {
					super.onFailure(error, statusCode, content);
				}

			};
		}
		
		return this;
	}

	@Override
	public AbstractResponse<?> getResponseHandler() {
		return mHttpResponse;
	}
	
	@Override
	public HttpRequest setHeaders(HashMap<String, String> headers) {
		mHeaderMap = headers;
		return this;
	}

	@Override
	public HttpRequest addHeader(String key, String value) {
		mHeaderMap.put(key, value);
		return this;
	}

	@Override
	public HttpRequest addHeader(Map<String, String> headers) {
		if (headers == null || headers.size() <= 0) {
			return this;
		}
		mHeaderMap.putAll(headers);
		return this;
	}

	@Override
	public Map<String, String> getHeaders() {
		return mHeaderMap;
	}


	@Override
	public HttpRequest setBody(String jsonBody) {
		_getRequestParams().setJsonBody(jsonBody);
		this.mContentType = "application/json";
		return this;
	}

	@Override
	public HttpRequest setBody(Map<String, String> formMap) {
		_getRequestParams().setFormBody(formMap);
		this.mContentType = "application/x-www-form-urlencoded";
		return this;
	}

	@Override
	public HttpRequest setBody(byte[] bytesBody) {
		_getRequestParams().setBytesBody(bytesBody);
		this.mContentType = "application/octet-stream";
		return this;
	}

	@Override
	public HttpRequest setHttpDnsEnable(boolean enable) {
		this.mHttpDnsEnable = enable;
		return this;
	}

	@Override
	public boolean getHttpDnsEnable() {
		return this.mHttpDnsEnable;
	}

	@Override
	public HttpRequest execute() {
		if (HttpManager.isUserOkHttp()){
			OkHttpManager.get().execute(this);
		}
		return this;
	}

	@Override
	public synchronized void cancel() {
		if (mHttpExecutor != null) {
			mHttpExecutor.cancel();
			mHttpExecutor = null;
		}
	}

	@Override
	public int getRequestHashCode() {
		if (!StringUtils.isEmpty(mUrl)){
			return mUrl.hashCode();
		}
		return 0;
	}

	private RequestParams _getRequestParams() {
		if (mRequestParams == null) {
			mRequestParams = new RequestParams();
		}
		return mRequestParams;
	}
}
