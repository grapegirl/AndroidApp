package com.kiki.android.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionUtils {


    public static boolean checkPermission(Context context, String permission){

        System.out.println("@@ checkPermission permission: " + permission);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED){
                System.out.println("@@ checkPermission : granted");
                return  true;
            }else {
                System.out.println("@@ checkPermission : not granted");
            }
        }
        return false;
    }


}
