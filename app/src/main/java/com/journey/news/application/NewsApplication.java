package com.journey.news.application;

import com.billy.cc.core.component.CC;
import com.journey.base.BaseApplication;
import com.journey.base.loadsir.CustomCallback;
import com.journey.base.loadsir.EmptyCallback;
import com.journey.base.loadsir.ErrorCallback;
import com.journey.base.loadsir.LoadingCallback;
import com.journey.base.loadsir.TimeoutCallback;
import com.journey.network.ApiBase;
import com.journey.news.BuildConfig;
import com.kingja.loadsir.core.LoadSir;

public class NewsApplication extends BaseApplication {

    @Override
    public void onCreate(){
        super.onCreate();
        setDebug(BuildConfig.DEBUG);

        //初始化网络层
        //会将请求头的参数带入到网络拦截器当中
        ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);
    }

}
