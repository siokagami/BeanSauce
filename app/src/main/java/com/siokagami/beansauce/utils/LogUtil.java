package com.siokagami.beansauce.utils;

import android.util.Log;

/**
 * Created by SiOKagami on 2016/5/4.
 */
public class LogUtil
{
    private static final String TAG_PROJECT = "BeanSauce";
    private static boolean DEBUG = true;
    public static void log(String message) {
        if (DEBUG) {
            Log.wtf(TAG_PROJECT, message);
        }
    }
    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }
}

