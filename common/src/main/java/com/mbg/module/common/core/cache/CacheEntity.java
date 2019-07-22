package com.mbg.module.common.core.cache;

import android.util.Base64;


import com.mbg.module.common.core.cache.database.BasicEntity;
import com.mbg.module.common.tool.Headers;
import com.mbg.module.common.util.LogUtils;

import org.json.JSONException;

public class CacheEntity implements BasicEntity {
    private long id;

    /**
     * The cache key.
     */
    private String key;
    /**
     * The server response headers.
     */
    private Headers responseHeaders = new Headers();

    /**
     * CacheStore data.
     */
    private byte[] data = {};

    /**
     * Cached in the local expiration time.
     */
    private long localExpire;

    public CacheEntity() {
    }

    /**
     * @param id              id.
     * @param key             key.
     * @param responseHeaders http response headers.
     * @param data            http response data.
     * @param localExpire     local expire time.
     */
    public CacheEntity(long id, String key, Headers responseHeaders, byte[] data, long localExpire) {
        this.id = id;
        this.key = key;
        this.responseHeaders = responseHeaders;
        this.data = data;
        this.localExpire = localExpire;
    }

    /**
     * @return the id.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the responseHeaders.
     */
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * @param responseHeaders the responseHeaders to set.
     */
    public void setResponseHeaders(Headers responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * Set the can Parse the json data, format conforms to the corresponding
     * Http header format.
     *
     * @param jsonString conform to the relevant head of the Json data format.
     */
    public void setResponseHeadersJson(String jsonString) {
        try {
            this.responseHeaders.setJSONString(jsonString);
        } catch (JSONException e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * To getList the json data format of the head.
     *
     * @return json.
     */
    public String getResponseHeadersJson() {
        return this.responseHeaders.toJSONString();
    }

    /**
     * @return the data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set.
     */
    public void setDataBase64(String data) {
        this.data = Base64.decode(data, Base64.DEFAULT);
    }

    /**
     * @return the data.
     */
    public String getDataBase64() {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * @param data the data to set.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * @return the localExpire.
     */
    public long getLocalExpire() {
        return localExpire;
    }

    /**
     * @param localExpire the localExpire to set.
     */
    public void setLocalExpire(long localExpire) {
        this.localExpire = localExpire;
    }

    /**
     * @return the localExpire.
     */
    public String getLocalExpireString() {
        return Long.toString(localExpire);
    }

    /**
     * @param localExpire the localExpire to set.
     */
    public void setLocalExpireString(String localExpire) {
        this.localExpire = Long.parseLong(localExpire);
    }
}
