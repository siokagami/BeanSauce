package com.siokagami.beansauce.base;

import android.app.Activity;
import android.content.Context;

import com.siokagami.beansauce.utils.LogUtil;

import java.util.Stack;

public class AppManager {
    private static final String TAG = "AppManager";
    /**
     * 自定义Activity栈
     */
    private static Stack<Activity> activityStack;
    /**
     * 静态类单例模式
     */
    private AppManager() {
    }
    public static AppManager getInstance() {
        return AppManagerHolder.INSTANCE;
    }
    /**
     * 获取自定义Activity栈中特定类名Activity的引用
     *
     * @param cls Activity类
     * @return Activity引用
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }
    /**
     * 打印自定义Activity栈内容
     */
    public static void logActivityStack() {
        if (activityStack == null || activityStack.size() == 0) {
            LogUtil.log(TAG + "\t" + "null");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (Activity activity : activityStack) {
            builder.append(activity.getClass().getSimpleName()).append(" , ");
        }
        LogUtil.log(TAG + "\t" + builder.toString());
    }
    public int getStackSize() {
        return activityStack.size();
    }
    /**
     * 向自定义Activity栈中添加Activity. 建议在Activity的onCreate方法中调用
     *
     * @param activity 添加的Activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }
    /**
     * 从自定义Activity栈中移除特定Activity.建议在Activity的onDestroy方法中调用
     *
     * @param activity 要移除的Activity引用
     */
    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
            activity = null;
            System.gc();
        }
    }
    /**
     * 获取当前栈最顶层的Activity.
     *
     * @return 返回当前栈最顶层Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }
    /**
     * 销毁当前栈最顶层的Activity
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }
    /**
     * 销毁特定Activity
     *
     * @param activity 要销毁的Activity引用
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }
    public boolean containActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 销毁特定类名的Activity
     *
     * @param cls 要销毁的Activity的类名
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }
    /**
     * 销毁自定义Activity栈的中所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i) != null) {
                finishActivity(activityStack.get(i));
            }
        }
    }
    /**
     * 退出应用
     *
     * @param context 应用上下文环境
     */
    public void appExit(Context context) {
        try {
            //MobclickAgent.onKillProcess(context);
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static class AppManagerHolder {
        private static AppManager INSTANCE = new AppManager();
    }
}