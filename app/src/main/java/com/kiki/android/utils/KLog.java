package com.kiki.android.utils;

import android.util.Log;

/***
 * @author grapegirl
 * @version 1.1
 * @Class Name : KLog.java
 * @Description : Log 클래스
 * @since 2017.02.11
 */
public class KLog {

    private static boolean VIEW_LOG = true;

    public static void d(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.d(tag, buildLogMsg(msg));
    }

    public static void e(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.e(tag, buildLogMsg(msg));
    }

    public static void w(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.w(tag, buildLogMsg(msg));
    }

    public static void i(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.i(tag, buildLogMsg(msg));
    }

    public static void v(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.v(tag, buildLogMsg(msg));
    }

    public static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);
        return sb.toString();
    }

    public void setLogging(boolean isDebugging) {
        VIEW_LOG = isDebugging;
    }
}
