package com.lzokks04.myutilbox.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

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

    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static String getCpuName() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static String getX86CPUName() {
        String aLine = "Intel";
        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                String strArray[] = new String[2];
                while ((aLine = br.readLine()) != null) {
                    if(aLine.contains("model name")){
                        br.close();
                        strArray = aLine.split(":", 2);
                        aLine = strArray[1];
                    }
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return aLine;
    }

    public static int getCpuCoreSize() {
        if (Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            // Use saurabh64's answer
            return getNumCoresOldPhones();
        }
    }

    private static int getNumCoresOldPhones() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static String getBaseband() {
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            return (String) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String isRoot() {
        Process process = null;//额外的进程
        OutputStream outputStream = null;
        //change user
        try {
            //开一个进程，切换至root账号
            process = Runtime.getRuntime().exec("su");
            outputStream = process.getOutputStream();
            outputStream.write("ls /data/\n".getBytes());
            outputStream.write("exit\n".getBytes());
            outputStream.flush();//刷新
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return "否";
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //销毁进程
            if (process != null) {
                process.destroy();
            }
        }
        return "是";
    }

    public static boolean isSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            return false;
        }
        return true;
    }

    public static String getSdCardSize(Context context) {
        File SdPath = Environment.getExternalStorageDirectory();//得到SD卡的路径
        StatFs stat = new StatFs(SdPath.getPath());//创建StatFs对象，用来获取文件系统的状态
        long sdBlockCount = stat.getBlockCount();
        long sdBlockSize = stat.getBlockSize();
        long sdAvailableBlocks = stat.getAvailableBlocks();
        String sdAvailableSize = Formatter.formatFileSize(context, (sdBlockCount * sdAvailableBlocks) / 1024 / 1024);//获得SD卡可用容量
        String sdTotalSize = Formatter.formatFileSize(context, sdBlockCount * sdBlockSize);//格式化获得SD卡总容量
        return sdAvailableSize + "/" + sdTotalSize;
    }
}
