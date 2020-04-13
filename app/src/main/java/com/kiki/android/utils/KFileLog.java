package com.kiki.android.utils;

import java.io.File;
import java.io.IOException;

public class KFileLog {
    private static File mLogFile = null;

    private static void createLogFile(String path) {
        if (mLogFile == null) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }

            if (folder.mkdir() || folder.isDirectory()) {
                mLogFile = new File(path, "log_" + TimeUtils.getCurrentTime() + ".txt");
                try {
                    if (mLogFile.createNewFile()) {
                        // 파일이 생성 되었음
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startFileLogging(boolean isDebug, String path) {
        if (isDebug) {
            try {
                createLogFile(path);
                Runtime.getRuntime().exec(new String[]{"logcat", "-d", "-f", mLogFile.getPath()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
