package com.example.hreminder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.room.RoomWarnings;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.R;

import io.reactivex.Flowable;

public class MainActivity extends BaseActitivty {


    private static CreateDatabase mDB;

    private EditText getLoginUsername;
    private EditText getLoginPin;

    private String checkLoginUsername;
    private String checkLoginPin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void fetchData() {

      Flowable loginUser = mDB.reminderDAO().getAllUsers();

    }


    public void onClickSwitchToCalender(View view) {

        checkLogin();
        fetchData();

        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);


        finish();
    }

    public void onClickSwitchToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void checkLogin() {

        getLoginUsername = findViewById(R.id.inputUsername);
        checkLoginUsername = getLoginUsername.getText().toString();

        getLoginPin = findViewById(R.id.inputPin);
        checkLoginPin = getLoginPin.getText().toString();


    }

}
