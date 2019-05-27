package com.example.hreminder.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hreminder.Activities.Database;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface HReminderDAO {

    @Query("select * from HReminder where id=:userID")
    Flowable<Database> getUserById(int userID);

    @Query("select * from HReminder")
    Flowable<List<Database>> getAllUsers();

    @Insert
    void insertDB(Database... HReminder);

    @Update
    void updateDB(Database... HReminder);

    @Delete
    void deleteDB(Database... HReminder);

    @Query("delete from HReminder")
    void deleteAllDB();


}
