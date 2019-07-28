package com.example.hreminder.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.hreminder.R;
import com.example.hreminder.behindTheScenes.AlarmNotificationReceiver;
import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.graphics.Color.parseColor;

/**
 * CalendarActivity is the main/home screen of App
 * responsible for Reminder-Date-Calculation
 */
public class CalenderActivity extends BaseActivity {

    // private Session session;
    private DbHelper db;
    private String lastUserID;

    private String sex;
    private int cardiacInt;
    private int neuroInt;
    private int orthoInt;
    private int skinInt;
    private int eyeInt;
    private int hearingInt;
    private int smokeInt;
    private int allergiesInt;

    private int age;

    private boolean physYes = false;
    private boolean dentistYes = false;
    private boolean gynYes = false;
    private boolean dermaYes = false;
    private boolean opthalYes = false;
    private boolean entYes = false;
    private boolean cardYes = false;
    private boolean neuYes = false;
    private boolean orthoYes = false;
    private boolean pulYes = false;

    private Date appDphys;
    private Date appDdent;
    private Date appDgyn;
    private Date appDderma;
    private Date appDopthal;
    private Date appDent;
    private Date appDcardio;
    private Date appDneu;
    private Date appDortho;
    private Date appDpul;

    private boolean alarmPhys = false;
    private boolean alarmDent = false;
    private boolean alarmGyn = false;
    private boolean alarmDerma = false;
    private boolean alarmOpthal = false;
    private boolean alarmEnt = false;
    private boolean alarmCardio = false;
    private boolean alarmNeu = false;
    private boolean alarmOrtho = false;
    private boolean alarmPul = false;


