package com.journey.base.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.CallSuper;


import com.journey.base.preference.BasicDataPreferenceUtil;
import com.journey.base.utils.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * model的实现 需要 分页 缓存 预制（刚进入的显示）
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public abstract class SuperBaseModel<T> {
    protected Handler mUiHandler = new Handler(Looper.getMainLooper());
    private CompositeDisposable compositeDisposable;
    //使用弱引用 将监听者保存起来
    protected ReferenceQueue<IBaseModelListener> mReferenceQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> mWeakListenerArrayList;
    private BaseCachedData<T> mData;//需要缓存的数据

    //初始化数据
    public SuperBaseModel() {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        if(getCachedPreferenceKey() != null){
            mData = new BaseCachedData<T>();
        }
    }

    /**
     * 注册监听
     * 监听数据发送到什么地方
     * @param listener
     */
    public void register(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }

        synchronized (this) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends IBaseModelListener> releaseListener = null;
            while ((releaseListener = mReferenceQueue.poll()) != null) {
                mWeakListenerArrayList.remove(releaseListener);
            }

            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener listenerItem = weakListener.get();
                if (listenerItem == listener) {
                    return;
                }
            }
            WeakReference<IBaseModelListener> weakListener = new WeakReference<IBaseModelListener>(listener, mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }

    }

    /**
     * 取消监听
     *
     * @param listener
     */
    public void unRegister(IBaseModelListener listener) {

        if (listener == null) {
            return;
        }
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener listenerItem = weakListener.get();
                if (listener == listenerItem) {
                    mWeakListenerArrayList.remove(weakListener);
                    break;
                }
            }
        }
    }

    /**
     * 由于渠道处在App的首页，为了保证app打开的时候由于网络慢或者异常的情况下tablayout不为空，
     * 所以app对渠道数据进行了预制；
     * 加载完成以后会立即进行网络请求，同时缓存在本地，今后app打开都会从preference读取，而不在读取
     * apk预制数据，由于渠道数据变化没那么快，在app第二次打开的时候会生效，并且是一天请求一次。
     */
    protected void saveDataToPreference(T data) {
        mData.data = data;
        mData.updateTimeInMills = System.currentTimeMillis();
        //保存数据到preference  getCachedPreferenceKey() 定义的key值
        BasicDataPreferenceUtil.getInstance().setString(getCachedPreferenceKey(), GsonUtils.toJson(mData));
    }

    //刷新数据
    public abstract void refresh();
    //加载数据
    protected abstract void load();
    //通知缓存数据
    protected abstract void notifyCachedData(T data);


    /** 该model的数据是否需要缓存，如果需要请返回缓存的key
     * */
    protected String getCachedPreferenceKey(){
        return null;
    }

    /** 缓存的数据的类型
     * */
    protected Type getTClass(){
        return null;
    }


    /** 该model的数据是否有apk预制的数据，如果有请返回，默认没有
     * */
    protected String getApkString(){
        return null;
    }

    /** 是否更新数据，可以在这里设计策略，可以是一天一次，一月一次等等，
     *  默认是每次请求都更新
     * */
    protected boolean isNeedToUpdate() {
        return true;
    }

    /**
     * 防止内存泄露
     * 在页面创建时添加
     * 在页面退出时取消
     */
    @CallSuper
    public void cancel(){
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected interface IBaseModelListener {
    }

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(d);
    }

    /**
     * 读取缓存数据
     * 三级缓存机制
     * 1。首先读取是否存在缓存
     * 2、1不存在判断apk数据存在
     * 3、前两者都没有就从网络获取数据
     */
    public void getCachedDataAndLoad() {
        //判断key是否为空
        if (getCachedPreferenceKey() != null) {
            //获取缓存value
            String saveDataString = BasicDataPreferenceUtil.getInstance().getString(getCachedPreferenceKey());
            //判断value是否是空值
            if (!TextUtils.isEmpty(saveDataString)) {
                try {
                    //反序列化获取到的值 即将数据解析为T对象
                    T savedData = GsonUtils.fromLocalJson(new JSONObject(saveDataString).getString("data"), getTClass());
                    if (savedData != null) {
                        notifyCachedData(savedData);
                        if (isNeedToUpdate()) {//是否更新数据
                            load();
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (getApkString() != null) {
                T t = GsonUtils.fromLocalJson(getApkString(), getTClass());
                notifyCachedData(t);
            }
        }
        load();
    }
}
