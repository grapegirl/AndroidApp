package com.kiki.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : BitmapUtils.java
 * @Description : 비트맵 관련 변환 매니저
 * @since 2017.02.11
 */
public class BitmapUtils {

    public Bitmap getImageToBitmap(Context context, int resultId) {
        if (context != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resultId);
            if (bitmapDrawable != null) {
                return bitmapDrawable.getBitmap();
            } else {
                return null;
            }
        }
        return null;
    }

    public Bitmap getDrawbleToBitmap(Drawable drawable) {
        return Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    public byte[] getBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void saveBitmapToFile(Bitmap bitmap, String saveFilePath, String fileName) {
        String path = saveFilePath + "/" + fileName;
        FileUtils.createFile(path);
        File makeFile = new File(path);
        OutputStream out = null;
        try {
            out = new FileOutputStream(makeFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
