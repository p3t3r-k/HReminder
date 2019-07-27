package com.example.hreminder.behindTheScenes;

/**
 * Class for storing the ID of the last logged user to be used for database queries
 */
public class LastUser {
    private static String  lastUserID;


    /**
     * getter
     * @return lastUserID
     */
    public static String getLastUserID() {
        return lastUserID;
    }

    /**
     * setter
     * @param lastUserID ID
     */
    public static void setLastUserID(String lastUserID) {
        LastUser.lastUserID = lastUserID;
    }
}
