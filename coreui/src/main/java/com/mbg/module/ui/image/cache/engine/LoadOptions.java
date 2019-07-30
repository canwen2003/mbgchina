package com.mbg.module.ui.image.cache.engine;


import java.io.Serializable;

public class LoadOptions implements Serializable{
	private static LoadOptions defaultOptions;
	private int defaultImageResId; // 默认加载的图片
	private int imageOnFail; // 加载失败时需要设置的图片
	private boolean enableCache;//是否允许图片缓存

	private LoadOptions() {
		defaultImageResId = 0; // 默认加载的图片
		imageOnFail = 0; // 加载失败时需要设置的图片(<0标识不需要设置)
		enableCache=false;//是否允许图片缓存
	}

	public LoadOptions(LoadOptions copyOptions) {
		this.defaultImageResId = copyOptions.defaultImageResId;
		this.imageOnFail = copyOptions.imageOnFail;
		this.enableCache=copyOptions.enableCache;
	}

	public LoadOptions setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
		return this;
	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	public LoadOptions setImageOnFail(int imageOnFail) {
		this.imageOnFail = imageOnFail;
		return this;
	}

	public int getImageOnFail() {
		return imageOnFail;
	}


	public LoadOptions setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
		return this;
	}

	public boolean isEnableCache() {
		return enableCache;
	}

	public static LoadOptions getDefault() {
		if (defaultOptions==null){
			synchronized (LoadOptions.class) {
				if (defaultOptions==null) {
					defaultOptions = new LoadOptions();
				}
			}
		}
    	return defaultOptions;
    }

	public static LoadOptions get() {
		return new LoadOptions();
	}

}
