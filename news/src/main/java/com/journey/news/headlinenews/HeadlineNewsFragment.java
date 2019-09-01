package com.journey.news.headlinenews;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.journey.base.fragment.MvvmFragment;
import com.journey.news.R;
import com.journey.news.databinding.FragmentHomeBinding;

import java.util.ArrayList;

/**
 * 头条新闻导航
 */
public class HeadlineNewsFragment extends MvvmFragment<FragmentHomeBinding,HeadlineNewsViewModel> implements HeadlineNewsViewModel.IMainView {

    HeadlineNewsFragmentAdapter mAdapter;

    /**
     * 创建好view后viewmodel刷新数据
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.refresh();
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        initChannels();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HeadlineNewsViewModel getViewModel() {
        return new HeadlineNewsViewModel();
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return "HeadlineNewsFragment";
    }

    /**
     * 初始化布局
     */
    public void initChannels() {
        mAdapter = new HeadlineNewsFragmentAdapter(getChildFragmentManager());
        viewDataBinding.viewpager.setAdapter(mAdapter);
        viewDataBinding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(viewDataBinding.tablayout));
        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        //绑定tab点击事件
        viewDataBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewDataBinding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onChannelsLoaded(ArrayList<ChannelsModel.Channel> channels) {
        mAdapter.setChannels(channels);
        viewDataBinding.tablayout.removeAllTabs();
        for (ChannelsModel.Channel channel : channels) {
            viewDataBinding.tablayout.addTab(viewDataBinding.tablayout.newTab().setText(channel.channelName));
        }
        viewDataBinding.tablayout.scrollTo(0, 0);
    }
}
