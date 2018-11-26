package cn.zjx.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Clock extends View {
    private static final String TAG = "Clock";
    /**
     * 表盘画笔
     */
    private Paint paintCircle;
    /**
     * 刻度画笔
     */
    private Paint paintDegree;
    /**
     * 圆心画笔
     */
    private Paint paintPointer;
    /**
     * 时针画笔
     */
    private Paint paintHour;
    /**
     * 分针画笔
     */
    private Paint paintMinute;

    public Clock(Context context) {
        super(context);
        initView();
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 画外圆
        paintCircle = new Paint();
        // 画刻度
        paintDegree = new Paint();
        // 画圆心
        paintPointer = new Paint();
        // 画时针
        paintHour = new Paint();
        // 画分针
        paintMinute = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mHeight, mWidth;
        // 获取可用空间的宽高参数
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d(TAG, "mWidth == " + mWidth + ", mHeight == " + mHeight);
        // 设置表盘画笔
        // 空心
        paintCircle.setStyle(Paint.Style.STROKE);
        // 抗锯齿
        paintCircle.setAntiAlias(true);
        // 边框宽度 5
        paintCircle.setStrokeWidth(5);
        // 圆心坐标为屏幕中心，半径为屏幕宽度的一半
        canvas.drawCircle(mWidth / 2,
                mHeight / 2, mWidth / 2, paintCircle);
        // 边框宽度 3
        paintCircle.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {
            // 区分整点与非整点
            if (i % 5 == 0) {
                // 整点 0,6,12,18
                // 设置刻度画笔
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                // startX = 屏幕X轴中点, startY = (屏幕Y轴中点 - 屏幕X轴中点), 坐标是圆环的上顶点
                // endX = 屏幕X轴中点(不变), endY = (屏幕Y轴中点 - 屏幕X轴中点) + 60
                // 整点刻度的高度为 60
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);

                // 时间点数
                String degree = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                // 绘制文本

                    drawNum(canvas, i * 30, degree, paintDegree);

//                canvas.drawText(degree,
//                        mWidth / 2 - paintDegree.measureText(degree) / 2,
//                        mHeight / 2 - mWidth / 2 + 90,
//                        paintDegree);
            } else {
                // 非整点
                // 设置刻度画笔
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(15);
                // startX = 屏幕X轴中点, startY = (屏幕Y轴中点 - 屏幕X轴中点), 坐标是圆环的上顶点
                // endX = 屏幕X轴中点(不变), endY = (屏幕Y轴中点 - 屏幕X轴中点) + 30
                // 整点刻度的高度为 30
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30,
                        paintDegree);
            }
            // 通过旋转画布简化坐标运算
            // 画布以圆心为原点，每次旋转15度
            canvas.rotate(6, mWidth / 2, mHeight / 2);
        }
        // 设置圆心画笔
        paintPointer.setStrokeWidth(30);
        // 圆心坐标为屏幕中心，半径为屏幕宽度的一半
        canvas.drawPoint(mWidth / 2, mHeight / 2, paintPointer);
        // 设置时针画笔
        paintHour.setStrokeWidth(20);
        // 设置分针画笔
        paintMinute.setStrokeWidth(10);
        // 保存画布
        canvas.save();
        // 将原点从屏幕的左上角(0,0)平移到圆心处
        canvas.translate(mWidth / 2, mHeight / 2);
        // 时针，长度100
        canvas.drawLine(0, 0, 100, 100, paintHour);
        // 分针，长度200
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        // 还原合并画布
        canvas.restore();
    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 50 - getWidth() / 3);
        //这里的50是坐标中心距离时钟最外边框的距离，当然你可以根据需要适当调节
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2, textBound.height() / 2, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3 - 50);
        canvas.rotate(-degree);
    }

}
