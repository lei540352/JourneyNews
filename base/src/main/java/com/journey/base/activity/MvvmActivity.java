package com.journey.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.journey.base.loadsir.EmptyCallback;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;


//Activity的基类
// mvvm  v--vm -- m  view -->viewmodel-->model
public abstract class MvvmActivity <V extends ViewDataBinding,VM extends MvvmBaseViewModel> extends AppCompatActivity implements IBaseView{

    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;

    public abstract
    @LayoutRes
    int getLayoutId();

    protected  abstract VM getViewModel();

    public abstract int getBindingVariable();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
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


    private void performDataBinding(){
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        if (viewModel == null){
            this.viewModel = getViewModel();
        }

        //完成绑定
        if (getBindingVariable() > 0){
           viewDataBinding.setVariable(getBindingVariable(),viewDataBinding);
        }
        viewDataBinding.executePendingBindings();
    }

    protected abstract void onRetryBtnClick();

    @Override
    public void onRefreshEmpty(){
        if (mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message){
        if (mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showLoading(){
        if (mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showContent(){
        if (mLoadService != null){
            mLoadService.showSuccess();
        }
    }
}
