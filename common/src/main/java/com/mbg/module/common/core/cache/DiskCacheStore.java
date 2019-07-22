package com.mbg.module.common.core.cache;

import android.content.Context;
import android.text.TextUtils;


import com.mbg.module.common.tool.Encryption;
import com.mbg.module.common.util.IOUtils;
import com.mbg.module.common.util.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DiskCacheStore extends BasicCacheStore {
    /**
     * Database sync lock.
     */
    private Lock mLock;
    /**
     *
     */
    private Encryption mEncryption;
    /**
     * Folder.
     */
    private String mCacheDirectory;

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

        if (TextUtils.isEmpty(cacheDirectory))
            throw new IllegalArgumentException("The cacheDirectory can't be null.");
        mLock = new ReentrantLock();
        mEncryption = new Encryption(DiskCacheStore.class.getSimpleName());
        mCacheDirectory = cacheDirectory;
    }

    @Override
    public CacheEntity get(String key) {
        mLock.lock();
        key = uniqueKey(key);

        BufferedReader bufferedReader = null;
        try {
            if (TextUtils.isEmpty(key))
                return null;
            File file = new File(mCacheDirectory, key);
            if (!file.exists() || file.isDirectory())
                return null;
            CacheEntity cacheEntity = new CacheEntity();

            bufferedReader = new BufferedReader(new FileReader(file));
            cacheEntity.setResponseHeadersJson(decrypt(bufferedReader.readLine()));
            cacheEntity.setDataBase64(decrypt(bufferedReader.readLine()));
            cacheEntity.setLocalExpireString(decrypt(bufferedReader.readLine()));
            return cacheEntity;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mCacheDirectory, key));
            LogUtils.e(e.toString());
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            mLock.unlock();
        }
        return null;
    }

    @Override
    public CacheEntity replace(String key, CacheEntity cacheEntity) {
        mLock.lock();
        key = uniqueKey(key);

        BufferedWriter bufferedWriter = null;
        try {
            if (TextUtils.isEmpty(key) || cacheEntity == null)
                return cacheEntity;
            initialize();
            File file = new File(mCacheDirectory, key);

            IOUtils.createNewFile(file);

            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(encrypt(cacheEntity.getResponseHeadersJson()));
            bufferedWriter.newLine();
            bufferedWriter.write(encrypt(cacheEntity.getDataBase64()));
            bufferedWriter.newLine();
            bufferedWriter.write(encrypt(cacheEntity.getLocalExpireString()));
            bufferedWriter.flush();
            bufferedWriter.close();
            return cacheEntity;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mCacheDirectory, key));
            LogUtils.e(e.toString());
            return null;
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
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

    private boolean initialize() {
        return IOUtils.createFolder(mCacheDirectory);
    }

    private String encrypt(String encryptionText) throws Exception {
        return mEncryption.encrypt(encryptionText);
    }

    private String decrypt(String cipherText) throws Exception {
        return mEncryption.decrypt(cipherText);
    }

}
