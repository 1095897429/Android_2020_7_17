package com.liangzai.myjavaapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/8/4
 * 描述:
 */
public class MyNestedScrollView extends NestedScrollView {
    private String TAG = MyNestedScrollView.class.getSimpleName();
    final int MAX_SCROLL_LENGTH = 400;
    /**
     * 该控件滑动的高度，高于这个高度后交给子滑动控件
     */
    int mParentScrollHeight ;
    int mScrollY ;
    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollHeight(int scrollLength) {
        this.mParentScrollHeight = scrollLength;
    }


    //子控件告诉父控件 开始滑动了
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        if (mScrollY   < mParentScrollHeight) {
            consumed[0] = dx;
            consumed[1] = dy;
            scrollBy(0, dy);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
        if (scrollViewListener != null) {
            scrollViewListener.onScroll(mScrollY);
        }
    }


    private ScrollViewListener scrollViewListener = null;
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    public interface ScrollViewListener {

        void onScroll(int scrollY);

    }



    private boolean isNeedScroll = true;


    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }



    /**
     *
     *阻尼：1000为将惯性滚动速度缩小1000倍，近似drag操作。
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }
}
