package com.mbg.module.common.core.cache;

import android.content.Context;


import com.mbg.module.common.core.cache.database.BaseDao;
import com.mbg.module.common.core.cache.database.Where;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DBCacheStore extends BasicCacheStore {

    /**
     * Database sync lock.
     */
    private Lock mLock;
    /**
     * Database manager.
     */
    private BaseDao<CacheEntity> mManager;

    private boolean mEnable = true;

    public DBCacheStore(Context context) {
        super(context);
        mLock = new ReentrantLock();
        mManager = new CacheEntityDao(context);
    }

    public CacheStore<CacheEntity> setEnable(boolean enable) {
        this.mEnable = enable;
        return this;
    }

    @Override
    public CacheEntity get(String key) {
        mLock.lock();
        key = uniqueKey(key);
        try {
            if (!mEnable) return null;
            Where where = new Where(CacheSQLHelper.KEY, Where.Options.EQUAL, key);
            List<CacheEntity> cacheEntities = mManager.getList(where.get(), null, null, null);
            return cacheEntities.size() > 0 ? cacheEntities.get(0) : null;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public CacheEntity replace(String key, CacheEntity cacheEntity) {
        mLock.lock();
        key = uniqueKey(key);
        try {
            if (!mEnable) return cacheEntity;
            cacheEntity.setKey(key);
            mManager.replace(cacheEntity);
            return cacheEntity;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public boolean remove(String key) {
        mLock.lock();
        key = uniqueKey(key);
        try {
            if (!mEnable)
                return false;
            Where where = new Where(CacheSQLHelper.KEY, Where.Options.EQUAL, key);
            return mManager.delete(where.toString());
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public boolean clear() {
        mLock.lock();
        try {
            return mEnable && mManager.deleteAll();
        } finally {
            mLock.unlock();
        }
    }

}
