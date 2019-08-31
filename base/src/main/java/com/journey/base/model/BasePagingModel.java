package com.journey.base.model;

import java.lang.ref.WeakReference;

/**
 * 分页的model 基类
 * @param <T>
 */
public abstract class BasePagingModel<T> extends SuperBaseModel<T>{
    protected boolean isRefresh = true;//是不是刷新页面
    protected int pageNumber = 0;

    /**
     * 发消息给UI线程
     *
     * @param isEmpty     model的整个数据是否为空
     * @param isFirstPage 是否是第一页
     * @param hasNextPage 是否还有下一页
     */
    protected  void loadSuccess(final T data,final boolean isEmpty,final boolean isFirstPage,final boolean hasNextPage){
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            //通知监听者数据已经拿到，并将数据发送过去
                            listenerItem.onLoadFinish(BasePagingModel.this, data, isEmpty, isFirstPage, hasNextPage);
                        }
                    }
                }
                //将第一页数据缓存起来
                if (getCachedPreferenceKey() != null && isFirstPage) {
                    saveDataToPreference(data);
                }
            }
        },0);
    }

    protected void loadFail(final String prompt,final boolean isFirstPage){
        synchronized (this){
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                        if (weakListener.get() instanceof IModelListener) {
                            IModelListener listenerItem = (IModelListener) weakListener.get();
                            if (listenerItem != null) {
                                listenerItem.onLoadFail(BasePagingModel.this, prompt, isFirstPage);
                            }
                        }
                    }
                }
            },0);
        }
    }
    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data, false, true, true);
    }

    /**
     * 监听数据
     * @param <T>
     */
    public interface IModelListener<T> extends IBaseModelListener {
        /**
         *加载成功
         * @param model 数据模型
         * @param data  数据内容
         * @param isEmpty  是否为空
         * @param isFirstPage 是否是第一页
         * @param hasNextPage 是否分页
         */
        void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage);

        /**
         * 加载失败
         * @param model 数据模型
         * @param prompt  失败原因
         * @param isFirstPage  是否是第一页 如果是第一页显示错误页面 不是第一页显示加载更多
         */
        void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
    }
}
