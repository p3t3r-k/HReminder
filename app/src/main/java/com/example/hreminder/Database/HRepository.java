package com.example.hreminder.Database;

import com.example.hreminder.Activities.Database;

import java.util.List;

import io.reactivex.Flowable;

public class HRepository implements IDataSource {

    private IDataSource mLocalDataSource;
    private static HRepository mInstance;

    public HRepository(IDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static HRepository getInstance(IDataSource mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new HRepository(mLocalDataSource);
        }
        return mInstance;
    }


    @Override
    public Flowable<Database> getUserById(int userID) {
        return mLocalDataSource.getUserById(userID);
    }

    @Override
    public Flowable<List<Database>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertDB(Database... HReminder) {
        mLocalDataSource.insertDB(HReminder);
    }

    @Override
    public void updateDB(Database... HReminder) {
        mLocalDataSource.updateDB(HReminder);
    }

    @Override
    public void deleteDB(Database... HReminder) {
        mLocalDataSource.deleteDB(HReminder);
    }

    @Override
    public void deleteAllDB() {
        mLocalDataSource.deleteAllDB();
    }
}
