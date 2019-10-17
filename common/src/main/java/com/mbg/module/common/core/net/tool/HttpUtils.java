package com.mbg.module.common.core.net.tool;



import androidx.collection.ArrayMap;

import java.util.Map;


public class HttpUtils {
	private static String sUserAgent;
	public static Map<String, String> buildBaseParams() {
		return  new ArrayMap<>();
	}

	public static Map<String, String> buildBaseHeader(boolean addAuthHead) {
		Map<String, String> baseHeaders = new ArrayMap<>();
		baseHeaders.put("Accept", "*/*");
		baseHeaders.put("Connection", "keep-alive");
		baseHeaders.put("Content-Type", "application/json");
		baseHeaders.put("Accept-Language", "zh-cn");
		baseHeaders.put("User-Agent", getUserAgent());
		// 添加验证头
		if (addAuthHead) {
			baseHeaders.put("Authorization", "Basic ");
		}
		return baseHeaders;
	}


	private static String getUserAgent() {
		if (sUserAgent==null) {
			synchronized (HttpUtils.class) {
				if (sUserAgent==null) {
					String userAgent = System.getProperty("http.agent");
					if (userAgent == null) {
						return "";
					}
					StringBuilder sb = new StringBuilder();
					for (int i = 0, length = userAgent.length(); i < length; i++) {
						char c = userAgent.charAt(i);
						if (c <= '\u001f' || c >= '\u007f') {
							sb.append(String.format("\\u%04x", (int) c));
						} else {
							sb.append(c);
						}
					}
					sUserAgent = sb.toString();
				}
			}
		}
		return sUserAgent;
	}

}

