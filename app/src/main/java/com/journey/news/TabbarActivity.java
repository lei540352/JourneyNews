package com.journey.news;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.journey.base.statusbarcompat.StatusBarCompat;
import com.journey.base.utils.Logger;
import com.journey.base.utils.MeasureUtil;
import com.journey.base.widget.LottieTabView;
import com.journey.base.widget.UserGuideView;

import java.util.LinkedHashMap;

/**
 *  带Lottie动画的导航栏  类似demo
 * https://blog.csdn.net/yiranhaiziqi/article/details/88965548?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 * https://www.jianshu.com/p/01f6bb509d54
 */
public class TabbarActivity extends AppCompatActivity implements View.OnClickListener {

    private LottieTabView mLottieMainTab;
    private LottieTabView mLottieMsgTab;
    private LottieTabView mLottieDealTab;
    private LottieTabView mLottieMineTab;
    private LottieTabView tab_view_min1;
    private UserGuideView guideView;
    private Button changebg;
    private ImageView back;
    private ImageView img_bg;
    private int bgtype = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true, false);
        setContentView(R.layout.test_activity);

        mLottieMainTab = findViewById(R.id.tab_view_main);
        mLottieMsgTab = findViewById(R.id.tab_view_msg);
        mLottieDealTab = findViewById(R.id.tab_view_deal);
        mLottieMineTab = findViewById(R.id.tab_view_mine);
        tab_view_min1 = findViewById(R.id.tab_view_min1);
        changebg = findViewById(R.id.changebg);
        back = findViewById(R.id.back);
        img_bg = findViewById(R.id.img_bg);

        guideView = findViewById(R.id.guideView);
        guideView.setTouchOutsideDismiss(false);
        guideView.setStatusBarHeight(MeasureUtil.getStatuBarHeight(this));

        mLottieMainTab.setOnClickListener(this);
        mLottieMsgTab.setOnClickListener(this);
        mLottieDealTab.setOnClickListener(this);
        mLottieMineTab.setOnClickListener(this);
        tab_view_min1.setOnClickListener(this);
        changebg.setOnClickListener(this);
        back.setOnClickListener(this);

        LinkedHashMap<View, Integer> targets = new LinkedHashMap<>();
//        targets.put(top, R.mipmap.panda);
//        targets.put(topLeft, R.mipmap.tip3);
//        targets.put(topRight, R.mipmap.tip3);
        targets.put(mLottieMainTab, R.mipmap.tip2);
        targets.put(mLottieDealTab, R.mipmap.tip3);
        targets.put(tab_view_min1, R.mipmap.tip_view);
//        guideView.setHighLightView(top,icon,back);
        guideView.setArrowDownRight(R.mipmap.guide_arrow_right);
        guideView.setArrowDownCenter(R.mipmap.guide_arrow_left);
        guideView.setArrowDownLeft(R.mipmap.guide_arrow_left);
        guideView.setArrowUpLeftMoveX(-30);
        guideView.setArrowDownRightMoveX(0);
        guideView.setTipViewMoveX(mLottieMsgTab,-60);
//        guideView.setTipViewMoveX(bottomRight,140);
        guideView.setTipViewMoveX(mLottieMineTab,-50);
        guideView.setArrowDownLeftMoveX(-20);
//        guideView.setArrowDownCenterMoveX(100);
//        guideView.setTipViewMoveX(icon,-100);
//        guideView.setTipViewMoveY(icon,100);
        guideView.setHighLightView(targets);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_view_main:
                mLottieMainTab.selected();
                mLottieMsgTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMineTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_msg:

                mLottieMsgTab.selected();
                mLottieDealTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_deal:

                mLottieDealTab.selected();
                mLottieMsgTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_mine:

                mLottieMineTab.selected();
                mLottieMsgTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_min1:

                tab_view_min1.selected();

                mLottieMsgTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMainTab.unSelected();
                break;
            case R.id.changebg:
                if(bgtype == 1){
                    img_bg.setImageResource(R.mipmap.girl2);

                    img_bg.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(img_bg.getDrawingCache());

                    Logger.d("PPP=1="+StatusBarCompat.generateBitmapYAverage(bitmap));
                    if (StatusBarCompat.generateBitmapYAverage(bitmap) < 0) {
                        Logger.d("PPP", "它是浅色"); // It's a light color
                        back.setImageResource(R.mipmap.arrow_left);
                        StatusBarCompat.translucentStatusBar(this, true, true);
                    } else {
                        Logger.d("PPP", "它是深色"); // It's a dark color
                        back.setImageResource(R.mipmap.arrow_left_white);
                        StatusBarCompat.translucentStatusBar(this, true, false);
                    }
                    img_bg.setDrawingCacheEnabled(false);
                    bgtype++;
                }else if(bgtype == 2){
                    img_bg.setImageResource(R.mipmap.girl3);
                    bgtype++;
                    img_bg.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(img_bg.getDrawingCache());

                    Logger.d("PPP=2="+StatusBarCompat.generateBitmapYAverage(bitmap));
                    if (StatusBarCompat.generateBitmapYAverage(bitmap) < 0) {
                        Logger.d("PPP", "它是浅色"); // It's a light color
                        back.setImageResource(R.mipmap.arrow_left);
                        StatusBarCompat.translucentStatusBar(this, true, true);
                    } else {
                        Logger.d("PPP", "它是深色"); // It's a dark color
                        back.setImageResource(R.mipmap.arrow_left_white);
                        StatusBarCompat.translucentStatusBar(this, true, false);
                    }
                    img_bg.setDrawingCacheEnabled(false);
                }else {
                    img_bg.setImageResource(R.mipmap.girl4);
                    bgtype=1;
                    img_bg.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(img_bg.getDrawingCache());
                    Logger.d("PPP=3="+StatusBarCompat.generateBitmapYAverage(bitmap));
                    if (StatusBarCompat.generateBitmapYAverage(bitmap) < 0) {
                        Logger.d("PPP", "它是浅色"); // It's a light color
                        back.setImageResource(R.mipmap.arrow_left);
                        StatusBarCompat.translucentStatusBar(this, true, true);
                    } else {
                        Logger.d("PPP", "它是深色"); // It's a dark color
                        back.setImageResource(R.mipmap.arrow_left_white);
                        StatusBarCompat.translucentStatusBar(this, true, false);
                    }
                    img_bg.setDrawingCacheEnabled(false);
                }
                break;
            case R.id.back:
                finish();
                break;
                default:
                    break;
        }
    }
}
