package com.example.hreminder.Database;

import java.util.List;

import io.reactivex.Flowable;

public interface IDataSource {

    Flowable<HReminder> getUserById(int userID);
    Flowable<List<HReminder>> getAllUsers();
    void insertDB(HReminder... HReminder);
    void updateDB(HReminder... HReminder);
    void deleteDB(HReminder... HReminder);
    void deleteAllDB();

}
