package com.example.hreminder.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.LastUser;
import com.example.hreminder.Database.DbHelper;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Database.HRepository;
import com.example.hreminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class UserProfilActivity extends BaseActitivty {

    private RadioGroup radioGroupSex;
    private RadioButton radioButtonSex;
    private String sex;
    private EditText dateEdit;
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
    private Calendar myCalendar;

    private String loggedUsername;

    private DbHelper db;

    //Database
    private CompositeDisposable compositeDisposable;
    private HRepository hRepository;

    //Adapter
    ArrayAdapter adapter;
    List<HReminder> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);

        //get Username of logged-in User for Database Query
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loggedUsername = extras.getString("loggedUser");
        } else {
            //kein Extra
        }

        db = new DbHelper(this);


        buildDatePickerDialog();


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

        dateEdit.setOnClickListener(v -> new DatePickerDialog(UserProfilActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }

    private void getUserData() {
        boolean sexFilled;
        boolean smokeFilled;
        boolean allergiesFilled;
        boolean dateFilled;
        boolean weightFilled;
        boolean heightFilled;


        //get sex
        radioGroupSex = findViewById(R.id.radioGroupSex);
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
        dateEdit = findViewById(R.id.dateEdit);
        if (dateEdit.getText().toString().equals("")) {
            dateFilled = false;
        } else {
            birthdateStr = dateEdit.getText().toString();
            dateFilled = true;
        }

        //get weight
        weightEdit = findViewById(R.id.weightEdit);
        if (weightEdit.getText().toString().equals("")) {
            weightFilled = false;
        } else {
            weightS = weightEdit.getText().toString();
            weightFilled = true;
        }

        //get height
        heightEdit = findViewById(R.id.heightEdit);
        if (heightEdit.getText().toString().equals("")) {
            heightFilled = false;
        } else {
            heightS = heightEdit.getText().toString();
            heightFilled = true;
        }

        //get predispositions
        cardiac = findViewById(R.id.cardiac);
        cardiacChecked = cardiac.isChecked();
        if (cardiacChecked) {
            cardiacInt = 1;
        } else {
            cardiacInt = 0;
        }

        neuro = findViewById(R.id.neuro);
        neuroChecked = neuro.isChecked();
        if (neuroChecked) {
            neuroInt = 1;
        } else {
            neuroInt = 0;
        }

        ortho = findViewById(R.id.ortho);
        orthoChecked = ortho.isChecked();
        if (orthoChecked) {
            orthoInt = 1;
        } else {
            orthoInt = 0;
        }

        skin = findViewById(R.id.skin);
        skinChecked = skin.isChecked();
        if (skinChecked) {
            skinInt = 1;
        } else {
            skinInt = 0;
        }

        eye = findViewById(R.id.eye);
        eyeChecked = eye.isChecked();
        if (eyeChecked) {
            eyeInt = 1;
        } else {
            eyeInt = 0;
        }

        hearing = findViewById(R.id.hearing);
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


    public void onClickSwitchToLastApp(View view) {
        getUserData();
        if (allFilled) {


            String idString = LastUser.getLastUserID();
            db.addUserProfile(idString, sex, birthdateStr, weightS, heightS, cardiacInt, neuroInt, orthoInt, skinInt, eyeInt, hearingInt, smokeInt, allergiesInt);

            Toast.makeText(this, db.getUserProfileByID(idString), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, LastAppointmentsActivity.class);
            intent.putExtra("idUser",idString);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfilActivity.this);
            builder.setMessage(R.string.inputError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }


}
