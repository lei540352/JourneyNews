package com.journey.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.journey.base.R;
import com.journey.base.loadsir.EmptyCallback;
import com.journey.base.loadsir.ErrorCallback;
import com.journey.base.loadsir.LoadingCallback;
import com.journey.base.utils.ToastUtil;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

/**
 * fragment 的基类
 * @param <V>
 * @param <VM>
 */
public abstract class MvvmFragment <V extends ViewDataBinding,VM extends MvvmBaseViewModel> extends Fragment implements IBasePagingView {
    protected VM viewModel;
    protected V viewDataBinding;
    protected String mFragmentTag = "";
    private LoadService mLoadService;//打印日志

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract VM getViewModel();

    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    /**
     * 初始化参数
     */
    protected void initParameters(){

    }
    /**
     * 绑定dataBinding
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return viewDataBinding.getRoot();
    }

    /**
     * fragment初始化时(即view创建好后)就要让ui绑定viewmodel
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (viewModel != null){
            viewModel.attachUI(this);
        }

        if (getBindingVariable() > 0){
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
            viewDataBinding.executePendingBindings();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefreshEmpty() {
        if (mLoadService != null)
        {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null){
            if (!isShowedContent){
                mLoadService.showCallback(ErrorCallback.class);
            }else{
                ToastUtil.show(getContext(),message);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null){
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    private boolean isShowedContent = false;

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void onLoadMoreFailure(String message) {
        ToastUtil.show(getContext(), message);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show(getContext(), getString(R.string.no_more_data));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getFragmentTag(), this + ": " + "onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Log.d(getFragmentTag(), this + ": " + "onAttach");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //绑定完成后detachUI（分离）
        if (viewModel != null && viewModel.isUIAttached()){
            viewModel.detachUI();
        }
        Log.d(getFragmentTag(), this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), this + ": " + "onDestroy");
        super.onDestroy();
    }

    protected abstract void onRetryBtnClick();

    //出现错误要重试
    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    /**
     * 通过抽象 获取页面的Tag 并且打印日志
     */
    protected abstract String getFragmentTag();
}
