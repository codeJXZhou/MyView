package cn.zjx.myview.jz;

import android.os.AsyncTask;

import cn.zjx.myview.design.beiwang.single.Singleton;

/**
 * @author zjx on 2018/8/28.
 */
public class SingleTone {
    private static volatile Singleton instance;

    public Singleton getInstance() {
        Singleton inst = instance;
        if (inst == null) {
            synchronized (Singleton.class) {
                inst = instance;
                if (inst == null) {
                    inst = new Singleton();
                    instance = inst;
                }
            }
        }
        return inst;
    }

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
