package cn.zjx.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * @author zjx on 2018/7/31.
 */
public class ScrollViewView extends ViewGroup {
    private int screenHeight;

    public ScrollViewView(Context context) {
        super(context);
        init(context);
    }

    public ScrollViewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollViewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        screenHeight = getScreenHeight();
        scroller = new Scroller(context);
    }

    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = screenHeight * count;
        setLayoutParams(mlp);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(l, i * screenHeight, r, (i + 1) * screenHeight);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec, getScreenHeight()*count);
    }


    private int mLastY;
    private int mStart;
    private int mEnd;
    private Scroller scroller;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {
                    dy = 0;
                }
                if (getScrollY() > getHeight() - screenHeight) {
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < screenHeight / 3) {
                        scroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, screenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < screenHeight / 3) {
                        scroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, -screenHeight - dScrollY);
                    }
                }
                break;
            default:
                break;

        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

}
