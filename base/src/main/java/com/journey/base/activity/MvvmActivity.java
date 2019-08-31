package com.journey.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.journey.base.loadsir.EmptyCallback;
import com.journey.base.loadsir.ErrorCallback;
import com.journey.base.loadsir.LoadingCallback;
import com.journey.base.utils.Logger;
import com.journey.base.viewmodel.IMvvmBaseViewModel;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;


//Activity的基类
// mvvm  v--vm -- m  view -->viewmodel-->model
public abstract class MvvmActivity<V extends ViewDataBinding, VM extends IMvvmBaseViewModel> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("MainActivity"," 输出内容 二 22222");
        initViewModel();
        performDataBinding();
    }

    private void initViewModel(){
        viewModel = getViewModel();
        if (viewModel != null){
            viewModel.attachUI(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null){
            viewModel.detachUI();
        }
    }


    @Override
    public void onRefreshEmpty(){
        if (mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message){
        if (mLoadService != null){
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading(){
        if (mLoadService != null){
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showContent(){
        if (mLoadService != null){
            mLoadService.showSuccess();
        }
    }

    //用错误页面替换view  比如网络错误 、空数据状态
    public void setLoadSir(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    protected abstract void onRetryBtnClick();

    protected  abstract VM getViewModel();

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    private void performDataBinding(){
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() :viewModel;
        //完成绑定
        if (getBindingVariable() > 0){
            viewDataBinding.setVariable(getBindingVariable(),viewDataBinding);
        }
        viewDataBinding.executePendingBindings();
    }
}
