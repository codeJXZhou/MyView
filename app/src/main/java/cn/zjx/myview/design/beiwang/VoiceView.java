package cn.zjx.myview.design.beiwang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zjx on 2018/7/31.
 */
public class VoiceView extends View {
    private Paint paint;
    private int mCount;
    private int mWidth;
    private int mRectHeight;
    private int mRectWidth;
    private int offset;
    private LinearGradient linearGradient;
    public VoiceView(Context context) {
        super(context);
        init();
    }

    public VoiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#3ae3b8"));
        mCount=10;
        offset=10;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mRectHeight=getHeight();
        mRectWidth= (int) (mWidth*0.6/mCount);
        linearGradient=new LinearGradient(0,0,mRectWidth,mRectHeight,Color.YELLOW,Color.BLUE, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (int i = 0; i < mCount; i++) {
            float currentHeight= (float) (mRectHeight* Math.random());
            canvas.drawRect((float)(mWidth*.4/2+mRectWidth*i+offset),currentHeight,(float)(mWidth*0.4/2+mRectWidth*(i+1)),mRectHeight,paint);
        }
        postInvalidateDelayed(300);
    }
}
