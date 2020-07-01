package com.journey.news;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.journey.news.adapter.CustomViewHolder;
import com.journey.news.adapter.RecyclerViewUniversalAdapter;
import com.journey.news.bean.DataBean;
import com.journey.news.utils.RvAnimationConst;
import com.journey.news.widget.CustomItemTouchCallback;
import com.journey.news.widget.CustomRvLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */

/**
 * 利用给SplashActivity设置带有背景的SplashTheme来避免启动白屏的问题
 */
public class TantanActivity extends AppCompatActivity {

    private RecyclerViewUniversalAdapter adapter;//万能适配器
    private TextView tvRes;
    private RecyclerView rv;
    private List<DataBean> dataBeans;
    private CustomItemTouchCallback customItemTouchCallback;
    private ItemTouchHelper touchHelper;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantan);

        tvRes = findViewById(R.id.tv_res);
        rv = findViewById(R.id.rv);
        RvAnimationConst.init(this);//初始化相关参数，自定义

        initData();//Rv的数据
        initAdapter();//Rv的适配器

        initItemTouchCallback();//Rv的item触摸反馈事件
        initLayoutManager();//Rv的布局管理器

        // 4个准备工作都做好了
        initRv();//rv初始化
    }

    public void initData(){
        dataBeans = new ArrayList<>();
        DataBean dataBean ;
        for (int i = 0; i < DataManager.dataCount ;i++){
            dataBean = new DataBean();
            dataBean.index = i;
            dataBean.name = DataManager.getGirlName(i);
            dataBeans.add(dataBean);
        }
    }

    /**
     * 使用万能的RecycleViewAdapter 适配器，来节省代码
     */
    public void initAdapter(){
        adapter = new RecyclerViewUniversalAdapter<DataBean>(this,dataBeans,R.layout.item_data) {

            @Override
            public void convert(CustomViewHolder viewHolder, DataBean data) {
                viewHolder.setText(R.id.tv_name,data.name);
                viewHolder.setText(R.id.tv_id,data.index+"");
                viewHolder.setImageDrawable(R.id.iv_girl,getResources().getDrawable(DataManager.getGirlImg(data.index)));
                CardView cv = viewHolder.itemView.findViewById(R.id.cv_view);
                cv.setCardBackgroundColor(getResources().getColor(DataManager.getBgColorByIndex(data.index)));
            }
        };
    }

    public void initItemTouchCallback(){
        //子view的触摸反馈事件，都在CustomItemTouchCallback中
        customItemTouchCallback = new CustomItemTouchCallback(rv,adapter,dataBeans);
        customItemTouchCallback.setCallback(new CustomItemTouchCallback.LikeOrDislikeCallback() {
            @Override
            public void call(boolean ifLike, String who) {
                tvRes.setText(ifLike ? "喜欢  " +who  : "不喜欢  " +who);
            }
        });
        touchHelper = new ItemTouchHelper(customItemTouchCallback);
    }

    /**
     * 设置recycleview排版 重写LayoutManager
     */
    public void initLayoutManager(){
        layoutManager = new CustomRvLayoutManager(this);
    }

    //填充数据到RecycleView
    public void initRv(){
        if (layoutManager == null) throw new RuntimeException("ERROR：layoutManager is null!");
        if (adapter == null) throw new RuntimeException("ERROR：adapter is null!");
        if (touchHelper == null) throw new RuntimeException("ERROR：touchHelper is null!");
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        touchHelper.attachToRecyclerView(rv);// 给rv加上滑动事件
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * 学学RecyclerView的写法，内部类实现
     */
    private static class DataManager{
        private static final int dataCount = 10;

        /**
         * 背景颜色
         * @param index
         * @return
         */
        private static int getBgColorByIndex(int index){
            int a = index % 3;
            switch (a) {
                case 0:
                    return R.color.color_0;
                case 1:
                    return R.color.color_1;
                case 2:
                    return R.color.color_2;
                default:
                    return R.color.color_default;
            }
        }
        //图片
        private static int getGirlImg(int index) {
            int s = index;
            switch (s) {
                case 0:
                    return R.mipmap.girl1;
                case 1:
                    return R.mipmap.girl2;
                case 2:
                    return R.mipmap.girl3;
                case 3:
                    return R.mipmap.girl4;
                case 4:
                    return R.mipmap.girl5;
                case 5:
                    return R.mipmap.girl6;
                case 6:
                    return R.mipmap.girl7;
                case 7:
                    return R.mipmap.girl8;
                case 8:
                    return R.mipmap.girl9;
                case 9:
                    return R.mipmap.girl10;
                default:
                    return R.mipmap.girl1;
            }
        }

        //名称
        private static String getGirlName(int index) {
            int s = index;
            switch (s) {
                case 0:
                    return "江袭月";
                case 1:
                    return "苗蕊";
                case 2:
                    return "雪未央";
                case 3:
                    return "玉芙";
                case 4:
                    return "苏妙弋";
                case 5:
                    return "花溪";
                case 6:
                    return "顾红妆";
                case 7:
                    return "月采屏";
                case 8:
                    return "夏艺容";
                case 9:
                    return "玉流霞";
                default:
                    return "柳诗妍";
            }
        }

    }

}
