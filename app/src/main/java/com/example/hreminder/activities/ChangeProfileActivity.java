package com.example.hreminder.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.graphics.Color.parseColor;

/**
 * Activity to let User adapt his/her profil information
 */
public class ChangeProfileActivity extends BaseActivity {

    private String callingActivity = "";
    private Calendar myCalendar;
    private EditText dateEdit;
    private DbHelper db;

    private RadioButton radioButtonSex;
    private String sex;
    private String birthdateStr;
    private EditText weightEdit;
    private String weightS;
    private EditText heightEdit;
    private String heightS;
    private CheckBox cardiac;
    private int cardiacInt;
    private CheckBox neuro;
    private int neuroInt;
    private CheckBox ortho;
    private int orthoInt;
    private CheckBox skin;
    private int skinInt;
    private CheckBox eye;
    private int eyeInt;
    private CheckBox hearing;
    private int hearingInt;
    private RadioButton radioButtonSmoke;
    private int smokeInt;
    private RadioButton radioButtonAllergies;
    private int allergiesInt;
    private boolean allFilled = false;


    /**
     * build Layout, Actionbar, and DatePickerDialog for birthdayInput
     * get instance of DBHelper
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callingActivity = extras.getString("source");
        }

        buildDatePickerDialog();

        db = new DbHelper(this);

    }

    /**
     * get ID of last logged User for database query
     * and input data that is stored in DB
     */
    @Override
    protected void onStart() {
        super.onStart();

        String iDUser = LastUser.getLastUserID();

        //Datenbankabfrage
        Cursor cursorProfile = db.getProfileByID(iDUser);
        getDataFromDB(cursorProfile);

    }

    /**
     * get all Data from Database and display it in Activity
     * @param cursor Cursor with Profile information
     */
    private void getDataFromDB(Cursor cursor) {
        cursor.moveToFirst();

        sex = cursor.getString(0);
        birthdateStr = cursor.getString(1);
        weightS = cursor.getString(2);
        heightS = cursor.getString(3);
        cardiacInt = cursor.getInt(4);
        neuroInt = cursor.getInt(5);
        orthoInt = cursor.getInt(6);
        skinInt = cursor.getInt(7);
        eyeInt = cursor.getInt(8);
        hearingInt = cursor.getInt(9);
        smokeInt = cursor.getInt(10);
        allergiesInt = cursor.getInt(11);

        if (sex.equals(getResources().getString(R.string.maleRadio))) {
            radioButtonSex = findViewById(R.id.maleRadio);
            radioButtonSex.toggle();
        } else {
            radioButtonSex = findViewById(R.id.femaleRadio);
            radioButtonSex.toggle();
        }

        dateEdit.setText(birthdateStr);
        weightEdit = findViewById(R.id.weightEdit);
        weightEdit.setText(weightS);
        heightEdit = findViewById(R.id.heightEdit);
        heightEdit.setText(heightS);

        cardiac = findViewById(R.id.cardiac);
        neuro = findViewById(R.id.neuro);
        ortho = findViewById(R.id.ortho);
        skin = findViewById(R.id.skin);
        eye = findViewById(R.id.eye);
        hearing = findViewById(R.id.hearing);
        cardiac = findViewById(R.id.cardiac);
        if (cardiacInt == 1) {
            cardiac.toggle();
        }
        if (neuroInt == 1) {
            neuro.toggle();
        }
        if (orthoInt == 1) {
            ortho.toggle();
        }
        if (skinInt == 1) {
            skin.toggle();
        }
        if (eyeInt == 1) {
            eye.toggle();
        }
        if (hearingInt == 1) {
            hearing.toggle();
        }
        if (smokeInt == 1) {
            radioButtonSmoke = findViewById(R.id.yesSmoke);
            radioButtonSmoke.toggle();
        } else {
            radioButtonSmoke = findViewById(R.id.noSmoke);
            radioButtonSmoke.toggle();
        }
        if (allergiesInt == 1) {
            radioButtonAllergies = findViewById(R.id.yesAllergies);
            radioButtonAllergies.toggle();
        } else {
            radioButtonAllergies = findViewById(R.id.noAllergies);
            radioButtonAllergies.toggle();
        }
    }

