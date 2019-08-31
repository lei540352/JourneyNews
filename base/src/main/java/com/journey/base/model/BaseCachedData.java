package com.journey.base.model;

import java.io.Serializable;

public class BaseCachedData<T> implements Serializable {
    public long updateTimeInMills;//保存时间，下次读取缓存时根据这个时间来判断是否显示缓存
    public T data;
}
