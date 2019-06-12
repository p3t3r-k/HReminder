package com.example.hreminder.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hreminder.Database.HReminder;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HReminderDAO {

    @Query("select * from HReminder where id=:userID")
    Flowable<HReminder> getUserById(int userID);

    @Query("select * from HReminder where username=:username")
    Flowable<HReminder> getUserByName(String username);

    @Query("select * from HReminder")
    Flowable<HReminder> getAllUsers();

    @Insert
    void insertDB(HReminder... HReminder);

    @Update
    void updateDB(HReminder... HReminder);

    @Delete
    void deleteDB(HReminder... HReminder);

    @Query("delete from HReminder")
    void deleteAllDB();


}
