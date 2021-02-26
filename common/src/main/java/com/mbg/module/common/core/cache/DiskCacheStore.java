package com.mbg.module.common.core.cache;

import android.content.Context;
import android.text.TextUtils;

import com.mbg.module.common.tool.Encryption;
import com.mbg.module.common.util.IOUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.StringUtils;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;


public class DiskCacheStore extends BasicCacheStore {
    /**
     * Database sync lock.
     */
    private final Lock mLock;
    /**
     *
     */
    private final Encryption mEncryption;
    /**
     * Folder.
     */
    private final String mCacheDirectory;

    /**
     * You must remember to check the runtime permissions.
     *
     * @param context {@link Context}.
     */
    public DiskCacheStore(Context context) {
        this(context, context.getCacheDir().getAbsolutePath());
    }

    /**
     * Introduced to the cache folder, you must remember to check the runtime permissions.
     *
     * @param cacheDirectory cache directory.
     */
    public DiskCacheStore(Context context, String cacheDirectory) {
        super(context);

        if (TextUtils.isEmpty(cacheDirectory)) {
            throw new IllegalArgumentException("The cacheDirectory can't be null.");
        }
        mLock = new ReentrantLock();
        mEncryption = new Encryption(DiskCacheStore.class.getSimpleName());
        mCacheDirectory = cacheDirectory;
    }

    @Override
    public CacheEntity get(String key) {
        mLock.lock();
        key = uniqueKey(key);

        BufferedSource bufferedSource = null;
        try {
            if (TextUtils.isEmpty(key))
                return null;
            File file = new File(mCacheDirectory, key);
            if (!file.exists() || file.isDirectory())
                return null;
            CacheEntity cacheEntity = new CacheEntity();
            Source fileSource= Okio.source(file);
            bufferedSource=Okio.buffer(fileSource);

            cacheEntity.setResponseHeadersJson(decrypt(bufferedSource.readUtf8Line()));
            cacheEntity.setDataBase64(decrypt(bufferedSource.readUtf8Line()));
            cacheEntity.setLocalExpireString(decrypt(bufferedSource.readUtf8Line()));
            return cacheEntity;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mCacheDirectory, key));
            LogUtils.e(e.toString());
        } finally {

            IOUtils.closeQuietly(bufferedSource);
            mLock.unlock();
        }
        return null;
    }

    @Override
    public CacheEntity replace(String key, CacheEntity cacheEntity) {
        mLock.lock();
        key = uniqueKey(key);

        BufferedSink bufferedSink = null;
        try {
            if (StringUtils.isEmpty(key) || cacheEntity == null) {
                return cacheEntity;
            }
            IOUtils.createFolder(mCacheDirectory);
            File file = new File(mCacheDirectory, key);

            IOUtils.createNewFile(file);
            Sink fileSink = Okio.sink(file);
            bufferedSink=Okio.buffer(fileSink);

            bufferedSink.writeUtf8(encrypt(cacheEntity.getResponseHeadersJson()));
            bufferedSink.writeUtf8("\n");
            bufferedSink.writeUtf8(encrypt(cacheEntity.getDataBase64()));
            bufferedSink.writeUtf8("\n");
            bufferedSink.writeUtf8(encrypt(cacheEntity.getLocalExpireString()));
            bufferedSink.flush();
            return cacheEntity;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mCacheDirectory, key));
            LogUtils.e(e.toString());
            return null;
        } finally {
            IOUtils.closeQuietly(bufferedSink);
            mLock.unlock();
        }
    }

    @Override
    public boolean remove(String key) {
        mLock.lock();
        key = uniqueKey(key);

        try {
            return IOUtils.delFileOrFolder(new File(mCacheDirectory, key));
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public boolean clear() {
        mLock.lock();
        try {
            return IOUtils.delFileOrFolder(mCacheDirectory);
        } finally {
            mLock.unlock();
        }
    }


    private String encrypt(String encryptionText) throws Exception {
        return mEncryption.encrypt(encryptionText);
    }

    private String decrypt(String cipherText) throws Exception {
        return mEncryption.decrypt(cipherText);
    }

}
