package com.example.hreminder.Local;

import com.example.hreminder.Activities.HReminder;

import java.util.List;

import com.example.hreminder.Database.IDataSource;
import io.reactivex.Flowable;

public class ReminderDataSource implements IDataSource {

    private HReminderDAO remDAO;
    private static ReminderDataSource mInstance;

    public ReminderDataSource(HReminderDAO remDAO) {
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
    public Flowable<List<HReminder>> getAllUsers() {
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
