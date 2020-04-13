package com.kiki.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @version 1.0
 * @Class Name : SharedPreferenceUtils.java
 * @Description : 프레퍼런스 유틸 클래스
 * @since 2014.08.01
 */
public class SharedPreferenceUtils {

    /**
     * 프리퍼런스 이름
     */
    public static final String KEY_PREFER_NAME = "APP_PRF_NAME";

    /**
     * 프레퍼런스 integer 타입
     */
    public static final int SHARED_PREF_VALUE_INTEGER = 0;

    /**
     * 프레퍼런스 float 타입
     */
    public static final int SHARED_PREF_VALUE_FLOAT = 1;

    /**
     * 프레퍼런스 long 타입
     */
    public static final int SHARED_PREF_VALUE_LONG = 2;

    /**
     * 프레퍼런스 string 타입
     */
    public static final int SHARED_PREF_VALUE_STRING = 3;

    /**
     * 프레퍼런스 boolean 타입
     */
    public static final int SHARED_PREF_VALUE_BOOLEAN = 4;


    /***
     * 프레퍼런스 쓰기 메소드
     *
     * @param context 컨텍스트
     * @param key     저장할 키값
     * @param value   저장할 값
     */
    public static void write(Context context, String key, Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_PREFER_NAME, Context.MODE_PRIVATE).edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value == null) {
            editor.putString(key, null);
        } else {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    /**
     * 프레퍼런스 읽기 메소드
     *
     * @param context 컨텍스트
     * @param key     읽어올 키값
     * @param type    읽어올 값의 타입
     * @return 프레퍼런스 저장한 값
     */
    public static Object read(Context context, String key, int type) {
        SharedPreferences sharedPref = context.getSharedPreferences(KEY_PREFER_NAME, Context.MODE_PRIVATE);
        switch (type) {
            case SHARED_PREF_VALUE_INTEGER:
                return sharedPref.getInt(key, -1);
            case SHARED_PREF_VALUE_FLOAT:
                return sharedPref.getFloat(key, -1);
            case SHARED_PREF_VALUE_LONG:
                return sharedPref.getLong(key, -1);
            case SHARED_PREF_VALUE_STRING:
                return sharedPref.getString(key, null);
            case SHARED_PREF_VALUE_BOOLEAN:
                return sharedPref.getBoolean(key, false);
        }
        return null;
    }
}