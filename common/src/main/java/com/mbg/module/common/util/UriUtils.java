package com.mbg.module.common.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;


import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import static android.text.TextUtils.isEmpty;


public class UriUtils {

    public static Uri getUriFromFile(String path) {
        if (isEmpty(path)) return null;
        return getUriFromFile(new File(path));
    }

    public static Uri getUriFromFile(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
             String FILE_PROVIDER = AppUtils.getPackageName() + ".fileprovider";
            uri = FileProvider.getUriForFile(AppUtils.getApplication(),FILE_PROVIDER, file);
        }
        return uri;
    }

    /**
     * Uri to file.
     *
     * @param uri The uri.
     * @return file
     */
    public static File getFileFromUri(@NonNull final Uri uri) {
        String authority = uri.getAuthority();
        String scheme = uri.getScheme();
        String path = uri.getPath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && path != null && path.startsWith("/external/")) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + path.replace("/external", ""));
            if (file.exists()) {
                LogUtils.d( uri.toString() + " -> /external");
                return file;
            }
        }
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            if (path != null) return new File(path);
            LogUtils.d( uri.toString() + " parse failed. -> 0");
            return null;
        }// end 0
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(AppUtils.getApplication(), uri)) {
            if ("com.android.externalstorage.documents".equals(authority)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return new File(Environment.getExternalStorageDirectory() + "/" + split[1]);
                } else {
                    StorageManager mStorageManager = (StorageManager) AppUtils.getApplication().getSystemService(Context.STORAGE_SERVICE);
                    try {
                        Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                        Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
                        Method getUuid = storageVolumeClazz.getMethod("getUuid");
                        Method getState = storageVolumeClazz.getMethod("getState");
                        Method getPath = storageVolumeClazz.getMethod("getPath");
                        Method isPrimary = storageVolumeClazz.getMethod("isPrimary");
                        Method isEmulated = storageVolumeClazz.getMethod("isEmulated");

                        Object result = getVolumeList.invoke(mStorageManager);

                        final int length = Array.getLength(result);
                        for (int i = 0; i < length; i++) {
                            Object storageVolumeElement = Array.get(result, i);
                            //String uuid = (String) getUuid.invoke(storageVolumeElement);

                            final boolean mounted = Environment.MEDIA_MOUNTED.equals(getState.invoke(storageVolumeElement))
                                    || Environment.MEDIA_MOUNTED_READ_ONLY.equals(getState.invoke(storageVolumeElement));

                            //if the media is not mounted, we need not get the volume details
                            if (!mounted) continue;

                            //Primary storage is already handled.
                            if ((Boolean) isPrimary.invoke(storageVolumeElement)
                                    && (Boolean) isEmulated.invoke(storageVolumeElement)) {
                                continue;
                            }

                            String uuid = (String) getUuid.invoke(storageVolumeElement);

                            if (uuid != null && uuid.equals(type)) {
                                return new File(getPath.invoke(storageVolumeElement) + "/" + split[1]);
                            }
                        }
                    } catch (Exception ex) {
                        LogUtils.d( uri.toString() + " parse failed. " + ex.toString() + " -> 1_0");
                    }
                }
                LogUtils.d( uri.toString() + " parse failed. -> 1_0");
                return null;
            }// end 1_0
            else if ("com.android.providers.downloads.documents".equals(authority)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (!StringUtils.isEmpty(id)) {
                    try {
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(id)
                        );
                        return getFileFromUri(contentUri, "1_1");
                    } catch (NumberFormatException e) {
                        if (id.startsWith("raw:")) {
                            return new File(id.substring(4));
                        }
                    }
                }
                LogUtils.d( uri.toString() + " parse failed. -> 1_1");
                return null;
            }// end 1_1
            else if ("com.android.providers.media.documents".equals(authority)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    LogUtils.d( uri.toString() + " parse failed. -> 1_2");
                    return null;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getFileFromUri(contentUri, selection, selectionArgs, "1_2");
            }// end 1_2
            else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                return getFileFromUri(uri, "1_3");
            }// end 1_3
            else {
                LogUtils.d( uri.toString() + " parse failed. -> 1_4");
                return null;
            }// end 1_4
        }// end 1
        else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            return getFileFromUri(uri, "2");
        }// end 2
        else {
            LogUtils.d( uri.toString() + " parse failed. -> 3");
            return null;
        }// end 3
    }

    private static File getFileFromUri(final Uri uri, final String code) {
        return getFileFromUri(uri, null, null, code);
    }

    private static File getFileFromUri(final Uri uri,
                                       final String selection,
                                       final String[] selectionArgs,
                                       final String code) {
        final Cursor cursor = AppUtils.getApplication().getContentResolver().query(
                uri, new String[]{"_data"}, selection, selectionArgs, null);
        if (cursor == null) {
            LogUtils.d( uri.toString() + " parse failed(cursor is null). -> " + code);
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndex("_data");
                if (columnIndex > -1) {
                    return new File(cursor.getString(columnIndex));
                } else {
                    LogUtils.d( uri.toString() + " parse failed(columnIndex: " + columnIndex + " is wrong). -> " + code);
                    return null;
                }
            } else {
                LogUtils.d( uri.toString() + " parse failed(moveToFirst return false). -> " + code);
                return null;
            }
        } catch (Exception e) {
            LogUtils.d( uri.toString() + " parse failed. -> " + code);
            return null;
        } finally {
            cursor.close();
        }
    }

}
