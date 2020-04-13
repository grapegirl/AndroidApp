package com.kiki.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ByteUtils
 * @Description : 바이트 변환 유틸 클래스
 * @since 2015-07-03.
 */
public class ByteUtils {

    /**
     * 비트맵을 바이트 배열로 변환하는 메소드
     *
     * @param map 비트맵
     * @return 바이트 배열
     */
    public static byte[] getByteArrayFromBitmap(Bitmap map) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getResBitmap(Context context, int bmpResId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;

        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);

        if (bmp == null) {
            Drawable d = res.getDrawable(bmpResId);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            d.setBounds(0, 0, w - 1, h - 1);
            d.draw(c);
        }

        return bmp;
    }

    /***
     * 파일로부터 이미지 리사이즈해서 다시 저장하기
     *
     * @param photoPath 파일경로
     */
    public static void setFileResize(String photoPath, int width, int height, boolean filter) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);

        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, filter);
        saveBitmapToFile(newBitmap, photoPath);
    }

    /**
     * 비트맵 파일 저장하기
     *
     * @param bitmap      저장할 비트맵 이미지
     * @param strFilePath 저장할 파일 경로
     */
    public static void saveBitmapToFile(Bitmap bitmap, String strFilePath) {
        File fileCacheItem = new File(strFilePath);
        OutputStream out = null;
        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 파일로부터 이미지 가져오기
     *
     * @param photoPath
     * @return
     */
    public static Bitmap getFileBitmap(String photoPath) {
        Bitmap bm = BitmapFactory.decodeFile(photoPath);
        return bm;
    }

    /**
     * 파일에서 바이트 배열로 변환하는 메소드
     *
     * @param path 경로
     * @return 바이트 배열
     */
    public static byte[] getByteArrayFromFile(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bytes;
    }
}
