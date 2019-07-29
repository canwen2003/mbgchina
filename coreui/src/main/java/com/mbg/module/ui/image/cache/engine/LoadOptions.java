package com.mbg.module.ui.image.cache.engine;


import java.io.Serializable;

public class LoadOptions implements Serializable{
	private static LoadOptions defaultOptions;
	private int defaultImageResId = 0; // 默认加载的图片
	private String defaultUri; // 默认加载的图片资源, 只取内存中的, 内存中找不到就使用defaultImageResId
	private int imageOnFail; // 加载失败时需要设置的图片(<0标识不需要设置)
	private boolean systemResourceLoad ; // 使用系统的资源加载, 默认不使用, 当defaultImageResId或imageOnFail为.9图时需要设置为true
	private String sizeString;  // 图片大小
	private boolean syncFlag; // 同步处理
	private boolean allowDownload; // 是否允许下载
	private boolean createMemory; // 是否创建内存

	private boolean isProcessTransfer ;//是否需要处理旋转，目前只有发布照片时预览本地图片用到
	private boolean gifEnable; // 是否生成gif(生成GifDrawable)
	private boolean animationForAsync ; // 对异步获取的图片是否需要动画展示
	private boolean cacheToDisk; // 是否缓存到本地磁盘(图片验证码就不需要缓存到本地)
	private boolean requestWebp; // 是否请求webp格式的图片

	private LoadOptions() {
		defaultImageResId = 0; // 默认加载的图片
		defaultUri = null; // 默认加载的图片资源, 只取内存中的, 内存中找不到就使用defaultImageResId
		imageOnFail = 0; // 加载失败时需要设置的图片(<0标识不需要设置)
		systemResourceLoad = false; // 使用系统的资源加载, 默认不使用, 当defaultImageResId或imageOnFail为.9图时需要设置为true
		syncFlag = false; // 同步处理
		allowDownload = true; // 是否允许下载
		createMemory = true; // 是否创建内存
		isProcessTransfer = false;//是否需要处理旋转，目前只有发布照片时预览本地图片用到
		gifEnable = false; // 是否生成gif(生成GifDrawable)
		animationForAsync = false; // 对异步获取的图片是否需要动画展示
		cacheToDisk = true; // 是否缓存到本地磁盘(图片验证码就不需要缓存到本地)
		requestWebp = false; // 请求webp格式的图片
	}

	public LoadOptions(LoadOptions copyOptions) {
		this.defaultImageResId = copyOptions.defaultImageResId;
		this.defaultUri = copyOptions.defaultUri;
		this.imageOnFail = copyOptions.imageOnFail;
		this.systemResourceLoad = copyOptions.systemResourceLoad;
		this.sizeString = copyOptions.sizeString;
		this.syncFlag = copyOptions.syncFlag;
		this.allowDownload = copyOptions.allowDownload;
		this.createMemory = copyOptions.createMemory;
		this.requestWebp = copyOptions.requestWebp;
		this.isProcessTransfer = copyOptions.isProcessTransfer;
        this.gifEnable = copyOptions.gifEnable;
		this.animationForAsync = copyOptions.animationForAsync;
        this.cacheToDisk = copyOptions.cacheToDisk;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	public void setDefaultUri(String defaultUri) {
		this.defaultUri = defaultUri;
	}

	public String getDefaultUri() {
		return defaultUri;
	}

	public void setImageOnFail(int imageOnFail) {
		this.imageOnFail = imageOnFail;
	}

	public int getImageOnFail() {
		return imageOnFail;
	}

	public void setSystemResourceLoad(boolean systemResourceLoad) {
		this.systemResourceLoad = systemResourceLoad;
	}

	public boolean isSystemResourceLoad() {
		return systemResourceLoad;
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
