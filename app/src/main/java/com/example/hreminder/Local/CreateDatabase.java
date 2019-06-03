package com.example.hreminder.Local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hreminder.Activities.HReminder;

import static com.example.hreminder.Local.CreateDatabase.DATABASE_VERSION;

@Database(entities = HReminder.class, version = DATABASE_VERSION, exportSchema = false)
public abstract class CreateDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "APP-com.example.hreminder.Activities.HReminder-Room";



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
