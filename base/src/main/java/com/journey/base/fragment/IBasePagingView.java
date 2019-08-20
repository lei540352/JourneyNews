package com.journey.base.fragment;

import com.journey.base.activity.IBaseView;

public interface IBasePagingView extends IBaseView {

    //fragment 分页加载更多
    void onLoadMoreFailure(String message);

    //fragment 加载更多没有数据
    void onLoadMoreEmpty();
}
