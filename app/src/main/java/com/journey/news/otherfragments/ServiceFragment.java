package com.journey.news.otherfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.journey.news.R;
import com.journey.news.databinding.ActivitySelfDrawableBinding;
import com.journey.news.databinding.FragmentOthersBinding;
import com.journey.news.utils.TaskClearDrawable;
import com.journey.news.utils.Utils;


public class ServiceFragment extends Fragment {
    ActivitySelfDrawableBinding mBinding;

    private TaskClearDrawable mTaskClearDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_self_drawable, container, false);

        mTaskClearDrawable = new TaskClearDrawable(getContext(), Utils.dp2px(400), Utils.dp2px(400));
        mBinding.imageView.setImageDrawable(mTaskClearDrawable);

        mBinding.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("Zero", "mTaskClearDrawable = " + mTaskClearDrawable.isRunning() );
                if(false == mTaskClearDrawable.isRunning()){
//                    mTaskClearDrawable.start();
                }
            }
        });
        return mBinding.getRoot();
    }
}
