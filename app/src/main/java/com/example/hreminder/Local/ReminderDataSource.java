package com.example.hreminder.Local;

import com.example.hreminder.Activities.Database;

import java.util.List;

import com.example.hreminder.Activities.Database.IDataSource;
import io.reactivex.Flowable;

public class ReminderDataSource implements IDataSource {

    private ReminderDAO remDAO;
    private static ReminderDataSource mInstance;

    public ReminderDataSource(ReminderDAO remDAO) {
        this.remDAO = remDAO;
    }

    public static ReminderDataSource getInstance(ReminderDAO remDAO) {
        if (mInstance == null) {
            mInstance = new ReminderDataSource(remDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<Database> getUserById(int userID) {
        return remDAO.getUserById(userID);
    }

    @Override
    public Flowable<List<Database>> getAllUsers() {
        return remDAO.getAllUsers();
    }

    @Override
    public void insertDB(Database... HReminder) {
        remDAO.insertDB(HReminder);
    }

    @Override
    public void updateDB(Database... HReminder) {
        remDAO.updateDB(HReminder);
    }

    @Override
    public void deleteDB(Database... HReminder) {
        remDAO.deleteDB(HReminder);
    }

    @Override
    public void deleteAllDB() {
        remDAO.deleteAllDB();
    }
}
