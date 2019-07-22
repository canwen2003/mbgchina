package com.mbg.module.common.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;


public class ImageUtils {
    private ImageUtils(){}

    public static Bitmap decodeBitmapFromFilePath(String imagePath,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int getPixel(Bitmap bitmap, int x, int y) {
        if (bitmap == null) {
            return -1;
        }
        if (x < 0 || x > bitmap.getWidth()) {
            return -1;
        }
        if (y < 0 || y > bitmap.getHeight()) {
            return -1;
        }
        return bitmap.getPixel(x, y);
    }

}
