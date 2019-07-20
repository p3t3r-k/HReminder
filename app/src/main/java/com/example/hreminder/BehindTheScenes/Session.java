package com.example.hreminder.BehindTheScenes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Context ctx;

    @SuppressLint("CommitPrefEdits")
    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean loggedin) {
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }
}
