package cn.zjx.myview;

import android.content.Context;


/**
 * @author Administrator
 */
public class UiUtils {
    /**
     * @param context
     * @param dpValue 传入的dip值
     * @return int 转化后的px值
     * @throws
     * @Title: dip2px
     * @Description: 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

}
