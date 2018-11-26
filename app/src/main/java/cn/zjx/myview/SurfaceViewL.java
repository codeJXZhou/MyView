package cn.zjx.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author zjx on 2018/7/4.
 */
public class SurfaceViewL extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Canvas canvas;
    private boolean isDrawing;

    private Paint paint;
    private Path path;
    private float lastX, lasty;

    public SurfaceViewL(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            drawing();
        }
    }

    private void drawing() {
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                canvas.drawPath(path, paint);
            }

        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lasty = y;
                path.moveTo(lastX, lasty);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - lastX);
                float dy = Math.abs(y - lasty);
                if (dx >= 3 || dy >= 3) {
                    path.quadTo(lastX, lasty, (lastX + x) / 2, (lasty + y) / 2);
                }
                lastX = x;
                lasty = y;
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return true;
    }

}
