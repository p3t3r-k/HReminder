package com.example.hreminder.local;
/*
import com.example.hreminder.database.HReminder;
import com.example.hreminder.database.IDataSource;

import io.reactivex.Flowable;

public class ReminderDataSource implements IDataSource {

    private final HReminderDAO remDAO;
    private static ReminderDataSource mInstance;

    private ReminderDataSource(HReminderDAO remDAO) {
        this.remDAO = remDAO;
    }

    public static ReminderDataSource getInstance(HReminderDAO remDAO) {
        if (mInstance == null) {
            mInstance = new ReminderDataSource(remDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<HReminder> getUserById(int userID) {
        return remDAO.getUserById(userID);
    }

    @Override
    public Flowable<HReminder> getAllUsers() {
        return remDAO.getAllUsers();
    }

    @Override
    public void insertDB(HReminder... HReminder) {
        remDAO.insertDB(HReminder);
    }

    @Override
    public void updateDB(HReminder... HReminder) {
        remDAO.updateDB(HReminder);
    }

    @Override
    public void deleteDB(HReminder... HReminder) {
        remDAO.deleteDB(HReminder);
    }

    @Override
    public void deleteAllDB() {
        remDAO.deleteAllDB();
    }
}
*/