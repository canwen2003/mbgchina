package com.mbg.module.common.core.manager;


import com.mbg.module.common.core.cache.CacheEntity;
import com.mbg.module.common.core.cache.CacheStore;
import com.mbg.module.common.core.cache.CacheType;
import com.mbg.module.common.core.cache.DBCacheStore;
import com.mbg.module.common.core.cache.DiskCacheStore;
import com.mbg.module.common.util.AppUtils;

/***
 * 缓存管理类，支持硬盘和数据库来缓存
 * created by Gap
 */
public class CacheManager {
    private static volatile CacheManager mInstance;

    private CacheStore<CacheEntity> mCacheStore;
    private CacheType mCacheType;


    private CacheManager(CacheType cacheType){
        this.mCacheType=cacheType;

        switch (mCacheType){
            case DISK:
                mCacheStore=new DiskCacheStore(AppUtils.getApplication());
                break;
            case DATABASE:
                mCacheStore=new DBCacheStore(AppUtils.getApplication());
                break;
        }
    }

    public static CacheManager getInstance() {
        if (mInstance==null){
            synchronized (CacheManager.class){
                if (mInstance==null){
                    mInstance=new CacheManager(CacheType.DATABASE);
                }
            }
        }
        return mInstance;
    }

    public CacheType getCacheType() {
        return mCacheType;
    }

    public CacheStore<CacheEntity> getCacheStore() {
        return mCacheStore;
    }

}
