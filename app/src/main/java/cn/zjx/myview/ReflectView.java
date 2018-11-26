package cn.zjx.myview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zjx on 2018/7/4.
 */
public class ReflectView extends View {
    private Paint paint;
    private Bitmap dstBitmap, srcBitmap;
    private PorterDuffXfermode xfermode;
    private int x, y;

    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        dstBitmap= decodeBitmapFormRes(getResources(),R.drawable.icon,100,200);
        Matrix matrix=new Matrix();
        matrix.setScale(1f,-1f);
        srcBitmap=Bitmap.createBitmap(dstBitmap,0,0,dstBitmap.getWidth(),dstBitmap.getHeight(),matrix,true);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xfermode= new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        int w=getResources().getDisplayMetrics().widthPixels;
        x= w/2-dstBitmap.getWidth()/2;
        y=0;
        paint.setShader(new LinearGradient(x,dstBitmap.getHeight(),x,dstBitmap.getHeight()*3/2,0xDD000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(dstBitmap,x,y,null);
        canvas.drawBitmap(srcBitmap,x,dstBitmap.getHeight(),null);
        paint.setXfermode(xfermode);
        canvas.drawRect(x,dstBitmap.getHeight(),x+dstBitmap.getWidth(),dstBitmap.getHeight()*2,paint);
        paint.setXfermode(null);
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
    /**
     * 图片的缩放
     */
    private Bitmap decodeBitmapFormRes(Resources resources, int resId, int targetWidth, int targetHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeResource(resources, resId, options);
        int inSample = calculateInSample(options, targetWidth, targetHeight);
        options.inSampleSize = inSample;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private int calculateInSample(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        if (targetWidth <= 0 || targetHeight <= 0) {
            return 1;
        }
        int inSample = 1;
        final int rawWidth = options.outWidth;
        final int rawHeight = options.outHeight;
        if (rawWidth > targetWidth || rawHeight > targetHeight) {
            final int halfWidth = rawWidth / 2;
            final int halfHeight = rawHeight / 2;
            while ((halfWidth / inSample >= targetWidth) && (halfHeight / inSample >= targetHeight)) {
                inSample *= 2;
            }
        }
        return inSample;
    }


}
