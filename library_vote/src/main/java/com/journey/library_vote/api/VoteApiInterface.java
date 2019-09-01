package com.journey.library_vote.api;

import com.journey.network.beans.BaseResult;
import com.journey.library_vote.baen.VoteItemResultProtocol;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface VoteApiInterface {

    @GET("App/getListVote")
    Observable<BaseResult<ArrayList<VoteItemResultProtocol>>> getVoteList(
            @Header("Source") String source,
            @Header("Authorization") String authorization,
            @Header("Date") String date,
            @QueryMap Map<String, String> options);

}
