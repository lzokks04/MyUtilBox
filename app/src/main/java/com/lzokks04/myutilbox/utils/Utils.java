package com.lzokks04.myutilbox.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Liu on 2016/9/8.
 */
public class Utils {

    public static void startIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    // 判断ip是否标准
    public static boolean validate(String ip) {
        if (ip == null || ip.length() == 0)
            return false;
        String[] array = ip.split("\\.");
        if (array.length != 4)
            return false;
        for (String str : array) {
            try {
                int num = Integer.valueOf(str);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
