package com.journey.news.headlinenews;

import com.google.gson.reflect.TypeToken;
import com.journey.base.model.BaseModel;
import com.journey.network.errorhandler.ExceptionHandle;
import com.journey.network.observer.BaseObserver;
import com.journey.news.api.NewsApi;
import com.journey.news.bean.NewsChannelsBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * model 从网络上获取数据
 * BaseModel<T> 因为返回的是列表数据 所以泛型数据为list集合
 * BaseModel<ArrayList<ChannelsModel.Channel>>
 */
public class ChannelsModel extends BaseModel<ArrayList<ChannelsModel.Channel>>{

    private static final String PREF_KEY_HOME_CHANNEL = "pref_key_home_channel";//缓存新闻类型标题
    public static final String PREDEFINED_CHANNELS = "[\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001cd\",\n" +
            "        \"channelName\": \"国内焦点\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001ce\",\n" +
            "        \"channelName\": \"国际焦点\"\n" +
            "    }\n" +
            "]";

    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_HOME_CHANNEL;
    }

    //反序列化的基类
    @Override
    protected Type getTClass() {
        return new TypeToken<ArrayList<Channel>>(){}.getType();
    }

    /**
     * apk级别的缓存
     * 即写死的本地数据，网络不好或者数据加载失败时显示
     * @return
     */
    @Override
    protected String getApkString() {
        return PREDEFINED_CHANNELS;
    }

    @Override
    public void refresh() {

    }

    @Override
    protected void load() {
        NewsApi.getInstance().getNewsChannels(new BaseObserver<NewsChannelsBean>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message);
            }

            @Override
            public void onNext(NewsChannelsBean newsChannelsBean) {
                ArrayList<Channel> channels = new ArrayList<>();
                for (NewsChannelsBean.ChannelList source : newsChannelsBean.showapiResBody.channelList){
                    Channel channel = new Channel();
                    channel.channelId = source.channelId;
                    channel.channelName = source.name;
                    channels.add(channel);
                }
                loadSuccess(channels);
            }
        });
    }

    public class Channel{
        public String channelId;
        public String channelName;
    }

}
