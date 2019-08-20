package com.journey.news.application;

import com.journey.network.BuildConfig;
import com.journey.network.INetworkRequestInfo;

import java.util.HashMap;

/**
 * 网络层请求头的实现类
 */
public class NetworkRequestInfo implements INetworkRequestInfo {

    HashMap<String,String> headerMap = new HashMap<>();

    public NetworkRequestInfo(){
        headerMap.put("os","android");
        headerMap.put("versionName",BuildConfig.VERSION_NAME);
        headerMap.put("versionCode",String.valueOf(BuildConfig.VERSION_CODE));
        headerMap.put("applicationId",BuildConfig.APPLICATION_ID);
    }
    @Override
    public HashMap<String, String> getRequestHeaderMap() {
        return headerMap;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
