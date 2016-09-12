package com.lzokks04.myutilbox.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Liu on 2016/9/9.
 */
public class MemoryUtil {

    /**
     * 获取现在所用内存大小
     *
     * @return
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /**
     * 获得总内存
     *
     * @return
     */
    public static long getTotalMem() {
        InputStream in = null;
        try {
            in = new FileInputStream("/proc/meminfo");
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            String[] tokens = line.split("\\s+");
            return Long.parseLong(tokens[1]) * 1024;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return 0;
    }
}
