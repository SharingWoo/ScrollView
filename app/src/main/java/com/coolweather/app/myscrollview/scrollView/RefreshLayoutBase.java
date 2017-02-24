package com.coolweather.app.myscrollview.scrollView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.coolweather.app.myscrollview.R;

/**
 * RefreshLayoutBase是下拉刷新、上拉加载更多的抽象类。该组件含有Header、Content
 * View、Footer三部分，并且从上到下布局。Header、Footer一般大小固定，Content
 * View则为与屏幕一样大。在初始化时通过Scroller将Header滚出屏幕的可见区域
 * ,当下拉时将Header慢慢移入用户的视野,用户抬起手指时判断Header的可见高度
 * ，如果可见的高度大于Header总高度的一半，那么则触发下拉刷新操作。加载更多则是当Content
 * View到了底部，用户还继续上拉，那么触发加载更多操作。
 *
 * @param <T> Content View的类型,可以为任何View的子类,例如ListView、GridView等
 * @author sharingwoo
 */

public abstract class RefreshLayoutBase<T extends View> extends ViewGroup implements OnScrollListener {
    private static final String TAG = "REFRESH_LAYOUT_BASE";
    //界面处在的状态：下拉中，上拉中，空闲中，刷新中等；
    private static final int STATUS_PULL_TO_REFRESH = 1;//下拉：未达到刷新要求
    private static final int STATUS_RELEASE_TO_REFRESH = 2;//下拉：已达到刷新要求
    private static final int STATUS_REFRESHING = 3;//刷新中
    private static final int STATUS_LOADING = 4;//加载中
    private static final int STATUS_IDLE = 0;//空闲中
    private int mCurrentStatus = STATUS_IDLE;//当前状态
    private int mCurrentY = 0;
    private T mContentView;

    public RefreshLayoutBase(Context context) {
        super(context);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int height = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            height += childView.getMeasuredHeight();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    //初始化界面
    private void initView(Context context) {
        // header view
        setUpHeaderView(context);
        // content view布局
        setUpContentView(context);
        //设置content View参数
        setContentViewParams();
        //添加content view
        addView(mContentView);
        //boot view
        setUpBooterView(context);
    }

    //设置标题界面
    private void setUpHeaderView(Context context) {
        //生成header view 界面
        View headerView = LayoutInflater.from(context).inflate(R.layout.layout_header_view, this, false);
        //设置header view 的界面参数
        headerView.setBackgroundColor(Color.BLUE);
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(headerView);
    }

    //设置底部界面
    private void setUpBooterView(Context context) {
        View bootView = LayoutInflater.from(context).inflate(R.layout.layout_booter_view, this, false);
        bootView.setBackgroundColor(Color.CYAN);
        bootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(bootView);
    }

    //设置
    private void setContentViewParams() {
        mContentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public abstract void setUpContentView(Context context);
}
