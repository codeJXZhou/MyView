package cn.zjx.myview.design.beiwang.beiwang;

/**
 * @author zjx on 2018/7/30.
 */
public class PlayerBeiwang {

    public static void main(String[] args){
        Player player=new Player();
        player.initState();
        System.out.println(player.toString());
        BeiwangCreater creater=new BeiwangCreater();
        creater.setBeiwang(player.saveState());
        player.fight();
        System.out.println(player.toString());
        player.recoveryState(creater.getBeiwang());
        System.out.println(player.toString());
    }
}
