package com.daqianjietong.diandian.utils;

import android.util.Log;

import com.daqianjietong.diandian.BuildConfig;


/**
 * Created by MuWenlei on 16/10/15.
 */
public class LogUtil {

    private static final int JSON_INDENT = 4;

    public static void d(String tag, String data) {
//        if (!BuildConfig.DEBUG) {
//            return;
//        }
        Log.d(tag, data);
    }
}
