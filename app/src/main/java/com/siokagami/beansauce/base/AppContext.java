package com.siokagami.beansauce.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.siokagami.beansauce.api.Client;

public class AppContext extends Application {
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
            .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
            .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
    @Override public void onCreate() {
        super.onCreate();

        //初始化 android_async_libary
        Client.setAppClient(new AsyncHttpClient());
        Client.setAuthClient(new AsyncHttpClient());
    }
}