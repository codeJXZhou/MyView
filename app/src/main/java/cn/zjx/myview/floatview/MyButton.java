package cn.zjx.myview.floatview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.zjx.myview.R;

/**
 * @author zjx on 2018/9/11.
 */
public class MyButton extends AbstractDragFloatActionButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_app_widget;
    }

    @Override
    public void renderView(View view) {

    }
}
