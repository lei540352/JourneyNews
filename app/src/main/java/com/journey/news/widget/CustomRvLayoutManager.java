package com.journey.news.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.journey.news.utils.RvAnimationConst;

/**
 * RecyclerView的子View排布，重写LayoutManager
 */
public class CustomRvLayoutManager extends RecyclerView.LayoutManager {
    private Context mContext;

    public CustomRvLayoutManager(Context context){
        this.mContext = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 自定义layoutManager 重布局子view的方法
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //第一步 让所有子view参与回收复用机制
        detachAndScrapAttachedViews(recycler);
        //第二步，制定子view的排版规则
        final int itemCount = getItemCount();//所有要显示的子view数量 ItemCount ，adapter中的getItemCount
        //对比 getChildCount 它是当前附加在RV上可见的子view个数，在布局之前，rv上的子view个数一定是 0 ，所以这里不能使用getChildCount
        int currentLevel;//层级，影响子view的缩放倍率和Y偏移量
        // 以4个为标准显示
        int stateIndex = itemCount - RvAnimationConst.maxShownChildCount;
        Log.i("Children","stateIndex========"+stateIndex);
        //即从倒数第四个显示到最后一张 0,1,2,3,4,5,6,7,8,9  首先显示 6 7 8 9
        for (int i= stateIndex; i < itemCount; i++){

            View child = recycler.getViewForPosition(i);//从recycle中获取子view ，因为子view已经被Recycler统筹管理
            addView(child);//加入到LayoutManager

            //对子view进行测量 带margin
            measureChildWithMargins(child,0,0);//既然要重新布局  那么一点要在layout之前先measure
            //拿到子view的宽高  Recycler自带了ItemDecoration (item之间的分隔符)，计算宽高的时候使用getDecoratedMeasuredXXXX
            int childWidth = getDecoratedMeasuredWidth(child);
            int childHeight = getDecoratedMeasuredHeight(child);
            //让所有子view横向居中，要事先计算出子view左右两侧应该留出的间隙大小
            // (屏幕总宽度 - 子view宽度)/2 距两边等距
            int widthSpace = (getWidth() - childWidth) / 2;
            // (屏幕总高度 - 子view高度)/2 距上下等距
            int heightSpace = (getHeight() - childHeight) / 2;

            Log.i("Children","widthSpace========"+widthSpace);
            Log.i("Children","childHeight========"+childHeight);
            Log.i("Children","getHeight()========"+getHeight());

            //现在对子view进行layout 设置他的位置 l t r b 坐标
            layoutDecoratedWithMargins(child,
                    widthSpace,
                    heightSpace,
                    widthSpace + childWidth,
                    heightSpace + childHeight);

            //然后让子view进行适当缩小 下面三张缩放
            //计算Y偏移值， 越往上 图越大，所以最底下的层级最低
            //每个level的缩放倍率和y平移距离
            currentLevel = itemCount - i -1;
            if(currentLevel > 0){
                //如果是最后一个item，让它和倒数第二个重叠（缩放倍率和Y平移量一样）
                if(currentLevel >= RvAnimationConst.maxShownChildCount - 1){
                    currentLevel-- ;
                }

                float currentScaleOffset = RvAnimationConst.scaleOffset * currentLevel;
                //item 宽高各缩放相同的倍数
                child.setScaleX(1 - currentScaleOffset);
                child.setScaleY(1 - currentScaleOffset);
                //将下面的item 向下平移
                child.setTranslationY(RvAnimationConst.baseYTransOffset * currentLevel);
            }
        }
    }
}
