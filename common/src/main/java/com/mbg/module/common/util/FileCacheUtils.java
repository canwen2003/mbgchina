package com.mbg.module.common.util;

import android.support.annotation.NonNull;

import com.mbg.module.common.core.cache.CacheEntity;
import com.mbg.module.common.core.cache.DiskCacheStore;
import com.mbg.module.common.tool.Headers;


/**
 * 用于文件缓存
 * 
 * @author Gap
 */
public class FileCacheUtils {
	private static DiskCacheStore sDiskCacheStore;
	private static final String CHARSET_NAME="UTF-8";
	private static  Headers sHeaders;

	private static void init(){
		synchronized (FileCacheUtils.class){
			if (sDiskCacheStore==null){
				sDiskCacheStore=new DiskCacheStore(AppUtils.getApplication());
				sHeaders=new Headers();
			}
		}
	}
	/**
	 * 保存数据
	 * @param key key值
	 * @param content 要保存的数据
	 */
	public static void saveContent(@NonNull String key, String content) {
		if (content==null){
			return;
		}

		if (sDiskCacheStore==null){
			init();
		}

		try {

			CacheEntity entity=new CacheEntity(HashCodeUtils.hashCode(key),key,sHeaders,content.getBytes(CHARSET_NAME),0);
			sDiskCacheStore.replace(key,entity);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 返回数据
	 * @param key key值
	 * @return
	 */
	public static String getContent(String key) {
		String content=null;
		if (sDiskCacheStore==null){
			init();
		}
		CacheEntity cacheEntity=sDiskCacheStore.get(key);
		try {
			if (cacheEntity!=null) {
				content= new String(cacheEntity.getData(), CHARSET_NAME);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return content;
	}

}
