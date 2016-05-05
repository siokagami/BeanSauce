package com.siokagami.beansauce;


import android.os.Bundle;

import com.siokagami.beansauce.base.BaseActivity;
import com.siokagami.beansauce.utils.UIUtil;

public class AppStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIUtil.showSearchActivity(AppStart.this);
    }
}
