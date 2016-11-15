package cn.bmob.relationdemo;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by nothing on 2016/11/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        Bmob.initialize(getApplicationContext(),"");
    }
}