    /**
     * set Layout, Actionbar and get instance of DBHelper for database interactions
     * set last logged User ID
     * @param savedInstanceState savedInstances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));

        db = new DbHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lastUserID = extras.getString("idUser");
        }

    }

    /**
     * get ID of last logged user and get relevant information of User for further calculations
     */
    @Override
    protected void onStart() {
        super.onStart();
        lastUserID = db.getLastUserID();
        LastUser.setLastUserID(lastUserID);

        //get Profile Information
        Cursor cursor = db.getProfileByID(lastUserID);
        cursor.moveToFirst();
        sex = cursor.getString(0);
        String birthdateStr = cursor.getString(1);
        cardiacInt = cursor.getInt(4);
        neuroInt = cursor.getInt(5);
        orthoInt = cursor.getInt(6);
        skinInt = cursor.getInt(7);
        eyeInt = cursor.getInt(8);
        hearingInt = cursor.getInt(9);
        smokeInt = cursor.getInt(10);
        allergiesInt = cursor.getInt(11);

        //Calculate Age of User
        @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date birthDate = null;
        try {
            birthDate = sdf.parse(birthdateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new java.util.Date(System.currentTimeMillis());
        if (birthDate != null) {
            age = ((int) ((currentDate.getTime() / (24 * 60 * 60 * 1000) / 365) - (int) (birthDate.getTime() / (24 * 60 * 60 * 1000)) / 365));
        }

        getAppointmentDates();
        determineIntervallForReminder();
        buildTable();
        startAlarm();
    }

    /**
     * release Database
     */
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    /**
     * logic for calculation of the date, for the AlarmManager
     * depending on age, predispositions and the official recommendations for each doctor, the reminder dates are calculated
     */
    private void determineIntervallForReminder() {
        //get current Date
        Date currentDate = new java.util.Date(System.currentTimeMillis());

        //Case Woman
        int jahrZeitraum;
        if (sex.equals(getResources().getString(R.string.femaleRadio))) {
            //Gynäkologe
            if (age > 16 && appDgyn != null) {
                jahrZeitraum = 1;
                //difference in days
                int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDgyn.getTime() / (24 * 60 * 60 * 1000)));
                //more than one year ago
                if (diff >= 365) {
                    //heute erinnern
                    appDgyn = currentDate;
                    gynYes = true;
                } else { //weniger als 1 Jahr her
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(appDgyn);
                    //genau in einem Jahr erinnern
                    cal.add(Calendar.YEAR, jahrZeitraum);
                    appDgyn = cal.getTime();
                    gynYes = true;
                }
            } else if (age > 16) { //kein letzter Termin eingetragen
                appDgyn = currentDate;
                gynYes = true;
            }

        }

        //Case Geschlecht egal

        //Hausarzt
        if (age > 18 && appDphys != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDphys.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDphys = currentDate;
                physYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDphys);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDphys = cal.getTime();
                physYes = true;
            }
        } else if (age > 18) { //kein letzter Termin eingetragen
            appDphys = currentDate;
            physYes = true;
        }

        //Zahnarzt >18
        if (age > 18 && appDdent != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDdent.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDdent = currentDate;
                dentistYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDdent);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDdent = cal.getTime();
                dentistYes = true;
            }
        } else if (age > 18) { //kein letzter Termin eingetragen
            appDdent = currentDate;
            dentistYes = true;
        }
        //Zahnarzt <18
        if (age < 18 && appDdent != null) {
            int monateZeitraum = 6;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDdent.getTime() / (24 * 60 * 60 * 1000)));
            //more than half a year ago
            if (diff >= 182) {
                //heute erinnern
                appDdent = currentDate;
                dentistYes = true;
            } else { //less than half a year ago
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDdent);
                //genau in einem halben Jahr erinnern
                cal.add(Calendar.MONTH, monateZeitraum);
                appDdent = cal.getTime();
                dentistYes = true;
            }
        } else if (age < 18) { //kein letzter Termin eingetragen
            appDdent = currentDate;
            dentistYes = true;
        }

        //Hautarzt - Vorbelastung
        if (skinInt == 1 && appDderma != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDderma.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDderma = currentDate;
                dermaYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDderma);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDderma = cal.getTime();
                dermaYes = true;
            }
        } else if (skinInt == 1) {
            appDderma = currentDate;
            dermaYes = true;
        }

        //Augenarzt 10 < age < 50
        if (age >= 10 && age <= 50 && appDopthal != null && eyeInt != 1) {
            jahrZeitraum = 2;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDopthal.getTime() / (24 * 60 * 60 * 1000)));
            //more than two years ago
            if (diff >= 730) {
                //heute erinnern
                appDopthal = currentDate;
                opthalYes = true;
            } else { //weniger als 2 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDopthal);
                //genau in 2 Jahren erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDopthal = cal.getTime();
                opthalYes = true;
            }
        } else if (age >= 10 && age <= 50 && eyeInt != 1) { //kein letzter Termin eingetragen
            appDopthal = currentDate;
            opthalYes = true;
        }
        //Augenarzt - Vorbelastung
        if (age >= 10 && age <= 50 && appDopthal != null && eyeInt == 1) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDopthal.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDopthal = currentDate;
                opthalYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDopthal);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDopthal = cal.getTime();
                opthalYes = true;
            }
        } else if (age >= 10 && age <= 50 && eyeInt == 1) { //kein letzter Termin eingetragen
            appDopthal = currentDate;
            opthalYes = true;
        }

        //Augenarzt > 50
        if (age >= 50 && appDopthal != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDopthal.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDopthal = currentDate;
                opthalYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDopthal);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDopthal = cal.getTime();
                opthalYes = true;
            }
        } else if (age >= 50) { //kein letzter Termin eingetragen
            appDopthal = currentDate;
            opthalYes = true;
        }


        //HNO Arzt >65
        if (age >= 65 && appDent != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDent.getTime() / (24 * 60 * 60 * 1000)));
            //more than one year ago
            if (diff >= 365) {
                //heute erinnern
                appDent = currentDate;
                entYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDent);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDent = cal.getTime();
                entYes = true;
            }
        } else if (age >= 65) { //kein letzter Termin eingetragen
            appDent = currentDate;
            entYes = true;
        }
        //HNO Arzt - Vorbelastung
        if ((allergiesInt == 1 || hearingInt == 1) && appDent != null) {
            jahrZeitraum = 2;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDent.getTime() / (24 * 60 * 60 * 1000)));
            //more than 2 years ago
            if (diff >= 730) {
                //heute erinnern
                appDent = currentDate;
                entYes = true;
            } else { //weniger als 2 Jahre her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDent);
                //genau in 2 Jahren erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDent = cal.getTime();
                entYes = true;
            }
        } else if (allergiesInt == 1 || hearingInt == 1) { //kein letzter Termin eingetragen
            appDent = currentDate;
            entYes = true;
        }

        //Kardiologe - Vorbelastung
        if (cardiacInt == 1 && appDcardio != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDcardio.getTime() / (24 * 60 * 60 * 1000)));
            //more than 1 year ago
            if (diff >= 365) {
                //heute erinnern
                appDcardio = currentDate;
                cardYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDcardio);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDcardio = cal.getTime();
                cardYes = true;
            }
        } else if (cardiacInt == 1) { //kein letzter Termin eingetragen
            appDcardio = currentDate;
            cardYes = true;
        }

        //Neurologe - Vorbelastung
        if (neuroInt == 1 && appDneu != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDneu.getTime() / (24 * 60 * 60 * 1000)));
            //more than 1 year ago
            if (diff >= 365) {
                //heute erinnern
                appDneu = currentDate;
                neuYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDneu);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDneu = cal.getTime();
                neuYes = true;
            }
        } else if (neuroInt == 1) { //kein letzter Termin eingetragen
            appDneu = currentDate;
            neuYes = true;
        }

        //Orthopäde - Vorbelastung
        if (orthoInt == 1 && appDortho != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDortho.getTime() / (24 * 60 * 60 * 1000)));
            //more than 1 year ago
            if (diff >= 365) {
                //heute erinnern
                appDortho = currentDate;
                orthoYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDortho);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDortho = cal.getTime();
                orthoYes = true;
            }
        } else if (orthoInt == 1) { //kein letzter Termin eingetragen
            appDortho = currentDate;
            orthoYes = true;
        }

        //Lungenfacharzt - Vorbelastung
        if (smokeInt == 1 && appDpul != null) {
            jahrZeitraum = 1;
            //difference in days
            int diff = (int) ((currentDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (appDpul.getTime() / (24 * 60 * 60 * 1000)));
            //more than 1 year ago
            if (diff >= 365) {
                //heute erinnern
                appDpul = currentDate;
                pulYes = true;
            } else { //weniger als 1 Jahr her
                Calendar cal = Calendar.getInstance();
                cal.setTime(appDpul);
                //genau in einem Jahr erinnern
                cal.add(Calendar.YEAR, jahrZeitraum);
                appDpul = cal.getTime();
                pulYes = true;
            }
        } else if (smokeInt == 1) { //kein letzter Termin eingetragen
            appDpul = currentDate;
            pulYes = true;
        }
    }

    /**
     * if an appointment is recommended, method addRow will be executed
     */
    private void buildTable() {
        if (physYes) {
            addRow(getResources().getString(R.string.physician), appDphys);
        }
        if (gynYes) {
            addRow(getResources().getString(R.string.gynaecologist), appDgyn);
        }
        if (dentistYes) {
            addRow(getResources().getString(R.string.dentist), appDdent);
        }
        if (dermaYes) {
            addRow(getResources().getString(R.string.dermatologist), appDderma);
        }
        if (entYes) {
            addRow(getResources().getString(R.string.ENTspecialist), appDent);
        }
        if (opthalYes) {
            addRow(getResources().getString(R.string.ophthalmologist), appDopthal);
        }
        if (cardYes) {
            addRow(getResources().getString(R.string.cardiologist), appDcardio);
        }
        if (neuYes) {
            addRow(getResources().getString(R.string.neurologist), appDneu);
        }
        if (orthoYes){
            addRow(getResources().getString(R.string.orthopedist), appDortho);
        }
        if (pulYes) {
            addRow(getResources().getString(R.string.pulmonologist), appDpul);
        }
    }

    /**
     * Method to add a row to an existing TableLayout
     * @param doc String doctor to be set as Text in TableRow
     * @param nextDate Date nextDate to be set as Text in TableRow
     */
    private void addRow(String doc, Date nextDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateUpcoming = sdf.format(nextDate);

        TableLayout tableLayout = findViewById(R.id.tableLayoutCal);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);

        TextView physician = new TextView(this);
        TextView date = new TextView(this);

        physician.setText(doc);
        physician.setBackgroundResource(R.drawable.border);
        physician.setGravity(Gravity.CENTER);
        physician.setPadding(30, 0, 30, 0);
        physician.setLayoutParams(lp);
        date.setText(dateUpcoming);
        date.setBackgroundResource(R.drawable.border);
        date.setGravity(Gravity.CENTER);
        date.setPadding(30, 0, 30, 0);
        date.setLayoutParams(lp);

        row.addView(physician);
        row.addView(date);
        tableLayout.addView(row);
    }


    /**
     * get lastAppointments, which the user added in LastAppointmentsActivity
     * Strings have to be converted to Dates
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private void getAppointmentDates() {
        @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Cursor cursor = db.getAppointmentsByID(LastUser.getLastUserID());
        for (int i = 1; i <= cursor.getCount(); i++) {

            String doc = cursor.getString(0);
            String lastApp = cursor.getString(1);

            switch (doc) {
                case "Hausarzt":
                case "physician":
                    try {
                        appDphys = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Zahnarzt":
                case "dentist":
                    try {
                        appDdent = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Frauenarzt":
                case "gynaecologist":
                    try {
                        appDgyn = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Hautarzt":
                case "dermatologist":
                    try {
                        appDderma = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Augenarzt":
                case "opthalmologist":
                    try {
                        appDopthal = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "HNO Arzt":
                case "ENT specialist":
                    try {
                        appDent = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Kardiologe":
                case "cardiologist":
                    try {
                        appDcardio = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Neurologe":
                case "neurologist":
                    try {
                        appDneu = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Orthopäde":
                case "orthopedist":
                    try {
                        appDortho = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Lungenarzt":
                case "pulmonologist":
                    try {
                        appDpul = sdf.parse(lastApp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Toast.makeText(this, "Something failed.", Toast.LENGTH_SHORT).show();
            }
            if (cursor.moveToNext()) {
                //go to next
            }
        }
        cursor.close();
    }


    /**
     * start AlarmManager in order to get a notification
     * Dates are converted to long for AlarmManager to work
     * start a notification for every doctor that is needed, when needed
     */
    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");


        //GP-------------------------------------------------------------------------------------------------------------------
        if (!alarmPhys) {
            Intent intentGP = new Intent(this, AlarmNotificationReceiver.class);
            final int _id1 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentGP = PendingIntent.getBroadcast(this, _id1, intentGP, 0);

            long milliseconds1 = 0;
            try {
                Date d = f.parse(String.valueOf(appDphys));
                milliseconds1 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarGP = Calendar.getInstance();
            calendarGP.setTimeInMillis(milliseconds1);
            calendarGP.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarGP.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentGP);
            alarmPhys = true;

        }

        //Gyn-----------------------------------------------------------------------------------------------------------------
        if (!alarmGyn) {
            Intent intentGyn = new Intent(this, AlarmNotificationReceiver.class);
            final int _id2 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentGyn = PendingIntent.getBroadcast(this, _id2, intentGyn, 0);

            long milliseconds2 = 0;
            try {
                Date d = f.parse(String.valueOf(appDgyn));
                milliseconds2 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarGyn = Calendar.getInstance();
            calendarGyn.setTimeInMillis(milliseconds2);
            calendarGyn.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarGyn.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentGyn);
            alarmGyn = true;
        }

        //Dentist--------------------------------------------------------------------------------------------------------------
        if (!alarmDent) {
            Intent intentDent = new Intent(this, AlarmNotificationReceiver.class);
            final int _id3 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentDent = PendingIntent.getBroadcast(this, _id3, intentDent, 0);

            long milliseconds3 = 0;
            try {
                Date d = f.parse(String.valueOf(appDdent));
                milliseconds3 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarDent = Calendar.getInstance();
            calendarDent.setTimeInMillis(milliseconds3);
            calendarDent.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarDent.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentDent);
            alarmDent = true;
        }

        //Derma----------------------------------------------------------------------------------------------------------------
        if (!alarmDerma) {
            Intent intentDerma = new Intent(this, AlarmNotificationReceiver.class);
            final int _id4 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentDerma = PendingIntent.getBroadcast(this, _id4, intentDerma, 0);

            long milliseconds4 = 0;
            try {
                Date d = f.parse(String.valueOf(appDderma));
                milliseconds4 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarDerma = Calendar.getInstance();
            calendarDerma.setTimeInMillis(milliseconds4);
            calendarDerma.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarDerma.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentDerma);
            alarmDerma = true;
        }

        //ENT------------------------------------------------------------------------------------------------------------------
        if (!alarmEnt) {
            Intent intentENT = new Intent(this, AlarmNotificationReceiver.class);
            final int _id5 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentENT = PendingIntent.getBroadcast(this, _id5, intentENT, 0);

            long milliseconds5 = 0;
            try {
                Date d = f.parse(String.valueOf(appDent));
                milliseconds5 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarENT = Calendar.getInstance();
            calendarENT.setTimeInMillis(milliseconds5);
            calendarENT.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarENT.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentENT);
            alarmEnt = true;
        }

        //Eyes-----------------------------------------------------------------------------------------------------------------
        if (!alarmOpthal) {
            Intent intentEyes = new Intent(this, AlarmNotificationReceiver.class);
            final int _id6 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentEyes = PendingIntent.getBroadcast(this, _id6, intentEyes, 0);

            long milliseconds6 = 0;
            try {
                Date d = f.parse(String.valueOf(appDopthal));
                milliseconds6 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarEyes = Calendar.getInstance();
            calendarEyes.setTimeInMillis(milliseconds6);
            calendarEyes.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarEyes.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentEyes);
            alarmOpthal = true;
        }

        //Cardio---------------------------------------------------------------------------------------------------------------
        if (!alarmCardio) {
            Intent intentCardio = new Intent(this, AlarmNotificationReceiver.class);
            final int _id7 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentCardio = PendingIntent.getBroadcast(this, _id7, intentCardio, 0);

            long milliseconds7 = 0;
            try {
                Date d = f.parse(String.valueOf(appDcardio));
                milliseconds7 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarCardio = Calendar.getInstance();
            calendarCardio.setTimeInMillis(milliseconds7);
            calendarCardio.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarCardio.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentCardio);
            alarmCardio = true;
        }

        //Neuro----------------------------------------------------------------------------------------------------------------
        if (!alarmNeu) {
            Intent intentNeuro = new Intent(this, AlarmNotificationReceiver.class);
            final int _id8 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentNeuro = PendingIntent.getBroadcast(this, _id8, intentNeuro, 0);

            long milliseconds8 = 0;
            try {
                Date d = f.parse(String.valueOf(appDneu));
                milliseconds8 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarNeuro = Calendar.getInstance();
            calendarNeuro.setTimeInMillis(milliseconds8);
            calendarNeuro.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarNeuro.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentNeuro);
            alarmNeu = true;
        }

        //Ortho----------------------------------------------------------------------------------------------------------------
        if (!alarmOrtho) {
            Intent intentOrtho = new Intent(this, AlarmNotificationReceiver.class);
            final int _id9 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentOrtho = PendingIntent.getBroadcast(this, _id9, intentOrtho, 0);

            long milliseconds9 = 0;
            try {
                Date d = f.parse(String.valueOf(appDortho));
                milliseconds9 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarOrtho = Calendar.getInstance();
            calendarOrtho.setTimeInMillis(milliseconds9);
            calendarOrtho.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarOrtho.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentOrtho);
            alarmOrtho = true;
        }

        //Pulmo----------------------------------------------------------------------------------------------------------------
        if (!alarmPul) {
            Intent intentPulmo = new Intent(this, AlarmNotificationReceiver.class);
            final int _id10 = (int) System.currentTimeMillis();
            PendingIntent pendingIntentPulmo = PendingIntent.getBroadcast(this, _id10, intentPulmo, 0);

            long milliseconds10 = 0;
            try {
                Date d = f.parse(String.valueOf(appDpul));
                milliseconds10 = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendarPulmo = Calendar.getInstance();
            calendarPulmo.setTimeInMillis(milliseconds10);
            calendarPulmo.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarPulmo.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntentPulmo);
            alarmPul = true;
        }

    }


    /**
     * method to build actionbar
     * @param menu menu
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_calender, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * onClick on different MenuItems go to Activities
     * @param item MenuItem of Actionbar
     * @return boolean true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotToSettings();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Help icon is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_changeProfile:
                goToProfile();
                return true;
            case R.id.action_map:
                goToMap();
                return true;
            case R.id.action_appointments:
                goToAppointments();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * go to CalenderActivity
     */
    private void goToProfile() {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    /**
     * go to SettingsActivity
     */
    private void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    /**
     * go to DoctorsMapActivity
     */
    private void goToMap() {
        Intent intent = new Intent(this, DoctorsMapActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    /**
     * go to LastAppointmentsActivity
     */
    private void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "CalenderActivity");
        //  intent.putExtra("idUser",loggedUserID);
        startActivity(intent);
    }

}


