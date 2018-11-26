package cn.zjx.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zjx on 2018/7/4.
 */
public class PorterDuffView extends View {
    private Paint paint;
    private RectF rectF;
    private PorterDuffXfermode porterDuffXfermode;

    public PorterDuffView(Context context) {
        super(context);
        initPaint();
    }

    public PorterDuffView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PorterDuffView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        BitmapShader shader=new BitmapShader(bitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.MIRROR);
        LinearGradient gradient=new LinearGradient(0,0,500,500,Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
//        rectF = new RectF(150, 150, 500, 500);
//        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.YELLOW);
//        int layerId=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
//        float width=getWidth()/4;
//        float height=getHeight()/4;
//        float radius=Math.min(getWidth(),getHeight())/4;
//        paint.setColor(Color.CYAN);
//        canvas.drawCircle(width,height,radius,paint);
//        paint.setColor(Color.RED);
//        paint.setXfermode(porterDuffXfermode);
//        canvas.drawRect(rectF,paint);
//        paint.setXfermode(null);
//        canvas.restoreToCount(layerId);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        } else if (wSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, hSpecSize);
//        } else if (hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(wSpecSize, 300);
//        }
//    }
}
