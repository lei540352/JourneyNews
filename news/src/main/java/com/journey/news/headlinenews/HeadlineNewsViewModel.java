package com.journey.news.headlinenews;

import com.journey.base.activity.IBaseView;
import com.journey.base.model.BaseModel;
import com.journey.base.viewmodel.MvvmBaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * viewmodel 通知view 更新页面即 修改fragment的内容
 */
public class HeadlineNewsViewModel extends MvvmBaseViewModel<HeadlineNewsViewModel.IMainView, ChannelsModel> implements BaseModel.IModelListener<ArrayList<ChannelsModel.Channel>> {

        public ArrayList<ChannelsModel.Channel> channels = new ArrayList<>();
        public HeadlineNewsViewModel(){
            model = new ChannelsModel();
            model.register(this);
        }

        public void refresh(){
            model.getCachedDataAndLoad();
        }

        @Override
        public void onLoadFinish(BaseModel model, ArrayList<ChannelsModel.Channel> data) {
            if(model instanceof ChannelsModel){
                if(getPageView() != null && data instanceof List) {
                    channels.clear();
                    channels.addAll(data);
                    getPageView().onChannelsLoaded(channels);
                }
            }
        }

        @Override
        public void onLoadFail(BaseModel model, String prompt) {
        }


        public interface IMainView extends IBaseView {
            void onChannelsLoaded(ArrayList<ChannelsModel.Channel> channels);
        }
}
