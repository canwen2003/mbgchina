package com.mbg.module.ui.image.view;


public interface IRecyclerDrawable {
     boolean DEBUG = false;
     String LOG_TAG = "IRecyclerDrawable";

    /**
     * 是否支持缓存
     */
    boolean isSupportCache();

	/**
	 * 获取uri
	 *
	 */
	String getUri();

	/**
	 * 设置uri
	 */
	void setUri(String uri);

	/**
	 * 是否有效
	 *
	 */
	boolean isValid();

    /**
     * Notify the drawable that the displayed state has changed. Internally a
     * count is kept so that the drawable knows when it is no longer being
     * displayed.
     *
     * @param isDisplayed - Whether the drawable is being displayed or not
     */
    void displayed(boolean isDisplayed);

    /**
     * Notify the drawable that the cache state has changed. Internally a count
     * is kept so that the drawable knows when it is no longer being cached.
     *
     * @param isCached - Whether the drawable is being cached or not
     */
    void cached(boolean isCached);

    /**
     * 回收资源
     */
    void recycle();

    /**
     * 获取存储大小, 单位是kb
     */
    int sizeof();


    /**
     * 用于处理引用计数相关功能
     */
     class CountRef {
        private IRecyclerDrawable recyclingDrawable;
        private int cacheRefCount = 0;
        private int displayRefCount = 0;
        private boolean hasBeenDisplayed;

        CountRef(IRecyclerDrawable recyclingDrawable) {
            this.recyclingDrawable = recyclingDrawable;
        }

        /**
         * Notify the drawable that the displayed state has changed. Internally a
         * count is kept so that the drawable knows when it is no longer being
         * displayed.
         *
         * @param isDisplayed - Whether the drawable is being displayed or not
         */
        public void setIsDisplayed(boolean isDisplayed) {
            synchronized (this) {
                if (isDisplayed) {
                    displayRefCount++;
                    hasBeenDisplayed = true;
                } else {
                    displayRefCount--;
                }
            }

            // Check to see if recycle() can be called
            checkState();
        }

        /**
         * Notify the drawable that the cache state has changed. Internally a count
         * is kept so that the drawable knows when it is no longer being cached.
         *
         * @param isCached - Whether the drawable is being cached or not
         */
        public void setIsCached(boolean isCached) {
            synchronized (this) {
                if (isCached) {
                    cacheRefCount++;
                } else {
                    cacheRefCount--;
                }
            }

            // Check to see if recycle() can be called
            checkState();
        }

        private synchronized void checkState() {
            // If the drawable cache and display ref counts = 0, and this drawable
            // has been displayed, then recycle
            if (cacheRefCount <= 0 && displayRefCount <= 0 && hasBeenDisplayed && recyclingDrawable.isValid()) {
                recyclingDrawable.recycle();
            }
        }
    }
}
