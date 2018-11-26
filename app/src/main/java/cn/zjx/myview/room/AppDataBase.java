package cn.zjx.myview.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @author zjx on 2018/8/28.
 */
@Database(entities = {Userr.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserrDao roomDao();

}
