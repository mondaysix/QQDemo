package com.example.day06qqdemo;

import android.app.Application;

import com.oy.util.SharedUtil;

/**
 * Created by Administrator on 2016/10/18.
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedUtil.init(this);
    }
}
