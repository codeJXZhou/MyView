package cn.zjx.myview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import cn.zjx.myview.R;

/**
 * @author zjx on 2018/11/23.
 */
public class MyBehavior extends CoordinatorLayout.Behavior<Button> {

    private Context mContext;

    private int width;
    private int height;


    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //如果dependency 是 DodoMoveView类型， 就依赖
        return dependency.getId() == R.id.tv ;//instanceof DodoMoveView;
    }

    //每次dependency位置发生变化，都会执行onDependentViewChanged方法
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button btn, View dependency) {
        //根据dependency的位置，设置Button的位置
/*        int x = (int) dependency.getX();
        int y = 0;
        setPosition(btn, x, y);
        return true;*/
        int top = dependency.getTop();
        int left = dependency.getLeft();
        int x = width - left - btn.getWidth();
        int y = height - top - btn.getHeight();
        setPosition(btn, x, y);
        return true;

    }

/*    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams(layoutParams);
    }*/

    private void setPosition(View v, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        layoutParams.width = y / 2;
        v.setLayoutParams(layoutParams);
    }

}

