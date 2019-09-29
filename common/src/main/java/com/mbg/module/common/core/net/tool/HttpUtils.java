package com.mbg.module.common.core.net.tool;

import android.support.v4.util.ArrayMap;

import java.util.Map;


public class HttpUtils {

	public static Map<String, String> buildBaseParams() {
		return  new ArrayMap<>();
	}

	public static Map<String, String> buildBaseHeader(boolean addAuthHead) {
		Map<String, String> baseHeaders = new ArrayMap<>();
		baseHeaders.put("Accept", "*/*");
		baseHeaders.put("Connection", "keep-alive");
		baseHeaders.put("Content-Type", "application/json");
		baseHeaders.put("Accept-Language", "en");
		baseHeaders.put("User-Agent", "");
		// 添加验证头
		if (addAuthHead) {
			baseHeaders.put("Authorization", "Basic ");
		}
		return baseHeaders;
	}

}

