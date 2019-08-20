package com.journey.network;


import java.util.HashMap;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface INetworkRequestInfo {
    //此接口要获取APP请求头的map是什么。可以再application中初始化它
    HashMap<String, String> getRequestHeaderMap();
    boolean isDebug();
}
