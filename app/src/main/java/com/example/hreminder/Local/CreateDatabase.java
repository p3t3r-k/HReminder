package com.example.hreminder.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static com.example.hreminder.Local.CreateDatabase.DATABASE_VERSION;

@Database(entities = Database.class, version = DATABASE_VERSION)
public abstract class CreateDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "APP-com.example.hreminder.Activities.Database-Room";



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
