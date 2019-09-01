package com.journey.news.newslist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.journey.base.customview.BaseCustomViewModel;
import com.journey.base.fragment.MvvmFragment;
import com.journey.news.R;
import com.journey.news.databinding.NewsFragmentBinding;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * 实现viewmodel接口，view 与数据进行交互通信
 */
public class NewsListFragment extends MvvmFragment<NewsFragmentBinding,NewsListViewModel> implements NewsListViewModel.INewsView {

    private NewsListRecyclerViewAdapter mAdapter;
    private String mChannelId = "";
    private String mChannelName = "";

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.news_fragment;
    }

    @Override
    public NewsListViewModel getViewModel() {
        Log.e(this.getClass().getSimpleName(), this + ": createViewModel.");
        return new NewsListViewModel(mChannelId, mChannelName);
    }

    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            mChannelId = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID);
            mChannelName = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
            mFragmentTag = mChannelName;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTag = "NewsListFragment";
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewsListRecyclerViewAdapter();
        viewDataBinding.listview.setAdapter(mAdapter);
        viewDataBinding.refreshLayout.setRefreshHeader(new WaterDropHeader(getContext()));
        viewDataBinding.refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.tryToRefresh();
            }
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.tryToLoadNextPage();
            }
        });
        setLoadSir(viewDataBinding.refreshLayout);
        showLoading();
    }

    //加载完数据通知页面刷新，隐藏loading
    @Override
    public void onNewsLoaded(ArrayList<BaseCustomViewModel> newsItems) {
        if (newsItems != null && newsItems.size() > 0) {
            viewDataBinding.refreshLayout.finishLoadMore();
            viewDataBinding.refreshLayout.finishRefresh();
            showContent();
            mAdapter.setData(newsItems);
        } else {
            onRefreshEmpty();
        }
    }

    /***
     * 重试按钮点击
     */
    protected void onRetryBtnClick() {
        viewModel.tryToRefresh();
    }

    @Override
    protected String getFragmentTag() {
        return mChannelName;
    }
}
