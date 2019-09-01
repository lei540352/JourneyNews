package com.journey.library_vote.baen;

import com.google.gson.reflect.TypeToken;
import com.journey.base.model.BaseModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
/**
 * model 从网络上获取数据
 * BaseModel<T> 因为返回的是列表数据 所以泛型数据为list集合
 * BaseModel<ArrayList<VoteTypeModel.VoteType>>
 */
public class VoteTypeModel extends BaseModel<ArrayList<VoteTypeModel.VoteType>> {

    private static final String PREF_KEY_VOTE_TYPE = "pref_key_vote_type";//缓存投票类型标题
    public static final String PREDEFINED_VOTE = "[\n" +
            "    {\n" +
            "        \"votetypeID\": \"index\",\n" +
            "        \"voteTypeName\": \"全部投票\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"votetypeID\": \"publish\",\n" +
            "        \"voteTypeName\": \"我发布的\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"votetypeID\": \"join\",\n" +
            "        \"voteTypeName\": \"我参与的\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"votetypeID\": \"favorite\",\n" +
            "        \"voteTypeName\": \"我收藏的\"\n" +
            "    }\n" +
            "]";

    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_VOTE_TYPE;
    }

    @Override
    protected String getApkString() {
        return PREDEFINED_VOTE;
    }

    // 反序列化
    protected Type getTClass(){
        return new TypeToken<ArrayList<VoteType>>(){}.getType();
    }
    @Override
    public void refresh() {

    }

    @Override
    protected void load() {
        loadFail("");
    }

    public class VoteType{//投票类型
        public String votetypeID;
        public String voteTypeName;
    }
}
