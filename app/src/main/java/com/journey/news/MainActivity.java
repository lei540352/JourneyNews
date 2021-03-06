package com.journey.news;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.journey.base.activity.MvvmActivity;
import com.journey.base.utils.Logger;
import com.journey.base.utils.MeasureUtil;
import com.journey.base.viewmodel.MvvmBaseViewModel;
import com.journey.base.widget.UserGuideView;
import com.journey.news.databinding.ActivityMainBinding;
import com.journey.news.databinding.ActivityPrimaryBinding;
import com.journey.news.otherfragments.AccountFragment;
import com.journey.news.otherfragments.CategoryFragment;
import com.journey.news.otherfragments.ServiceFragment;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import q.rorbin.badgeview.QBadgeView;

/**
 *  继承基类，实现抽象方法
 */
public class MainActivity extends MvvmActivity<ActivityMainBinding,MvvmBaseViewModel> {

    private Fragment mHomeFragment;
    private Fragment mVoteFragment;
    private ServiceFragment mServiceFragment = new ServiceFragment();
    private AccountFragment mAccountFragment = new AccountFragment();

    private UserGuideView guideView;
    @Override
    public int getLayoutId(){
        return R.layout.activity_main;
    }

    @Override
    protected MvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("MainActivity"," 输出内容 一 11111");

        viewDataBinding.guideView.setTouchOutsideDismiss(false);
        viewDataBinding.guideView.setStatusBarHeight(MeasureUtil.getStatuBarHeight(this));

        //根据CC获取到fragment 组件化
        CCResult result = CC.obtainBuilder("News").setActionName("getHeadlineNewsFragment").build().call();
        mHomeFragment = (Fragment) result.getDataMap().get("fragment");
        fromFragment = mHomeFragment;

        //投票页面
        CCResult result2 = CC.obtainBuilder("Vote").setActionName("getVoteFragment").build().call();
        mVoteFragment = (Fragment) result2.getDataMap().get("fragment");

        //Set Toolbar
        setSupportActionBar(viewDataBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_home));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(viewDataBinding.bottomView);
        }

        viewDataBinding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.menu_home:
                        fragment = mHomeFragment;
                        break;
                    case R.id.menu_vote:
                        fragment = mVoteFragment;
                        break;
                    case R.id.menu_services:
                        fragment = mServiceFragment;
                        break;
                    case R.id.menu_account:
                        fragment= mAccountFragment;
                        break;
                }
                if (getSupportActionBar() != null)
                {
                    getSupportActionBar().setTitle(menuItem.getTitle());
                }
                switchFragment(fromFragment, fragment);
                fromFragment = fragment;
                return true;
            }
        });
        viewDataBinding.bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewDataBinding.container.getId(),mHomeFragment);
        transaction.commit();
        showBadgeView(3,1);

        LinkedHashMap<View, Integer> targets = new LinkedHashMap<>();
//        targets.put(top, R.mipmap.panda);
//        targets.put(topLeft, R.mipmap.tip3);
//        targets.put(topRight, R.mipmap.tip3);
        targets.put(viewDataBinding.bottomView.getChildAt(0), R.mipmap.tip2);
        targets.put(viewDataBinding.bottomView.getChildAt(1), R.mipmap.tip3);
        targets.put(viewDataBinding.bottomView.getChildAt(2), R.mipmap.tip_view);
//        viewDataBinding.guideView.setHighLightView(top,icon,back);
        viewDataBinding.guideView.setArrowDownRight(R.mipmap.guide_arrow_right);
        viewDataBinding.guideView.setArrowDownCenter(R.mipmap.guide_arrow_left);
        viewDataBinding.guideView.setArrowDownLeft(R.mipmap.guide_arrow_left);
        viewDataBinding.guideView.setArrowUpLeftMoveX(-30);
        viewDataBinding.guideView.setArrowDownRightMoveX(0);
        viewDataBinding.guideView.setTipViewMoveX(viewDataBinding.bottomView.getChildAt(0),-60);
//        viewDataBinding.guideView.setTipViewMoveX(bottomRight,140);
        viewDataBinding.guideView.setTipViewMoveX(viewDataBinding.bottomView.getChildAt(2),-50);
        viewDataBinding.guideView.setArrowDownLeftMoveX(-20);
//        viewDataBinding.guideView.setArrowDownCenterMoveX(100);
//        viewDataBinding.guideView.setTipViewMoveX(icon,-100);
//        viewDataBinding.guideView.setTipViewMoveY(icon,100);
        viewDataBinding.guideView.setHighLightView(targets);
    }

    Fragment fromFragment ;
    //切换页面
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.container, to).commit();
                }

            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commit();
                }

            }
        }
    }
    @Override
    protected void onRetryBtnClick() {

    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) viewDataBinding.bottomView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth + 50, false).setBadgeNumber(showNumber);
        }
    }


    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                // item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }
}