    /**
     * get newly put in data
     */
    private void getUserData() {
        boolean sexFilled;
        boolean smokeFilled;
        boolean allergiesFilled;
        boolean dateFilled;
        boolean weightFilled;
        boolean heightFilled;

        //get sex
        RadioGroup radioGroupSex = findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup
        int selectedId = radioGroupSex.getCheckedRadioButtonId();
        if (selectedId != -1) {
            // find the radiobutton by returned id
            radioButtonSex = findViewById(selectedId);
            if (radioButtonSex.getText().equals(getString(R.string.femaleRadio))) {
                sex = getResources().getString(R.string.femaleRadio);
            } else {
                sex = getResources().getString(R.string.maleRadio);
            }
            sexFilled = true;
        } else {
            sexFilled = false;
        }

        //get birthdate
        if (dateEdit.getText().toString().equals("")) {
            dateFilled = false;
        } else {
            birthdateStr = dateEdit.getText().toString();
            dateFilled = true;
        }

        //get weight
        if (weightEdit.getText().toString().equals("")) {
            weightFilled = false;
        } else {
            weightS = weightEdit.getText().toString();
            weightFilled = true;
        }

        //get height
        if (heightEdit.getText().toString().equals("")) {
            heightFilled = false;
        } else {
            heightS = heightEdit.getText().toString();
            heightFilled = true;
        }

        //get predispositions
        boolean cardiacChecked = cardiac.isChecked();
        if (cardiacChecked) {
            cardiacInt = 1;
        } else {
            cardiacInt = 0;
        }

        boolean neuroChecked = neuro.isChecked();
        if (neuroChecked) {
            neuroInt = 1;
        } else {
            neuroInt = 0;
        }

        boolean orthoChecked = ortho.isChecked();
        if (orthoChecked) {
            orthoInt = 1;
        } else {
            orthoInt = 0;
        }

        boolean skinChecked = skin.isChecked();
        if (skinChecked) {
            skinInt = 1;
        } else {
            skinInt = 0;
        }

        boolean eyeChecked = eye.isChecked();
        if (eyeChecked) {
            eyeInt = 1;
        } else {
            eyeInt = 0;
        }

        boolean hearingChecked = hearing.isChecked();
        if (hearingChecked) {
            hearingInt = 1;
        } else {
            hearingInt = 0;
        }

        //get if smoking
        RadioGroup radioGroupSmoke = findViewById(R.id.radioGroupSmoke);
        // get selected radio button from radioGroup
        int selectedIdSmoke = radioGroupSmoke.getCheckedRadioButtonId();
        if (selectedIdSmoke != -1) {
            // find the radiobutton by returned id
            radioButtonSmoke = findViewById(selectedIdSmoke);
            if (radioButtonSmoke.getText().equals(getString(R.string.yesRadio))) {
                smokeInt = 1;
            } else {
                smokeInt = 0;
            }
            smokeFilled = true;
        } else {
            smokeFilled = false;
        }

        //get if allergies
        RadioGroup radioGroupAllergies = findViewById(R.id.radioGroupAllergies);
        // get selected radio button from radioGroup
        int selectedIdAllergies = radioGroupAllergies.getCheckedRadioButtonId();
        if (selectedIdAllergies != -1) {
            // find the radiobutton by returned id
            radioButtonAllergies = findViewById(selectedIdAllergies);
            if (radioButtonAllergies.getText().equals(getString(R.string.yesRadio))) {
                allergiesInt = 1;
            } else {
                allergiesInt = 0;
            }
            allergiesFilled = true;
        } else {
            allergiesFilled = false;
        }
        allFilled = sexFilled && smokeFilled && allergiesFilled && dateFilled && weightFilled && heightFilled;
    }


    /**
     * create Actionbar
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onClick on Actionbar Buttons
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotToSettings();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Help will come soon.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_home:
                goToHome();
                return true;
            case android.R.id.home:
                if (callingActivity.equals("SettingsActivity")) {
                    NavUtils.navigateUpTo(this, new Intent(this, SettingsActivity.class));
                } else {
                    //zu Calender zurück
                    NavUtils.navigateUpTo(this, new Intent(this, CalenderActivity.class));
                }
                return true;
            case R.id.action_appointments:
                goToAppointments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * DatePicker Dialog
     */
    private void buildDatePickerDialog() {
        myCalendar = Calendar.getInstance();

        myCalendar.set(2000, 1, 1);
        dateEdit = findViewById(R.id.dateEdit);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        dateEdit.setOnClickListener(v -> new DatePickerDialog(ChangeProfileActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    /**
     * set Label of DatePicker
     */
    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * go to SettingsActivity
     */
    private void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    /**
     * go to CalenderActivity
     */
    private void goToHome() {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    /**
     * on Click on Button get Data and if all is filled, update database with new information and go to CalenderActivity
     * if not everything is filled, a AlertDialog appears
     * @param view Button
     */
    public void onClickSwitchToHome(View view) {
        getUserData();
        if (allFilled) {
            String idString = LastUser.getLastUserID();
            db.updateProfile(idString, sex, birthdateStr, weightS, heightS, cardiacInt, neuroInt, orthoInt, skinInt, eyeInt, hearingInt, smokeInt, allergiesInt);

            Intent intent = new Intent(this, CalenderActivity.class);
            intent.putExtra("source", "ChangeProfileActivity");
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
            builder.setMessage(R.string.inputError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * go to LastAppointmentsActivity
     */
    private void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "ChangeProfileActivity");
        startActivity(intent);
    }
}
