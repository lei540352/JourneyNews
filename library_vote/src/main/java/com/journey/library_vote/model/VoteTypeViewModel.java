package com.journey.library_vote.model;

import com.journey.base.activity.IBaseView;
import com.journey.base.model.BaseModel;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.journey.library_vote.baen.VoteTypeModel;

import java.util.ArrayList;
import java.util.List;

public class VoteTypeViewModel extends MvvmBaseViewModel<VoteTypeViewModel.IMainView,VoteTypeModel> implements BaseModel.IModelListener<ArrayList<VoteTypeModel.VoteType>> {

    public ArrayList<VoteTypeModel.VoteType> voteTypes = new ArrayList<>();

    public VoteTypeViewModel(){
        model = new VoteTypeModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }

    public void refresh(){
        model.getCachedDataAndLoad();
    }

    @Override
    public void onLoadFinish(BaseModel model, ArrayList<VoteTypeModel.VoteType> data) {
        if(model instanceof VoteTypeModel){
            if(getPageView() != null && data instanceof List) {
                voteTypes.clear();
                voteTypes.addAll(data);
                getPageView().onVoteTypeLoaded(voteTypes);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

    }

    public interface IMainView extends IBaseView {
        void onVoteTypeLoaded(ArrayList<VoteTypeModel.VoteType> voteTypes);
    }
}
