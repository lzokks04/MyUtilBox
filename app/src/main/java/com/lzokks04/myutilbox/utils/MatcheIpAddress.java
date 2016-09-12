package com.lzokks04.myutilbox.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Liu on 2016/9/7.
 */
public class MatcheIpAddress {

    public static String match(String str) {
        Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static boolean isIpAddress(String str) {
        Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static String removeField(String str) {
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace("\"", "");
        str = str.replace(",,,", ",");
        str = str.replace(",,", ",");
        return str;
    }

    public static String matchMs(String str) {
        Pattern pattern = Pattern.compile("\\d{1,3}.\\d{1,2}\\sms");
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group() : "";
    }

    public static boolean isMs(String str) {
        Pattern pattern = Pattern.compile("\\d{1,3}.\\d{1,2}\\sms");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
