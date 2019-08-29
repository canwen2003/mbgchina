package com.mbg.module.common.util;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;


import java.io.File;

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

}
