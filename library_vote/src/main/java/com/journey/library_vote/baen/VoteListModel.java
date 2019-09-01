package com.journey.library_vote.baen;

import com.google.gson.reflect.TypeToken;
import com.journey.base.model.BasePagingModel;
import com.journey.network.beans.BaseResult;
import com.journey.library_vote.api.VoteApi;
import com.journey.network.errorhandler.ExceptionHandle;
import com.journey.network.observer.BaseObserver;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VoteListModel<T> extends BasePagingModel<T> {

    private String mVoteTypeId = "";
    private String mVoteTypeName = "";
    private static final String PREF_KEY_NEWS_CHANNEL = "pref_key_votes_";//mVoteTypeId 来缓存数据

    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_NEWS_CHANNEL + mVoteTypeId;
    }

    protected Type getTClass() {
        return new TypeToken<ArrayList<VoteItemResultProtocol>>() {
        }.getType();
    }

    public VoteListModel(String votetypeId, String votetypeName) {
        mVoteTypeId = votetypeId;
        mVoteTypeName = votetypeName;
    }


    @Override
    public void refresh() {
        isRefresh = true;
        load();
    }

    //下一页
    public void loadNexPage() {
        isRefresh = false;
        load();
    }

    @Override
    protected void load() {
        VoteApi.getInstance().getVoteList(new BaseObserver<BaseResult<ArrayList<VoteItemResultProtocol>>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loadFail(e.message, isRefresh);
            }

            @Override
            public void onNext(BaseResult<ArrayList<VoteItemResultProtocol>> arrayListBaseResult) {
                // All observer run on main thread, no need to synchronize
                pageNumber = isRefresh ? 2 : pageNumber + 1;

                ArrayList<VoteItemResultProtocol> voteItemResultProtocols = arrayListBaseResult.getData();
                loadSuccess((T) voteItemResultProtocols, voteItemResultProtocols.size() == 0, isRefresh, voteItemResultProtocols.size() == 0);
            }
        },mVoteTypeId,"1", String.valueOf(isRefresh ? 1 : pageNumber),"");
    }
}
