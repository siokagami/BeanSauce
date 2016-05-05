package com.siokagami.beansauce;


import android.os.Bundle;
import android.os.Handler;

import com.siokagami.beansauce.base.AppManager;
import com.siokagami.beansauce.base.BaseActivity;
import com.siokagami.beansauce.utils.UIUtil;

public class AppStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                AppManager.getInstance().finishActivity();
                UIUtil.showSearchActivity(AppStart.this);
            }
        }, 2000);

    }
}
