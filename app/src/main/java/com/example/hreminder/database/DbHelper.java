package com.example.hreminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper Class
 */
public class DbHelper extends SQLiteOpenHelper {

    //public static final String TAG = DbHelper.class.getSimpleName();
    private static final String DB_NAME = "login.db";
    private static final int DB_VERSION = 1;


    private long id_PK;

    /**
     * User Table - Login
     */
    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Username";
    private static final String COLUMN_PIN = "PIN";

    /**
     *UserProfile Table
     */
    private static final String USERPROFILE_TABLE = "profiles";
    private static final String COLUMN_ID_Pr = "_id";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_HEART = "heart";
    private static final String COLUMN_NEURO = "neuro";
    private static final String COLUMN_ORTHO = "ortho";
    private static final String COLUMN_DERMA = "derma";
    private static final String COLUMN_EYES = "eyes";
    private static final String COLUMN_EARS = "ears";
    private static final String COLUMN_SMOKE = "smoke";
    private static final String COLUMN_ALLERGY = "allergy";

    /**
     * Appointments Table
     */
    private static final String APPOINTMENTS_TABLE = "appointments";
    private static final String COLUMN_ID_AP = "_id";
    private static final String COLUMN_ID_User = "_id_user";
    private static final String COLUMN_DOC = "physician";
    private static final String COLUMN_DATE = "lastAppoint";

    /**
     * Log Table
     */
    private static final String LOG_TABLE = "logs";
    private static final String COLUMN_ID_LOG = "_id";
    private static final String COLUMN_LASTUSER_ID = "lastUserID";

    /**
     * String Create Table Users
     */
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT ,"
            + COLUMN_PIN + " TEXT);";

    /**
     * String Create Table Userprofile
     */
    private static final String CREATE_TABLE_USERPROFILE = "CREATE TABLE " + USERPROFILE_TABLE + "("
            + COLUMN_ID_Pr + " INTEGER PRIMARY KEY,"
            + COLUMN_GENDER + " TEXT ,"
            + COLUMN_BIRTHDATE + " TEXT ,"
            + COLUMN_WEIGHT + " REAL ,"
            + COLUMN_HEIGHT + " REAL ,"
            + COLUMN_HEART + " INTEGER ,"
            + COLUMN_NEURO + " INTEGER ,"
            + COLUMN_ORTHO + " INTEGER ,"
            + COLUMN_DERMA + " INTEGER ,"
            + COLUMN_EYES + " INTEGER ,"
            + COLUMN_EARS + " INTEGER ,"
            + COLUMN_SMOKE + " INTEGER ,"
            + COLUMN_ALLERGY + " INTEGER);";

    /**
     * String Create Table Appointments
     */
    private static final String CREATE_TABLE_APPOINTMENTS = "CREATE TABLE " + APPOINTMENTS_TABLE + "("
            + COLUMN_ID_AP + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID_User + " INTEGER ,"
            + COLUMN_DOC + " TEXT ,"
            + COLUMN_DATE + " TEXT" +
            ");";

    /**
     * String Create Table Logs
     */
    private static final String CREATE_TABLE_LOG = "CREATE TABLE " + LOG_TABLE + "("
            + COLUMN_ID_LOG + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_LASTUSER_ID + " TEXT);";

