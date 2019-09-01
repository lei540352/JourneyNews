package com.journey.library_vote.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.journey.base.fragment.MvvmFragment;
import com.journey.library_vote.R;
import com.journey.library_vote.adapter.VoteTypeFragmentAdapter;
import com.journey.library_vote.baen.VoteTypeModel;
import com.journey.library_vote.databinding.FragmentVoteBinding;
import com.journey.library_vote.model.VoteTypeViewModel;

import java.util.ArrayList;

//cc组件 注册投票列表页面
public class VoteTypeFragment extends MvvmFragment<FragmentVoteBinding, VoteTypeViewModel> implements VoteTypeViewModel.IMainView {

    private VoteTypeFragmentAdapter voteTypeFragmentAdapter;//导航栏适配器

    /**
     * 创建好view后viewmodel刷新数据
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.refresh();
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        initVoteType();
    }


    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    public VoteTypeViewModel getViewModel() {
        return new VoteTypeViewModel();
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return "VoteTypeFragment";
    }

    @Override
    public void onVoteTypeLoaded(ArrayList<VoteTypeModel.VoteType> voteTypes) {
        voteTypeFragmentAdapter.setVoteTypes(voteTypes);//获取到数据
        //设置tabbar
        viewDataBinding.tablayout.removeAllTabs();
        for (VoteTypeModel.VoteType channel : voteTypes) {
            viewDataBinding.tablayout.addTab(viewDataBinding.tablayout.newTab().setText(channel.voteTypeName));
        }
        viewDataBinding.tablayout.scrollTo(0, 0);
    }

    /**
     * 初始化
     */
    public void initVoteType(){
        voteTypeFragmentAdapter = new VoteTypeFragmentAdapter(getChildFragmentManager());
        viewDataBinding.viewpager.setAdapter(voteTypeFragmentAdapter);
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
}
