package cn.zjx.myview.design.beiwang.single;

/**
 * @author zjx on 2018/7/30.
 */
public class Singleton {

    private static Singleton instance = null;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
