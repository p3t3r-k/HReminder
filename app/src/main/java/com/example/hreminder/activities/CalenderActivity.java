package com.example.hreminder.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.util.Objects;

import static android.graphics.Color.parseColor;

public class CalenderActivity extends BaseActivity {

   // private Session session;
    private DbHelper db;
    private String lastUserID;

    // private String loggedUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));

     /*   session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        */

        db = new DbHelper(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
               lastUserID = extras.getString("idUser");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        lastUserID = db.getLastUserID();
        LastUser.setLastUserID(lastUserID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_calender, menu);
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

    private void goToProfile() {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    private void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    private void goToMap() {
        Intent intent = new Intent(this, DoctorsMapActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    private void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "CalenderActivity");
        //  intent.putExtra("idUser",loggedUserID);
        startActivity(intent);
    }

  /*  private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(CalenderActivity.this, MainActivity.class));
    } */

}

