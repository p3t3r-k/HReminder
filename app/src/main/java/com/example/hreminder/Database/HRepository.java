package com.example.hreminder.Database;

import io.reactivex.Flowable;

public class HRepository implements IDataSource {

    private final IDataSource mLocalDataSource;
    private static HRepository mInstance;

    private HRepository(IDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static HRepository getInstance(IDataSource mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new HRepository(mLocalDataSource);
        }
        return mInstance;
    }


    @Override
    public Flowable<HReminder> getUserById(int userID) {
        return mLocalDataSource.getUserById(userID);
    }

    @Override
    public Flowable<HReminder> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertDB(HReminder... HReminder) {
        mLocalDataSource.insertDB(HReminder);
    }

    @Override
    public void updateDB(HReminder... HReminder) {
        mLocalDataSource.updateDB(HReminder);
    }

    @Override
    public void deleteDB(HReminder... HReminder) {
        mLocalDataSource.deleteDB(HReminder);
    }

    @Override
    public void deleteAllDB() {
        mLocalDataSource.deleteAllDB();
    }
}
