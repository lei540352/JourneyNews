package com.journey.base.activity;

public interface IBaseView {
    //显示内容
    void showContent();

    //加载中
    void showLoading();

    //刷新
    void onRefreshEmpty();

    //显示失败
    void onRefreshFailure(String message);
}
