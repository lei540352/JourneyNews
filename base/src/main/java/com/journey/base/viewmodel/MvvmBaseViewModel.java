package com.journey.base.viewmodel;

import androidx.lifecycle.ViewModel;

import com.journey.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * viewmodel 的基类
 * 与UI进行绑定
 */
public class MvvmBaseViewModel <V,M extends SuperBaseModel> extends ViewModel implements IMvvmBaseViewModel<V> {

    private Reference<V> mUIRef;
    protected M model;

    public void attachUI(V ui){
        mUIRef = new WeakReference<>(ui);
    }

    @Override
    public V getPageView() {
        if (mUIRef == null){
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null &&mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null){
            mUIRef.clear();
            mUIRef = null;
        }
        if (model != null){
            model.cancel();
        }
    }
}
