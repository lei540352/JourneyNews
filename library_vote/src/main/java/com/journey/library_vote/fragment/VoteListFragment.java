package com.journey.library_vote.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.journey.base.fragment.MvvmFragment;
import com.journey.library_vote.R;
import com.journey.library_vote.adapter.VotesListRecyclerViewAdapter;
import com.journey.library_vote.baen.VoteItemResultProtocol;
import com.journey.library_vote.databinding.VotesFragmentBinding;
import com.journey.library_vote.model.VoteListViewModel;
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
public class VoteListFragment extends MvvmFragment<VotesFragmentBinding, VoteListViewModel> implements VoteListViewModel.IVoewView {

    private VotesListRecyclerViewAdapter mAdapter;
    private String mVoteTypeId = "";
    private String mVoteTypeName = "";

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_votetype_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_votetype_name";

    public static VoteListFragment newInstance(String channelId, String channelName) {
        VoteListFragment fragment = new VoteListFragment();
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
        return R.layout.votes_fragment;
    }

    @Override
    public VoteListViewModel getViewModel() {
        Log.e(this.getClass().getSimpleName(), this + ": createViewModel.");
        return new VoteListViewModel(mVoteTypeId, mVoteTypeName);
    }

    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            mVoteTypeId = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID);
            mVoteTypeName = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
            mFragmentTag = mVoteTypeName;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTag = "VoteListFragment";
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new VotesListRecyclerViewAdapter();
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

    /***
     * 重试按钮点击
     */
    protected void onRetryBtnClick() {
        viewModel.tryToRefresh();
    }

    @Override
    protected String getFragmentTag() {
        return mVoteTypeName;
    }

    //加载完数据通知页面刷新，隐藏loading
    @Override
    public void onVoteLoaded(ArrayList<VoteItemResultProtocol> voteItemResultProtocols) {
        if (voteItemResultProtocols != null && voteItemResultProtocols.size() > 0) {
            viewDataBinding.refreshLayout.finishLoadMore();
            viewDataBinding.refreshLayout.finishRefresh();
            showContent();
            mAdapter.setData(voteItemResultProtocols);
        } else {
            onRefreshEmpty();
        }
    }
}
