package com.journey.base.viewmodel;

import androidx.lifecycle.ViewModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * viewmodel 的基类
 * 与UI进行绑定
 */
public class MvvmBaseViewModel <V> extends ViewModel implements IMvvmBaseViewModel<V> {

    private Reference<V> mUIRef;

    public void attachUI(V ui){
        mUIRef = new WeakReference<>(ui);
    }

    @Override
    public V getPageView() {
        return null;
    }

    @Override
    public boolean isUIAttached() {
        return false;
    }

    @Override
    public void detachUI() {

    }


}
