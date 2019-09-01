package com.journey.library_vote.voteview_item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.journey.base.customview.BaseCustomView;
import com.journey.library_vote.R;
import com.journey.library_vote.baen.VoteItemResultProtocol;
import com.journey.library_vote.databinding.VoteItemBinding;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class VoteItemView extends BaseCustomView<VoteItemBinding, VoteItemResultProtocol> {
    public VoteItemView(Context context) {
        super(context);
    }

    public VoteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.vote_item;
    }

    @Override
    protected void setDataToView(VoteItemResultProtocol data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
//        WebActivity.startCommonWeb(view.getContext(), "", getViewModel().link);
    }
}
