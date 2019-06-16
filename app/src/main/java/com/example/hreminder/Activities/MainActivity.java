package com.example.hreminder.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.Session;
import com.example.hreminder.Database.DbHelper;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.R;

import io.reactivex.Flowable;

public class MainActivity extends BaseActitivty {


    //private static CreateDatabase mDB;

    private EditText getLoginUsername;
    private EditText getLoginPin;

    private String checkLoginUsername;
    private String checkLoginPin;

    private DbHelper db;
    private Session session;
    private ImageView fingerprintView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);
        session = new Session(this);

        if(session.loggedin()){
            startActivity(new Intent(MainActivity.this, CalenderActivity.class));
            finish();
        }
    }

    //private void fetchData() {

    //Flowable loginUser = mDB.reminderDAO().getAllUsers();


    //}

    private void login() {

        getLoginUsername = findViewById(R.id.inputUsername);
        checkLoginUsername = getLoginUsername.getText().toString();

        getLoginPin = findViewById(R.id.inputPin);
        checkLoginPin = getLoginPin.getText().toString();



/*
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

        String userDetails = preferences.getString(checkLoginUsername + checkLoginPin, "incorrect");

        SharedPreferences.Editor editor = preferences.edit();
*/
    }


    public void onClickSwitchToCalender(View view) {

        login();
        //fetchData();

        if (db.getUser(checkLoginUsername, checkLoginPin)) {
            session.setLoggedin(true);
            Intent intent = new Intent(this, CalenderActivity.class);
            startActivity(intent);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.loginError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    public void onClickSwitchToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickPromptFingerprint(View view){
        //Fingerprint Abfrage starten
        Intent intent = new Intent(this,FingerprintPrActivity.class);
        startActivity(intent);
    }


}
