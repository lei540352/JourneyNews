package com.journey.library_vote.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.journey.library_vote.baen.VoteTypeModel;
import com.journey.library_vote.fragment.VoteListFragment;

import java.util.ArrayList;

public class VoteTypeFragmentAdapter extends FragmentPagerAdapter {

    public ArrayList<VoteTypeModel.VoteType> voteTypes;

    public VoteTypeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setVoteTypes(ArrayList<VoteTypeModel.VoteType> voteTypes) {
        this.voteTypes = voteTypes;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return  VoteListFragment.newInstance(voteTypes.get(position).votetypeID,voteTypes.get(position).voteTypeName);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return FragmentPagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        if (voteTypes != null && voteTypes.size() > 0) {
            return voteTypes.size();
        }
        return 0;
    }
}
