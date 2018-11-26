package cn.zjx.myview.floatview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @author zjx on 2018/9/11.
 */
public abstract class AbstractDragFloatActionButton extends RelativeLayout {
    /**
     * 悬浮的父布局高度
     */
    private int parentHeight;
    private int parentWidth;
    private long mLastTime;
    private long mCurrentTime;

    public AbstractDragFloatActionButton(Context context) {
        this(context, null, 0);
    }

    public AbstractDragFloatActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public abstract int getLayoutId();

    public abstract void renderView(View view);

    public AbstractDragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(getLayoutId(), this);
        renderView(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 获取屏幕的宽度
     */
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        View view = getChildAt(0);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private int lastX;
    private int lastY;

    private boolean isDrag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //默认是点击事件
                setPressed(true);
                //父布局不要拦截子布局的监听
                isDrag = false;
                //父布局不要拦截子布局的监听
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                mLastTime = System.currentTimeMillis();
                ViewGroup parent;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentTime = System.currentTimeMillis();
                //只有父布局存在你才可以拖动
                isDrag = (parentHeight > 0 && parentWidth > 0);
                if (!isDrag) {
                    break;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (mCurrentTime - mLastTime < 200) {
                    //只有位移大于1说明拖动了
                    isDrag = distance > 1;
                    if (!isDrag) {
                        break;
                    }
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = y < 0 ? 0 : y > parentHeight - getHeight() ? parentHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //如果是拖动状态下即非点击按压事件
                setPressed(!isDrag);
                int center = (parentWidth - getWidth()) / 2;
                int destX = 0;
                //抬起时 根据位置强制把浮动按钮归于左边或右边
                if (rawX < center) { // 左边
                    destX = 50;
                } else {
                    destX = parentWidth - getWidth()-50;
                }
                setX(destX);
                break;
            default:
                break;
        }
        //如果不是拖拽，那么就不消费这个事件，以免影响点击事件的处理
        //拖拽事件要自己消费
        return isDrag || super.onTouchEvent(event);
    }

}