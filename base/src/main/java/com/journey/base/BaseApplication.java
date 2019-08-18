package com.journey.base;

import android.app.Application;

public class BaseApplication extends Application {
    //静态的context  不会发生oom 跟随整个生命周期
    public  static Application mApplication;
    public static boolean sDebug;

    public void setDebug(boolean debug){
        sDebug = debug;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        mApplication = this;
    }
}
