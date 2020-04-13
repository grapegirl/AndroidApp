package com.kiki.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/***
 * @version 1.1
 * @Class Name : FileUtils
 * @Description : 데이터 관련 클래스
 * @since 2017. 02. 11.
 */
public class FileUtils {

    public static final boolean createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            return true;
        }
        return false;
    }

    public static final boolean createNoMediaFile(String folder) {
        File path = new File(folder);
        File noMediaFile = new File(folder + "/.nomedia");
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!noMediaFile.exists()) {
            boolean isResult = noMediaFile.mkdir();
            return isResult;
        }
        return false;
    }

    public static void copyFile(String selectedImagePath, String string) throws IOException {
        InputStream in = new FileInputStream(selectedImagePath);
        OutputStream out = new FileOutputStream(string);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static String getMediaScanPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static final boolean deleteFile(String fileName) {
        File file = new File(fileName);
        boolean isResult = false;
        if (file.exists()) {
            isResult = file.delete();
        }
        return isResult;
    }

    public static Typeface getAssetFont(Context context, String fontName) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), fontName);
        return typeFace;
    }
}
