package cn.zjx.myview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author zjx on 2018/7/4.
 */
public class PaperFlyView extends View implements View.OnClickListener {

    private Bitmap flyBitmap;
    private float flyX, flyY;
    private float commandPointX, commandPointY; //控制点坐标
    private float startPointX, startPointY; //动画起始位置
    private float endPointX, endPointY;//动画结束位置
    private ValueAnimator anim;
    public PaperFlyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        Matrix m = new Matrix();
        m.setScale(0.25f, 0.25f);
        flyBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
        bitmap.recycle();
        //控制点 坐标
        commandPointX = 1080;
        commandPointY = 1080;
        //设置点击监听
        setOnClickListener(this);
    }

    /**
     * 拿到控件的宽和高后 根据宽高设置绘制位置，动画开始，结束位置
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flyX = 2 * flyBitmap.getWidth();
        flyY = h - 3 * flyBitmap.getHeight();
        //动画开始位置
        startPointX = flyX;
        startPointY = flyY;
        //动画结束位置
        endPointX = w / 2 - flyBitmap.getWidth();
        endPointY = 3 * flyBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(flyBitmap, flyX, flyY, null);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        //估值器
        BezierEvaluator bezierEvaluator = new BezierEvaluator(new PointF(commandPointX, commandPointY));
        //设置属性动画
        PointF startPointF = new PointF(startPointX, startPointY);
        PointF endPointF = new PointF(endPointX, endPointY);
        anim = ValueAnimator.ofObject(bezierEvaluator, startPointF, endPointF);
        anim.setDuration(1000);
        //在动画过程中，更新绘制的位置 位置的轨迹就是贝塞尔曲线
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                flyX = point.x;
                flyY = point.y;
                invalidate();
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());//加速减速插值器
        anim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != anim && anim.isRunning()) {
            anim.cancel();
        }
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }


    public class BezierEvaluator implements TypeEvaluator<PointF> {
        private PointF mPointF;

        public BezierEvaluator(PointF mPointF) {
            this.mPointF = mPointF;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return calculateBezierPointForQuadratic(fraction, startValue, mPointF, endValue);
        }

        /**
         * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1] * *
         *
         * @param t  曲线长度比例 *
         * @param p0 起始点 *
         * @param p1 控制点 *
         * @param p2 终止点 *
         * @return t对应的点
         */
        private PointF calculateBezierPointForQuadratic(float t, PointF p0, PointF p1, PointF p2) {
            PointF point = new PointF();
            float temp = 1 - t;
            point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
            point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
            return point;
        }
    }

}
