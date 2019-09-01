package com.journey.library_vote.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.journey.base.recyclerview.BaseViewHolder;
import com.journey.base.utils.Utils;
import com.journey.library_vote.baen.VoteItemResultProtocol;
import com.journey.library_vote.voteview_item.VoteItemView;

import java.util.ArrayList;

public class VotesListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<VoteItemResultProtocol> mItems;


    public void setData(ArrayList<VoteItemResultProtocol> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VoteItemView voteItemView = new VoteItemView(parent.getContext());
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        voteItemView.setLayoutParams(layoutParams);
        return new BaseViewHolder(voteItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        VoteItemResultProtocol voteItemResultProtocol = mItems.get(position);
        holder.bind(voteItemResultProtocol);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


}
