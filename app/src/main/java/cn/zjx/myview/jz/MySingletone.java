package cn.zjx.myview.jz;


/**
 * @author zjx on 2018/9/3.
 */
public class MySingletone {

    public enum Singletone {
        INSTANCE;
        private MySingletone singletone;

        Singletone() {
            System.out.println("---------------->>初始化单例");
            singletone = new MySingletone();
        }

        public MySingletone getInstance() {
            return singletone;
        }
    }

    private MySingletone() {
    }

    public static MySingletone getInstance() {
        return Singletone.INSTANCE.getInstance();
    }
}
