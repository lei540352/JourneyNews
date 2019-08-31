package com.journey.news.headlinenews;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.journey.news.newslist.NewsListFragment;

import java.util.ArrayList;

public class HeadlineNewsFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ChannelsModel.Channel> mChannels;

    public void setChannels(ArrayList<ChannelsModel.Channel> mChannels) {
        this.mChannels = mChannels;
        this.notifyDataSetChanged();
    }

    public HeadlineNewsFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(mChannels.get(position).channelId,mChannels.get(position).channelName);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return FragmentPagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        if(mChannels != null && mChannels.size() > 0) {
            return mChannels.size();
        }
        return 0;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader){
    }
}
