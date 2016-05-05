package com.siokagami.beansauce.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.siokagami.beansauce.utils.LogUtil;

public class BaseActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        AppManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        log("onCreate");
        AppManager.logActivityStack();
    }
    @Override protected void onResume() {
        super.onResume();
        log("onResume");
        AppManager.logActivityStack();
    }
    @Override protected void onPause() {
        super.onPause();
        log("onPause");
        AppManager.logActivityStack();
    }
    @Override protected void onDestroy() {
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
        log("onDestroy");
        AppManager.logActivityStack();
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            log("onKeyDown");
            AppManager.getInstance().removeActivity(this);
            AppManager.logActivityStack();
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void log(String message) {
        LogUtil.log(this.getClass().getName() + " " + message);
    }
}
