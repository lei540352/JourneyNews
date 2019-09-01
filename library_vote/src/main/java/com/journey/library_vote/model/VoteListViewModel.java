package com.journey.library_vote.model;

import com.journey.base.customview.BaseCustomViewModel;
import com.journey.base.fragment.IBasePagingView;
import com.journey.base.model.BasePagingModel;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.journey.library_vote.baen.VoteItemResultProtocol;
import com.journey.library_vote.baen.VoteListModel;

import java.util.ArrayList;

public class VoteListViewModel extends MvvmBaseViewModel<VoteListViewModel.IVoewView, VoteListModel> implements BasePagingModel.IModelListener<ArrayList<VoteItemResultProtocol>> {
    private ArrayList<VoteItemResultProtocol> mNewsList = new ArrayList<>();

    public VoteListViewModel(String classId, String lboClassId) {
        model = new VoteListModel(classId, lboClassId);
        model.register(this);
        model.getCachedDataAndLoad();
    }

    @Override
    public void onLoadFinish(BasePagingModel model, ArrayList<VoteItemResultProtocol> data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        if (getPageView() != null) {
            if (model instanceof VoteListModel) {
                if (isFirstPage) {//是不是第0页
                    mNewsList.clear();
                }
                if (isEmpty) {//是否为空
                    if (isFirstPage) {
                        getPageView().onRefreshEmpty();
                    } else {
                        getPageView().onLoadMoreEmpty();
                    }
                } else {
                    mNewsList.addAll(data);
                    getPageView().onVoteLoaded(mNewsList);
                }
            }
        }
    }

    @Override
    public void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage) {
        if (getPageView() != null) {
            if (isFirstPage) {
                getPageView().onRefreshFailure(prompt);
            } else {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }

    //
    public void tryToRefresh() {
        model.refresh();
    }

    public void tryToLoadNextPage() {
        model.loadNexPage();
    }

    public interface IVoewView extends IBasePagingView {
        void onVoteLoaded(ArrayList<VoteItemResultProtocol> voteItemResultProtocols);
    }
}
