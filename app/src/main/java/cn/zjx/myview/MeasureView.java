package cn.zjx.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zjx on 2018/7/3.
 */
public class MeasureView extends View {
    private Paint paint;


    public MeasureView(Context context) {
        super(context);
        initPaint();
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#FF4081"));
        paint.setTextSize(50f);
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.OVERLAY);
        paint.setColorFilter(filter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = getLeft();
        float right = getRight();
        float top = getTop();
        float bottom = getBottom();
//        canvas.drawRect(left,top,right,bottom,paint);
        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        RectF rect = new RectF(0f, 0f, 200f, 200f);
//        canvas.drawArc(rect,0,90,false,paint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        canvas.drawBitmap(bitmap, width/2, height/2, paint);
        String text="zsjyx";
        float stringWidth = paint.measureText(text);
        float x =(getWidth()-stringWidth)/2;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float y = getHeight() / 2 + getBaseline(paint);
//        canvas.drawText(text, x ,y ,paint);
        Path path=new Path();
        path.moveTo(50,0);
        path.lineTo(100,30);
        path.lineTo(50,100);
        path.lineTo(0,30);
//        canvas.drawPath(path,paint);
    }

    public static float getBaseline(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2 -fontMetrics.descent;
    }


    private int measureWidth(int widthMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
