package com.mbg.module.common.core.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.mbg.module.common.core.cache.database.BaseDao;
import com.mbg.module.common.tool.Encryption;
import com.mbg.module.common.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


public class CacheEntityDao extends BaseDao<CacheEntity> {

    private Encryption mEncryption;

    public CacheEntityDao(Context context) {
        super(new CacheSQLHelper(context));
        String encryptionKey = context.getApplicationInfo().packageName;
        mEncryption = new Encryption(encryptionKey);
    }

    @Override
    public long replace(CacheEntity cacheEntity) {
        SQLiteDatabase database = getWriter();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CacheSQLHelper.KEY, cacheEntity.getKey());
            values.put(CacheSQLHelper.HEAD, encrypt(cacheEntity.getResponseHeadersJson()));
            values.put(CacheSQLHelper.DATA, encrypt(Base64.encodeToString(cacheEntity.getData(), Base64.DEFAULT)));
            values.put(CacheSQLHelper.LOCAL_EXPIRES, encrypt(Long.toString(cacheEntity.getLocalExpire())));
            long result = database.replace(getTableName(), null, values);
            database.setTransactionSuccessful();
            return result;
        } catch (Exception e) {
            return -1;
        } finally {
            database.endTransaction();
            closeDateBase(database);
        }
    }

    @Override
    protected List<CacheEntity> getList(String querySql) {
        SQLiteDatabase database = getReader();
        List<CacheEntity> cacheEntities = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(querySql, null);
            while (!cursor.isClosed() && cursor.moveToNext()) {
                CacheEntity cacheEntity = new CacheEntity();
                cacheEntity.setId(cursor.getInt(cursor.getColumnIndex(CacheSQLHelper.ID)));
                cacheEntity.setKey(cursor.getString(cursor.getColumnIndex(CacheSQLHelper.KEY)));
                cacheEntity.setResponseHeadersJson(decrypt(cursor.getString(cursor.getColumnIndex(CacheSQLHelper.HEAD))));
                cacheEntity.setData(Base64.decode(decrypt(cursor.getString(cursor.getColumnIndex(CacheSQLHelper.DATA))), Base64.DEFAULT));
                cacheEntity.setLocalExpire(Long.parseLong(decrypt(cursor.getString(cursor.getColumnIndex(CacheSQLHelper.LOCAL_EXPIRES)))));
                cacheEntities.add(cacheEntity);
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        } finally {
            closeCursor(cursor);
            closeDateBase(database);
        }
        return cacheEntities;
    }

    @Override
    protected String getTableName() {
        return CacheSQLHelper.TABLE_NAME;
    }

    private String encrypt(String encryptionText) throws Exception {
        return mEncryption.encrypt(encryptionText);
    }

    private String decrypt(String cipherText) throws Exception {
        return mEncryption.decrypt(cipherText);
    }
}
