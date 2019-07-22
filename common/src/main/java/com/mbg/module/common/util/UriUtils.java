package com.mbg.module.common.util;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.mbg.module.common.BuildConfig;

import java.io.File;

import static android.text.TextUtils.isEmpty;


public class UriUtils {

    private static final String FILE_PROVIDER = BuildConfig.APPLICATION_ID + ".fileprovider";

    public static Uri getUriFromFile(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(AppUtils.getApplication(),FILE_PROVIDER, file);
        }
        return uri;
    }


    public static Uri getUriFromFile(String path) {
        if (isEmpty(path)) return null;
        return getUriFromFile(new File(path));
    }
}
