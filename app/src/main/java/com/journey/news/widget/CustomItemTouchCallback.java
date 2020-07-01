package com.journey.news.widget;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.journey.news.R;
import com.journey.news.adapter.CustomViewHolder;
import com.journey.news.bean.DataBean;
import com.journey.news.utils.RvAnimationConst;

import java.util.List;

/**
 * 统筹管理item的各种个点击拖拽效果
 */
public class CustomItemTouchCallback extends ItemTouchHelper.SimpleCallback {

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    List<DataBean> dataBeans;
    public CustomItemTouchCallback(RecyclerView rv, RecyclerView.Adapter<?> adapter, List<DataBean> dataBeans){
        super(0,15);//二进制1111(表示上下左右拖动都支持)，换算成十进制，就是15
        this.rv = rv;
        this.adapter = adapter;
        this.dataBeans = dataBeans;
    }

    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to the new position.
     * 当itemTouchHelper 要将item从旧位置移动到新位置时，此方法会执行
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return True if the {@code viewHolder} has been moved to the adapter position of
     *  {@code target}. 如果viewHolder被从adapter的目标位置移除时，返回true
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //由于我们这里不需要移动位置，只需要让它从界面上移出去就可以，false
        return false;
    }

    /**
     * called when a  CustomViewHolder is swiped by the user.
     * 滑动逻辑核心item滑出去之后执行
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        DataBean dataBean = dataBeans.remove(viewHolder.getLayoutPosition());
        dataBeans.add(0,dataBean);
        adapter.notifyDataSetChanged();
    }

    /**
     * Called by ItemTouchHelper on RecyclerView's onDraw callback.
     * 在RecycleView的onDraw回调这个方法将被itemTouchHelper执行
     * ？那recycleview的onDraw啥时候调用？
     *  应该是在调用 invalidate（）的时候，onDraw会执行，也就是说由外界触发了
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        //重写子view的draw方法，拖动时改变子view的位置
        float maxDistanceX = recyclerView.getWidth() * 0.5f;//左右各占一半距离
        float maxDistanceY = recyclerView.getHeight() * 0.5f;//左右各占一半距离
        float maxDistance = (float) Math.sqrt(maxDistanceX * maxDistanceX + maxDistanceY * maxDistanceY);//计算对角线最大宽度

        float distance = (float)Math.sqrt(dX*dX + dY* dY);//dx和dy是横竖方向移动距离，算出直接移动的距离，使用勾股定理，再开方。
        float fraction = distance / maxDistance;//当前移动比例
        //防止意外
        if(fraction > 1){
            fraction = 1;
        }

        //recyclerView器里面的当前子view个数
        int count = recyclerView.getChildCount();
        //滑动的时候，会触发invalidate，从而触发onChildDraw 引起子view的一些实时变化
        if(callback != null){
            //获取最上层的子view
            View child = recyclerView.getLayoutManager().getChildAt(RvAnimationConst.maxShownChildCount - 1);
            CustomViewHolder vh = (CustomViewHolder) recyclerView.getChildViewHolder(child);
            TextView textView = vh.getView(R.id.tv_name);
            String name = (String)textView.getText();
            //向左负数， 喜欢  向右不喜欢
            callback.call(dX < 0,name);
        }

        //根据层级不同来进行不同程度的缩放
        for (int i = 0;i < count ;i++){
            View child = recyclerView.getChildAt(i);

            int currentLevel = count - i - 1; //i 越小,越在最上层
            if(currentLevel > 0){
                //让子view进行缩放和平移。之前布局的时候平移过了，现在拖动最上层item移动，
                //相应的底下三层的item也发生一些变化，让整个ui看起来自然一些
                if(currentLevel  < count -1){//当前拖动的整个子view不动，你已经在被拖，还是别动了
                    //布局的时候调整x y 缩放和y轴位移。现在要放大 ，Y轴向上移动
                    child.setTranslationY(RvAnimationConst.baseYTransOffset * (currentLevel - fraction));
                    child.setScaleX( 1- RvAnimationConst.scaleOffset * (currentLevel - fraction));
                    child.setScaleY( 1- RvAnimationConst.scaleOffset * (currentLevel - fraction));
                    Log.d("currentLevel" + currentLevel, "" + (1 - RvAnimationConst.scaleOffset * (currentLevel - fraction)));
                }
            }
        }
    }

   @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }

    public interface  LikeOrDislikeCallback{
        //添加一个回调，将向左向右的事件传递出去
        void call(boolean ifLike,String who);
    }
    LikeOrDislikeCallback callback;

    public void setCallback(LikeOrDislikeCallback callback) {
        this.callback = callback;
    }
}