    /**
     * constructor
     * @param context Context
     */
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * create all Tables
     * @param db Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_USERPROFILE);
        db.execSQL(CREATE_TABLE_APPOINTMENTS);
        db.execSQL(CREATE_TABLE_LOG);
    }

    /**
     * on Upgrade drop existing table and create new tables
     * @param db Database
     * @param oldVersion oldV
     * @param newVersion newV
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USERPROFILE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOG_TABLE);
        onCreate(db);
    }


    //USER_TABLE ACTIONS

    /**
     * Storing user details (username,pin) in database
     * @param username Username
     * @param pin PIN
     */
    public void addUser(String username, String pin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PIN, pin);
        values.put(COLUMN_NAME, username);


        id_PK = db.insert(USER_TABLE, null, values);
        db.close();
    }

    /**
     * get user
     * @param username Username
     * @param pin PIM
     * @return boolean
     */
    public boolean getUser(String username, String pin) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_NAME + " = " + "'" + username + "'" + " and " + COLUMN_PIN + " = " + "'" + pin + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * check PIN with ID
     * @param id UserID
     * @param pin PIN
     * @return boolean
     */
    public boolean checkPinByID(String id, String pin) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_ID + " = " + "'" + id + "'" + " and " + COLUMN_PIN + " = " + "'" + pin + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * check if Any User is registered
     * @return boolean
     */
    public boolean getAnyUser() {
        String selectQuery = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    /**
     * get USser ID with username
     * @param username Username
     * @return ID or -1 (no user like that)
     */
    public long getUserIDByName(String username) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_NAME + " = " + "'" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            id_PK = cursor.getInt(0);
            cursor.close();
            db.close();
            return id_PK;
        }
        cursor.close();
        db.close();

        return -1;
    }

    /**
     * get Username with ID
     * @param id ID
     * @return Username or null (no user like that)
     */
    public String getUsernameByID(String id) {
        String selectQuery = "select " + COLUMN_NAME + " from  " + USER_TABLE + " where " +
                COLUMN_ID + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String username = cursor.getString(0);
            cursor.close();
            db.close();
            return username;
        }
        cursor.close();
        db.close();

        return null;
    }

    /**
     * update existing PIN
     * @param id ID
     * @param pin new PIN
     * @return boolean
     */
    public boolean updatePIN(String id, String pin) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();

        args.put(COLUMN_ID, id);
        args.put(COLUMN_PIN, pin);

        return db.update(USER_TABLE, args, COLUMN_ID + "=" + id, null) > 0;
    }

    //USERPROFILE_TABLE ACTIONS

    /**
     * store userprofile information
     * @param id ID
     * @param gender sex
     * @param birthdate birthdate
     * @param weight weight
     * @param height height
     * @param heart cardiac predisposition
     * @param neuro neurological predisposition
     * @param ortho orthopedic predisposition
     * @param derma skin predisposition
     * @param eyes eye predisposition
     * @param ears hearing impairment
     * @param smoke smoker
     * @param allergy allergy issues
     */
    public void addUserProfile(String id, String gender, String birthdate, String weight, String height, int heart, int neuro, int ortho, int derma,
                               int eyes, int ears, int smoke, int allergy) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_Pr, id);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_BIRTHDATE, birthdate);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_HEART, heart);
        values.put(COLUMN_NEURO, neuro);
        values.put(COLUMN_ORTHO, ortho);
        values.put(COLUMN_DERMA, derma);
        values.put(COLUMN_EYES, eyes);
        values.put(COLUMN_EARS, ears);
        values.put(COLUMN_SMOKE, smoke);
        values.put(COLUMN_ALLERGY, allergy);

        db.insert(USERPROFILE_TABLE, null, values);
        db.close();
    }

    /**
     * get profile info
     * @param id ID
     * @return Cursor with profile information
     */
    public Cursor getProfileByID(String id) {
        String selectQuery = "SELECT gender,birthdate,weight,height,heart,neuro,ortho,derma,eyes,ears,smoke,allergy FROM " + USERPROFILE_TABLE + " where " +
                COLUMN_ID_Pr + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(selectQuery, null);
    }

    /**
     * check if profile exists by ID
     * @param id ID
     * @return boolean
     */
    public boolean checkIfProfileExists(String id){
        String selectQuery = "SELECT * FROM " + USERPROFILE_TABLE + " where " +
                COLUMN_ID_Pr + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    /**
     * update profile information
     *  @param id ID
     *  @param gender sex
     *  @param birthdate birthdate
     *  @param weight weight
     *  @param height height
     *  @param heart cardiac predisposition
     *  @param neuro neurological predisposition
     *  @param ortho orthopedic predisposition
     *  @param derma skin predisposition
     *  @param eyes eye predisposition
     *  @param ears hearing impairment
     *  @param smoke smoker
     *  @param allergy allergy issues
     */
    public void updateProfile(String id, String gender, String birthdate, String weight, String height, int heart, int neuro, int ortho, int derma,
                              int eyes, int ears, int smoke, int allergy) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();

        args.put(COLUMN_ID_Pr, id);
        args.put(COLUMN_GENDER, gender);
        args.put(COLUMN_BIRTHDATE, birthdate);
        args.put(COLUMN_WEIGHT, weight);
        args.put(COLUMN_HEIGHT, height);
        args.put(COLUMN_HEART, heart);
        args.put(COLUMN_NEURO, neuro);
        args.put(COLUMN_ORTHO, ortho);
        args.put(COLUMN_DERMA, derma);
        args.put(COLUMN_EYES, eyes);
        args.put(COLUMN_EARS, ears);
        args.put(COLUMN_SMOKE, smoke);
        args.put(COLUMN_ALLERGY, allergy);

        db.update(USERPROFILE_TABLE, args, COLUMN_ID_Pr + "=" + id, null);
    }

    //APPOINTMENTS_TABLE ACTIONS

    /**
     * store new last appointment
     * @param id ID
     * @param physician doctor
     * @param date date of last appointment
     */
    public void addAppointment(String id, String physician, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_User, id);
        values.put(COLUMN_DOC, physician);
        values.put(COLUMN_DATE, date);

        db.insert(APPOINTMENTS_TABLE, null, values);
        db.close();
    }

    /**
     * check if any appointments exist with ID
     * @param id ID
     * @return boolean
     */
    public boolean checkIfAppointmentsExistByID(String id) {
        String selectQuery = "SELECT * FROM " + APPOINTMENTS_TABLE + " where " +
                COLUMN_ID_User + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    /**
     * get doc and date of last appointment by ID
     * @param id ID
     * @return Cursor with doc, lastAppointment
     */
    public Cursor getAppointmentsByID(String id) {
        String selectQuery = "SELECT physician, lastAppoint FROM " + APPOINTMENTS_TABLE + " where " +
                COLUMN_ID_User + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * get doctor by ID
     * @param id ID
     * @return Cursor with doc
     */
    public Cursor getDocByID(String id) {
        String selectQuery = "SELECT physician FROM " + APPOINTMENTS_TABLE + " where " +
                COLUMN_ID_User + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * get date by ID
     * @param id ID
     * @return Cursor with date
     */
    public Cursor getDateByID(String id) {
        String selectQuery = "SELECT lastAppoint FROM " + APPOINTMENTS_TABLE + " where " +
                COLUMN_ID_User + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * delete specific appointment by ID
     * @param idUser ID
     * @param doc doctor
     * @param date Appointment date
     * @return boolean
     */
    public boolean deleteAppByID(String idUser, String doc, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_ID_User + " = " + idUser + " AND " + COLUMN_DOC + " = '" + doc + "' AND " + COLUMN_DATE + " = '" + date + "'";

        return db.delete(APPOINTMENTS_TABLE, where, null) > 0;

    }

    //LOG TABLE ACTIONS

    /**
     * store last logged in User
     * @param lastUserID lastUserID
     */
    public void setLastUserID(String lastUserID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LASTUSER_ID, lastUserID);

        db.insert(LOG_TABLE, null, values);
        db.close();
    }

    /**
     * get last logged user
     * @return ID of user
     */
    public String getLastUserID() {
        String selectQuery = "SELECT * FROM " + LOG_TABLE + " ORDER BY " +
                COLUMN_ID_LOG + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String lastUserID = cursor.getString(1);
        cursor.close();
        db.close();
        return lastUserID;
    }

    /**
     * check if a log "file" exists
     * @return boolean
     */
    public boolean checkIfLogExists() {
        String selectQuery = "SELECT * FROM " + LOG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * drop Users, Userprofile, Appointments and Log Table
     * @param id ID
     */
    public  void dropUserProfile(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERPROFILE_TABLE, COLUMN_ID_Pr + "=" + id, null);
        db.delete(USER_TABLE,COLUMN_ID + "="+id,null);
        db.delete(APPOINTMENTS_TABLE,COLUMN_ID_User + "="+id,null);
        db.delete(LOG_TABLE,null,null);
    }


}