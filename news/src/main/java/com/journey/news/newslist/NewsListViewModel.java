package com.journey.news.newslist;

import com.journey.base.customview.BaseCustomViewModel;
import com.journey.base.fragment.IBasePagingView;
import com.journey.base.model.BasePagingModel;
import com.journey.base.viewmodel.MvvmBaseViewModel;

import java.util.ArrayList;

public class NewsListViewModel extends MvvmBaseViewModel<NewsListViewModel.INewsView,NewsListModel> implements BasePagingModel.IModelListener<ArrayList<BaseCustomViewModel>> {
    private ArrayList<BaseCustomViewModel> mNewsList = new ArrayList<>();

    public NewsListViewModel(String classId, String lboClassId) {
        model = new NewsListModel(classId, lboClassId);
        model.register(this);
        model.getCachedDataAndLoad();
    }

    @Override
    public void onLoadFinish(BasePagingModel model, ArrayList<BaseCustomViewModel> data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        if (getPageView() != null) {
            if (model instanceof NewsListModel) {
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
                    getPageView().onNewsLoaded(mNewsList);
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

    public interface INewsView extends IBasePagingView {
        void onNewsLoaded(ArrayList<BaseCustomViewModel> channels);
    }
}
