package com.example.hreminder.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Database.HRepository;
import com.example.hreminder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserProfilActivity extends BaseActitivty {

    private RadioGroup radioGroupSex;
    private RadioButton radioButtonSex;
    private String sex;
    private EditText dateEdit;
    private long dateLong;
    private EditText weightEdit;
    private float weight;
    private EditText heightEdit;
    private float height;
    private CheckBox cardiac;
    private boolean cardiacChecked = false;
    private CheckBox neuro;
    private boolean neuroChecked = false;
    private CheckBox ortho;
    private boolean orthoChecked = false;
    private CheckBox skin;
    private boolean skinChecked = false;
    private CheckBox eye;
    private boolean eyeChecked = false;
    private CheckBox hearing;
    private boolean hearingChecked = false;
    private RadioGroup radioGroupSmoke;
    private RadioButton radioButtonSmoke;
    private boolean smoke;
    private RadioGroup radioGroupAllergies;
    private RadioButton radioButtonAllergies;
    private boolean allergies;
    private boolean allFilled = false;
    private Calendar myCalendar;


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


        //Init
        compositeDisposable = new CompositeDisposable();

        //load all data from database
        //  loadData();

        buildDatePickerDialog();



    }

    private void buildDatePickerDialog(){
        myCalendar = Calendar.getInstance();

        myCalendar.set(2000,01,01);
        dateEdit= findViewById(R.id.dateEdit);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfilActivity.this, date, myCalendar
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

    private void loadData() {
        //Use RxJava
        Disposable disposable = hRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onGetAllUserSuccess, throwable -> Toast.makeText(UserProfilActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show());
        compositeDisposable.add(disposable);
    }

    private void onGetAllUserSuccess(List<HReminder> hReminders) {
        list.clear();
        list.addAll(hReminders);
        adapter.notifyDataSetChanged();
    }

    private void getUserData() {
        boolean sexFilled = false;
        boolean smokeFilled = false;
        boolean allergiesFilled = false;
        boolean dateFilled = false;
        boolean weightFilled = false;
        boolean heightFilled = false;


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
            } sexFilled = true;
        } else {
            sexFilled = false;
        }

        //get birthdate
        dateEdit = findViewById(R.id.dateEdit);
        if (dateEdit.getText().toString().equals("")){
            dateFilled = false;
        } else {
            String dateText = dateEdit.getText().toString();
            String myFormat = "dd.MM.yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);
            Date date = null;
            try {
                date = sdf.parse(dateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateLong = date.getTime();
            dateFilled = true;
        }

        //get weight
        weightEdit = findViewById(R.id.weightEdit);
        if (weightEdit.getText().toString().equals("")){
            weightFilled = false;
        } else {
            String weightS = weightEdit.getText().toString();
            weight = Float.parseFloat(weightS);
            weightFilled =true;
        }

        //get height
        heightEdit = findViewById(R.id.heightEdit);
        if (heightEdit.getText().toString().equals("")){
            heightFilled = false;
        } else {
            String heightS = heightEdit.getText().toString();
            height = Float.parseFloat(heightS);
            heightFilled = true;
        }

        //get predispositions
        cardiac = findViewById(R.id.cardiac);
        cardiacChecked = cardiac.isChecked();

        neuro = findViewById(R.id.neuro);
        neuroChecked = neuro.isChecked();

        ortho = findViewById(R.id.ortho);
        orthoChecked = ortho.isChecked();

        skin = findViewById(R.id.skin);
        skinChecked = skin.isChecked();

        eye = findViewById(R.id.eye);
        eyeChecked = eye.isChecked();

        hearing = findViewById(R.id.hearing);
        hearingChecked = hearing.isChecked();

        //get smoking
        radioGroupSmoke = findViewById(R.id.radioGroupSmoke);
        // get selected radio button from radioGroup
        int selectedIdSmoke = radioGroupSmoke.getCheckedRadioButtonId();
        if (selectedIdSmoke != -1) {
            // find the radiobutton by returned id
            radioButtonSmoke = findViewById(selectedIdSmoke);
            if (radioButtonSmoke.getText().equals(getString(R.string.yesRadio))) {
                smoke = true;
            } else {
                smoke = false;
            } smokeFilled = true;
        } else {
            smokeFilled= false;
        }

        //get allergies
        radioGroupAllergies = findViewById(R.id.radioGroupAllergies);
        // get selected radio button from radioGroup
        int selectedIdAllergies = radioGroupAllergies.getCheckedRadioButtonId();
        if (selectedIdAllergies != -1) {
            // find the radiobutton by returned id
            radioButtonAllergies = findViewById(selectedIdAllergies);
            if (radioButtonAllergies.getText().equals(getString(R.string.yesRadio))) {
                allergies = true;
            } else {
                allergies = false;
            } allergiesFilled = true;
        } else {
            allergiesFilled = false;
        }

        if (sexFilled && smokeFilled && allergiesFilled && dateFilled && weightFilled && heightFilled){
            allFilled = true;
        } else {
            allFilled =false;
        }

    }

    /*

    private void saveData() {


        Disposable disposable = Observable.create(emitter -> {
            //getUserData();
            HReminder hReminder = new HReminder(getcreateUsername, getcreatePin, false, "w", 10 - 10 - 2000, 123, false, false, false, false, false, false, false, false);

            hRepository.insertDB(hReminder);
            emitter.onComplete();
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer) o -> Toast.makeText(RegisterActivity.this, "user added", Toast.LENGTH_LONG).show(), throwable -> Toast.makeText(RegisterActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show(), () -> loadData()

                );

    }*/

    public void onClickSwitchToLastApp(View view) {
        getUserData();
        if (allFilled){


            Intent intent = new Intent(this, LastAppointmentsActivity.class);
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
