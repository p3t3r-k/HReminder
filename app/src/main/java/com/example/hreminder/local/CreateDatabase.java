package com.example.hreminder.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hreminder.database.HReminder;

import static com.example.hreminder.local.CreateDatabase.DATABASE_VERSION;

@Database(entities = HReminder.class, version = DATABASE_VERSION, exportSchema = false)
public abstract class CreateDatabase extends RoomDatabase {


    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "APP-com.example.hreminder.Database.HReminder-Room";


    private static CreateDatabase mInstance;

    public static CreateDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, CreateDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return mInstance;
    }

    public abstract HReminderDAO reminderDAO();

}
