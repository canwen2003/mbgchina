package com.mbg.module.common.core.net.manager;

import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.wrapper.request.HttpRequest;
import com.mbg.module.common.core.net.wrapper.response.HttpResponse;
import com.mbg.module.common.util.LogUtils;

public class HttpManager {
	public static HttpRequest get(String url) {
		return get(url, null);
	}

	public static HttpRequest get(String url, HttpResponse<?> responseHandler) {
		if (HttpConfigs.debug) {
			LogUtils.d("Url="+url);
		}
		HttpRequest requestWrapper = new HttpRequest(HttpType.GET, url);
		requestWrapper.setResponseHandler(responseHandler);
		return requestWrapper;
	}

	public static HttpRequest post(String url) {
		return post(url, null);
	}

	public static HttpRequest post(String url, HttpResponse<?> responseHandler) {
		HttpRequest requestWrapper = new HttpRequest(HttpType.POST, url);
		requestWrapper.setResponseHandler(responseHandler);
		return requestWrapper;
	}

	public static boolean isDebug() {
		return HttpConfigs.debug;
	}


	public static boolean isDnsForce() {
		return HttpConfigs.httpDnsForce;
	}

	public static boolean isUserOkHttp(){
		return HttpConfigs.usrOkHttp;
	}

}
