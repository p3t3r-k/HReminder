package com.example.hreminder.Activities;

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

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.LastUser;
import com.example.hreminder.Database.DbHelper;
import com.example.hreminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.graphics.Color.parseColor;

public class ChangeProfileActivity extends BaseActitivty {

    private String callingActivity = "";
    private Calendar myCalendar;
    private EditText dateEdit;
    private String iDUser;
    private DbHelper db;
    private Cursor cursorProfile;

    //Layout
    private RadioGroup radioGroupSex;
    private RadioButton radioButtonSex;
    private String sex;
    // private EditText dateEdit;
    private long dateLong;
    private Date date;
    private String birthdateStr;
    private EditText weightEdit;
    private float weight;
    private String weightS;
    private EditText heightEdit;
    private float height;
    private String heightS;
    private CheckBox cardiac;
    private int cardiacInt;
    private boolean cardiacChecked = false;
    private CheckBox neuro;
    private int neuroInt;
    private boolean neuroChecked = false;
    private CheckBox ortho;
    private int orthoInt;
    private boolean orthoChecked = false;
    private CheckBox skin;
    private int skinInt;
    private boolean skinChecked = false;
    private CheckBox eye;
    private int eyeInt;
    private boolean eyeChecked = false;
    private CheckBox hearing;
    private int hearingInt;
    private boolean hearingChecked = false;
    private RadioGroup radioGroupSmoke;
    private RadioButton radioButtonSmoke;
    private int smokeInt;
    private RadioGroup radioGroupAllergies;
    private RadioButton radioButtonAllergies;
    private int allergiesInt;
    private boolean allFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callingActivity = extras.getString("source");
        } else {
            //kein Extra
        }

        buildDatePickerDialog();

        db = new DbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        iDUser = LastUser.getLastUserID();

        //Datenbankabfrage
        cursorProfile = db.getProfileByID(iDUser);
        insertData(cursorProfile);

    }

    public void insertData(Cursor cursor) {
        cursor.moveToFirst();

        //gender, birthdate, weight, height, heart, neuro, ortho, derma, eyes, ears, smoke, allergy

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

        if (sex.equals("m")) {
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

    private void getUserData() {
        boolean sexFilled;
        boolean smokeFilled;
        boolean allergiesFilled;
        boolean dateFilled;
        boolean weightFilled;
        boolean heightFilled;


        //get sex
        radioGroupSex = findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup
        int selectedId = radioGroupSex.getCheckedRadioButtonId();
        if (selectedId != -1) {
            // find the radiobutton by returned id
            radioButtonSex = findViewById(selectedId);
            if (radioButtonSex.getText().equals(getString(R.string.femaleRadio))) {
                sex = "w";
            } else {
                sex = "m";
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
        cardiacChecked = cardiac.isChecked();
        if (cardiacChecked) {
            cardiacInt = 1;
        } else {
            cardiacInt = 0;
        }

        neuroChecked = neuro.isChecked();
        if (neuroChecked) {
            neuroInt = 1;
        } else {
            neuroInt = 0;
        }

        orthoChecked = ortho.isChecked();
        if (orthoChecked) {
            orthoInt = 1;
        } else {
            orthoInt = 0;
        }

        skinChecked = skin.isChecked();
        if (skinChecked) {
            skinInt = 1;
        } else {
            skinInt = 0;
        }

        eyeChecked = eye.isChecked();
        if (eyeChecked) {
            eyeInt = 1;
        } else {
            eyeInt = 0;
        }

        hearingChecked = hearing.isChecked();
        if (hearingChecked) {
            hearingInt = 1;
        } else {
            hearingInt = 0;
        }

        //get smoking
        radioGroupSmoke = findViewById(R.id.radioGroupSmoke);
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

        //get allergies
        radioGroupAllergies = findViewById(R.id.radioGroupAllergies);
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

        if (sexFilled && smokeFilled && allergiesFilled && dateFilled && weightFilled && heightFilled) {
            allFilled = true;
        } else {
            allFilled = false;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotToSettings();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Help icon is selected", Toast.LENGTH_SHORT).show();
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

    private void buildDatePickerDialog() {
        myCalendar = Calendar.getInstance();

        myCalendar.set(2000, 01, 01);
        dateEdit = findViewById(R.id.dateEdit);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ChangeProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToHome() {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToHome(View view) {
        getUserData();
        if (allFilled) {
            String idString = LastUser.getLastUserID();


            db.updateProfile(idString, sex, birthdateStr, weightS, heightS, cardiacInt, neuroInt, orthoInt, skinInt, eyeInt, hearingInt, smokeInt, allergiesInt);

            Toast.makeText(this, db.getUserProfileByID(idString), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, CalenderActivity.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
            builder.setMessage(R.string.inputError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }



        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "ChangeProfileActivity");
        startActivity(intent);
    }
}
