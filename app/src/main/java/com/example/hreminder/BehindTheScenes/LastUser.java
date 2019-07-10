package com.example.hreminder.BehindTheScenes;

public class LastUser {
    public static String  lastUserID;


    public static String getLastUserID() {
        return lastUserID;
    }

    public static void setLastUserID(String lastUserID) {
        LastUser.lastUserID = lastUserID;
    }
}
