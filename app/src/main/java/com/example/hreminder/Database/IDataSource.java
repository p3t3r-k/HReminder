package com.example.hreminder.Database;

import com.example.hreminder.Activities.Database;

import java.util.List;

import io.reactivex.Flowable;

public interface IDataSource {

    Flowable<Database> getUserById(int userID);
    Flowable<List<Database>> getAllUsers();
    void insertDB(Database... HReminder);
    void updateDB(Database... HReminder);
    void deleteDB(Database... HReminder);
    void deleteAllDB();

}
