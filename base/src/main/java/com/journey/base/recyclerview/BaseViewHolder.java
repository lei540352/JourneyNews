package com.journey.base.recyclerview;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.journey.base.customview.BaseCustomViewModel;
import com.journey.base.customview.ICustomView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    ICustomView iCustomView;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.iCustomView = view;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        iCustomView.setData(item);
    }

}
