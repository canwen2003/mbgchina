/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.mbg.module.common.core.net.wrapper.request;

import android.text.TextUtils;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A collection of string request parameters or files to send along with
 * requests made from an {@link } instance.
 * <p>
 * For example:
 * <p>
 * 
 * <pre>
 * RequestParams params = new RequestParams();
 * params.put(&quot;username&quot;, &quot;james&quot;);
 * params.put(&quot;password&quot;, &quot;123456&quot;);
 * params.put(&quot;email&quot;, &quot;my@email.com&quot;);
 * params.put(&quot;profile_picture&quot;, new File(&quot;pic.jpg&quot;)); // Upload a File
 * params.put(&quot;profile_picture2&quot;, someInputStream); // Upload an InputStream
 * params.put(&quot;profile_picture3&quot;, new ByteArrayInputStream(someBytes)); // Upload
 * // some
 * // bytes
 * 
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.post(&quot;http://myendpoint.com&quot;, params, responseHandler);
 * </pre>
 */
public class RequestParams {
	private static String ENCODING = "UTF-8";
	protected ConcurrentHashMap<String, String> urlParams;
	protected String uploadParameterName;
	protected String uploadFilePath;
	protected String downloadFilePath;

	protected String jsonBody;
	protected Map<String, String> formBody;
	protected byte[] bytesBody;

	/**
	 * Constructs a new empty <code>RequestParams</code> instance.
	 */
	public RequestParams() {
		init();
	}

	/**
	 * Adds a key/value string pair to the request.
	 * 
	 * @param key
	 *            the key name for the new param.
	 * @param value
	 *            the value string for the new param.
	 */
	public void put(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}

	/**
	 * 设置json body
	 * @param jsonBody
	 */
	public void setJsonBody(String jsonBody) {
		// 不给jsonBody赋值null，是为了产生body时能走到jsonBody逻辑
		if (jsonBody == null) {
			this.jsonBody = "";
		} else {
			this.jsonBody = jsonBody;
		}
	}

	/**
	 * 设置form body
	 * @param formBody
	 */
	public void setFormBody(Map<String, String> formBody) {
		// 不给formBody赋值null，是为了产生body时能走到formBody逻辑
		if (formBody == null) {
			this.formBody = new HashMap<>();
		} else {
			this.formBody = formBody;
		}
	}

	/**
	 * 设置bytes body
	 * @param bytesBody
	 */
	public void setBytesBody(byte[] bytesBody) {
		this.bytesBody = bytesBody;
	}

	/**
	 * 上传文件
	 * 
	 * @param filePath
	 */
	public void uploadFile(String filePath, String uploadParameterName) {
		this.uploadFilePath = filePath;
		this.uploadParameterName = uploadParameterName;
	}

	/**
	 * 获取上传文件路径
	 * 
	 * @return
	 */
	public String getUploadFilePath() {
		return uploadFilePath;
	}

	/**
	 * 下载文件
	 * 
	 * @param saveFilePath
	 */
	public void downloadFile(String saveFilePath) {
		downloadFilePath = saveFilePath;
	}

	/**
	 * 获取下载文件路径
	 * 
	 * @return
	 */
	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	/**
	 * Removes a parameter from the request.
	 * 
	 * @param key
	 *            the key name for the parameter to remove.
	 */
	public void remove(String key) {
		urlParams.remove(key);
		// fileParams.remove(key);
		//urlParamsWithArray.remove(key);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
			if (result.length() > 0) {
				result.append("&");
			}

			result.append(entry.getKey());
			result.append("=");
			result.append(entry.getValue());
		}

		if (!TextUtils.isEmpty(uploadFilePath)) {
			if (result.length() > 0) {
				result.append("&");
			}
			result.append("uploadFile=" + uploadFilePath);
		}
		if (!TextUtils.isEmpty(downloadFilePath)) {
			if (result.length() > 0) {
				result.append("&");
			}
			result.append("downloadFile=" + downloadFilePath);
		}

		return result.toString();
	}

	private void init() {
		urlParams = new ConcurrentHashMap<String, String>();
	}

	/**
	 * 参考 org.apache.hc.core5.net.URLEncodedUtils 的 formatNameValuePairs 方法而来
	 * @return urlencode之后的参数
	 */
	public String getParamString() {
		StringBuffer strBuff = new StringBuffer();
		try {
			String key, value;
			int i = 0;
			for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
				key = entry.getKey();
				if (!TextUtils.isEmpty(key)) {
					if (i > 0) {
						strBuff.append("&");
					}
					strBuff.append(URLEncoder.encode(key, ENCODING));
					value = entry.getValue();
					if (!TextUtils.isEmpty(value)) {
						strBuff.append("=");
						strBuff.append(URLEncoder.encode(value, ENCODING));
					}
					i ++;
				}
			}
		} catch (UnsupportedEncodingException ex) {
		}

		return strBuff.toString();
	}

	public static String getUrlWithQueryString(String url, RequestParams params) {
		if(params != null) {
			String paramString = params.getParamString();
			if (url.indexOf("?") == -1) {
				url += "?" + paramString;
			} else {
				url += "&" + paramString;
			}
		}

		return url;
	}
}
