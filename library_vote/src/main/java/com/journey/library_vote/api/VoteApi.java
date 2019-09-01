package com.journey.library_vote.api;

import com.journey.network.beans.BaseResult;
import com.journey.library_vote.baen.VoteItemResultProtocol;
import com.journey.network.ApiBase;
import com.journey.network.utils.TecentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;

public class VoteApi extends ApiBase {

    private static volatile VoteApi instance = null;
    private VoteApiInterface voteApiInterface;
    public static final String PAGE = "page";

    private VoteApi() {
        super("http://www.diaoyanbang.com/");
        voteApiInterface = retrofit.create(VoteApiInterface.class);
    }

    public static VoteApi getInstance() {
        if (instance == null) {
            synchronized (VoteApi.class) {
                if (instance == null) {
                    instance = new VoteApi();
                }
            }
        }
        return instance;
    }

    /**
     * 用于获取投票列表
     *
     * @param observer    由调用者传过来的观察者对象
     * @param votetypeId   类型ID
     * @param keyname  搜索关键字
     * @param page        获取页号
     */
    public void getVoteList(Observer<BaseResult<ArrayList<VoteItemResultProtocol>>> observer, String votetypeId, String userid, String page, String keyname) {
        Map<String, String> requestMap = new HashMap<>();
        String timeStr = TecentUtil.getTimeStr();
        requestMap.put("mod", votetypeId);
        requestMap.put("uid", userid);
        requestMap.put("key", keyname);
        requestMap.put(PAGE, page);
        ApiSubscribe(voteApiInterface.getVoteList("source", TecentUtil.getAuthorization(timeStr), timeStr, requestMap), observer);
    }
}
