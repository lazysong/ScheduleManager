package com.lazysong.schedulemanagement; 

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
 
public class SlidingMenu extends ViewGroup {
 
    private static final String TAG = SlidingMenu.class.getName();
 
    private enum Scroll_State {
        Scroll_to_Open, Scroll_to_Close;
    }
 
    private Scroll_State state;
    private int mMostRecentX;
    private int downX;
    private boolean isOpen = false;
 
    private View menu;
    private View mainView;
 
    private Scroller mScroller;
 
    private OnSlidingMenuListener onSlidingMenuListener;
 
    public SlidingMenu(Context context, View main, View menu) {
        super(context);
        // TODO Auto-generated constructor stub
        setMainView(main);
        setMenu(menu);
        init(context);
    }
 
    private void init(Context context) {
        mScroller = new Scroller(context);
    }
 
    @Override
    protected void onLayout(boolean arg0, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        mainView.layout(l, t - 25, r, b);
        menu.layout(-menu.getMeasuredWidth(), t - 25, 0, b);
//    	mainView.layout(l, t, r, b);
//        menu.layout(-menu.getMeasuredWidth(), t, 0, b);
    }
 
    public void setMainView(View view) {
        mainView = view;
        addView(mainView);
    }
 
    public void setMenu(View view) {
        menu = view;
        addView(menu);
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        menu.measure((int)(widthMeasureSpec * 0.7), heightMeasureSpec);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mMostRecentX = (int) event.getX();
            downX = (int) event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            int moveX = (int) event.getX();
            int deltaX = mMostRecentX - moveX;
            // 如果在菜单打开时向右滑动及菜单关闭时向左滑动不会触发Scroll事件
            if ((!isOpen && (downX - moveX) < 0)
                    || (isOpen && (downX - moveX) > 0)) {
                scrollBy(deltaX / 2, 0);
            }
            mMostRecentX = moveX;
            break;
        case MotionEvent.ACTION_UP:
            int upX = (int) event.getX();
            int dx = upX - downX;
            if (!isOpen) {// 菜单关闭时
                // 向右滑动超过menu一半宽度才会打开菜单
                if (dx > menu.getMeasuredWidth() / 10) {
                    state = Scroll_State.Scroll_to_Open;
                } else {
                    state = Scroll_State.Scroll_to_Close;
                }
            } else {// 菜单打开时
                // 当按下时的触摸点在menu区域时，只有向左滑动超过menu的一半，才会关闭
                // 当按下时的触摸点在main区域时，会立即关闭
                if (downX < menu.getMeasuredWidth()) {
                    if (dx < -menu.getMeasuredWidth() / 10) {
                        state = Scroll_State.Scroll_to_Close;
                    } else {
                        state = Scroll_State.Scroll_to_Open;
                    }
                } else {
                    state = Scroll_State.Scroll_to_Close;
                }
            }
            smoothScrollto();
            break;
        default:
            break;
        }
        return true;
    }
 
    private void smoothScrollto() {
        int scrollx = getScrollX();
        switch (state) {
        case Scroll_to_Close:
            mScroller.startScroll(scrollx, 0, -scrollx, 0, 500);
            if (onSlidingMenuListener != null && isOpen) {
                onSlidingMenuListener.close();
            }
            isOpen = false;
            break;
        case Scroll_to_Open:
            mScroller.startScroll(scrollx, 0,
                    -scrollx - menu.getMeasuredWidth(), 0, 500);
            if (onSlidingMenuListener != null && !isOpen) {
                onSlidingMenuListener.close();
            }
            isOpen = true;
            break;
        default:
            break;
        }
    }
 
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
        }
        invalidate();
    }
 
    public void open() {
        state = Scroll_State.Scroll_to_Open;
        smoothScrollto();
    }
 
    public void close() {
        state = Scroll_State.Scroll_to_Close;
        smoothScrollto();
    }
 
    public boolean isOpen() {
        return isOpen;
    }
 
    public void setOnSlidingMenuListener(
            OnSlidingMenuListener onSlidingMenuListener) {
        this.onSlidingMenuListener = onSlidingMenuListener;
    }
 
    public interface OnSlidingMenuListener {
        public void open();
 
        public void close();
    }
 
}