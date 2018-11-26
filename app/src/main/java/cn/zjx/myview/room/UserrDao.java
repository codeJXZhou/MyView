package cn.zjx.myview.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author zjx on 2018/8/28.
 */
@Dao
public interface UserrDao {
    @Query("SELECT * FROM rooms")
    List<Userr> getAll();

    @Insert
    void insertAll(Userr... users);

    @Delete
    void delete(Userr user);

    @Update
    void update(Userr user);

    @Query("DELETE FROM rooms")
    void deleteAll();


}
