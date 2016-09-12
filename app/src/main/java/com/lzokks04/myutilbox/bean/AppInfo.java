package com.lzokks04.myutilbox.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Liu on 2016/9/9.
 */
public class AppInfo {

    private String packageName;//包名
    private Drawable icon;//图标
    private String name;//名字
    private float size;//占用内存大小

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
