package com.lzokks04.myutilbox.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import com.lzokks04.myutilbox.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu on 2016/9/9.
 */
public class AppManager {

    private Context context;
    private PackageManager pm;

    public AppManager(Context context) {
        this.context = context;
        pm = context.getPackageManager();
    }

    /**
     * 获取正在运行的app列表信息
     *
     * @return
     */
    public List<AppInfo> getRunningApp() {
        List<AppInfo> list = new ArrayList<AppInfo>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runApp : processInfos) {
            String packName = runApp.processName;
            int pid = runApp.pid;
            try {
                ApplicationInfo app = pm.getApplicationInfo(packName,
                        pm.GET_META_DATA | pm.GET_SHARED_LIBRARY_FILES);
                AppInfo appInfo = new AppInfo();
                appInfo.setPackageName(packName);
                appInfo.setIcon(app.loadIcon(pm));
                appInfo.setName(app.loadLabel(pm).toString());
                Debug.MemoryInfo memoryInfo[] = am.getProcessMemoryInfo(new int[]{pid});
                appInfo.setSize(memoryInfo[0].dalvikPrivateDirty * 1024);
                list.add(appInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取用户app信息
     *
     * @return
     */
    public List<AppInfo> getUserAppInfo() {
        List<AppInfo> list = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        for (PackageInfo pi : packageInfoList) {
            try {
                ApplicationInfo ai = pm.getApplicationInfo(pi.packageName, 0);
                if (isUserApp(ai)) {
                    setData(list, pi);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取系统app信息
     *
     * @return
     */
    public List<AppInfo> getSysAppInfo() {
        List<AppInfo> list = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        for (PackageInfo pi : packageInfoList) {
            try {
                ApplicationInfo ai = pm.getApplicationInfo(pi.packageName, 0);
                if (isSysApp(ai)) {
                    setData(list, pi);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 设置数据
     *
     * @param list
     * @param pi
     */
    private void setData(List<AppInfo> list, PackageInfo pi) {
        AppInfo appInfo = new AppInfo();
        appInfo.setPackageName(pi.packageName);
        try {
            appInfo.setSize(getSize(context, pi.packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appInfo.setName(pm.getApplicationLabel(pi.applicationInfo).toString());
        appInfo.setIcon(pm.getApplicationIcon(pi.applicationInfo));
        list.add(appInfo);
    }

    /**
     * 计算大小
     *
     * @param context
     * @param packageName
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    private long getSize(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return new File(context.getPackageManager().getApplicationInfo(packageName, 0).publicSourceDir).length();
    }

    /**
     * 判断是否为用户app
     *
     * @param ai
     * @return
     */
    private boolean isUserApp(ApplicationInfo ai) {
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (ai.flags & mask) == 0 ? true : false;
    }

    /**
     * 判断是否为系统app
     *
     * @param ai
     * @return
     */
    private boolean isSysApp(ApplicationInfo ai) {
        int mask = ai.flags & ApplicationInfo.FLAG_SYSTEM;
        return mask != 0 ? true : false;
    }
}
