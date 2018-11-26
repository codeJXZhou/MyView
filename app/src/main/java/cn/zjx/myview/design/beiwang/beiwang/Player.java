package cn.zjx.myview.design.beiwang.beiwang;

/**
 * @author zjx on 2018/7/30.
 */
public class Player {
    private int vit;
    private int atk;
    private int def;

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

    public void initState(){
        this.vit=100;
        this.atk=100;
        this.def=100;
    }

    public void fight(){
        this.vit=0;
        this.atk=0;
        this.def=0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "vit=" + vit +
                ", atk=" + atk +
                ", def=" + def +
                '}';
    }

    public Beiwang saveState(){
        return new Beiwang(vit,atk,def);
    }

    public void  recoveryState(Beiwang player){
       this.vit=player.getVit();
       this.atk=player.getAtk();
       this.def=player.getDef();
    }

}
