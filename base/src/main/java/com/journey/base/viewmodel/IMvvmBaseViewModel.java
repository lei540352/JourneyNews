package com.journey.base.viewmodel;

/**
 * UI要和viewmodel绑定 ，所以要在UI初始化后加进来
 * @param <V>
 */
public interface IMvvmBaseViewModel<V> {

    void attachUI(V view);

    V getPageView();

    boolean isUIAttached();

    void detachUI();
}
