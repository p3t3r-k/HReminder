package com.example.hreminder.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hreminder.Activities.HReminder;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HReminderDAO {

    @Query("select * from HReminder where id=:userID")
    Flowable<HReminder> getUserById(int userID);

    @Query("select * from HReminder")
    Flowable<List<HReminder>> getAllUsers();

    @Insert
    void insertDB(HReminder... HReminder);

    @Update
    void updateDB(HReminder... HReminder);

    @Delete
    void deleteDB(HReminder... HReminder);

    @Query("delete from HReminder")
    void deleteAllDB();


}
