package cn.zjx.myview.design.beiwang.beiwang;

/**
 * @author zjx on 2018/7/30.
 */
public class Beiwang {
    private int vit;
    private int atk;
    private int def;

    public Beiwang(int vit, int atk, int def) {
        this.vit = vit;
        this.atk = atk;
        this.def = def;
    }

    public int getVit() {
        return vit;
    }

    public void setVit(int vit) {
        this.vit = vit;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
