package com.mbg.module.common.core.net.wrapper.request;

import android.content.Context;


import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.wrapper.response.AbstractResponse;
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;


public class HttpRequest {
	private int mContextHashCode;
	private HttpType mHttpType;
	private String mUrl;
	private String mContentType;
	private RequestParams mRequestParams;
	private Map<String, String> mHeaderMap = new HashMap<>();
	private AbstractResponse<?> mHttpResponse;
	private Call request = null;

	private Object data = null;
	/**
	 * 是否启用httpdns
 	 */
	private boolean httpdnsEnable;

	public HttpRequest(HttpType type, String url) {
		this.mHttpType = type;
		this.mUrl = url;
	}
	
	/**
	 * 下载文件
	 * @param saveFilePath
	 * @return
	 */
	public HttpRequest downloadFile(String saveFilePath) {
		_getRequestParams().downloadFile(saveFilePath);
		return this;
	}
	
	/**
	 * 上传文件
	 * @param uploadFilePath
	 * @return
	 */
	public HttpRequest uploadFile(String uploadFilePath, String uploadParameterName) {
		_getRequestParams().uploadFile(uploadFilePath, uploadParameterName);
		return this;
	}
	
	public HttpRequest setContext(Context context) {
		this.mContextHashCode = context.hashCode();
		return this;
	}
	
	public int getmContextHashCode() {
		return mContextHashCode;
	}
	
	public HttpRequest setType(HttpType type) {
		this.mHttpType = type;
		return this;
	}
	
	public HttpType getType() {
		return mHttpType;
	}

	public HttpRequest setUrl(String url) {
		this.mUrl = url;
		return this;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public HttpRequest setContentType(String contentType) {
		this.mContentType = contentType;
		return this;
	}
	
	public String getContentType() {
		return mContentType;
	}
	
	public HttpRequest addRequestParams(String key, String value) {
		_getRequestParams().put(key, value);
		return this;
	}
	
	public HttpRequest addRequestParams(Map<String, String> params) {
		if (params == null || params.size() <= 0) {
			return this;
		}
		Iterator<Map.Entry<String, String>> paramsIter = params.entrySet().iterator();
		while (paramsIter.hasNext()) {
			Map.Entry<String, String> paramEntery = paramsIter.next();
			addRequestParams(paramEntery.getKey(), paramEntery.getValue());
		}
		return this;
	}
	
	public RequestParams getRequestParams() {
		return mRequestParams;
	}
	
	public HttpRequest setResponseHandler(AbstractResponse<?> responseHandler) {
		this.mHttpResponse = responseHandler;
		// 设置默认的response, 方便查看http返回信息以及上传网络日志
		if (this.mHttpResponse == null) {
			this.mHttpResponse = new DefaultHttpResponse() {
				@Override
				protected void onSuccess(int statusCode, String content) {
					super.onSuccess(statusCode, content);
				}
			};
		}
		
		return this;
	}
	
	public AbstractResponse<?> getResponseHandler() {
		return mHttpResponse;
	}
	
	
	public HttpRequest setHeaders(HashMap<String, String> headers) {
		mHeaderMap = headers;
		return this;
	}
	
	public HttpRequest addHeader(String key, String value) {
		mHeaderMap.put(key, value);
		return this;
	}
	
	public HttpRequest addHeader(Map<String, String> headers) {
		if (headers == null || headers.size() <= 0) {
			return this;
		}
		mHeaderMap.putAll(headers);
		return this;
	}

	public Map<String, String> getHeadersMap() {
		return mHeaderMap;
	}

	public HttpRequest setCall(Call request) {
		this.request = request;
		return this;
	}

	public HttpRequest setJsonBody(String jsonBody) {
		_getRequestParams().setJsonBody(jsonBody);
		this.mContentType = "application/json";
		return this;
	}

	public HttpRequest setFormBody(Map<String, String> formMap) {
		_getRequestParams().setFormBody(formMap);
		this.mContentType = "application/x-www-form-urlencoded";
		return this;
	}

	public HttpRequest setBytesBody(byte[] bytesBody) {
		_getRequestParams().setBytesBody(bytesBody);
		this.mContentType = "application/octet-stream";
		return this;
	}
	
	public HttpRequest setData(Object data) {
		this.data = data;
		return this;
	}
	
	public Object getData() {
		return data;
	}

	public HttpRequest setHttpdnsEnable(boolean enable) {
		this.httpdnsEnable = enable;
		return this;
	}

	public boolean getHttpdnsEnable() {
		return this.httpdnsEnable;
	}

	/**
	 * 异步执行
	 * @return this
	 */
	public HttpRequest execute() {
		return this;
	}

	/**
	 * 取消执行
	 */
	public synchronized void cancel() {
		if (request != null) {
			request.cancel();
			request = null;
		}
	}
	
	private RequestParams _getRequestParams() {
		if (mRequestParams == null) {
			mRequestParams = new RequestParams();
		}
		return mRequestParams;
	}

	public String getRealUrl() {
		String realUrl = getUrl();
		if (getType() == HttpType.GET && getRequestParams() != null) {
			realUrl = RequestParams.getUrlWithQueryString(realUrl, getRequestParams());
		}
		return realUrl;
	}
}
