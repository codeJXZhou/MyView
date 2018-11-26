package cn.zjx.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zjx on 2018/7/4.
 */
public class BezierView extends View {
    private Paint paint1, paint2;
    private Path path;

    private int viewWidth, viewHeight;
    private float commandX, commandY;
    private float waterHeight;
    private boolean isInc;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.parseColor("#3ed486"));
        path = new Path();
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.RED);
        paint2.setStrokeWidth(5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        commandY = 7 / 8f * viewHeight;
        waterHeight = 15 / 16f * viewHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(-1 / 4f * viewWidth, waterHeight);
        path.quadTo(commandX, commandY, viewWidth + 1 / 4f * viewWidth, waterHeight);
        path.lineTo(viewWidth + 1 / 4f * viewWidth, viewHeight);
        path.lineTo(-1 / 4f * viewWidth, viewHeight);
        path.close();
        canvas.drawPath(path, paint1);
        canvas.drawLine(0, waterHeight, viewWidth, waterHeight, paint2);
        if (commandX >= viewWidth + 1 / 4f * viewWidth) {
            isInc = false;
        } else if (commandX <= -1 / 4f * viewWidth) {
            isInc = true;
        }
        commandX = isInc ? commandX + 20 : commandX - 20;
        if (commandY >= 1 / 8f * viewHeight) {
            commandY -= 2;
            waterHeight -= 2;
        }
        path.reset();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpec == MeasureSpec.AT_MOST && heightSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (widthSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, heightSize);
        } else if (heightSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 300);
        }
    }
}
