package com.example.hreminder.behindTheScenes;

public class LastUser {
    private static String  lastUserID;


    public static String getLastUserID() {
        return lastUserID;
    }

    public static void setLastUserID(String lastUserID) {
        LastUser.lastUserID = lastUserID;
    }
}
