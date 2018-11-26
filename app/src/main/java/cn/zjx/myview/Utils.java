package cn.zjx.myview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author zjx on 2018/9/4.
 */
public class Utils {
    private Activity activity;
    private volatile static Utils instance;

    private Utils(Activity activity) {
        this.activity = activity;
    }

    public static Utils getInstance(Activity activity) {
        Utils inst = instance;
        if (inst == null) {
            synchronized (Utils.class) {
                inst = instance;
                if (inst == null) {
                    inst = new Utils(activity);
                    instance = inst;
                }
            }
        }
        return inst;
    }


    public Bitmap compress(String path) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        int sampleSize = 1;
        int h = options.outHeight;
        int w = options.outWidth;
        sampleSize = (h > w) ? Math.round(h / w) : Math.round(w / h);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int opt = 90;
        while (outputStream.toByteArray().length / 1024 < 100) {
            outputStream.reset();
            opt -= 10;
            bitmap.compress(Bitmap.CompressFormat.PNG, opt, outputStream);
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        return bitmap;
    }

}
