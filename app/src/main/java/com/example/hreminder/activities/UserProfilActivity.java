package com.example.hreminder.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.R;
import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserProfilActivity extends BaseActivity {

    private String sex;
    private EditText dateEdit;
    private String birthdateStr;
    private String weightS;
    private String heightS;
    private int cardiacInt;
    private int neuroInt;
    private int orthoInt;
    private int skinInt;
    private int eyeInt;
    private int hearingInt;
    private int smokeInt;
    private int allergiesInt;
    private boolean allFilled = false;
    private Calendar myCalendar;

    private DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);

        db = new DbHelper(this);


        buildDatePickerDialog();


    }

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
        RadioGroup radioGroupSex = findViewById(R.id.radioGroupSex);
        // get selected radio button from radioGroup
        int selectedId = radioGroupSex.getCheckedRadioButtonId();
        if (selectedId != -1) {
            // find the radiobutton by returned id
            RadioButton radioButtonSex = findViewById(selectedId);
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
        dateEdit = findViewById(R.id.dateEdit);
        if (dateEdit.getText().toString().equals("")) {
            dateFilled = false;
        } else {
            birthdateStr = dateEdit.getText().toString();
            dateFilled = true;
        }

        //get weight
        EditText weightEdit = findViewById(R.id.weightEdit);
        if (weightEdit.getText().toString().equals("")) {
            weightFilled = false;
        } else {
            weightS = weightEdit.getText().toString();
            weightFilled = true;
        }

        //get height
        EditText heightEdit = findViewById(R.id.heightEdit);
        if (heightEdit.getText().toString().equals("")) {
            heightFilled = false;
        } else {
            heightS = heightEdit.getText().toString();
            heightFilled = true;
        }

        //get predispositions
        CheckBox cardiac = findViewById(R.id.cardiac);
        boolean cardiacChecked = cardiac.isChecked();
        if (cardiacChecked) {
            cardiacInt = 1;
        } else {
            cardiacInt = 0;
        }

        CheckBox neuro = findViewById(R.id.neuro);
        boolean neuroChecked = neuro.isChecked();
        if (neuroChecked) {
            neuroInt = 1;
        } else {
            neuroInt = 0;
        }

        CheckBox ortho = findViewById(R.id.ortho);
        boolean orthoChecked = ortho.isChecked();
        if (orthoChecked) {
            orthoInt = 1;
        } else {
            orthoInt = 0;
        }

        CheckBox skin = findViewById(R.id.skin);
        boolean skinChecked = skin.isChecked();
        if (skinChecked) {
            skinInt = 1;
        } else {
            skinInt = 0;
        }

        CheckBox eye = findViewById(R.id.eye);
        boolean eyeChecked = eye.isChecked();
        if (eyeChecked) {
            eyeInt = 1;
        } else {
            eyeInt = 0;
        }

        CheckBox hearing = findViewById(R.id.hearing);
        boolean hearingChecked = hearing.isChecked();
        if (hearingChecked) {
            hearingInt = 1;
        } else {
            hearingInt = 0;
        }

        //get smoking
        RadioGroup radioGroupSmoke = findViewById(R.id.radioGroupSmoke);
        // get selected radio button from radioGroup
        int selectedIdSmoke = radioGroupSmoke.getCheckedRadioButtonId();
        if (selectedIdSmoke != -1) {
            // find the radiobutton by returned id
            RadioButton radioButtonSmoke = findViewById(selectedIdSmoke);
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
        RadioGroup radioGroupAllergies = findViewById(R.id.radioGroupAllergies);
        // get selected radio button from radioGroup
        int selectedIdAllergies = radioGroupAllergies.getCheckedRadioButtonId();
        if (selectedIdAllergies != -1) {
            // find the radiobutton by returned id
            RadioButton radioButtonAllergies = findViewById(selectedIdAllergies);
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


    public void onClickSwitchToLastApp(View view) {
        getUserData();
        if (allFilled) {


            String idString = LastUser.getLastUserID();
            db.addUserProfile(idString, sex, birthdateStr, weightS, heightS, cardiacInt, neuroInt, orthoInt, skinInt, eyeInt, hearingInt, smokeInt, allergiesInt);

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
