package com.example.hreminder.database;

import io.reactivex.Flowable;

public interface IDataSource {

    Flowable<HReminder> getUserById(int userID);
    Flowable<HReminder> getAllUsers();
    void insertDB(HReminder... HReminder);
    void updateDB(HReminder... HReminder);
    void deleteDB(HReminder... HReminder);
    void deleteAllDB();

}
